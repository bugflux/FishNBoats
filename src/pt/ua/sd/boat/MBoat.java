/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;

import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.toboat.LifeEndMessage;
import pt.ua.sd.communication.toboat.NoActionMessage;
import pt.ua.sd.communication.toboat.ReturnToWharfMessage;
import pt.ua.sd.communication.toboat.SetToHighSeaMessage;
import pt.ua.sd.log.MClock;
import pt.ua.sd.log.MLog;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class MBoat implements IBoat, IBoatDirOper {

	protected final BoatId id;

	protected BoatMessage message;
	protected BoatMessage noAction = new NoActionMessage();
	protected BoatMessage setToHighSea = new SetToHighSeaMessage();
	protected BoatMessage returnToWharf = new ReturnToWharfMessage();
	protected BoatMessage lifeEnd = new LifeEndMessage();

	protected final MClock clock = MClock.getClock();
	protected final MLog log = MLog.getInstance();

	/**
	 * Construct a new boat monitor. This is used to communicate with a single
	 * boat.
	 * 
	 * @param id
	 *            the id of the boat associated with this monitor.
	 */
	public MBoat(BoatId id) {
		this.id = id;
	}

	/**
	 * @see IBoat.#popMsg(boolean)
	 */
	synchronized public BoatMessage popMsg(boolean blocking) {
		BoatMessage msg;

		if (blocking) {
			while (message == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException();
				}
			}
		}

		msg = message;
		message = null;
		if (msg == null) {
			msg = noAction;
		}

		return msg;
	}

	/**
	 * @see IBoatDirOper.#setToHighSea()
	 */
	@Override
	public void setToHighSea() {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(setToHighSea);
		}

		log.push("Set to high sea", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatDirOper.#returnToWharf()
	 */
	@Override
	public void returnToWharf() {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(returnToWharf);
		}

		log.push("Return to wharf", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatDirOper.#changeCourse(BoatId, Point)
	 */
	@Override
	public void changeCourse(BoatId id, Point p) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new ChangeCourseMessage(id, p));
		}

		log.push("Change course", this.id.toString(),
				"helping " + id.toString() + " at " + "(" + p.y + "," + p.x
						+ ")", logTick);
	}

	/**
	 * @see IBoatDirOper.#helpRequestServed(BoatId)
	 */
	@Override
	public void helpRequestServed(BoatId id) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(new HelpRequestServedMessage(id));
		}

		log.push("Help request served", this.id.toString(),
				"assigned " + id.toString(), logTick);
	}

	/**
	 * @see IBoatDirOper.#lifeEnd()
	 */
	@Override
	public void lifeEnd() {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			pushMsg(lifeEnd);
		}

		log.push("Life end", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatDirOper.#getId()
	 */
	@Override
	// no need to synchronize
	public BoatId getId() {
		return id;
	}

	protected void pushMsg(BoatMessage msg) {
		if (message == null) {
			message = msg;
			notify();
		} else if (msg.getMsgType().getPriority() <= message.getMsgType()
				.getPriority()) {
			message = msg;
			notify();
		}
	}
}