/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;

/**
 * A specialized Message: SetBoatState
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SetBoatStateMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4732700979808195557L;

	protected final BoatId boat;
	protected final INTERNAL_STATE_BOAT state;

	/**
	 * Set a new state for a given Boat in this Ocean.
	 * 
	 * @param b
	 *            the boat
	 * @param s
	 *            the new state
	 */
	public SetBoatStateMessage(BoatId b, INTERNAL_STATE_BOAT s) {
		this.boat = b;
		this.state = s;
	}

	/**
	 * @return the boat
	 */
	public BoatId getBoat() {
		return boat;
	}

	/**
	 * @return the state
	 */
	public INTERNAL_STATE_BOAT getState() {
		return state;
	}

	/**
	 * @return MESSAGE_TYPE.SetBoatState
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetBoatState;
	}
}
