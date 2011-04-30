/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: Get Radar
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class GetRadarMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1925538413943736717L;

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
