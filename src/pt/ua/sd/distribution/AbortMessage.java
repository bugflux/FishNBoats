/**
 * 
 */
package pt.ua.sd.distribution;

import pt.ua.sd.communication.Message;

/**
 * A specialized Message: Abort
 * 
 * @author André Prata
 * @author Eriksson Monteiro
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
