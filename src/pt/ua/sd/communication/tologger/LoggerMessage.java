/**
 * 
 */
package pt.ua.sd.communication.tologger;

import pt.ua.sd.communication.Message;

/**
 * Ancestor of all Logger messages.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
@SuppressWarnings("serial")
public abstract class LoggerMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

		getTickClock, pushMessage, popContiguous;
		// smaller is more important!

		@Override
		public int getPriority() {
			return 0;
		}
	};
}
