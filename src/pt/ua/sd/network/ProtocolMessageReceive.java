/**
 * 
 */
package pt.ua.sd.network;

import pt.ua.sd.communication.Message;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolMessageReceive extends AbstractProtocolMessage {

	public ProtocolMessageReceive() {
	}

	public ProtocolMessageReceive(Message m) {
		this.m = m;
	}

	/**
	 * 
	 */


	private static final long serialVersionUID = -312164427954553505L;
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
