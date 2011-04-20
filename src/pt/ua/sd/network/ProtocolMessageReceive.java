/**
 * 
 */
package pt.ua.sd.network;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolMessageReceive extends AbstractProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -312164427954553505L;

	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.testMessageReceived;
	}
}
