/**
 * 
 */
package pt.ua.sd.network;

import java.net.Socket;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public interface IProtocolRunnable extends Runnable {
	
	/**
	 * Set the connection for this protocol.
	 * @param socket
	 */
	public void setConnection(Socket socket);
}
