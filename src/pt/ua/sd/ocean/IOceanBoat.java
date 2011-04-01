/**
 * 
 */
package pt.ua.sd.ocean;

import java.awt.Point;
import java.util.List;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.shoal.IShoalBoat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IOceanBoat {

	/**
	 * @see #tryMoveBoat(BoatId, Point)
	 */
	// TODO: consider moving to the side or back, in case no cells can
	// be taken in that direction, in order to reduce probability of
	// indefinite wait.
	public Point tryMoveBoat(BoatId id, int x, int y);

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
	public Point tryMoveBoat(BoatId id, Point p);

	/**
	 * Get a list of points where the radar of a given boat BoatId managed to
	 * find fish.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @return a list of points with fish, at the current moment.
	 */
	public List<Point> getRadar(BoatId id);

	/**
	 * Get the number of lines for this ocean instance.
	 * 
	 * @return the number of lines (nlines), or y coordinates, that can be
	 *         occupied, from 0 to nlines-1
	 */
	public int getHeight();

	/**
	 * Get the number of columns for this ocean instance.
	 * 
	 * @return the number of columns (ncolumns), or x coordinates, that can be
	 *         occupied, from 0 to ncolumns-1
	 */
	public int getWidth();

	/**
	 * Update state the of a given boat.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @param state
	 *            the new state to set.
	 */
	public void setBoatState(BoatId id, BoatStats.INTERNAL_STATE_BOAT state);

	/**
	 * Get the position of the wharf associated with this ocean.
	 * 
	 * @return the point that indicates the wharf location.
	 */
	public Point getWharf();

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
	public IShoalBoat companionDetected(BoatId id, BoatId helper);
}
