/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * A specialized Message: Release helper boat
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ReleaseHelperMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3210243299911139077L;

	/**
	 * @return MESSAGE_TYPE.ReleaseHelper
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.ReleaseHelper;
	}
}
