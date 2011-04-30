/**
 * 
 */
package pt.ua.sd.communication.todiroper;

/**
 * A specialized Message: Season End
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SeasonEndMessage extends DirOperMessage {

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
		return MESSAGE_TYPE.SeasonEnd;
	}
}
