/**
 * 
 */
package pt.ua.sd.boat.network;

import java.io.Serializable;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.shoal.ShoalId;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class BoatProtocolMessage implements Serializable,IProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2232336744180183339L;

	protected final BoatMessage m;
	protected final BoatId id;
	protected final ShoalId sId;
	/**
	 * Construct a new BoatProtocolMessage given a destination Shoal and the
	 * message to deliver to it.
	 * @param sId
	 *			  the id of the shoal that send the message
	 * @param id
	 *            the shoal to deliver the message to.
	 * @param m
	 *            the message to deliver.
	 */
	public BoatProtocolMessage(BoatId id, BoatMessage m, ShoalId sId) {
		this.m = m;
		this.id = id;
		this.sId = sId;
	}

	/**
	 * @return the message
	 */
	public BoatMessage getMessage() {
		return m;
	}

	/**
	 *
	 * @return the id of the shoal tha send the message
	 */
	public ShoalId getShoalId() {
		return sId;
	}



	/**
	 * @return the boat id
	 */
	public BoatId getBoatId() {
		return id;
	}
}
