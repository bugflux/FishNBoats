/**
 * 
 */
package pt.ua.sd.network;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolMessageSend extends AbstractProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5726496440379509541L;

	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.testMessageSend;
	}
}
