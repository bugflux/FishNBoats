/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.shoal.ShoalId;

/**
 * A specialized Message: Set Shoal Size
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class SetShoalSizeMessage extends OceanMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687398894238776658L;

	protected final ShoalId shoal;
	protected final int size;

	/**
	 * Set the size for a given Shoal in this Ocean.
	 * 
	 * @param s
	 *            the shoal
	 * @param size
	 *            the new size
	 */
	public SetShoalSizeMessage(ShoalId s, int size) {
		this.shoal = s;
		this.size = size;
	}

	/**
	 * @return the shoal
	 */
	public ShoalId getShoal() {
		return shoal;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return MESSAGE_TYPE.SetShoalSize
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.SetShoalSize;
	}
}
