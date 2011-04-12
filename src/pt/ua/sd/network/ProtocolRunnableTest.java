/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.network;

import java.net.Socket;
import pt.ua.sd.communication.Message.MESSAGE_TYPE;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class ProtocolRunnableTest implements IProtocolRunnable {

	private Socket socket;

	public void setConnetion(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		if (socket == null) {
			throw new RuntimeException("socket not setted");
		}
		ProtocolClient client = new ProtocolClient(socket);
		IProtocolMessage msg = client.getMessageObject();
		if (msg == null) {
			throw new RuntimeException("Message is null");
		}

		switch ((AbstractProtocolMessage.MESSAGE_TYPE) msg.getMsgType()) {
			case testMessageSend:
				System.out.println("Server receive a testMessageSend");
				client.sendMessageObject(new ProtocolMessageReceive());
				break;
			case testMessageReceived:
				System.out.println("Server receive a testMessageReceive");
				throw new RuntimeException("Message received in bad context");
			default:
				throw new RuntimeException("Invalid message");
		}
		client.disconnect();
	}
}
