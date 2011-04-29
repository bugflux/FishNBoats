/**
 * 
 */
package pt.ua.sd.communication.tologger;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */

public abstract class LoggerMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5154299654398238473L;

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

		getTickClock, pushMessage, popContiguous;
		// smaller is more important!

		@Override
		public int getPriority() {
			return 0;
		}
	};
}
