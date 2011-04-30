/**
 * 
 */
package pt.ua.sd.shoal.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * The server side listener of messages to a Shoal monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ShoalServer extends ProtocolServer {

	/**
	 * @see ProtocolServer#ProtocolServer(int, pt.ua.sd.network.IProtocolRunnable)
	 */
	public ShoalServer(int port, ShoalProtocolRunnable runnable) {
		super(port, runnable);
	}
}
