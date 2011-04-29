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
	 * 
	 */
	private static final long serialVersionUID = 3597970031339658207L;

	/**
	 * @return MESSAGE_TYPE.TrappedByTheNet
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.TrappedByTheNet;
	}
}
