/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.sd.network;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class ProtocolMessageSend extends AbstractProtocolMessage{

	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.testMessageSend;
	}

}
