/**
 * 
 */
package pt.ua.sd.shoal;

import java.rmi.RemoteException;

import pt.ua.sd.communication.toshoal.GoToFeedingAreaMessage;
import pt.ua.sd.communication.toshoal.NoActionMessage;
import pt.ua.sd.communication.toshoal.RetrieveTheNetMessage;
import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.communication.toshoal.TrappedByTheNetMessage;
import pt.ua.sd.log.ILogger;

/**
 * Point of communication with the Shoals
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class MShoal implements ICompleteShoal {

	protected final ShoalId id;
	protected final int ndiroper;
	protected int n_diropers_began_season = 0;
	protected int trappedAmount = 0;
	protected boolean isTrapped = false;
	protected boolean escaped = false;
	protected int n_boat_cast_the_net = 0;

	protected ILogger log;
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
	public MShoal(ShoalId id, int ndiroper, ILogger log) {
		this.ndiroper = ndiroper;
		this.id = id;
		this.log = log;
	}

	/**
	 * @see IShoal#popMsg(boolean)
	 */
	@Override
	synchronized public ShoalMessage popMsg(boolean blocking) {
		ShoalMessage msg;

		if (blocking) {
			while (message == null) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
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
	 * @see IShoalDirOper#seasonBegin()
	 */
	@Override
	public void seasonBegin() throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			n_diropers_began_season++;

			if (n_diropers_began_season == ndiroper) {
				n_diropers_began_season = 0;
				pushMsg(goToFeedingArea);
				notify();
			} else {
				while (n_diropers_began_season != 0) {
					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}

		log.push("Season begin", id.toString(), "", logTick);
	}

	/**
	 * @see IShoalBoat#castTheNet()
	 */
	@Override
	public void castTheNet() throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = log.getClockTick();
			logMessage = (n_boat_cast_the_net + 1) + " of " + 2;

			n_boat_cast_the_net++;

			if (n_boat_cast_the_net == 2) {
				pushMsg(trappedByTheNet);
			}

			while (!isTrapped) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

			// n_boat_cast_the_net = 0;
		}

		log.push("Cast the net", id.toString(), logMessage, logTick);
	}

	/**
	 * @see IShoal#isTrapped(int)
	 */
	@Override
	public void isTrapped(int amount) throws RemoteException {
		int logTick;

		synchronized (this) {
			logTick = log.getClockTick();

			isTrapped = true;
			trappedAmount = amount;
			notifyAll();

			while (isTrapped) {
				try {
					wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}

		log.push("Trapped", id.toString(), "in the net: " + amount, logTick);
	}

	/**
	 * @see IShoalBoat#retrieveTheNet()
	 */
	@Override
	public int retrieveTheNet() throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = log.getClockTick();
			logMessage = (n_boat_cast_the_net - 1) + " left";

			n_boat_cast_the_net--;

			if (n_boat_cast_the_net != 0) {
				while (isTrapped) {
					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
			} else {
				isTrapped = false;

				notifyAll();
			}
		}

		log.push("Retrieve the net", id.toString(), logMessage, logTick);

		return trappedAmount;
	}

	protected void pushMsg(ShoalMessage msg) {
		if (message == null || msg.getMsgType().getPriority() <= message.getMsgType().getPriority()) {
			message = msg;
		}
		notify();
	}
}
