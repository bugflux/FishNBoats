/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class SetDirOperStateMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 104150735150615880L;

	protected final DirOperId dirOper;
	protected final INTERNAL_STATE_DIROPER state;

	/**
	 * Set a new state for a given DirOper in this Ocean.
	 * 
	 * @param d
	 *            the diroper
	 * @param s
	 *            the new state
	 */
	public SetDirOperStateMessage(DirOperId d, INTERNAL_STATE_DIROPER s) {
		this.dirOper = d;
		this.state = s;
	}

	/**
	 * @return the dirOper
	 */
	public DirOperId getDirOper() {
		return dirOper;
	}

	/**
	 * @return the state
	 */
	public INTERNAL_STATE_DIROPER getState() {
		return state;
	}

	/**
	 * @return MESSAGE_TYPE.SetDirOperState
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetDirOperState;
	}
}
