/**
 * 
 */
package pt.ua.sd.diroper;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.IBoatDirOper;
import pt.ua.sd.boat.IBoatHelper;
import pt.ua.sd.communication.todiroper.BackAtWharfMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage.MESSAGE_TYPE;
import pt.ua.sd.communication.todiroper.FishingDoneMessage;
import pt.ua.sd.communication.todiroper.RequestHelpMessage;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.log.MLog;
import pt.ua.sd.ocean.IOceanDirOper;
import pt.ua.sd.shoal.IShoalDirOper;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class TDirOper extends Thread {

	protected final DirOperStats stats;
	protected final IDirOper monitor;
	protected final IOceanDirOper ocean;
	protected final IShoalDirOper shoals[];
	protected final IBoatDirOper boats[];

	protected final MLog logger;

	protected final Set<BoatId> boatsAtWharf = new HashSet<BoatId>();
	protected int totalCatch = 0;

	protected final HashMap<BoatId, BoatId> assignedCompanions = new HashMap<BoatId, BoatId>();

	public TDirOper(MLog logger, IOceanDirOper ocean, IDirOper monitor,
			IBoatDirOper[] boats, IShoalDirOper shoals[], DirOperStats stats) {
		this.ocean = ocean;
		this.boats = boats;
		this.monitor = monitor;
		this.logger = logger;
		this.stats = stats;
		this.shoals = shoals;
	}

	@Override
	public void run() {
		boolean lifeEnd = false;
		boolean lifeEnding = false;
		DirOperMessage popMsg;
		changeState(INTERNAL_STATE_DIROPER.starting_a_campaign);

		while (!lifeEnd) {

			switch (stats.getState()) {
			case starting_a_campaign:
				// set boats to high sea and alert the shoal to get to the
				// feeding zone
				startCampaign();
				break;
			case organising_the_catch:
				// TODO
				popMsg = monitor.popMsg();
				if (MESSAGE_TYPE.SeasonEnd == popMsg.getMsgType()) {
					seasonEnd();
				} else if (MESSAGE_TYPE.LifeEnd == popMsg.getMsgType()) {
					seasonEnd();
					lifeEnding = true;
				} else if (MESSAGE_TYPE.BackAtWharf == popMsg.getMsgType()) {
					BackAtWharfMessage m = (BackAtWharfMessage) popMsg;
					newBoatAtWharf(m.getBoatId(), m.getStored());
				} else if (MESSAGE_TYPE.RequestHelp == popMsg.getMsgType()) {
					RequestHelpMessage m = (RequestHelpMessage) popMsg;
					assignCompanion(m.getBoatId(), m.getLocation());
				} else if (MESSAGE_TYPE.FishingDone == popMsg.getMsgType()) {
					FishingDoneMessage m = (FishingDoneMessage) popMsg;
					removeCompanion(m.getId());
				} else {
					assert false; // not allowed
				}
				break;

			case ending_a_campaign:
				if (lifeEnding) {
					endLife();
					lifeEnd = true;
				} else {
					endCampaign();
				}
				break;

			case waiting_for_boats:
				popMsg = monitor.popMsg();
				if (MESSAGE_TYPE.BackAtWharf == popMsg.getMsgType()) {
					BackAtWharfMessage m = (BackAtWharfMessage) popMsg;
					newBoatAtWharf(m.getBoatId(), m.getStored());
				} else if (MESSAGE_TYPE.LifeEnd == popMsg.getMsgType()) {
					lifeEnding = true;
				} else if (MESSAGE_TYPE.FishingDone == popMsg.getMsgType()) {
					FishingDoneMessage m = (FishingDoneMessage) popMsg;
					removeCompanion(m.getId());
				} else {
					assert false;
				}

				break;

			default:
				assert false;
			}
		}

		System.out.println(stats.getId() + " dying");
	}

	/**
	 * Set boats to high sea and change state to organising_the_catch. Clears
	 * the boatsAtWharf count.
	 */
	protected void startCampaign() {
		boatsAtWharf.clear();

		for (IShoalDirOper s : shoals) {
			s.seasonBegin();
		}
		for (IBoatDirOper b : boats) {
			b.setToHighSea();
		}

		changeState(INTERNAL_STATE_DIROPER.organising_the_catch);
	}

	/**
	 * Confirms a new boat at wharf.
	 * 
	 * @param id
	 *            the id of the boat to confirm.
	 * @param stored
	 *            how much fish it had stored.
	 */
	protected void newBoatAtWharf(BoatId id, int stored) {
		totalCatch += stored;
		boatsAtWharf.add(id);

		if (boatsAtWharf.size() == boats.length) {
			changeState(INTERNAL_STATE_DIROPER.ending_a_campaign);
		} else {
			// if there's only one at high sea, call him. if it's the last one,
			// wait
			// for a seasonEnd.
			// if (boatsAtWharf.size() == boats.length - 1) {
			// for (IBoatDirOper b : boats) {
			// if (!boatsAtWharf.contains(b)) {
			// b.returnToWharf();
			// boatsAtWharf.add(b.getId());
			// break; // since there's only one left!
			// }
			// }
			// }
		}

	}

	/**
	 * Set remaining boats to wharf.
	 */
	protected void setBoatsToWharf() {
		for (IBoatDirOper b : boats) {
			if (!boatsAtWharf.contains(b)) {
				b.returnToWharf();
			}
		}
	}

	/**
	 * Determine the end of a season. If there still are boats to arrive, wait
	 * for them, otherwise effectively end the campaign.
	 */
	protected void seasonEnd() {
		if (boatsAtWharf.size() != boats.length) {
			setBoatsToWharf();
			changeState(INTERNAL_STATE_DIROPER.waiting_for_boats);
		} else {
			changeState(INTERNAL_STATE_DIROPER.ending_a_campaign);
		}
	}

	/**
	 * Assign or update a companion to a help request. May be ignored!
	 * 
	 * @param id
	 *            the id of the boat to assign a companion to.
	 * @param p
	 *            the point where the boat should be at.
	 */
	protected void assignCompanion(BoatId id, Point p) {
		// don't hand help to boats that are expected to help others
		if (!assignedCompanions.containsKey(id)
				&& !assignedCompanions.containsValue(id)) {
			// don't hand helpers that are already helping others
			for (IBoatDirOper helper : boats) {
				if (!helper.getId().equals(id)
						&& !assignedCompanions.containsKey(helper.getId())
						&& !assignedCompanions.containsValue(helper.getId())) {
					boats[id.getBoat()]
							.helpRequestServed((IBoatHelper) boats[helper
									.getId().getBoat()]);
					assignedCompanions.put(id, helper.getId());
					break;
				}
			}
		}
	}

	/**
	 * Remove a companion assignment from the pair table.
	 * 
	 * @param id
	 *            the id of the boat to remove.
	 */
	protected void removeCompanion(BoatId id) {
		assert assignedCompanions.containsKey(id);
		assignedCompanions.remove(id);
	}

	/**
	 * Alert all boats that life is ending.
	 */
	protected void endLife() {
		for (IBoatDirOper b : boats) {
			b.lifeEnd();
		}
	}

	/**
	 * Effectively end a campaign. Means the state is changed to start a new
	 * campaign.
	 */
	protected void endCampaign() {
		changeState(INTERNAL_STATE_DIROPER.starting_a_campaign);
		monitor.clearMessages();
	}

	/**
	 * Change the local state of the DirOper.
	 * 
	 * @param state
	 *            the new local state.
	 */
	protected void changeState(INTERNAL_STATE_DIROPER state) {
		stats.setState(state);
	}
}
