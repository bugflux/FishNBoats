/**
 * 
 */
package pt.ua.sd.communication.toboat;

import java.awt.Point;

/**
 * A specialized Message: Change Course
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class ChangeCourseMessage extends BoatMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5307135674046369045L;
	protected final Point newDestination;

	/**
	 * Constructs a ChangeCourse message with the order to move to a new
	 * destination point.
	 * 
	 * @param newDestination
	 *            the new destination Point for the boat to move to.
	 */
	public ChangeCourseMessage(Point newDestination) {
		this.newDestination = newDestination;
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
