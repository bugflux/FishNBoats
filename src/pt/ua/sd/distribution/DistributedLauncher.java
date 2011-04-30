/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * This class allows launching a distributed simulation. The location of each
 * entity is defined by an ip address and a port (servers only)
 * 
 * It is statically defined.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DistributedLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// must run at this pc, because of the graphic session!
			InetSocketAddress mOceanAddress = new InetSocketAddress("172.16.0.130", 22144);

			InetSocketAddress mBoatAddress = new InetSocketAddress("192.168.8.171", 22141);
			InetSocketAddress mDirOperAddress = new InetSocketAddress("192.168.8.173", 22142);
			InetSocketAddress mLogAddress = new InetSocketAddress("192.168.8.174", 22143);
			InetSocketAddress mShoalAddress = new InetSocketAddress("192.168.8.175", 22145);
			InetSocketAddress tBoatAddress = new InetSocketAddress("192.168.8.176", 0);
			InetSocketAddress tDirOperAddress = new InetSocketAddress("192.168.8.177", 0);
			InetSocketAddress tLogFlusherAddress = new InetSocketAddress("192.168.8.178", 0);
			InetSocketAddress tShoalAddress = new InetSocketAddress("192.168.8.180", 0);

			// stop services before starting
			DistributionClient c = new DistributionClient();
			c.stopEntities(new InetAddress[] { mLogAddress.getAddress(), tLogFlusherAddress.getAddress(),
					mOceanAddress.getAddress(), mShoalAddress.getAddress(), tShoalAddress.getAddress(),
					mBoatAddress.getAddress(), tBoatAddress.getAddress(), mDirOperAddress.getAddress(),
					tDirOperAddress.getAddress() });

			StartMessage startMessage = new StartMessage(new DistributionConfig());
			startMessage.setMBoat(mBoatAddress);
			startMessage.setMDirOper(mDirOperAddress);
			startMessage.setMLog(mLogAddress);
			startMessage.setMOcean(mOceanAddress);
			startMessage.setMShoal(mShoalAddress);
			startMessage.setTBoat(tBoatAddress);
			startMessage.setTDirOper(tDirOperAddress);
			startMessage.setTLogFlusher(tLogFlusherAddress);
			startMessage.setTShoal(tShoalAddress);

			c = new DistributionClient();
			if (c.startEntities(startMessage)) {
				System.out.println("all started ok!");
			} else {
				System.out.println("error starting entities");
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
