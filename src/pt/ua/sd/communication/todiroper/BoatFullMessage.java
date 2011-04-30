/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: Boat is full
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class BoatFullMessage extends DirOperMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1249930885495037978L;

	protected final BoatId id;

	/**
	 * Construct a new boat full message. This message is used to inform the
	 * DirOper that it can no longer catch fish.
	 * 
	 * @param id
	 *            the id of the boat that's full.
	 */
	public BoatFullMessage(BoatId id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public BoatId getId() {
		return id;
	}

	/**
	 * @return MESSAGE_TYPE.BoatFull
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.BoatFull;
	}
}
