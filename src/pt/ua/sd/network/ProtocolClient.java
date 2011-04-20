/**
 * 
 */
package pt.ua.sd.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolClient {

	private Socket client;
	private OutputStream outputStream;
	private InputStream inputStream;

	public ProtocolClient(String host, int port) {
		try {
			this.client = new Socket(host, port);
			outputStream = this.client.getOutputStream();
			inputStream = this.client.getInputStream();
			System.out.println("Client connected to server on " + host + ":" + port);
		} catch (UnknownHostException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public ProtocolClient(Socket socket) {

		try {
			this.client = socket;
			outputStream = this.client.getOutputStream();
			inputStream = this.client.getInputStream();

		} catch (UnknownHostException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	synchronized public void disconnect() {
		try {
			client.close();
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	synchronized public IProtocolMessage sendMessageObjectBlocking(IProtocolMessage msg) {
		IProtocolMessage ret = null;

		try {
			ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);


			objOutputStream.writeObject(msg);
			objOutputStream.flush();

			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);
			ret = (IProtocolMessage) objInputStream.readObject();
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ret;
	}

	synchronized public void sendMessageObject(IProtocolMessage msg) {

		try {
			ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);

			objOutputStream.writeObject(msg);
			objOutputStream.flush();
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	synchronized public IProtocolMessage getMessageObject() {
		try {

			ObjectInputStream objInputStream = new ObjectInputStream(inputStream);

			return (IProtocolMessage) objInputStream.readObject();
		} catch (IOException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}

	public static void main(String args[]) {

		do {
			ProtocolClient c = new ProtocolClient("127.0.0.1", 8090);
			System.out.println("Sending a message to server");
			IProtocolMessage msg = c.sendMessageObjectBlocking(new ProtocolMessageSend());
			switch ((AbstractProtocolMessage.MESSAGE_TYPE) msg.getMsgType()) {
				case testMessageReceived:
					System.out.println("Mensage confirmada com recebida");
					break;
				case testMessageSend:
					throw new RuntimeException("Message received in bad context");
				default:
					throw new RuntimeException("Invalid Message");
			}
			c.disconnect();
		} while (true);

	}
}
