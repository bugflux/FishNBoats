/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class RetrieveTheNetMessage extends ShoalMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6377519931567558395L;

	/**
	 * @return MESSAGE_TYPE.RetrieveTheNet
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.RetrieveTheNet;
	}
}
