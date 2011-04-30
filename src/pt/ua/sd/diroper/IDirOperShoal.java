/**
 * 
 */
package pt.ua.sd.diroper;

/**
 * Exposes DirOper communication interface to Shoals
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IDirOperShoal {

	/**
	 * This is for the Shoal to inform the DirOper that it arrived to the
	 * Spawning area, and that the boats should return to wharf.
	 */
	public void endSeason();

	/**
	 * This is for the Shoal to inform the DirOper that the simulation is over
	 * and no more seasons will be simulasted.
	 */
	public void endLife();
}
