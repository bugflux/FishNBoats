/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.sd.network;

import java.io.Serializable;
import pt.ua.sd.communication.Message.MESSAGE_TYPE;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public interface IProtocolMessage extends Serializable {
	public MESSAGE_TYPE getMsgType();
}
