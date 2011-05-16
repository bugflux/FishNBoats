/**
 * 
 */
package pt.ua.sd.distribution;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import pt.ua.sd.distribution.StartMessage.Entity;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * This entity accepts a valid {@link StartMessage} and sends it to the
 * respective machines that ought the entities assigned to them.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DistributionClient {

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

	public static void main(String args[]) {
		
		StartMessage start;
		Socket s;
		DistributionConfig config = new DistributionConfig();
		
		String rmiserveraddress = "127.0.0.1"; int rmiserverport = 22140;
		String mlogserveaddress = "127.0.0.1";
		String moceanseraddress = "127.0.0.1";
		String mshoalseraddress = "127.0.0.1";
		String mdiropersaddress = "127.0.0.1";
		String mboatservaddress = "127.0.0.1";
		String tboatservaddress = "127.0.0.1";
		String tshoalseraddress = "127.0.0.1";
		String tdiropersaddress = "127.0.0.1";

		try {
			// start rmi server
			InetSocketAddress rmi = new InetSocketAddress(rmiserveraddress, rmiserverport);
			start = new StartMessage(config, Entity.RmiServer, rmi);
			s = new Socket(rmiserveraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mlog
			start = new StartMessage(config, Entity.MLog, rmi);
			s = new Socket(mlogserveaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mocean
			start = new StartMessage(config, Entity.MOcean, rmi);
			s = new Socket(moceanseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			// start mshoal
			start = new StartMessage(config, Entity.MShoal, rmi);
			s = new Socket(mshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mdiroper
			start = new StartMessage(config, Entity.MDirOper, rmi);
			s = new Socket(mdiropersaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mboat
			start = new StartMessage(config, Entity.MBoat, rmi);
			s = new Socket(mboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tboat
			start = new StartMessage(config, Entity.TBoat, rmi);
			s = new Socket(tboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tshoal
			start = new StartMessage(config, Entity.TShoal, rmi);
			s = new Socket(tshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tdiroper
			start = new StartMessage(config, Entity.TDirOper, rmi);
			s = new Socket(tdiropersaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
