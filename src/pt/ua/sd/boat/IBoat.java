/**
 * 
 */
package pt.ua.sd.boat;

import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.communication.toboat.BoatMessage;

/**
 * This interface exposes all the methods in the Boat monitor to be used by
 * Boats.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IBoat extends Remote {

	/**
	 * Retrieve the last message for this boat.
	 * 
	 * @param blocking
	 *            use true if the pop should block waiting for a new message,
	 *            instead of returning a NoActionMessage.
	 * @return return the most recent message to this boat.
	 */
	public BoatMessage popMsg(boolean blocking) throws RemoteException;
}
