/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.Socket;

import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * This entity accepts a list of known servers, including the localhost, and the
 * server configurations.
 * 
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class DistributionClient {

	public DistributionClient() {
	}

	public boolean stopEntities(InetAddress entities[]) {
		try {
			AbortMessage abort = new AbortMessage();
			for (InetAddress entity : entities) {
				Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(new Socket(entity,
						DistributionConfig.DISTRIBUTION_SERVER_PORT), abort));
			}
		} catch (Throwable t) {
			return false;
		}
		return true;
	}

	/**
	 * Start all services, orderly. First Log, then Ocean, followed by Shoal,
	 * Boat and then DirOper.
	 * 
	 * @return true if it started ok, false otherwise.
	 */
	public boolean startEntities(StartMessage start) {
		boolean result = true;

		Socket s;

		// always test if cast to acknowledge works!
		try {
			// order here shouldn't matter!!
			s = new Socket(start.getMLogAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getTLogFlusherAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getMOceanAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getMShoalAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getMBoatAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getMDirOperAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getTBoatAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getTShoalAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			s = new Socket(start.getTDirOperAddress().getHostAddress(), DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

		} catch (Throwable t) {
			t.printStackTrace();
			result = false;
		}

		return result;
	}
}
