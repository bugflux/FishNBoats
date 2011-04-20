/**
 * 
 */
package pt.ua.sd.boat;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IBoatDirOper {

	/**
	 * Order the boat to go to high sea.
	 */
	public void setToHighSea();

	/**
	 * Order this boat to return.
	 */
	public void returnToWharf();

	/**
	 * Get the id associated with this boat monitor.
	 * 
	 * @return the BoatId.
	 */
	public BoatId getId();

	/**
	 * Tell a boat that life has ended. :P
	 */
	public void lifeEnd();

	/**
	 * Inform a boat that its request for help has been served and that a boat
	 * should be under way.
	 * 
	 * @param helper
	 *            the monitor of the assigned boat.
	 */
	public void helpRequestServed(IBoatHelper helper);
}
