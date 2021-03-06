/**
 * 
 */
package pt.ua.sd.diroper;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.boat.BoatId;

/**
 * Exposes DirOper communication interface to Boats
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IDirOperBoat extends Remote {

	/**
	 * Tell the DirOper that a boat arrived at the Wharf. Also indicate how much
	 * fish it caught.
	 * 
	 * @param stored
	 *            the amount of fish caught.
	 */
	public void backAtWharf(BoatId id, int stored) throws RemoteException;

	/**
	 * This method is used to request for help in a catch.
	 * 
	 * @param id
	 *            the id of the boat tracking the fish.
	 * @param p
	 *            the point where the fish was found.
	 */
	public void requestHelp(BoatId id, Point p) throws RemoteException;

	/**
	 * Tell the DirOper that a fishing operation has finished. This is done to
	 * help him manage the companion assignment management.
	 * 
	 * @param id
	 *            the id of the boat that requested the help.
	 */
	public void fishingDone(BoatId id) throws RemoteException;

	/**
	 * Inform the DirOper that no more fish can be stored in a boat.
	 * 
	 * @param id
	 *            the id of the boat that's full.
	 */
	public void boatFull(BoatId id) throws RemoteException;
}
