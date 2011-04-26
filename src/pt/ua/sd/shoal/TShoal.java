/**
 * 
 */
package pt.ua.sd.shoal;

import java.awt.Point;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.communication.toshoal.ShoalMessage.MESSAGE_TYPE;
import pt.ua.sd.diroper.IDirOperShoal;
import pt.ua.sd.ocean.IOceanShoal;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class TShoal extends Thread {

	protected final IShoal monitor;
	protected final IOceanShoal ocean;
	protected final IDirOperShoal diropers[];
	protected ShoalStats stats;
	protected final int period;
	protected final int seasonMoves;
	protected final int nCampaigns;
	protected final int growing_factor;
	protected final double eco_system_capacity;
	protected final double maxCatchPercentage;

	// get these at the beginning for improved efficiency
	protected final int oceanHeight, oceanWidth;
	protected final Point oceanSpawningArea;
	protected Socket socket;
	
	/**
	 * Construct a new Shoal Thread. Shoals execute at least nCampaign times
	 * seasonMoves iterations. It is the responsibility of the Shoal to update
	 * the stats in the ocean.
	 * 
	 * @param stats
	 *            the stats of this Shoal.
	 * @param period
	 *            the time the shoal should sleep at the end of each iteration.
	 * @param seasonMoves
	 *            the number of moves the Shoal should attempt in each season
	 *            (failed attempts are accounted as well).
	 * @param nCampaigns
	 *            the number of campaigns the Shoal should execute.
	 * @param monitor
	 *            the communication monitor associated with this Shoal.
	 * @param ocean
	 *            the communication monitor associated with the Ocean.
	 * @param diroper
	 *            the communication monitor associated with the DirOpers.
	 * @param growing_factor
	 *            parameter used to calculate the reproduction quantities.
	 * @param eco_system_capacity
	 *            parameters used to calculate the reproduction quantities.
	 * @param maxCatchPercentage
	 *            the maximum Fish that might be caught by the net. A percentage
	 *            of the total size of the fish in a given moment. [0..1].
	 */
	public TShoal(ShoalStats stats, int period, int seasonMoves,
			int nCampaigns, IShoal monitor, IOceanShoal ocean,
			IDirOperShoal diroper[], int growing_factor,
			double eco_system_capacity, double maxCatchPercentage) {
		this.period = period;
		this.monitor = monitor;
		this.ocean = ocean;
		this.stats = stats;
		this.seasonMoves = seasonMoves;
		this.diropers = diroper;
		this.eco_system_capacity = eco_system_capacity;
		this.growing_factor = growing_factor;
		this.maxCatchPercentage = maxCatchPercentage;
		this.nCampaigns = nCampaigns;

		this.oceanHeight = ocean.getHeight();
		this.oceanWidth = ocean.getWidth();
		this.oceanSpawningArea = ocean.getSpawningArea();
	}
	protected Random rand;

	@Override
	public void run() {
		rand = new Random(new Date().getTime() * Thread.currentThread().getId()
				* stats.getId().getShoal());
		ShoalMessage popMsg;
		int campaigns;
		int moves;
		boolean seasonOver;

		// set initial shoal stats in the ocean
		changeState(INTERNAL_STATE_SCHOOL.spawning);
		changeSize(stats.getSize());

		campaigns = 0;
		while (campaigns < nCampaigns) {

			seasonOver = false;
			moves = 0;
			while (!seasonOver) {

				switch (stats.getState()) {
					case spawning:
						// BLOCK waiting to go to feeding area
						popMsg = monitor.popMsg(true);
						if (MESSAGE_TYPE.GoToFeedingArea == popMsg.getMsgType()) {
							goToFeedingArea();
						} else {
							assert false; // cannot receive other messages in this
							// state
						}
						break;

					case feeding:
						feed(); // does nothing

						popMsg = monitor.popMsg(false);
						if (MESSAGE_TYPE.NoActionMessage == popMsg.getMsgType()) {
							if (moves < seasonMoves) {
								swimAbout();
							} else {
								seasonOver = swimToSpawningArea();
							}
						} else if (MESSAGE_TYPE.TrappedByTheNet == popMsg.getMsgType()) {
							isTrapped();
						} else {
							assert false; // cannot receive other messages in this
							// state
						}
						break;

					case trapped_by_the_net:
						trap(); // indicate how many fish were lost
						escapeTheNet();
						break;

					default:
						assert false;
				}

				moves++;
				sleep();
			}

			campaigns++;
			// don't set seasonEnd in the last one, just lifeEnd!
			if (campaigns < nCampaigns) {
				seasonEnd();
			}
		}

		lifeEnd();
		System.out.println(stats.getId() + " dying");
	}

	/**
	 * Updates the number of fish in this shoal, according to the ecosystem
	 * rules.
	 */
	protected void spawn() {
		int currentSize = stats.getSize();
		int newSize = (int) (currentSize * (1 + growing_factor - eco_system_capacity
				* currentSize));
		changeSize(newSize);
	}

	/**
	 * Sends this shoal to the feeding area. Means just that the state is
	 * changed to feeding.
	 */
	protected void goToFeedingArea() {
		changeState(INTERNAL_STATE_SCHOOL.feeding);
	}

	/**
	 * This method does nothing. It exists for consistency purposes only.
	 */
	protected void feed() {
	}

	/**
	 * The shoal tries to move to a random position.
	 */
	protected void swimAbout() {
		changePosition(new Point(rand.nextInt(oceanWidth),
				rand.nextInt(oceanHeight)));
	}

	/**
	 * The shoal tries to move to the reproducing position.
	 * 
	 * @return true if it has reached it, false otherwise.
	 */
	protected boolean swimToSpawningArea() {
		return changePosition(oceanSpawningArea);
	}

	/**
	 * Accept being trapped. Basically changes the state of this shoal to
	 * trapped_by_the_net.
	 */
	protected void isTrapped() {
		changeState(INTERNAL_STATE_SCHOOL.trapped_by_the_net);
	}

	/**
	 * Effectively gets trapped. This releases the boats locked on casting the
	 * net so they can retrieve the net. This also indicates how many fish was
	 * trapped in the net during the process.
	 */
	protected void trap() {
		int maxTrapped = (int) (stats.getSize() * maxCatchPercentage);
		int trapped = rand.nextInt(maxTrapped);
		int remaining = stats.getSize() - trapped;

		changeSize(remaining);

		monitor.isTrapped(trapped);
	}

	/**
	 * Escape the net. The boats may or may not cast the net again.
	 */
	protected void escapeTheNet() {
		// if the shoal changes to the feeding state before releasing the net,
		// the fish will be detected sooner >P
		changeState(INTERNAL_STATE_SCHOOL.feeding);
	}

	/**
	 * Sleep for the period amount of time, more or less a bit.
	 */
	protected void sleep() {
		try {
			Thread.sleep(period + (int) (0.1 * period * rand.nextGaussian()));
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Ends a season. Informs the DirOpers that the Shoal is reproducing.
	 */
	protected void seasonEnd() {
		changeState(INTERNAL_STATE_SCHOOL.spawning);
		spawn();

		for (IDirOperShoal d : diropers) {
			d.endSeason();
		}
	}

	/**
	 * Ends the life. Informs the DirOpers that the Shoal is dying.
	 */
	protected void lifeEnd() {
		// changeState(INTERNAL_STATE_SCHOOL.spawning);
		for (IDirOperShoal d : diropers) {
			d.endLife();
		}
	}

	/**
	 * Change the state of this shoal, both locally and on the ocean.
	 * 
	 * @param s
	 *            the state to set to.
	 */
	protected void changeState(INTERNAL_STATE_SCHOOL s) {
		if (stats.getState() != s) {
			stats.setState(s);
			ocean.setShoalState(stats.getId(), s);
		}
	}

	/**
	 * Change the size of this shoal, both locally and on the ocean.
	 * 
	 * @param size
	 *            the size to set to.
	 */
	protected void changeSize(int size) {
		stats.setSize(size);
		ocean.setShoalSize(stats.getId(), size);
	}

	/**
	 * Change the position of this shoal, both locally and on the ocean. It is
	 * the ocean who decides which real position the change resulted in.
	 * 
	 * @param p
	 *            the point to move the shoal to
	 * 
	 * @return true if the final position was the desired position, false
	 *         otherwise.
	 */
	protected boolean changePosition(Point p) {
		stats.setPosition(ocean.tryMoveShoal(stats.getId(), p));
		return stats.getPosition().equals(p);
	}

	/**
	 * define the socket which the thread should use
	 * @param socket
	 */
	public void setClientSocket(Socket socket){
		this.socket = socket;
	}
}
