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
public class BoatFullMessage extends DirOperMessage {

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
