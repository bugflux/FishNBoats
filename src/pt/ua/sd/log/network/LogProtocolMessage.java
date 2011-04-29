/**
 * 
 */
package pt.ua.sd.log.network;

import java.io.Serializable;
import pt.ua.sd.communication.tologger.LoggerMessage;
import pt.ua.sd.network.IProtocolMessage;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class LogProtocolMessage implements IProtocolMessage, Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -628384051581925384L;
	private LoggerMessage message;

	public LogProtocolMessage(LoggerMessage message) {
		this.message = message;
	}

	@Override
	public LoggerMessage getMessage() {
		return message;
	}
}
