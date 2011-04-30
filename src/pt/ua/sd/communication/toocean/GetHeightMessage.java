/**
 * 
 */
package pt.ua.sd.communication.toocean;

/**
 * A specialized Message: Get Height
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class GetHeightMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 57299582576651734L;

	/**
	 * @return MESSAGE_TYPE.GetWidth
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetHeight;
	}
}
