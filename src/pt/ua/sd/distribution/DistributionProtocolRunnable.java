/**
 * 
 */
package pt.ua.sd.distribution;

import java.awt.Point;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.boat.ICompleteBoat;
import pt.ua.sd.boat.MBoat;
import pt.ua.sd.boat.TBoat;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.diroper.ICompleteDirOper;
import pt.ua.sd.diroper.MDirOper;
import pt.ua.sd.diroper.TDirOper;
import pt.ua.sd.distribution.DistributionMessage.MESSAGE_TYPE;
import pt.ua.sd.log.ILogger;
import pt.ua.sd.log.MLog;
import pt.ua.sd.log.TLogFlusher;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.ocean.ICompleteOcean;
import pt.ua.sd.ocean.MOcean;
import pt.ua.sd.shoal.ICompleteShoal;
import pt.ua.sd.shoal.MShoal;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;
import pt.ua.sd.shoal.TShoal;

/**
 * The Distribution message handler at the server side.
 * 
 * For each message that it receives, it tries to launch an entity that's been
 * assigned to it. This is where lies the guarantee that entities start orderly!
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DistributionProtocolRunnable implements IProtocolRunnable {

	protected Socket socket;
	protected Acknowledge ack = new Acknowledge();

	public DistributionProtocolRunnable() {
	}

	public DistributionProtocolRunnable(Socket s) {
		this.socket = s;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		assert socket != null;

		try {
			String thisMachine = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Detected ip address for this server: " + thisMachine);

			// fetch the dsitribution message
			DistributionMessage distributionMessage = (DistributionMessage) ((IProtocolMessage) ProtocolEndPoint
					.getMessageObject(socket)).getMessage();

			// if it's a start message, start the needed service
			if (distributionMessage.getMsgType() == MESSAGE_TYPE.Start) {
				StartMessage msg = (StartMessage) distributionMessage;
				DistributionConfig config = msg.getConfig();
				Registry registry = null;
				InetSocketAddress rmiAddr = null;
				try {
					rmiAddr = msg.getRmiRegistryAddress();
					registry = LocateRegistry.getRegistry(rmiAddr.getAddress().getHostName(), rmiAddr.getPort());
				} catch (RemoteException e) {
					// do nothing with this exception, it just means the
					// registry could not be created, and that means that this
					// message is to create it for the first time, probably!
				}

				switch (msg.getEntity()) {

				case RmiServer: { // We'll use these brackets here just to avoid
									// scope-name collisions
					new RmiServer(msg.getRmiRegistryAddress().getPort());
					break;
				}

				case MLog: {
					// just reference the monitor once.
					// TODO: offer a reset!
					MLog log = new MLog(); // actually unnecessary, since it's done next

					// and register it
					ILogger stub = (ILogger) UnicastRemoteObject.exportObject(log, rmiAddr.getPort());
					registry.bind(ILogger.class.toString(), stub);

					// then create the flusher
					String logName = msg.getConfig().getLogFile();
					OutputStream file = null;
					if (logName == null || logName.length() == 0) {
						file = new FileOutputStream(logName);
					} else {
						file = System.out;
					}
					TLogFlusher flusher = new TLogFlusher(file, log);
					flusher.start();

					break;
				}

				case MOcean: {
					int height = config.getHeight();
					int width = config.getWidth();
					int maxShoalPerSquare = 1;
					int maxBoatsPerSquare = 3;
					Point wharf = new Point(0, 0);
					Point reproducingZone = new Point(width - 1, height - 1);

					ILogger log = (ILogger) registry.lookup(ILogger.class.toString());
					MOcean ocean = new MOcean(height, width, maxShoalPerSquare, maxBoatsPerSquare, wharf,
							reproducingZone, log);

					ICompleteOcean stub = (ICompleteOcean) UnicastRemoteObject.exportObject(ocean, rmiAddr.getPort());
					registry.bind(ICompleteOcean.class.toString(), stub);
					break;
				}

				case MShoal: {
					int nshoals = config.getNshoals();
					int ndiroper = config.getNcompanies();
					ILogger log = (ILogger) registry.lookup(ILogger.class.toString());

					for (int s = 0; s < nshoals; s++) {
						ShoalId id = new ShoalId(s);
						MShoal shoal = new MShoal(id, ndiroper, log);

						ICompleteShoal stub = (ICompleteShoal) UnicastRemoteObject.exportObject(shoal, rmiAddr.getPort());
						registry.bind(ICompleteShoal.class.toString() + String.valueOf(s), stub);
					}
					break;
				}

				case MDirOper: {
					int ndiroper = config.getNcompanies();
					int nshoals = config.getNshoals();
					int nboats = config.getNboats();
					ILogger log = (ILogger) registry.lookup(ILogger.class.toString());

					for (int d = 0; d < ndiroper; d++) {
						DirOperId id = new DirOperId(d);
						MDirOper diroper = new MDirOper(id, nshoals, nboats, log);

						ICompleteDirOper stub = (ICompleteDirOper) UnicastRemoteObject.exportObject(diroper, rmiAddr.getPort());
						registry.bind(ICompleteDirOper.class.toString() + String.valueOf(d), stub);
					}
					break;
				}

				case MBoat: {
					int ndiroper = config.getNcompanies();
					int nboats = config.getNboats();
					ILogger log = (ILogger) registry.lookup(ILogger.class.toString());

					for (int d = 0; d < ndiroper; d++) {
						for (int b = 0; b < nboats; b++) {
							BoatId id = new BoatId(d, b);

							MBoat boat = new MBoat(id, log);

							ICompleteBoat stub = (ICompleteBoat) UnicastRemoteObject.exportObject(boat, rmiAddr.getPort());
							registry.bind(ICompleteBoat.class.toString() + String.valueOf(d) + String.valueOf(b), stub);
						}
					}
					break;
				}

				case TBoat: {
					int ndiroper = config.getNcompanies();
					int nboats = config.getNboats();
					int capacity = config.getBoatCapacity();
					int period = config.getBoatPeriod();
					Point wharf = new Point(0, 0);

					for (int d = 0; d < ndiroper; d++) {
						for (int b = 0; b < nboats; b++) {
							BoatId id = new BoatId(d, b);
							BoatStats stats = new BoatStats(id, INTERNAL_STATE_BOAT.at_the_wharf, wharf,
									capacity);

							ICompleteDirOper diroper = (ICompleteDirOper) registry.lookup(ICompleteDirOper.class.toString()
									+ String.valueOf(d));
							ICompleteOcean ocean = (ICompleteOcean) registry.lookup(ICompleteOcean.class.toString());
							ICompleteBoat mboat = (ICompleteBoat) registry.lookup(ICompleteBoat.class.toString()
									+ String.valueOf(d) + String.valueOf(b));

							ocean.addBoat(stats, wharf);

							TBoat boat = new TBoat(stats, period, diroper, ocean, mboat);
							boat.start();
						}
					}
					break;
				}

				case TShoal: {
					int nshoals = config.getNshoals();
					int ndiroper = config.getNcompanies();
					int period = config.getShoalPeriod();
					int height = config.getHeight();
					int width = config.getWidth();
					int size = config.getShoalSize();
					int minDetectable = config.getMinShoalDetectable();
					int growing_factor = config.getGrowing_factor();
					double eco_system_capacity = config.getEco_system();
					double maxCatchPercentage = config.getCatchPercentage();
					int seasonMoves = config.getSeasonMoves();
					int nCampaigns = config.getnCampaign();
					Point reproduzingZone = new Point(width - 1, height - 1);

					for (int s = 0; s < nshoals; s++) {
						ShoalId id = new ShoalId(s);
						ShoalStats stats = new ShoalStats(id, INTERNAL_STATE_SCHOOL.spawning, reproduzingZone, size, minDetectable);

						ICompleteDirOper[] diroper = new ICompleteDirOper[ndiroper];
						for (int d = 0; d < ndiroper; d++) {
							diroper[d] = (ICompleteDirOper) registry.lookup(ICompleteDirOper.class.toString()
									+ String.valueOf(d));
						}

						ICompleteOcean ocean = (ICompleteOcean) registry.lookup(ICompleteOcean.class.toString());
						ICompleteShoal monitor = (ICompleteShoal) registry.lookup(ICompleteShoal.class.toString()
								+ String.valueOf(s));

						ICompleteShoal stub = (ICompleteShoal) registry.lookup(ICompleteShoal.class.toString() + String.valueOf(s));
						ocean.addShoal(stats, stub, reproduzingZone);
						
						TShoal shoal = new TShoal(stats, period, seasonMoves, nCampaigns, monitor, ocean, diroper,
								growing_factor, eco_system_capacity, maxCatchPercentage);
						shoal.start();
					}
					break;
				}

				case TDirOper: {
					int nshoals = config.getNshoals();
					int ndiroper = config.getNcompanies();
					int nboats = config.getNboats();

					ICompleteShoal[] shoals = new ICompleteShoal[nshoals];
					for (int s = 0; s < nshoals; s++) {
						shoals[s] = (ICompleteShoal) registry.lookup(ICompleteShoal.class.toString() + String.valueOf(s));
					}

					for (int d = 0; d < ndiroper; d++) {
						ICompleteBoat[] boats = new ICompleteBoat[nboats];
						for (int b = 0; b < nboats; b++) {
							boats[b] = (ICompleteBoat) registry.lookup(ICompleteBoat.class.toString() + String.valueOf(d)
									+ String.valueOf(b));
						}
						ICompleteOcean ocean = (ICompleteOcean) registry.lookup(ICompleteOcean.class.toString());
						ICompleteDirOper monitor = (ICompleteDirOper) registry.lookup(ICompleteDirOper.class.toString()
								+ String.valueOf(d));

						DirOperId id = new DirOperId(d);
						DirOperStats stats = new DirOperStats(INTERNAL_STATE_DIROPER.starting_a_campaign, id);

						ocean.addDirOper(stats);

						TDirOper diroper = new TDirOper(ocean, monitor, boats, shoals, stats);
						diroper.start();
					}

					break;
				}

				default:
					throw new RuntimeException("Unsupported Entity in StartMessage: " + msg.getEntity());
				}

			} else { // MESSAGE_TYPE.Abort

			}

			ProtocolEndPoint.sendMessageObject(socket, ack);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
