package pt.ua.sd.distribution;

import java.io.Serializable;

import pt.ua.sd.communication.Message;
import pt.ua.sd.network.IProtocolMessage;

/**
 * Ancestor of all Distribution-related messages
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
@SuppressWarnings("serial")
public abstract class DistributionMessage extends Message implements Serializable, IProtocolMessage {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		Start, Abort;

		public int getPriority() {
			return 0;
		};
	};
}
