/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;

import pt.ua.sd.distribution.DistributionMessage.MESSAGE_TYPE;
import pt.ua.sd.distribution.StartMessage.Entity;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.network.ProtocolServer;

/**
 * This is the main process for the distributed solution. This server must be
 * running in all machines. There is a manager somewhere who is giving
 * (ordered!) orders to all known machines.
 * 
 * For example, this machine is ordered to start an Ocean Server. The Ocean
 * monitor and OceanServer are started in this machine, in the specified port.
 * The manager also notifies all other machines that the Ocean is set up at this
 * machine's address and at the given port.
 * 
 * 
 * The startups must be ordered.
 * 
 * If this machine is ordered to start a Boat, for example, the Ocean and
 * DirOpers, for example must have been already started! Here or on another
 * machine, otherwise initialization will fail.
 * 
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class DistributionServer {
	static HashSet<Thread> runningServices = new HashSet<Thread>();
	static HashSet<Entity> runningEntities = new HashSet<Entity>();

	protected static boolean isThisMachine(InetAddress address) {
		try {
			InetAddress addresses[] = InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
			for(InetAddress addr : addresses) {
				System.out.println(addr.getHostAddress());
			}
			String thisMachine = InetAddress.getLocalHost().getHostAddress();
			return address.getHostAddress().equals(thisMachine);
		} catch (Throwable t) {

		}
		return false;
	}

	public static void main(String args[]) {
		ProtocolServer s = new ProtocolServer(
				DistributionConfig.DISTRIBUTION_SERVER_PORT,
				new IProtocolRunnable() {
					protected Socket socket = null;
					Acknowledge ack = new Acknowledge();

					@Override
					public void run() {
						assert socket != null;

						try {
							String thisMachine = InetAddress.getLocalHost()
									.getHostAddress();
							System.out
									.println("Detected ip address for this server: "
											+ thisMachine);

							// fetch the dsitribution message
							DistributionMessage distributionMessage = (DistributionMessage) ((IProtocolMessage) ProtocolEndPoint
									.getMessageObject(socket)).getMessage();

							// if it's a start message, and there are services
							// to
							// startup here, start them accordingly
							if (distributionMessage.getMsgType() == MESSAGE_TYPE.Start) {
								StartMessage msg = (StartMessage) distributionMessage;
								System.out.println(msg.getMLogAddress()
										.getHostAddress() + " " + thisMachine);

								if (!runningEntities.contains(Entity.MLog)
										&& isThisMachine(msg.getMLogAddress())) {

									System.out.println("Launched MLog");
									runningEntities.add(Entity.MLog);
								} else if (!runningEntities
										.contains(Entity.TLogFlusher)
										&& isThisMachine(msg.getTLogFlusherAddress())) {

									System.out.println("Launched TLogFlusher");
									runningEntities.add(Entity.TLogFlusher);
								} else if (!runningEntities
										.contains(Entity.MOcean)
										&& isThisMachine(msg.getMOceanAddress())) {

									System.out.println("Launched MOcean");
									runningEntities.add(Entity.MOcean);
								} else if (!runningEntities
										.contains(Entity.MShoal)
										&& isThisMachine(msg.getMShoalAddress())) {

									System.out.println("Launched MShoal");
									runningEntities.add(Entity.MShoal);
								} else if (!runningEntities
										.contains(Entity.TShoal)
										&& isThisMachine(msg.getTShoalAddress())) {

									System.out.println("Launched TShoal");
									runningEntities.add(Entity.TShoal);
								} else if (!runningEntities
										.contains(Entity.MBoat)
										&& isThisMachine(msg.getMBoatAddress())) {

									System.out.println("Launched MBoat");
									runningEntities.add(Entity.MBoat);
								} else if (!runningEntities
										.contains(Entity.TBoat)
										&& isThisMachine(msg.getTBoatAddress())) {

									System.out.println("Launched TBoat");
									runningEntities.add(Entity.TBoat);
								} else if (!runningEntities
										.contains(Entity.MDirOper)
										&& isThisMachine(msg.getMDirOperAddress())) {

									System.out.println("Launched MDirOper");
									runningEntities.add(Entity.MDirOper);
								} else if (!runningEntities
										.contains(Entity.TDirOper)
										&& isThisMachine(msg.getTDirOperAddress())) {

									System.out.println("Launched TDirOper");
									runningEntities.add(Entity.TDirOper);
								}

							} else { // MESSAGE_TYPE.Abort
								// if it's an abort message, kill all services
								for (Object o : runningServices.toArray()) {
									try {
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

					@Override
					public void setConnection(Socket socket) {
						this.socket = socket;
					}
				});

		s.startServer();
	}
}
