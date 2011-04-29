/**
 * 
 */
package pt.ua.sd.communication.toocean;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class GetSpawningAreaMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1609734072826554417L;

	/**
	 * @return MESSAGE_TYPE.GetSpawningArea
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetSpawningArea;
	}
}
