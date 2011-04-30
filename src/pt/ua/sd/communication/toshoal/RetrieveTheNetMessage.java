/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * A specialized Message: Retrieve the Net
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class RetrieveTheNetMessage extends ShoalMessage {

	/**
	 * @return MESSAGE_TYPE.RetrieveTheNet
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.RetrieveTheNet;
	}

}