/**
 * 
 */
package pt.ua.sd.ocean;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.shoal.IShoalBoat;

/**
 * Exposes Ocean methods to Boats
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IOceanBoat extends Remote {

	/**
	 * Attempt to move boat BoatId one square in the direction of point p.
	 * Movements are taxicab based (90 degree only).
	 * 
	 * If two cells are available to perform a movement in the desired
	 * direction, a random one will be chosen. If none can be taken, the boat
	 * will stay in the same position. If the position is its current, no
	 * movement will be performed.
	 * 
	 * @param id
	 *            the boat to move.
	 * @param p
	 *            the destination point.
	 * @return the new coordinate for boat id.
	 */
	public Point tryMoveBoat(BoatId id, Point p) throws RemoteException;

	/**
	 * Get a list of points where the radar of a given boat BoatId managed to
	 * find fish.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @return a list of points with fish, at the current moment.
	 */
	public List<Point> getRadar(BoatId id) throws RemoteException;

	/**
	 * Get the number of lines for this ocean instance.
	 * 
	 * @return the number of lines (nlines), or y coordinates, that can be
	 *         occupied, from 0 to nlines-1
	 */
	public int getHeight() throws RemoteException;

	/**
	 * Get the number of columns for this ocean instance.
	 * 
	 * @return the number of columns (ncolumns), or x coordinates, that can be
	 *         occupied, from 0 to ncolumns-1
	 */
	public int getWidth() throws RemoteException;

	/**
	 * Update state the of a given boat.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @param state
	 *            the new state to set.
	 */
	public void setBoatState(BoatId id, BoatStats.INTERNAL_STATE_BOAT state) throws RemoteException;

	/**
	 * Set the catch storage for a boat.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @param stored
	 *            the (total) amount stored.
	 */
	public void setBoatCatch(BoatId id, int stored) throws RemoteException;

	/**
	 * Get the position of the wharf associated with this ocean.
	 * 
	 * @return the point that indicates the wharf location.
	 */
	public Point getWharf() throws RemoteException;

	/**
	 * verify if the designated helper for a Boat has arrived.
	 * 
	 * @param id
	 *            the boat id
	 * @param helper
	 *            the helper id
	 * @return the monitor of the shoal if the conditions are gathered to fish
	 *         or null
	 */
	public IShoalBoat companionDetected(BoatId id, BoatId helper) throws RemoteException;
}
