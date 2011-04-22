/**
 * 
 */
package pt.ua.sd.communication.toocean;


/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class GetHeightMessage extends OceanMessage {

	/**
	 * @return MESSAGE_TYPE.GetWidth
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetWidth;
	}
}
