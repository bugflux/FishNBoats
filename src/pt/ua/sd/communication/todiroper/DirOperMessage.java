/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import pt.ua.sd.communication.Message;

/**
 * Ancestor of all DirOper messages.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public abstract class DirOperMessage extends Message {
	/**
	 * 
	 */
	private static final long serialVersionUID = 106871416756160299L;

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		SeasonEnd(10), LifeEnd(0), BackAtWharf(30), RequestHelp(40), FishingDone(20), BoatFull(50);
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
