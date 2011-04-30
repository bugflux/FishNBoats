/**
 * 
 */
package pt.ua.sd.communication.todiroper;

/**
 * A specialized Message: Clear all Messages
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ClearAllMessage extends DirOperMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7417228783877753205L;

	/**
	 * @return MESSAGE_TYPE.SeasonEnd
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.ClearMessage;
	}
}
