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
		
		String rmiserveraddress = "192.168.8.171"; int rmiserverport = 22145; // this and the next port will be used!
		String mlogserveaddress = "192.168.8.171"; int mlogserveport = 22140; 
		String moceanseraddress = "172.16.6.49"; int moceanserport = 22141;
		String mshoalseraddress = "172.16.6.49"; int mshoalserport = 22142;
		String mdiropersaddress = "172.16.6.49"; int mdiropersport = 22143;
		String mboatservaddress = "172.16.6.49"; int mboatservport = 22144;
		String tboatservaddress = "192.168.8.181";
		String tshoalseraddress = "192.168.8.181";
		String tdiropersaddress = "192.168.8.181";

		try {
			// start rmi server
			InetSocketAddress rmi = new InetSocketAddress(rmiserveraddress, rmiserverport);
			start = new StartMessage(config, Entity.RmiServer, 0, rmi);
			s = new Socket(rmiserveraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mlog
			start = new StartMessage(config, Entity.MLog, mlogserveport, rmi);
			s = new Socket(mlogserveaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mocean
			start = new StartMessage(config, Entity.MOcean, moceanserport, rmi);
			s = new Socket(moceanseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));

			// start mshoal
			start = new StartMessage(config, Entity.MShoal, mshoalserport, rmi);
			s = new Socket(mshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mdiroper
			start = new StartMessage(config, Entity.MDirOper, mdiropersport, rmi);
			s = new Socket(mdiropersaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start mboat
			start = new StartMessage(config, Entity.MBoat, mboatservport, rmi);
			s = new Socket(mboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tboat
			start = new StartMessage(config, Entity.TBoat, 0, rmi);
			s = new Socket(tboatservaddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tshoal
			start = new StartMessage(config, Entity.TShoal, 0, rmi);
			s = new Socket(tshoalseraddress, DistributionConfig.DISTRIBUTION_SERVER_PORT);
			Acknowledge.class.cast(ProtocolEndPoint.sendMessageObjectBlocking(s, start));
			
			// start tdiroper
			start = new StartMessage(config, Entity.TDirOper, 0, rmi);
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
