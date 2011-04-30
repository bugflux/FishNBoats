/**
 * 
 */
package pt.ua.sd.boat.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * The server side listener of messages to a boat monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class BoatServer extends ProtocolServer {

	/**
	 * @see ProtocolServer#ProtocolServer(int, pt.ua.sd.network.IProtocolRunnable)
	 */
	public BoatServer(int port, BoatProtocolRunnable runnable) {
		super(port, runnable);
	}
}
