/**
 * 
 */
package pt.ua.sd.network;

import pt.ua.sd.communication.Message;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolMessageSend extends AbstractProtocolMessage {

	public ProtocolMessageSend() {
	}

	public ProtocolMessageSend(Message m) {
		this.m = m;
	}

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -5726496440379509541L;

	private Message m;

	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.testMessageReceived;
	}

	public Message getMessage() {
		return m;
	}

	public void setMessage(Message m) {
		this.m = m;
	}
}
