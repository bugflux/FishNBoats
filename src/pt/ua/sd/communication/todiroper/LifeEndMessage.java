/**
 * 
 */
package pt.ua.sd.communication.todiroper;

/**
 * A specialized Message: Life End
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class LifeEndMessage extends DirOperMessage {

	/**
	 * @return MESSAGE_TYPE.LifeEnd
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.LifeEnd;
	}
}
