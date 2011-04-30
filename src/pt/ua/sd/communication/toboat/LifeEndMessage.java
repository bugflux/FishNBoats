/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * A specialized Message: Life ended
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class LifeEndMessage extends BoatMessage {

	/**
	 * @return MESSAGE_TYPE.LifeEnd
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.LifeEnd;
	}
}
