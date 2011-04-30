/**
 * 
 */
package pt.ua.sd.ocean.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * The server side listener of messages to a Ocean monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class OceanServer extends ProtocolServer {

	/**
	 * @see ProtocolServer#ProtocolServer(int, pt.ua.sd.network.IProtocolRunnable)
	 */
	public OceanServer(int port, OceanProtocolRunnable runnable) {
		super(port, runnable);
	}
}
