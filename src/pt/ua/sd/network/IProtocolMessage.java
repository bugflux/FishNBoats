/**
 * 
 */
package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public interface IProtocolMessage extends Serializable {
	public Message getMessage();
}
