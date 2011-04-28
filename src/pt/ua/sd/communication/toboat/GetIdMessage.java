/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class GetIdMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1373405856991513546L;

	/**
	 * @return MESSAGE_TYPE.NoAction
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetId;
	}
}
