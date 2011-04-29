package pt.ua.sd.distribution;

import java.io.Serializable;

import pt.ua.sd.communication.Message;
import pt.ua.sd.network.IProtocolMessage;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
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
