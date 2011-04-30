/**
 * 
 */
package pt.ua.sd.shoal.network;

import java.io.Serializable;

import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.shoal.ShoalId;

/**
 * A Message container for Shoal messages.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ShoalProtocolMessage implements Serializable, IProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4054980547168620833L;
	protected final ShoalMessage m;
	protected final ShoalId id;

	/**
	 * Construct a new ShoalProtocolMessage given a destination Shoal and the
	 * message to deliver to it.
	 * 
	 * @param id
	 *            the shoal to deliver the message to.
	 * @param m
	 *            the messsage to deliver.
	 */
	public ShoalProtocolMessage(ShoalId id, ShoalMessage m) {
		this.m = m;
		this.id = id;

	}

	/**
	 * @return the message
	 */
	public ShoalMessage getMessage() {
		return m;
	}

	/**
	 * @return the shoal id
	 */
	public ShoalId getShoalId() {
		return id;
	}
}
