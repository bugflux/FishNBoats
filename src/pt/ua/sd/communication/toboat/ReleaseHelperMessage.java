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
	 * @return MESSAGE_TYPE.ReleaseHelper
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.ReleaseHelper;
	}
}
