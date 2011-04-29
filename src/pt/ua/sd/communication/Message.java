/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.sd.communication;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public abstract class Message {
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
