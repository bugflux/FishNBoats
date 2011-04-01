/**
 * 
 */
package pt.ua.sd.communication.todiroper;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
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
