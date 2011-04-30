/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * A specialized Message: No Action pending
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class NoActionMessage extends ShoalMessage {

	/**
	 * @return MESSAGE_TYPE.NoActionMessage
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.NoActionMessage;
	}

}
