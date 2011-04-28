/**
 * 
 */
package pt.ua.sd.communication.toshoal;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
@SuppressWarnings("serial") // abstract class
public abstract class ShoalMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

		IsTrapped(0),ACK(0),PopMessage(0),GoToFeedingArea(Integer.MAX_VALUE), TrappedByTheNet(10), RetrieveTheNet(
		5), NoActionMessage(Integer.MAX_VALUE);
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
}
