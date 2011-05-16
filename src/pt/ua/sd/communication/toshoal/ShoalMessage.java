/**
 * 
 */
package pt.ua.sd.communication.toshoal;

import pt.ua.sd.communication.Message;

/**
 * Ancestor of all Shoal messages
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public abstract class ShoalMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2181167438383726230L;

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		GoToFeedingArea(Integer.MAX_VALUE), TrappedByTheNet(10), RetrieveTheNet(5), NoActionMessage(Integer.MAX_VALUE);
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
