/**
 * 
 */
package pt.ua.sd.communication.toocean;

import java.awt.Point;
import pt.ua.sd.shoal.MShoal;

import pt.ua.sd.shoal.ShoalStats;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class AddShoalMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4734097753573997980L;

	protected final ShoalStats shoal;
	protected final Point p;
        protected final MShoal mshoal;

	/**
	 * Add a new shoal to this Ocean.
	 * 
	 * @param s
	 *            the shoal
	 * @param p
	 *            the point to add to.
	 */
	public AddShoalMessage(ShoalStats s, Point p, MShoal mshoal) {
		this.shoal = s;
		this.p = p;
                this.mshoal=mshoal;
	}

        public MShoal getShoalMonitor(){
            return this.mshoal;
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
