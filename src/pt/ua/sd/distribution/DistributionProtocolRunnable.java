/**
 * 
 */
package pt.ua.sd.distribution;

import java.awt.Point;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.boat.MBoat;
import pt.ua.sd.boat.TBoat;
import pt.ua.sd.boat.network.BoatClient;
import pt.ua.sd.boat.network.BoatProtocolRunnable;
import pt.ua.sd.boat.network.BoatServer;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.diroper.MDirOper;
import pt.ua.sd.diroper.TDirOper;
import pt.ua.sd.diroper.network.DirOperClient;
import pt.ua.sd.diroper.network.DirOperProtocolRunnable;
import pt.ua.sd.diroper.network.DirOperServer;
import pt.ua.sd.distribution.DistributionMessage.MESSAGE_TYPE;
import pt.ua.sd.distribution.StartMessage.Entity;
import pt.ua.sd.log.ILogger;
import pt.ua.sd.log.network.LogClient;
import pt.ua.sd.log.network.LogProtocolRunnable;
import pt.ua.sd.log.network.LogServer;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.network.ProtocolServer;
import pt.ua.sd.ocean.MOcean;
import pt.ua.sd.ocean.network.OceanClient;
import pt.ua.sd.ocean.network.OceanProtocolRunnable;
import pt.ua.sd.ocean.network.OceanServer;
import pt.ua.sd.shoal.MShoal;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;
import pt.ua.sd.shoal.TShoal;
import pt.ua.sd.shoal.network.ShoalClient;
import pt.ua.sd.shoal.network.ShoalProtocolRunnable;
import pt.ua.sd.shoal.network.ShoalServer;

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

	static HashSet<Thread> runningServices = new HashSet<Thread>();
	static HashSet<Entity> runningEntities = new HashSet<Entity>();
	static HashSet<String> thisMachine = new HashSet<String>();
	static {
		try {
			for (NetworkInterface ifc : Collections.list(NetworkInterface.getNetworkInterfaces())) {
				for (InetAddress addr : Collections.list(ifc.getInetAddresses())) {
					thisMachine.add(addr.getHostAddress());
					System.out.println(addr.getHostAddress());
				}
			}
		} catch (Throwable t) {
			assert false;
		}
	}

	public DistributionProtocolRunnable() {
	}

	public DistributionProtocolRunnable(Socket s) {
		this.socket = s;
	}

	protected boolean isThisMachine(InetAddress address) {
		return thisMachine.contains(address.getHostAddress());
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

			// if it's a start message, and there are services
			// to
			// startup here, start them accordingly
			if (distributionMessage.getMsgType() == MESSAGE_TYPE.Start) {
				StartMessage msg = (StartMessage) distributionMessage;

				if (!runningEntities.contains(Entity.MLog) && isThisMachine(msg.getMLogAddress())) {

					final LogServer s = new LogServer(msg.getMLogPort(), new LogProtocolRunnable(), msg.getConfig()
							.getLogFile());

					runService(s, Entity.MLog);
					System.out.println("Launched MLog");

				} else if (!runningEntities.contains(Entity.MOcean) && isThisMachine(msg.getMOceanAddress())) {

					// TODO: max{Shoal,Boat}PerSquare shouldn't be static. or
					// maybe they should...
					int width = msg.getConfig().getWidth();
					int height = msg.getConfig().getHeight();
					Point wharf = new Point(0, 0);
					Point reproducingZone = new Point(width - 1, height - 1);
					ILogger logger = new LogClient(msg.getMLogAddress().getHostAddress(), msg.getMLogPort());

					MOcean ocean = new MOcean(height, width, 1, 3, wharf, reproducingZone, logger);

					String host = msg.getMShoalAddress().getHostAddress();
					int port = msg.getMShoalPort();

					// add shoals
					int nshoals = msg.getConfig().getNshoals();
					for (int r = 0; r < nshoals; r++) {
						ShoalId id = new ShoalId(r);
						Point spawningArea = new Point(msg.getConfig().getWidth() - 1, msg.getConfig().getHeight() - 1);
						int size = msg.getConfig().getShoalSize();
						int minDetectable = msg.getConfig().getMinShoalDetectable();
						ShoalStats stats = new ShoalStats(id, INTERNAL_STATE_SCHOOL.spawning, spawningArea, size,
								minDetectable);

						ShoalClient shoal = new ShoalClient(id, port, host);
						ocean.addShoal(stats, shoal, spawningArea);
					}

					// add diropers and boats
					int ndiropers = msg.getConfig().getNcompanies();
					int nboats = msg.getConfig().getNboats();
					for (int r = 0; r < ndiropers; r++) {
						DirOperId id = new DirOperId(r);
						DirOperStats stats = new DirOperStats(INTERNAL_STATE_DIROPER.starting_a_campaign, id);
						ocean.addDirOper(stats);

						for (int s = 0; s < nboats; s++) {
							BoatId boatid = new BoatId(r, s);
							int capacity = msg.getConfig().getBoatCapacity();
							BoatStats boatstats = new BoatStats(boatid, INTERNAL_STATE_BOAT.at_the_wharf, new Point(0,
									0), capacity);

							ocean.addBoat(boatstats, new Point(0, 0));
						}
					}

					final OceanServer s = new OceanServer(msg.getMOceanPort(), new OceanProtocolRunnable(ocean));

					runService(s, Entity.MOcean);
					System.out.println("Launched MOcean");

				} else if (!runningEntities.contains(Entity.MShoal) && isThisMachine(msg.getMShoalAddress())) {

					MShoal mshoals[] = new MShoal[msg.getConfig().getNshoals()];
					ILogger logger = new LogClient(msg.getMLogAddress().getHostAddress(), msg.getMLogPort());

					for (int r = 0; r < mshoals.length; r++) {
						ShoalId id = new ShoalId(r);
						mshoals[r] = new MShoal(id, msg.getConfig().getNcompanies(), logger);
					}

					ShoalProtocolRunnable shoal = new ShoalProtocolRunnable(mshoals);
					final ShoalServer s = new ShoalServer(msg.getMShoalPort(), shoal);

					runService(s, Entity.MShoal);
					System.out.println("Launched MShoal");

				} else if (!runningEntities.contains(Entity.MBoat) && isThisMachine(msg.getMBoatAddress())) {

					MBoat mboats[][] = new MBoat[msg.getConfig().getNcompanies()][msg.getConfig().getNboats()];
					ILogger logger = new LogClient(msg.getMLogAddress().getHostAddress(), msg.getMLogPort());

					for (int c = 0; c < mboats.length; c++) {
						for (int b = 0; b < mboats[c].length; b++) {
							BoatId id = new BoatId(c, b);
							mboats[c][b] = new MBoat(id, logger);
						}
					}

					BoatProtocolRunnable boat = new BoatProtocolRunnable(mboats);
					final BoatServer s = new BoatServer(msg.getMBoatPort(), boat);

					runService(s, Entity.MBoat);
					System.out.println("Launched MBoat");

				} else if (!runningEntities.contains(Entity.MDirOper) && isThisMachine(msg.getMDirOperAddress())) {

					MDirOper mdiropers[] = new MDirOper[msg.getConfig().getNcompanies()];
					ILogger logger = new LogClient(msg.getMLogAddress().getHostAddress(), msg.getMLogPort());

					for (int c = 0; c < mdiropers.length; c++) {
						DirOperId id = new DirOperId(c);
						int nshoals = msg.getConfig().getNshoals();
						int nboats = msg.getConfig().getNboats();
						mdiropers[c] = new MDirOper(id, nshoals, nboats, logger);
					}

					DirOperProtocolRunnable diroper = new DirOperProtocolRunnable(mdiropers);
					final DirOperServer s = new DirOperServer(msg.getMDirOperPort(), diroper);

					runService(s, Entity.MDirOper);
					System.out.println("Launched MDirOper");

				} else if (!runningEntities.contains(Entity.TBoat) && isThisMachine(msg.getTBoatAddress())) {

					TBoat tboats[][] = new TBoat[msg.getConfig().getNcompanies()][msg.getConfig().getNboats()];

					for (int c = 0; c < tboats.length; c++) {
						for (int b = 0; b < tboats[c].length; b++) {
							BoatId id = new BoatId(c, b);
							int capacity = msg.getConfig().getBoatCapacity();
							BoatStats stats = new BoatStats(id, INTERNAL_STATE_BOAT.at_the_wharf, new Point(0, 0),
									capacity);

							String host = msg.getMDirOperAddress().getHostAddress();
							int port = msg.getMDirOperPort();

							DirOperId diroperid = new DirOperId(c);
							DirOperClient diroper = new DirOperClient(diroperid, host, port);

							host = msg.getMOceanAddress().getHostAddress();
							port = msg.getMOceanPort();
							OceanClient ocean = new OceanClient(port, host);

							host = msg.getMBoatAddress().getHostAddress();
							port = msg.getMBoatPort();
							BoatClient boat = new BoatClient(diroperid, id, host, port);

							int period = msg.getConfig().getBoatPeriod();

							tboats[c][b] = new TBoat(stats, period, diroper, ocean, boat);
							tboats[c][b].start();
							runningServices.add(tboats[c][b]);
						}
					}

					System.out.println("Launched TBoat");
					runningEntities.add(Entity.TBoat);

				} else if (!runningEntities.contains(Entity.TShoal) && isThisMachine(msg.getTShoalAddress())) {

					TShoal tshoals[] = new TShoal[msg.getConfig().getNshoals()];

					for (int r = 0; r < tshoals.length; r++) {

						ShoalId id = new ShoalId(r);
						Point spawningArea = new Point(msg.getConfig().getWidth() - 1, msg.getConfig().getHeight() - 1);
						int size = msg.getConfig().getShoalSize();
						int minDetectable = msg.getConfig().getMinShoalDetectable();
						ShoalStats stats = new ShoalStats(id, INTERNAL_STATE_SCHOOL.spawning, spawningArea, size,
								minDetectable);

						String host = msg.getMShoalAddress().getHostAddress();
						int port = msg.getMShoalPort();
						ShoalClient shoal = new ShoalClient(id, port, host);

						host = msg.getMOceanAddress().getHostAddress();
						port = msg.getMOceanPort();
						OceanClient ocean = new OceanClient(port, host);

						DirOperClient diropers[] = new DirOperClient[msg.getConfig().getNcompanies()];
						for (int c = 0; c < diropers.length; c++) {
							DirOperId diroperid = new DirOperId(c);
							host = msg.getMDirOperAddress().getHostAddress();
							port = msg.getMDirOperPort();
							diropers[c] = new DirOperClient(diroperid, host, port);
						}

						int period = msg.getConfig().getShoalPeriod();
						int seasonMoves = msg.getConfig().getSeasonMoves();
						int campaigns = msg.getConfig().getnCampaign();
						int growingFactor = msg.getConfig().getGrowing_factor();
						double ecoCapacity = msg.getConfig().getEco_system();
						double maxCatchPercentage = msg.getConfig().getCatchPercentage();

						tshoals[r] = new TShoal(stats, period, seasonMoves, campaigns, shoal, ocean, diropers,
								growingFactor, ecoCapacity, maxCatchPercentage);
						tshoals[r].start();
						runningServices.add(tshoals[r]);
					}

					System.out.println("Launched TShoal");
					runningEntities.add(Entity.TShoal);

				} else if (!runningEntities.contains(Entity.TDirOper) && isThisMachine(msg.getTDirOperAddress())) {

					TDirOper tdiropers[] = new TDirOper[msg.getConfig().getNcompanies()];

					for (int r = 0; r < tdiropers.length; r++) {

						String host = msg.getMOceanAddress().getHostAddress();
						int port = msg.getMOceanPort();
						OceanClient ocean = new OceanClient(port, host);

						DirOperId id = new DirOperId(r);
						host = msg.getMDirOperAddress().getHostAddress();
						port = msg.getMDirOperPort();
						DirOperClient diroper = new DirOperClient(id, host, port);

						BoatClient boats[] = new BoatClient[msg.getConfig().getNboats()];
						host = msg.getMBoatAddress().getHostAddress();
						port = msg.getMBoatPort();
						for (int s = 0; s < boats.length; s++) {
							BoatId boatid = new BoatId(r, s);
							boats[s] = new BoatClient(id, boatid, host, port);
						}

						ShoalClient shoals[] = new ShoalClient[msg.getConfig().getNshoals()];
						host = msg.getMShoalAddress().getHostAddress();
						port = msg.getMShoalPort();
						for (int s = 0; s < shoals.length; s++) {
							ShoalId shoalid = new ShoalId(s);
							shoals[s] = new ShoalClient(shoalid, port, host);
						}

						DirOperStats stats = new DirOperStats(INTERNAL_STATE_DIROPER.starting_a_campaign, id);

						tdiropers[r] = new TDirOper(ocean, diroper, boats, shoals, stats);
						tdiropers[r].start();
						runningServices.add(tdiropers[r]);
					}

					System.out.println("Launched TDirOper");
					runningEntities.add(Entity.TDirOper);
				}

			} else { // MESSAGE_TYPE.Abort
				// if it's an abort message, kill all services
				for (Object o : runningServices.toArray()) {
					try {
						// kill abruptly, doesn't matter
						((Thread) o).interrupt();
					} catch (Throwable t) {

					}
				}

				runningServices.clear();
				runningEntities.clear();
			}

			ProtocolEndPoint.sendMessageObject(socket, ack);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private void runService(final ProtocolServer server, Entity entity) {

		Thread t = new Thread() {

			@Override
			public void run() {
				server.startServer();
			}

		};

		t.start();

		runningServices.add(t);
		runningEntities.add(entity);
	}
}
