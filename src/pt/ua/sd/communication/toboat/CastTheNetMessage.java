/**
 * 
 */
package pt.ua.sd.communication.toboat;

import pt.ua.sd.shoal.IShoalBoat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class CastTheNetMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1663544824383032288L;

	protected final IShoalBoat s;

	/**
	 * Constructs a CastTheNetMessage with the monitor of the shoal.
	 * 
	 * @param s
	 *            the monitor of the shoal to cast the net on.
	 */
	public CastTheNetMessage(IShoalBoat s) {
		this.s = s;
	}

	/**
	 * Get monitor of the shoal.
	 * 
	 * @return the monitor.
	 */
	public IShoalBoat getShoal() {
		return s;
	}

	/**
	 * @return MESSAGE_TYPE.CastTheNet
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.CastTheNet;
	}
}
