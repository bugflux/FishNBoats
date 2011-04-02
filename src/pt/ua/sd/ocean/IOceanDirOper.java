/**
 * 
 */
package pt.ua.sd.ocean;

import java.util.List;

import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public interface IOceanDirOper {

	/**
	 * Get all boat statistics for a company's boats.
	 * 
	 * @param companyId the company.
	 * @return a list with the stats of boats from the given company.
	 */
	public List<BoatStats> getBoats(int companyId);
	
	/**
	 * Set the DirOperState for a current DirOper.
	 * 
	 * @param id the id of the DirOper.
	 * @param state the state to set.
	 */
	public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER state);
}
