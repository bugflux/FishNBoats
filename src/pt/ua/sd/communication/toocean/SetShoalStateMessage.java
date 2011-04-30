/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;

/**
 * A specialized Message: Set Shoal State
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SetShoalStateMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6209681820954271698L;

	protected final ShoalId shoal;
	protected final INTERNAL_STATE_SCHOOL state;

	/**
	 * Set a new state for a given Shoal in this Ocean.
	 * 
	 * @param s
	 *            the shoal
	 * @param st
	 *            the new state
	 */
	public SetShoalStateMessage(ShoalId s, INTERNAL_STATE_SCHOOL st) {
		this.shoal = s;
		this.state = st;
	}

	/**
	 * @return the shoal
	 */
	public ShoalId getShoal() {
		return shoal;
	}

	/**
	 * @return the state
	 */
	public INTERNAL_STATE_SCHOOL getState() {
		return state;
	}

	/**
	 * @return MESSAGE_TYPE.SetShoalState
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetShoalState;
	}
}
