/**
 * 
 */
package pt.ua.sd.distribution;

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

	public static void main(String args[]) {
		ProtocolServer s = new ProtocolServer(
				DistributionConfig.DISTRIBUTION_SERVER_PORT,
				new DistributionProtocolRunnable());

		s.startServer();
	}
}
