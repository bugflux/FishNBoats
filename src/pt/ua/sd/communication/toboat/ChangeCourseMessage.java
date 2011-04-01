/**
 * 
 */
package pt.ua.sd.communication.toboat;

import java.awt.Point;
import pt.ua.sd.boat.BoatId;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class ChangeCourseMessage extends BoatMessage {

	protected final Point newDestination;
	protected final BoatId id;

	/**
	 * Constructs a ChangeCourse message with the order
	 * to move to a new destination point.
	 * 
	 * @param newDestination the new destination Point for the boat to move to.
	 * @param id the id of the boat to join with
	 */
	public ChangeCourseMessage(BoatId id, Point newDestination) {
		this.newDestination = newDestination;
		this.id = id;
	}

	/**
	 * return the id of the boat
	 * @return boat id
	 */
	public BoatId getId() {
		return id;
	}

	/**
	 * Get the Point this message orders to move to.
	 * 
	 * @return the Point.
	 */
	public Point getNewDestination() {
		return newDestination;
	}

	/**
	 * @return MESSAGE_TYPE.ChangeCourse
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.ChangeCourse;
	}
}
