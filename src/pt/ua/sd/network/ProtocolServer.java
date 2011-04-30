/**
 * 
 */
package pt.ua.sd.network;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The generic purpose server. When constructed, the socket is created. Upon
 * start, it start accepting connections and serving the requests in a new
 * thread, with the associated protocol handler
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ProtocolServer {

	protected ServerSocket server;
	protected IProtocolRunnable protocol;
	protected boolean isClosed = false;

	/**
	 * 
	 * @param port
	 *            The port to run at
	 * @param protocol
	 *            The message handler for this specific server
	 */
	public ProtocolServer(int port, IProtocolRunnable protocol) {

		this.protocol = protocol;

		try {
			this.server = new ServerSocket(port);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		}
	}

	/**
	 * Start handling messages in the port given port.
	 */
	synchronized public void startServer() {
		try {
			System.out.println("Server started");
			do {
				Socket accept = server.accept();
				protocol = protocol.getClass().getConstructor(Socket.class).newInstance(accept);
				new Thread(protocol).start();
			} while (!isClosed);
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SecurityException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalArgumentException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InvocationTargetException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException();
		}
	}

	/**
	 * Close the socket and don't accept any more connections. You can't call
	 * startServer again, you must recreate the object!
	 */
	synchronized public void stopServer() {
		this.isClosed = true;
		try {
			this.server.close();
		} catch (IOException e) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
