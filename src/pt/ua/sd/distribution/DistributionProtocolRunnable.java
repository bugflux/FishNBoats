/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;

import pt.ua.sd.distribution.DistributionMessage.MESSAGE_TYPE;
import pt.ua.sd.distribution.StartMessage.Entity;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * @author André Prata <andreprata@ua.pt>
 */
public class DistributionProtocolRunnable implements IProtocolRunnable {

	protected Socket socket;
	protected Acknowledge ack = new Acknowledge();

	static HashSet<Thread> runningServices = new HashSet<Thread>();
	static HashSet<Entity> runningEntities = new HashSet<Entity>();
	static HashSet<String> thisMachine = new HashSet<String>();
	static {
		try {
			for (NetworkInterface ifc : Collections.list(NetworkInterface
					.getNetworkInterfaces())) {
				for (InetAddress addr : Collections
						.list(ifc.getInetAddresses())) {
					thisMachine.add(addr.getHostAddress());
					System.out.println(addr.getHostAddress());
				}
			}
		} catch (Throwable t) {
			assert false;
		}
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
			System.out.println("Detected ip address for this server: "
					+ thisMachine);

			// fetch the dsitribution message
			DistributionMessage distributionMessage = (DistributionMessage) ((IProtocolMessage) ProtocolEndPoint
					.getMessageObject(socket)).getMessage();

			// if it's a start message, and there are services
			// to
			// startup here, start them accordingly
			if (distributionMessage.getMsgType() == MESSAGE_TYPE.Start) {
				StartMessage msg = (StartMessage) distributionMessage;

				if (!runningEntities.contains(Entity.MLog)
						&& isThisMachine(msg.getMLogAddress())) {

					System.out.println("Launched MLog");
					runningEntities.add(Entity.MLog);
				} else if (!runningEntities.contains(Entity.TLogFlusher)
						&& isThisMachine(msg.getTLogFlusherAddress())) {

					System.out.println("Launched TLogFlusher");
					runningEntities.add(Entity.TLogFlusher);
				} else if (!runningEntities.contains(Entity.MOcean)
						&& isThisMachine(msg.getMOceanAddress())) {

					System.out.println("Launched MOcean");
					runningEntities.add(Entity.MOcean);
				} else if (!runningEntities.contains(Entity.MShoal)
						&& isThisMachine(msg.getMShoalAddress())) {

					System.out.println("Launched MShoal");
					runningEntities.add(Entity.MShoal);
				} else if (!runningEntities.contains(Entity.TShoal)
						&& isThisMachine(msg.getTShoalAddress())) {

					System.out.println("Launched TShoal");
					runningEntities.add(Entity.TShoal);
				} else if (!runningEntities.contains(Entity.MBoat)
						&& isThisMachine(msg.getMBoatAddress())) {

					System.out.println("Launched MBoat");
					runningEntities.add(Entity.MBoat);
				} else if (!runningEntities.contains(Entity.TBoat)
						&& isThisMachine(msg.getTBoatAddress())) {

					System.out.println("Launched TBoat");
					runningEntities.add(Entity.TBoat);
				} else if (!runningEntities.contains(Entity.MDirOper)
						&& isThisMachine(msg.getMDirOperAddress())) {

					System.out.println("Launched MDirOper");
					runningEntities.add(Entity.MDirOper);
				} else if (!runningEntities.contains(Entity.TDirOper)
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

	/**
	 * @see pt.ua.sd.network.IProtocolRunnable#setConnection(java.net.Socket)
	 */
	@Override
	public void setConnection(Socket socket) {
		this.socket = socket;
	}

}
