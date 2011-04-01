/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IBoatDirOper {

	/**
	 * Order the boat to go to high sea.
	 */
	public void setToHighSea();

	/**
	 * Order this boat to return.
	 */
	public void returnToWharf();

	/**
	 * Get the id associated with this boat monitor.
	 * 
	 * @return the BoatId.
	 */
	public BoatId getId();

	/**
	 * Tell a boat that life has ended. :P
	 */
	public void lifeEnd();

	/**
	 * Order a boat to go to a specific point help a friend.
	 * 
	 * @param id
	 *            the id of the boat it should help.
	 * @param p
	 *            the location where that boat should be.
	 */
	public void changeCourse(BoatId id, Point p);

	/**
	 * Inform a boat that its request for help has been served and that a boat
	 * should be under way.
	 * 
	 * @param id
	 *            the id of the assigned boat.
	 */
	public void helpRequestServed(BoatId id);

	// /**
	// * Order this boat to go to a specific point, to help a companion in a
	// catch.
	// *
	// * @param p the point to go to.
	// */
	// public void changeCourse(Point p, BoatId id);
	//
	// /**
	// * Inform this boat that its request for help in a catch was served,
	// * and which boat is expected to arrive to help.
	// *
	// * @param id the id of the helper boat.
	// */
	// public void servedHelpRequest(BoatId id);
	//
	// /**
	// * Tells a boat to resume prowling after a catch.
	// */
	// public void resumeProwling();
}
