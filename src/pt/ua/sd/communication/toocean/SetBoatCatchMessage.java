/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: Set Boat Catch
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SetBoatCatchMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3156741616318727106L;

	protected final BoatId boat;
	protected final int catched;

	/**
	 * Set a new value for the catch of this Boat.
	 * 
	 * @param b
	 *            the boat
	 * @param c
	 *            the new catched count
	 */
	public SetBoatCatchMessage(BoatId b, int c) {
		this.boat = b;
		this.catched = c;
	}

	/**
	 * @return the boat
	 */
	public BoatId getBoat() {
		return boat;
	}

	/**
	 * @return the catched count
	 */
	public int getCatched() {
		return catched;
	}

	/**
	 * @return MESSAGE_TYPE.SetBoatCatch
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetBoatCatch;
	}
}
