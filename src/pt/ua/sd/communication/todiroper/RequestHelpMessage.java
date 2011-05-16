/**
 * 
 */
package pt.ua.sd.communication.todiroper;

import java.awt.Point;
import pt.ua.sd.boat.BoatId;

/**
 * A specialized Message: Request Help of a boat
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class RequestHelpMessage extends DirOperMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1925243343218662168L;
	protected final Point location;
	protected final BoatId boatId;

	/**
	 * Constructs a new RequestHelp message to the DirOper to send one boat to a
	 * given location and help catch fish.
	 * 
	 * @param boatId
	 *            The ID of the boat requesting help.
	 * @param location
	 *            The current location where the catch is planned.
	 */
	public RequestHelpMessage(BoatId boatId, Point location) {
		this.location = location;
		this.boatId = boatId;
	}

	/**
	 * @return the location
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @return the boatId
	 */
	public BoatId getBoatId() {
		return boatId;
	}

	/**
	 * @return MESSAGE_TYPE.RequestHelp
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.RequestHelp;
	}
}
