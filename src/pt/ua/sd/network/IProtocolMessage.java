/**
 * 
 */
package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message.MESSAGE_TYPE;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public interface IProtocolMessage extends Serializable {

	public MESSAGE_TYPE getMsgType();
}
