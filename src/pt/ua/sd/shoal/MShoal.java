/**
 * 
 */
package pt.ua.sd.shoal;

import java.util.logging.Level;
import java.util.logging.Logger;

import pt.ua.sd.communication.toshoal.GoToFeedingAreaMessage;
import pt.ua.sd.communication.toshoal.NoActionMessage;
import pt.ua.sd.communication.toshoal.RetrieveTheNetMessage;
import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.communication.toshoal.TrappedByTheNetMessage;
import pt.ua.sd.log.MClock;
import pt.ua.sd.log.MLog;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class MShoal implements IShoal, IShoalBoat, IShoalDirOper {

	protected final ShoalId id;
	protected final int ndiroper;

	protected int n_diropers_began_season = 0;
	protected int trappedAmount = 0;
	protected boolean isTrapped = false;
	protected boolean escaped = false;

	protected int n_boat_cast_the_net = 0;

	protected MClock clock = MClock.getClock();
	protected MLog log = MLog.getInstance();

	protected ShoalMessage message;
	protected ShoalMessage goToFeedingArea = new GoToFeedingAreaMessage();
	protected ShoalMessage noAction = new NoActionMessage();
	protected ShoalMessage trappedByTheNet = new TrappedByTheNetMessage();
	protected ShoalMessage retrieveTheNet = new RetrieveTheNetMessage();

	/**
	 * Instantiate a new MShoal object with given initial stats.
	 * 
	 * @param id
	 *            the associated ShoalId.
	 * @param ndiroper
	 *            the number of DirOpers that exist.
	 */
	public MShoal(ShoalId id, int ndiroper) {
		this.ndiroper = ndiroper;
		this.id = id;
	}

	/**
	 * @see IShoal.#popMsg()
	 */
	@Override
	synchronized public ShoalMessage popMsg(boolean blocking) {
		ShoalMessage msg;

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
	 * @see IShoalDirOper.#seasonBegin()
	 */
	@Override
	public void seasonBegin() {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			n_diropers_began_season++;

			if (n_diropers_began_season == ndiroper) {
				n_diropers_began_season = 0;
				pushMsg(goToFeedingArea);
			}
		}

		log.push("Season begin", id.toString(), "", logTick);
	}

	/**
	 * @see IShoalBoat.#castTheNet()
	 */
	@Override
	public void castTheNet() {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = clock.getClockTick();
			logMessage = (n_boat_cast_the_net + 1) + " of " + 2;

			n_boat_cast_the_net++;

			if (n_boat_cast_the_net == 2) {
				pushMsg(trappedByTheNet);
			}

			while (!isTrapped) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException();
				}
			}

			// n_boat_cast_the_net = 0;
		}

		log.push("Cast the net", id.toString(), logMessage, logTick);
	}

	/**
	 * @see IShoal.#isTrapped(int)
	 */
	@Override
	public void isTrapped(int amount) {
		int logTick;

		synchronized (this) {
			logTick = clock.getClockTick();

			isTrapped = true;
			trappedAmount = amount;
			notifyAll();

			while (isTrapped) {
				try {
					wait();
				} catch (InterruptedException ex) {
					Logger.getLogger(MShoal.class.getName()).log(Level.SEVERE,
							null, ex);
				}
			}
		}

		log.push("Trapped", id.toString(), "in the net: " + amount, logTick);
	}

	/**
	 * @see IShoalBoat.#retrieveTheNet()
	 */
	@Override
	public int retrieveTheNet() {
		int logTick;
		String logMessage;
		int captured = 0;

		synchronized (this) {
			logTick = clock.getClockTick();
			logMessage = (n_boat_cast_the_net - 1) + " left";

			n_boat_cast_the_net--;

			if (n_boat_cast_the_net != 0) {
				while (isTrapped) {
					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException();
					}
				}
			} else {
				isTrapped = false;

				notifyAll();
			}

			captured = trappedAmount;
			trappedAmount = 0;
		}

		log.push("Retrieve the net", id.toString(), logMessage, logTick);

		return captured;
	}

	/**
	 * @see IShoal.#releaseRemainers()
	 */
	@Override
	public void releaseRemainers() {

		// this does the "trapped by the net" sequence in case there is some
		// astray waiting
		synchronized(this) {
			if (n_boat_cast_the_net == 1) {
				isTrapped = true;
				trappedAmount = 0;
	
				notify();
	
				while (isTrapped) {
					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException();
					}
				}
	
				isTrapped = false;
				notify();
			}
		}
	}

	protected void pushMsg(ShoalMessage msg) {
		if (message == null
				|| msg.getMsgType().getPriority() <= message.getMsgType()
						.getPriority()) {
			message = msg;
		}
		notify();
	}
}