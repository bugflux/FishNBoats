/**
 * 
 */
package pt.ua.sd.shoal;

import pt.ua.sd.shoal.rmi.IRemoteShoal;

/**
 * Exposes Shoal methods to Boats
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IShoalBoat extends IRemoteShoal {

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
