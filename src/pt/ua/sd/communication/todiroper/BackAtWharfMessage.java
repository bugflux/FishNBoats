/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import pt.ua.sd.boat.BoatId;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class BackAtWharfMessage extends DirOperMessage {

	protected final BoatId boatId;
	protected final int stored;

	/**
	 * Construct a new BackAtWharf message.
	 * 
	 * @param boatId
	 *            the boat that arrived at the wharf.
	 * @param stored
	 *            the amount of fish that boat brings.
	 */
	public BackAtWharfMessage(BoatId boatId, int stored) {
		this.stored = stored;
		this.boatId = boatId;
	}

	/**
	 * @return the boatId
	 */
	public BoatId getBoatId() {
		return boatId;
	}

	/**
	 * @return the amount of fish stored in this boat
	 */
	public int getStored() {
		return stored;
	}

	/**
	 * @return MESSAGE_TYPE.BackAtWharf
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.BackAtWharf;
	}
}
