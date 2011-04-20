/**
 * 
 */
package pt.ua.sd.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolServer {

	private ServerSocket server;
	private IProtocolRunnable protocol;
	private boolean isClosed = false;

	public ProtocolServer(int port, IProtocolRunnable protocol) {

		this.protocol = protocol;

		try {
			this.server = new ServerSocket(port);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		}
	}

	synchronized public void startServer() {
		try {
			System.out.println("Server started");
			do {
				Socket accept = server.accept();
				protocol.setConnection(accept);
				new Thread(protocol).start();
			} while (!isClosed);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
			throw new RuntimeException();
		}
	}

	synchronized public void stopServer() {
		this.isClosed = true;
	}

	public static void main(String[] args) {

		ProtocolServer s = new ProtocolServer(8090, new ProtocolRunnableTest());
		s.startServer();

	}
}
