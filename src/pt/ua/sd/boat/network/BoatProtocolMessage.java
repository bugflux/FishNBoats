/**
 * 
 */
package pt.ua.sd.boat.network;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.communication.toboat.BoatMessage;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class BoatProtocolMessage {
	protected BoatMessage m;
	protected BoatId id;

	/**
	 * Construct a new BoatProtocolMessage given a destination Shoal and the
	 * message to deliver to it.
	 * 
	 * @param id
	 *            the shoal to deliver the message to.
	 * @param m
	 *            the message to deliver.
	 */
	public BoatProtocolMessage(BoatId id, BoatMessage m) {
		this.m = m;
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public BoatMessage getMessage() {
		return m;
	}

	/**
	 * @return the boat id
	 */
	public BoatId getBoatId() {
		return id;
	}
}
