/**
 * 
 */
package pt.ua.sd.network;


import java.net.Socket;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolRunnableTest implements IProtocolRunnable {

	private Socket socket;

	public void setConnection(Socket socket) {
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

		switch ((DirOperMessage.MESSAGE_TYPE) msg.getMessage().getMsgType()) {
			case BoatFull:
				System.out.println("Server receive a testMessageSend");
				client.sendMessageObject(new ProtocolMessageReceive(new HelpRequestServedMessage(null)));
				break;
			case BackAtWharf:
				System.out.println("Server receive a testMessageReceive");
				throw new RuntimeException("Message received in bad context");
			default:
				throw new RuntimeException("Invalid message");
		}
		client.disconnect();
	}
}
