/**
 * 
 */
package pt.ua.sd.communication.toocean;


/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class GetWharfMessage extends OceanMessage {

	/**
	 * @return MESSAGE_TYPE.GetWharf
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetWharf;
	}
}
