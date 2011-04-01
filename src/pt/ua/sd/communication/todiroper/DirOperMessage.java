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
public abstract class DirOperMessage extends Message {
	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		SeasonEnd(0), LifeEnd(0), EndSimulation(0), BackAtWharf(0), RequestHelp(0), FishingDone(0);
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
