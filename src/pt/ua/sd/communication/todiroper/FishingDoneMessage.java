/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: Fishing operation is Done
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class FishingDoneMessage extends DirOperMessage {

	protected final BoatId id;

	public FishingDoneMessage(BoatId id) {
		this.id = id;
	}

	/**
	 * @return id
	 */
	public BoatId getId() {
		return id;
	}

	/**
	 * @return MESSAGE_TYPE.FishingDone
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.FishingDone;
	}
}
