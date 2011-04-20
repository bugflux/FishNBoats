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
import pt.ua.sd.communication.toboat.BoatMessage.MESSAGE_TYPE;
import pt.ua.sd.communication.toboat.CastTheNetMessage;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
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
	 * @param period
	 *            How much should the thread sleep after each processing
	 *            iteration.
	 * @param diroper
	 *            The communication interface with the DirOper.
	 * @param ocean
	 *            The communication interface with the Ocean.
	 * @param stats
	 *            This boat's stats
	 * @param monitor
	 *            the monitor of this boat
	 * 
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
		IBoatHelper mHelper = null;

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
						joiningDestination = m.getNewDestination();
						changeState(INTERNAL_STATE_BOAT.joining_a_companion);
					} else if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else if (MESSAGE_TYPE.HelpRequestServed == popMsg
							.getMsgType()) {
						HelpRequestServedMessage m = (HelpRequestServedMessage) popMsg;
						mHelper = m.getHelper();
						mHelper.changeCourse(stats.getPosition());
						changeState(INTERNAL_STATE_BOAT.tracking_a_school);
					} else {
						assert false; // cannot receive other messages in this
						// state
					}
					break;

				case tracking_a_school:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.NoAction == popMsg.getMsgType()) {
						trackSchool(mHelper);
					} else if (MESSAGE_TYPE.ReturnToWharf == popMsg
							.getMsgType()) {
						mHelper.releaseHelper();
						diroper.fishingDone(stats.getId());
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else {
						assert false;
					}
					break;

				case joining_a_companion:
					popMsg = monitor.popMsg(false);
					if (MESSAGE_TYPE.NoAction == popMsg.getMsgType()) {
						joinCompanion(joiningDestination);
					} else if (MESSAGE_TYPE.ChangeCourse == popMsg.getMsgType()) {
						ChangeCourseMessage m = (ChangeCourseMessage) popMsg;
						joiningDestination = m.getNewDestination();
						joinCompanion(joiningDestination);
					} else if (MESSAGE_TYPE.CastTheNet == popMsg.getMsgType()) {
						CastTheNetMessage m = (CastTheNetMessage) popMsg;
						castTheNet(m.getShoal());
					} else if (MESSAGE_TYPE.ReleaseHelper == popMsg
							.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.searching_for_fish);
					} else {
						assert false;
					}
					break;

				case boat_full:
					diroper.boatFull(stats.getId()); // this may provoke that a
					// last returnToWharf
					// message is sent, even
					// when at wharf!
					popMsg = monitor.popMsg(true); // block just because there's
					// nothing else to do
					if (MESSAGE_TYPE.ReturnToWharf == popMsg.getMsgType()) {
						changeState(INTERNAL_STATE_BOAT.returning_to_wharf);
					} else if (MESSAGE_TYPE.ChangeCourse == popMsg.getMsgType()) {
						ChangeCourseMessage m = (ChangeCourseMessage) popMsg;
						joiningDestination = m.getNewDestination();
						changeState(INTERNAL_STATE_BOAT.joining_a_companion);
					} else {
						assert false;
					}
					break;

				case returning_to_wharf:
					popMsg = monitor.popMsg(false); // use this to clear
					// extraneous messages
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
	 * Casts the net on this shoal to catch it. The catch goes to the other guy.
	 * I, the helper, automatically switch to searching_for_fish state.
	 */
	protected void castTheNet(IShoalBoat s) {
		s.castTheNet();
		s.retrieveTheNet();
		conditionalResetState();
	}

	/**
	 * Sets the boat to high sea. What happens is that the boat starts searching
	 * for fish like it was already in high sea (so changing the state).
	 */
	protected void setToHighSea() {
		changeState(INTERNAL_STATE_BOAT.searching_for_fish);
		// searchFish();
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
	 * 
	 * @param p
	 *            the point the companion should be at.
	 */
	protected void joinCompanion(Point p) {
		changeState(INTERNAL_STATE_BOAT.joining_a_companion);
		changePosition(p);
	}

	/**
	 * Track a school knowing the id of the helper.
	 * 
	 * @param helper
	 *            the Boat that is joining.
	 */
	protected void trackSchool(IBoatHelper helper) {
		changeState(INTERNAL_STATE_BOAT.tracking_a_school);

		// attempt to join with companion right away. if not possible, track
		// school around here and update the help request.
		IShoalBoat b = ocean.companionDetected(stats.getId(), helper.getId());
		if (b != null) {
			helper.castTheNet(b);
			b.castTheNet();
			int trapped = b.retrieveTheNet();
			changeCatch(stats.getCatch() + trapped);

			diroper.fishingDone(stats.getId());
			conditionalResetState();
		} else { // search for fish
			List<Point> shoal = ocean.getRadar(stats.getId());
			Point follow;

			// if there is shoal, track!
			if (shoal.size() > 0) {
				// if it is in this cell, call a friend here, if not, somewhere
				// else
				if (shoal.contains(stats.getPosition())) {
					follow = stats.getPosition();
				} else { // follow a random one
					follow = shoal.get(rand.nextInt(shoal.size()));
				}

				// tell the helper again
				if (changePosition(follow)) {
					helper.changeCourse(follow);
				}
			} else {
				helper.releaseHelper();
				diroper.fishingDone(stats.getId());
				conditionalResetState();
				changePosition(new Point(rand.nextInt(ocean.getWidth()),
						rand.nextInt(ocean.getHeight())));
			}
		}
	}

	/**
	 * Moves this boat to wharf. This has an internal l
	 */
	protected void returnToWharf() {
		if (changePosition(ocean.getWharf())) {
			changeState(INTERNAL_STATE_BOAT.at_the_wharf);
			diroper.backAtWharf(stats.getId(), stats.getCatch());
			changeCatch(0);
		}
	}

	/**
	 * This resets the state after main operations. If the boat is full, the
	 * state is set to boat_full. If not, searching_for_fish.
	 */
	protected void conditionalResetState() {
		if (stats.isFull()) {
			changeState(INTERNAL_STATE_BOAT.boat_full);
		} else {
			changeState(INTERNAL_STATE_BOAT.searching_for_fish);
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

	/**
	 * Set the current boat catch, both locally and on the ocean.
	 * 
	 * @param store
	 *            the current catch count.
	 */
	protected void changeCatch(int store) {
		if (stats.getCatch() != store) {
			stats.setCatch(store);
			ocean.setBoatCatch(stats.getId(), stats.getCatch());
		}
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
