/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.shoal.IShoalBoat;

/**
 * This interface exposes all the methods in the Boat monitor to be used by a
 * helping boat.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IBoatHelper extends Remote {

	/**
	 * Get the BoatId associated to this monitor.
	 * 
	 * @return the BoatId
	 */
	public BoatId getId() throws RemoteException;

	/**
	 * Update the destination this boat should go to.
	 * 
	 * @param p
	 *            the point that represents the new destination.
	 */
	public void changeCourse(Point p) throws RemoteException;

	/**
	 * Tells this helper to cast the net. The casting is successful and, after
	 * retrieving the net, the helper automatically returns to the
	 * searching_for_fish state.
	 * 
	 * @param s
	 *            the monitor of the shoal to cast the net on.
	 */
	public void castTheNet(IShoalBoat s) throws RemoteException;

	/**
	 * Releases this helper from a previous catch.
	 */
	public void releaseHelper() throws RemoteException;
}
