/**
 * 
 */
package pt.ua.sd.ocean.network;

import pt.ua.sd.communication.toocean.OceanMessage;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class OceanProtocolMessage {

	protected OceanMessage m;

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
