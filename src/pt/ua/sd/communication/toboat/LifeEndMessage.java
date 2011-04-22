/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class LifeEndMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059662657689565567L;

	/**
	 * @return MESSAGE_TYPE.LifeEnd
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.LifeEnd;
	}
}
