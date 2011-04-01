/**
 * 
 */
package pt.ua.sd.ocean;

import java.util.List;

import pt.ua.sd.boat.BoatStats;

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
}
