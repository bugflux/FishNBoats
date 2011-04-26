/**
 * 
 */
package pt.ua.sd.ocean.network;

import java.io.Serializable;
import pt.ua.sd.communication.toocean.OceanMessage;
import pt.ua.sd.network.IProtocolMessage;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class OceanProtocolMessage implements Serializable, IProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5389538087775067037L;

	protected final OceanMessage m;

	/**
	 * Construct a new OceanMessage for Ocean Operations
	 * 
	 * @param m
	 *            the message that describes the operation
	 */
	public OceanProtocolMessage(OceanMessage m) {
		this.m = m;
	}

	/**
	 * @return the m
	 */
	public OceanMessage getMessage() {
		return m;
	}
}
