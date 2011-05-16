/**
 * 
 */
package pt.ua.sd.boat;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface exposes all the methods in the Boat monitor to be used by
 * DirOpers.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IBoatDirOper extends Remote {

	/**
	 * Order the boat to go to high sea.
	 */
	public void setToHighSea() throws RemoteException;

	/**
	 * Order this boat to return.
	 */
	public void returnToWharf() throws RemoteException;

	/**
	 * Get the id associated with this boat monitor.
	 * 
	 * @return the BoatId.
	 */
	public BoatId getId() throws RemoteException;

	/**
	 * Tell a boat that life has ended. :P
	 */
	public void lifeEnd() throws RemoteException;

	/**
	 * Inform a boat that its request for help has been served and that a boat
	 * should be under way.
	 * 
	 * @param helper
	 *            the monitor of the assigned boat.
	 */
	public void helpRequestServed(IBoatHelper helper) throws RemoteException;
}
