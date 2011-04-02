/**
 * 
 */
package pt.ua.sd.diroper;

import java.awt.Point;
import java.util.LinkedList;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.communication.todiroper.BackAtWharfMessage;
import pt.ua.sd.communication.todiroper.BoatFullMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.communication.todiroper.FishingDoneMessage;
import pt.ua.sd.communication.todiroper.LifeEndMessage;
import pt.ua.sd.communication.todiroper.RequestHelpMessage;
import pt.ua.sd.communication.todiroper.SeasonEndMessage;
import pt.ua.sd.log.MClock;
import pt.ua.sd.log.MLog;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class MDirOper implements IDirOper, IDirOperBoat, IDirOperShoal {
	protected final int nshoals;
	protected final int nboats;
	protected final DirOperId id;

	protected LinkedList<DirOperMessage> messages = new LinkedList<DirOperMessage>();
	protected DirOperMessage seasonEnd = new SeasonEndMessage();
	protected DirOperMessage lifeEnd = new LifeEndMessage();

	protected MClock clock = MClock.getClock();
	protected MLog log = MLog.getInstance();

	protected int n_shoals_ended_season = 0;
	protected int n_shoals_ended_life = 0;

	/**
	 * Construct a new DirOper monitor. This is used to send messages to the
	 * DirOpers.
	 * 
	 * @param id
	 *            the id of this DirOper.
	 * @param nshoals
	 *            the number of shoals this DirOper expects to handle.
	 * @param nboats
	 *            the number of boats this DirOper handles.
	 */
	public MDirOper(DirOperId id, int nshoals, int nboats) {
		this.nshoals = nshoals;
		this.id = id;
		this.nboats = nboats;
	}

	/**
	 * @see IDirOper.#popMsg()
	 */
	@Override
	synchronized public DirOperMessage popMsg() {
		while (!hasMsg()) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new RuntimeException();
			}
		}

		return messages.poll();
	}

	/**
	 * @see IDirOperBoat.#backAtWharf(BoatId, int)
	 */
	@Override
	public void backAtWharf(BoatId id, int stored) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new BackAtWharfMessage(id, stored));
		}

		log.push("Back at wharf", id.toString(), "catched " + stored, logTick);
	}

	/**
	 * @see IDirOperBoat.#fishingDone(BoatId)
	 */
	@Override
	public void fishingDone(BoatId id) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new FishingDoneMessage(id));
		}

		log.push("Fishing done", id.toString(), "", logTick);
	}

	/**
	 * @see IDirOperShoal.#endSeason()
	 */
	@Override
	public void endSeason() {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = clock.getClockTick();
			logMessage = (n_shoals_ended_season + 1) + " of " + nshoals;

			n_shoals_ended_season++;

			if (n_shoals_ended_season == nshoals) {
				logMessage += " (notified)";
				n_shoals_ended_season = 0;
				pushMsg(seasonEnd);
			}
		}

		log.push("Season end", id.toString(), logMessage, logTick);
	}

	/**
	 * @see IDirOperShoal.#endLife()
	 */
	@Override
	public void endLife() {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = clock.getClockTick();
			logMessage = (n_shoals_ended_life + 1) + " of " + nshoals;

			n_shoals_ended_life++;
			if (n_shoals_ended_life == nshoals) {
				logMessage += " (notified)";

				n_shoals_ended_life = 0;
				pushMsg(lifeEnd);
			}
		}

		log.push("Life end", id.toString(), logMessage, logTick);
	}

	/**
	 * @see IDirOperBoat.#requestHelp(BoatId, Point)
	 */
	@Override
	public void requestHelp(BoatId id, Point p) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new RequestHelpMessage(id, p));
		}

		log.push("Request help", id.toString(), "help " + id + " at " + "("
				+ p.y + "," + p.x + ")", logTick);
	}

	/**
	 * @see IDirOperBoat.#boatFull(BoatId);
	 */
	@Override
	public void boatFull(BoatId id) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new BoatFullMessage(id));
		}

		log.push("Boat full", this.id.toString(), id.toString(), logTick);
	}

	/**
	 * @see IDirOper.#clearMessages()
	 */
	@Override
	public void clearMessages() {
		synchronized (this) {
			messages.clear();
		}
	}

	protected boolean hasMsg() {
		return messages.size() > 0;
	}

	protected void pushMsg(DirOperMessage m) {
		messages.push(m);

		notify();
	}
}