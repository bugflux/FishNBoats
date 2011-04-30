/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.diroper.DirOperStats;

/**
 * A specialized Message: Add DirOper
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class AddDirOperMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2978159350380688613L;

	protected final DirOperStats dirOper;

	/**
	 * Add a new DirOper to this Ocean.
	 * 
	 * @param d
	 *            the diroper
	 */
	public AddDirOperMessage(DirOperStats d) {
		this.dirOper = d;
	}

	/**
	 * @return the dirOperStats
	 */
	public DirOperStats getDirOper() {
		return dirOper;
	}

	/**
	 * @return MESSAGE_TYPE.AddDirOper
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.AddDirOper;
	}
}
