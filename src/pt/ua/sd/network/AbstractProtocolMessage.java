/**
 * 
 */
package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public abstract class AbstractProtocolMessage implements IProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2975624544689947875L;

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE, Serializable {

		testMessageSend(1), testMessageReceived(2);
		// smaller is more important!
		protected int priority;

		MESSAGE_TYPE(int priority) {
			this.priority = priority;
		}

		@Override
		public int getPriority() {
			return this.priority;
		}
	};

	public abstract MESSAGE_TYPE getMsgType();
}
