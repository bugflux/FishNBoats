/**
 * 
 */
package pt.ua.sd.communication.toocean;

/**
 * A specialized Message: Get Wharf
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class GetWharfMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2552308939790020981L;

	/**
	 * @return MESSAGE_TYPE.GetWharf
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetWharf;
	}
}
