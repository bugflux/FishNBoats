/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author André Prata <andreprata@ua.pt>
 * 
 */
public class DistributedLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			InetSocketAddress mBoatAddress = new InetSocketAddress("172.16.0.130",
					22141), mDirOperAddress = new InetSocketAddress(
					"172.16.0.130", 22142), mLogAddress = new InetSocketAddress(
					"172.16.0.130", 22143), mOceanAddress = new InetSocketAddress(
					"172.16.0.130", 22144), mShoalAddress = new InetSocketAddress(
					"172.16.0.130", 22145), tBoatAddress = new InetSocketAddress(
					"172.16.0.130", 0), tDirOperAddress = new InetSocketAddress(
					"172.16.0.130", 0), tLogFlusherAddress = new InetSocketAddress(
					"172.16.0.130", 0), tShoalAddress = new InetSocketAddress(
					"172.16.0.130", 0);

			// stop services before starting
			DistributionClient c = new DistributionClient();
			c.stopEntities(new InetAddress[] { mLogAddress.getAddress(),
					tLogFlusherAddress.getAddress(),
					mOceanAddress.getAddress(), mShoalAddress.getAddress(),
					tShoalAddress.getAddress(), mBoatAddress.getAddress(),
					tBoatAddress.getAddress(), mDirOperAddress.getAddress(),
					tDirOperAddress.getAddress() });

			StartMessage startMessage = new StartMessage(
					new DistributionConfig());
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
