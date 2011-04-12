/**
 * 
 */
package pt.ua.sd.communication.toboat;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public abstract class BoatMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

		SetToHighSea(20), LifeEnd(0), ChangeCourse(30), HelpRequestServed(40), ReturnToWharf(
		10), NoAction(Integer.MAX_VALUE), CastTheNet(40), ReleaseHelper(
		40);
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
