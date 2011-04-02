/**
 * 
 */
package pt.ua.sd.shoal;

import pt.ua.sd.communication.toshoal.ShoalMessage;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IShoal {

	/**
	 * Retrieve the next message for this shoal to process.
	 * 
	 * @param blocking
	 *            if true, the method blocks waiting for a message. If false and
	 *            there is no specific message waiting, a NoActionMessage is
	 *            returned.
	 * @return ShoalMessge message retrieved
	 */
	public ShoalMessage popMsg(boolean blocking);

	/**
	 * This method releases the boats, for the Shoal is accepting that it has
	 * been trapped.
	 */
	public void isTrapped(int amount);
}
