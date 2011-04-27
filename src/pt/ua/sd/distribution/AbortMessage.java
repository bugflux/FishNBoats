/**
 * 
 */
package pt.ua.sd.distribution;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class AbortMessage extends DistributionMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1875579656928240045L;

	/**
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 * @return MESSAGE_TYPE.Abort
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.Abort;
	}
	
	@Override
	public Message getMessage() {
		return this;
	}
}
