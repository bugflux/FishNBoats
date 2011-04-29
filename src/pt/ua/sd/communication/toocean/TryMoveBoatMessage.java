/**
 * 
 */
package pt.ua.sd.communication.toocean;

import java.awt.Point;

import pt.ua.sd.boat.BoatId;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class TryMoveBoatMessage extends OceanMessage {

	/**
     * 
     */
	private static final long serialVersionUID = -582529586006724310L;
	protected final BoatId boat;
	protected final Point p;

	/**
	 * Try to move a given boat to a position.
	 * 
	 * @param b
	 *            the boat
	 * @param p
	 *            the position
	 */
	public TryMoveBoatMessage(BoatId b, Point p) {
		assert p != null;
		this.boat = b;
		this.p = p;
	}

	/**
	 * @return the boat
	 */
	public BoatId getBoat() {
		return boat;
	}

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return p;
	}

	/**
	 * @return MESSAGE_TYPE.TryMoveBoat
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.TryMoveBoat;
	}
}
