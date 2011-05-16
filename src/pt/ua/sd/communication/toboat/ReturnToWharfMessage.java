/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * A specialized Message: Return to Wharf
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ReturnToWharfMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7311056959846483575L;

	/**
	 * @return MESSAGE_TYPE.ReturnToWharf
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.ReturnToWharf;
	}

}
