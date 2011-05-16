/**
 * 
 */
package pt.ua.sd.communication.toboat;

/**
 * A specialized Message: Set to High Sea
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SetToHighSeaMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3849337385635416107L;

	/**
	 * @return MESSAGE_TYPE.SetToHighSea
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetToHighSea;
	}

}
