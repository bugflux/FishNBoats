/**
 * 
 */
package pt.ua.sd.ocean;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats;

/**
 * Exposes DirOper methods to Shoals
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IOceanShoal extends Remote {

	/**
	 * Attempt to move shoal ShoalId one square in the direction of point p.
	 * Movements are taxicab based (90 degree only).
	 * 
	 * If two cells are available to perform a movement in the desired
	 * direction, a random one will be chosen. If none can be taken, the shoal
	 * will stay in the same position. If the position is its current, no
	 * movement will be performed.
	 * 
	 * @param id
	 *            the boat to move.
	 * @param p
	 *            the destination point.
	 * @return the new coordinate for boat id.
	 */
	public Point tryMoveShoal(ShoalId id, Point p) throws RemoteException;

	/**
	 * Update the state of a given shoal.
	 * 
	 * @param id
	 *            the id of the shoal.
	 * @param state
	 *            the new state to set.
	 */
	public void setShoalState(ShoalId id, ShoalStats.INTERNAL_STATE_SCHOOL state) throws RemoteException;

	/**
	 * Update the size of a shoal.
	 * 
	 * @param id
	 *            the id of the shoal.
	 * @param size
	 *            the size of the shoal.
	 */
	public void setShoalSize(ShoalId id, int size) throws RemoteException;

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
	 * Get the spawning area of this ocean.
	 * 
	 * @return the Point that indicates the spawning area location in this
	 *         ocean.
	 */
	public Point getSpawningArea() throws RemoteException;
}
