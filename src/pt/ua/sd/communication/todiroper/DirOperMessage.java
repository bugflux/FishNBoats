/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
@SuppressWarnings("serial") // abstract class
public abstract class DirOperMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

		PopMessage(0),ClearMessage(0),ACK(0),SeasonEnd(10), LifeEnd(0), BackAtWharf(30), RequestHelp(40), FishingDone(20), BoatFull(50);
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
