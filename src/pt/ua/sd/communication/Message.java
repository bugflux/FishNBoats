/**
 * 
 */
package pt.ua.sd.communication;

import java.io.Serializable;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
@SuppressWarnings("serial")
// abstract class
public abstract class Message implements Serializable {

	public interface MESSAGE_TYPE {
		public int getPriority();
	};

	/**
	 * Get the message type for each message object. Usually you have to cast to
	 * the appropriate message type in order to determine actions or get special
	 * attributes of each message.
	 * 
	 * @return The MESSAGE_TYPE for the current Message instance.
	 */
	public abstract MESSAGE_TYPE getMsgType();
}
