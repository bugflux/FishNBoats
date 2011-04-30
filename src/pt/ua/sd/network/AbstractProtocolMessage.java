/**
 * 
 */
package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message;

/**
 * Ensures an outline of a ProtocolMessage
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
@SuppressWarnings("serial")
public abstract class AbstractProtocolMessage implements IProtocolMessage {

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
