/**
 * 
 */
package pt.ua.sd.communication.toocean;

import java.awt.Point;

import pt.ua.sd.boat.BoatStats;

/**
 * A specialized Message: Add Boat
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class AddBoatMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5782080989376661102L;

	protected final BoatStats boat;
	protected final Point p;

	/**
	 * Add a new boat to this Ocean.
	 * 
	 * @param b
	 *            the boat
	 * @param p
	 *            the point to add to.
	 */
	public AddBoatMessage(BoatStats b, Point p) {
		this.boat = b;
		this.p = p;
	}

	/**
	 * @return the boat
	 */
	public BoatStats getBoat() {
		return boat;
	}

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return p;
	}

	/**
	 * @return MESSAGE_TYPE.AddBoat
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.AddBoat;
	}
}
