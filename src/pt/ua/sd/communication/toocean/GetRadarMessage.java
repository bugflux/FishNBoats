/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.boat.BoatId;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class GetRadarMessage extends OceanMessage {

	protected final BoatId boat;

	/**
	 * Get the radar result for a given boat.
	 * 
	 * @param b
	 *            the boat
	 */
	public GetRadarMessage(BoatId b) {
		this.boat = b;
	}

	/**
	 * @return the boat
	 */
	public BoatId getBoat() {
		return boat;
	}

	/**
	 * @return MESSAGE_TYPE.GetRadar
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GetRadar;
	}
}
