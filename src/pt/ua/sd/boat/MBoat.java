/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.LinkedList;

import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.communication.toboat.CastTheNetMessage;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.toboat.LifeEndMessage;
import pt.ua.sd.communication.toboat.NoActionMessage;
import pt.ua.sd.communication.toboat.ReleaseHelperMessage;
import pt.ua.sd.communication.toboat.ReturnToWharfMessage;
import pt.ua.sd.communication.toboat.SetToHighSeaMessage;
import pt.ua.sd.log.ILogger;
import pt.ua.sd.shoal.IShoalBoat;

/**
 * This class is the monitor that coordinates all Boat communication.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class MBoat implements ICompleteBoat {

	protected final BoatId id;
	protected LinkedList<BoatMessage> messages = new LinkedList<BoatMessage>();
	protected BoatMessage noAction = new NoActionMessage();
	protected BoatMessage setToHighSea = new SetToHighSeaMessage();
	protected BoatMessage returnToWharf = new ReturnToWharfMessage();
	protected BoatMessage lifeEnd = new LifeEndMessage();
	protected BoatMessage releaseHelper = new ReleaseHelperMessage();

	protected final ILogger log;

	/**
	 * Construct a new boat monitor. This is used to communicate with a single
	 * boat.
	 * 
	 * @param id
	 *            the id of the boat associated with this monitor.
	 */
	public MBoat(BoatId id, ILogger log) {
		this.id = id;
		this.log = log;
	}

	/**
	 * @see IBoat#popMsg(boolean)
	 */
	synchronized public BoatMessage popMsg(boolean blocking) {
		BoatMessage msg;

		if (blocking) {
			while (messages.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}

		msg = messages.poll();
		if (msg == null) {
			msg = noAction;
		}

		return msg;
	}

	/**
	 * @see IBoatDirOper#setToHighSea()
	 */
	@Override
	public void setToHighSea() throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(setToHighSea);
		}

		log.push("Set to high sea", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatDirOper#returnToWharf()
	 */
	@Override
	public void returnToWharf() throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(returnToWharf);
		}

		log.push("Return to wharf", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatHelper#changeCourse(Point)
	 */
	@Override
	public void changeCourse(Point p) throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(new ChangeCourseMessage(p));
		}

		log.push("Change course", id.toString(), "helping at " + "(" + p.y + "," + p.x + ")", logTick);
	}

	/**
	 * @see IBoatDirOper#helpRequestServed(IBoatHelper)
	 */
	@Override
	public void helpRequestServed(IBoatHelper id) throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(new HelpRequestServedMessage(id));
		}

		log.push("Help request served", this.id.toString(), "assigned", logTick);
	}

	/**
	 * @see IBoatHelper#releaseHelper()
	 */
	@Override
	public void releaseHelper() throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(releaseHelper);
		}

		log.push("Release helper", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatHelper#castTheNet(IShoalBoat)
	 */
	@Override
	public void castTheNet(IShoalBoat s) throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(new CastTheNetMessage(s));
		}

		log.push("Cast the net", id.toString(), "to shoal", logTick);
	}

	/**
	 * @see IBoatDirOper#lifeEnd()
	 */
	@Override
	public void lifeEnd() throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			pushMsg(lifeEnd);
		}

		log.push("Life end", id.toString(), "", logTick);
	}

	/**
	 * @see IBoatDirOper#getId()
	 */
	@Override
	// no need to synchronize
	public BoatId getId() {
		return id;
	}

	protected void pushMsg(BoatMessage msg) {
		messages.add(msg);

		notify();
	}
}
