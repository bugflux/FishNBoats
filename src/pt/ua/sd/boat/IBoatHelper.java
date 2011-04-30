/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;

import pt.ua.sd.shoal.IShoalBoat;

/**
 * This interface exposes all the methods in the Boat monitor to be used by a
 * helping boat.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IBoatHelper {

	/**
	 * Get the BoatId associated to this monitor.
	 * 
	 * @return the BoatId
	 */
	public BoatId getId();

	/**
	 * Update the destination this boat should go to.
	 * 
	 * @param p
	 *            the point that represents the new destination.
	 */
	public void changeCourse(Point p);

	/**
	 * Tells this helper to cast the net. The casting is successful and, after
	 * retrieving the net, the helper automatically returns to the
	 * searching_for_fish state.
	 * 
	 * @param s
	 *            the monitor of the shoal to cast the net on.
	 */
	public void castTheNet(IShoalBoat s);

	/**
	 * Releases this helper from a previous catch.
	 */
	public void releaseHelper();
}
