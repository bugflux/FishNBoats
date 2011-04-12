/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class TrappedByTheNetMessage extends ShoalMessage {

	/**
	 * @return MESSAGE_TYPE.TrappedByTheNet
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.TrappedByTheNet;
	}
}
