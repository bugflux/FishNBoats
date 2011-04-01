/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;
import java.util.Date;
import java.util.List;
import java.util.Random;

import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.toboat.BoatMessage.MESSAGE_TYPE;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.diroper.IDirOperBoat;
import pt.ua.sd.ocean.IOceanBoat;
import pt.ua.sd.shoal.IShoalBoat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class TBoat extends Thread {

	protected final IDirOperBoat diroper;
	protected final IOceanBoat ocean;
	protected final IBoat monitor;
	protected final BoatStats stats;
	protected final int period;
	protected Random rand;

	/**
	 * Creates a new boat thread, that will interact with its monitor to follow
	 * orders and update status.
	 * 
	 * @param id
	 *            This boat id.
	 * @param period
	 *            How much should the thread sleep after each processing
	 *            iteration.
	 * @param diroper
	 *            The communication interface with the DirOper.
	 * @param ocean
	 *            The communication interface with the Ocean.
	 * @param boats
	 *            An arra of all boats. This boat is boats[id].
	 */
	public TBoat(BoatStats stats, int period, IDirOperBoat diroper,
			IOceanBoat ocean, IBoat monitor) {
		this.diroper = diroper;
		this.ocean = ocean;
		this.monitor = monitor;
		this.stats = stats;
		this.period = period;
	}

	@Override
	public void run() {
		BoatMessage popMsg;
		boolean lifeEnd = false;
		boolean seasonEnd = false;
		rand = new Random(new Date().getTime() * Thread.currentThread().getId()
				* stats.getId().getBoat());

		Point joiningDestination = null;
		BoatId joiningId = null;
		BoatId helperId = null;

		while (!lifeEnd) {
			while (!seasonEnd) {

				switch (stats.getState()) {
				case at_the_wharf:
					popMsg = monitor.popMsg(true);
					if (MESSAGE_TYPE.SetToHighSea == popMsg.getMsgType()) {
						setToHighSea();
					} else if (MESSAGE_TYPE.LifeEnd == popMsg.getMsgType()) {
						seasonEnd = true;
						lifeEnd = true;
					} else {
						assert false; // cannot receive other messages in this
										// state
					}
					break;

				case searching_for_fish:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.NoAction == popMsg.getMsgType()) {
						searchFish();
					} else if (MESSAGE_TYPE.ChangeCourse == popMsg.getMsgType()) {
						ChangeCourseMessage m = (ChangeCourseMessage) popMsg;
						joiningId = m.getId();
						joiningDestination = m.getNewDestination();
						joinCompanion(joiningId, joiningDestination);
					} else if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else if (MESSAGE_TYPE.HelpRequestServed == popMsg
							.getMsgType()) {
						HelpRequestServedMessage m = (HelpRequestServedMessage) popMsg;
						helperId = m.getHelperId();
						trackSchool(helperId);
					} else {
						assert false; // cannot receive other messages in this
										// state
					}
					break;

				case tracking_a_school:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.NoAction == popMsg.getMsgType()) {
						trackSchool(helperId);
						// searchFish();
					} else if (MESSAGE_TYPE.ChangeCourse == popMsg.getMsgType()) {
						ChangeCourseMessage m = (ChangeCourseMessage) popMsg;
						joiningId = m.getId();
						joiningDestination = m.getNewDestination();
						joinCompanion(joiningId, joiningDestination);
					} else if (MESSAGE_TYPE.HelpRequestServed == popMsg
							.getMsgType()) {
						// assert false;
						HelpRequestServedMessage m = (HelpRequestServedMessage) popMsg;
						helperId = m.getHelperId();
						trackSchool(helperId);
					} else if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else {
						assert false;
					}
					break;

				case joining_a_companion:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.NoAction == popMsg.getMsgType()) {
						joinCompanion(joiningId, joiningDestination);
					} else if (MESSAGE_TYPE.ChangeCourse == popMsg.getMsgType()) {
						ChangeCourseMessage m = (ChangeCourseMessage) popMsg;
						joiningId = m.getId();
						joiningDestination = m.getNewDestination();
						joinCompanion(joiningId, joiningDestination);
					} else if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else {
						assert false;
					}
					break;

				case returning_to_wharf:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						
					}
					returnToWharf();
					break;

				default:
					break;
				}

				sleep();
			}
		}

		System.out.println(stats.getId() + " dying");
	}

	/**
	 * Sets the boat to high sea. What happens is that the boat starts searching
	 * for fish like it was already in high sea (so changing the state).
	 */
	protected void setToHighSea() {
		changeState(INTERNAL_STATE_BOAT.searching_for_fish);
		searchFish();
	}

	/**
	 * Searches for fish in the nearby cell. If fish is found, changes the state
	 * to tracking_a_school and sends a help request to the DirOper.
	 */
	protected void searchFish() {
		List<Point> shoal = ocean.getRadar(stats.getId());
		Point follow;

		// if there is shoal, track!
		if (shoal.size() > 0) {
			// changeState(INTERNAL_STATE_BOAT.tracking_a_school);

			// if it is in this cell, call a friend here, else somewhere else
			if (shoal.contains(stats.getPosition())) {
				follow = stats.getPosition();
			} else { // follow a random one
				follow = shoal.get(rand.nextInt(shoal.size()));
			}

			diroper.requestHelp(stats.getId(), follow);
			changePosition(follow);
		} else {
			changePosition(new Point(rand.nextInt(ocean.getWidth()),
					rand.nextInt(ocean.getHeight())));
		}
	}

	/**
	 * Drive towards a point in order to help a companion. If arrived at the
	 * point and conditions are gathered, fish!
	 * 
	 * @param id
	 *            the id of the companion to join.
	 * @param p
	 *            the point the companion should be at.
	 */
	protected void joinCompanion(BoatId helping, Point p) {
		changeState(INTERNAL_STATE_BOAT.joining_a_companion);

		if (changePosition(p)) {
			IShoalBoat b = ocean.companionDetected(stats.getId(), helping);
			if (b != null) {
				b.castTheNet();
				b.retrieveTheNet();
				changeState(INTERNAL_STATE_BOAT.searching_for_fish);
			}
		}
	}

	/**
	 * Track a school knowing the id of the helper.
	 * 
	 * @param joining
	 *            the Boat they are joining.
	 */
	protected void trackSchool(BoatId helper) {
		changeState(INTERNAL_STATE_BOAT.tracking_a_school);

		// attempt to join with companion right away. if not possible, track
		// school around here and update the help request.
		IShoalBoat b = ocean.companionDetected(stats.getId(), helper);
		if (b != null) {
			b.castTheNet();
			int trapped = b.retrieveTheNet();
			changeCatch(stats.getCatch() + trapped);

			diroper.fishingDone(stats.getId());
			changeState(INTERNAL_STATE_BOAT.searching_for_fish);
		} else {
			searchFish();
		}
	}

	/**
	 * Moves this boat to wharf. This has an internal l
	 */
	protected void returnToWharf() {
		if(changePosition(ocean.getWharf())) {
			changeState(INTERNAL_STATE_BOAT.at_the_wharf);
			diroper.backAtWharf(stats.getId(), stats.getCatch());
		}
	}

	/**
	 * Changes the boat position to the specified point. The ocean decides which
	 * position the boat actually moved to.
	 * 
	 * @param p
	 *            the Point to move to.
	 * @return true if the desired position is now the current position, false
	 *         otherwise.
	 */
	protected boolean changePosition(Point p) {
		stats.setPosition(ocean.tryMoveBoat(stats.getId(), p));
		return p.equals(stats.getPosition());
	}

	/**
	 * Change the current state of the boat, both locally and on the ocean.
	 * 
	 * @param s
	 *            the state to set to.
	 */
	protected void changeState(INTERNAL_STATE_BOAT s) {
		if (stats.getState() != s) {
			stats.setState(s);
			ocean.setBoatState(stats.getId(), stats.getState());
		}
	}

	protected void changeCatch(int store) {
		stats.setCatch(store);
	}

	/**
	 * Sleep for the period amount of time, more or less a bit.
	 */
	protected void sleep() {
		try {
			Thread.sleep(period + (int) (0.1 * period * rand.nextGaussian()));
		} catch (InterruptedException e) {
			interrupt();
		}
	}
}
