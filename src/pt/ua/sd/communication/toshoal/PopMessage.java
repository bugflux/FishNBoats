/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class PopMessage extends ShoalMessage {

	/**
     * 
     */
	private static final long serialVersionUID = 8167486838241346503L;
	boolean blocking;

	public PopMessage(boolean blocking) {
		this.blocking = blocking;
	}

	public boolean isBlocking() {
		return blocking;
	}

	/**
	 * @return MESSAGE_TYPE.NoActionMessage
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.PopMessage;
	}
}
