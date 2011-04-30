/**
 * 
 */
package pt.ua.sd.communication.toocean;

import java.awt.Point;

import pt.ua.sd.shoal.ShoalId;

/**
 * A specialized Message: Try Move Shoal
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class TryMoveShoalMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1110452369492400229L;

	protected final ShoalId shoal;
	protected final Point p;

	/**
	 * Try to move a given shoal to a position.
	 * 
	 * @param s
	 *            the shoal
	 * @param p
	 *            the position
	 */
	public TryMoveShoalMessage(ShoalId s, Point p) {
		this.shoal = s;
		this.p = p;
	}

	/**
	 * @return the shoal
	 */
	public ShoalId getShoal() {
		return shoal;
	}

	/**
	 * @return the point
	 */
	public Point getPoint() {
		return p;
	}

	/**
	 * @return MESSAGE_TYPE.TryMoveShoal
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.TryMoveShoal;
	}
}
