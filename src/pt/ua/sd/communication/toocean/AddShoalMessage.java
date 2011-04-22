/**
 * 
 */
package pt.ua.sd.communication.toocean;

import java.awt.Point;

import pt.ua.sd.shoal.ShoalStats;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class AddShoalMessage extends OceanMessage {

	protected final ShoalStats shoal;
	protected final Point p;

	/**
	 * Add a new shoal to this Ocean.
	 * 
	 * @param s
	 *            the shoal
	 * @param p
	 *            the point to add to.
	 */
	public AddShoalMessage(ShoalStats s, Point p) {
		this.shoal = s;
		this.p = p;
	}

	/**
	 * @return the shoal
	 */
	public ShoalStats getShoal() {
		return shoal;
	}

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return p;
	}

	/**
	 * @return MESSAGE_TYPE.AddShoal
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.AddShoal;
	}
}
