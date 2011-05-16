/**
 * 
 */
package pt.ua.sd.shoal;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Exposes Shoal methods to Boats
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IShoalBoat extends Remote {

	/**
	 * Cast the net to this shoal.
	 */
	public void castTheNet() throws RemoteException;

	/**
	 * Retrieve the net from this shoal.
	 * 
	 * @return the number of fish caught in the net.
	 */
	public int retrieveTheNet() throws RemoteException;
}
