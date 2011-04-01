/**
 * 
 */
package pt.ua.sd.shoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IShoalBoat {

	/**
	 * Cast the net to this shoal.
	 */
	public void castTheNet();

	/**
	 * Retrieve the net from this shoal.
	 * 
	 * @return the number of fish caught in the net.
	 */
	public int retrieveTheNet();
}
