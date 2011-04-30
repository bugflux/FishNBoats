/**
 * 
 */
package pt.ua.sd.diroper.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * The server side listener of messages to a DirOper monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DirOperServer extends ProtocolServer {

	/**
	 * @see ProtocolServer#ProtocolServer(int, pt.ua.sd.network.IProtocolRunnable)
	 */
	public DirOperServer(int port, DirOperProtocolRunnable protocol) {
		super(port, protocol);
	}

}
