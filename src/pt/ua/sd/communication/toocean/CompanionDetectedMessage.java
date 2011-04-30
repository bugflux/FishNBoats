/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: CompanionDetected
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class CompanionDetectedMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8646584775253913685L;

	protected final BoatId boat;
	protected final BoatId helper;

	/**
	 * Detect if all conditions are gathered to start fishing with a helper
	 * boat.
	 * 
	 * @param b
	 *            the boat
	 * @param h
	 *            the helper boat
	 */
	public CompanionDetectedMessage(BoatId b, BoatId h) {
		this.boat = b;
		this.helper = h;
	}

	/**
	 * @return the fishing boat
	 */
	public BoatId getBoat() {
		return boat;
	}

	/**
	 * @return the helper boat
	 */
	public BoatId getHelper() {
		return helper;
	}

	/**
	 * @return MESSAGE_TYPE.CompanionDetected
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.CompanionDetected;
	}
}
