/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.diroper.DirOperStats;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class AddDirOperMessage extends OceanMessage {

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
