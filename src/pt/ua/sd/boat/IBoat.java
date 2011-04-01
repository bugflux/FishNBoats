/**
 * 
 */
package pt.ua.sd.boat;

import pt.ua.sd.communication.toboat.BoatMessage;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IBoat {

	/**
	 * Retrieve the last message for this boat.
	 * 
	 * @param blocking
	 *            use true if the pop should block waiting for a new message,
	 *            instead of returning a NoActionMessage.
	 * @return return the most recent message to this boat.
	 */
	public BoatMessage popMsg(boolean blocking);
}
