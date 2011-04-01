/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class NoActionMessage extends BoatMessage {

	/**
	 * @return MESSAGE_TYPE.NoAction
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.NoAction;
	}
}
