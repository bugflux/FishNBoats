/**
 * 
 */
package pt.ua.sd.diroper;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class DirOperStats {

	public enum INTERNAL_STATE_DIROPER {
		starting_a_campaign, organising_the_catch, ending_a_campaign, waiting_for_boats
	}
	protected INTERNAL_STATE_DIROPER state;
	protected final DirOperId id;

	/**
	 * Instantiate a new DirOperStats object with an initial state and a DirOper id.
	 * 
	 * @param state The initial state.
	 */
	public DirOperStats(INTERNAL_STATE_DIROPER state, DirOperId id) {
		this.state = state;
		this.id = id;
	}

	public void setState(INTERNAL_STATE_DIROPER s) {
		this.state = s;
	}

	public INTERNAL_STATE_DIROPER getState() {
		return state;
	}
	
	public DirOperId getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return id + ", State " + state; 
	}
}
