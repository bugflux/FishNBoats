/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * A specialized Message: Is trapped
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class IsTrapped extends ShoalMessage {

	/**
     * 
     */
	private static final long serialVersionUID = 8167486838241346503L;
	private int trapped;

	public IsTrapped(int trapped) {
		this.trapped = trapped;
	}

	public int getTrapped() {
		return trapped;
	}

	/**
	 * @return MESSAGE_TYPE.NoActionMessage
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.IsTrapped;
	}
}
