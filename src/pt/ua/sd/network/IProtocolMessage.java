/**
 * 
 */
package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message;

/**
 * Expose the ProtocolMessage methods
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IProtocolMessage extends Serializable {
	
	/**
	 * Get the Message that this ProtocolMessage encloses.
	 * 
	 * @return the message
	 */
	public Message getMessage();
}
