/**
 * 
 */
package pt.ua.sd.ocean;

import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;

/**
 * Exposes Ocean methods to DirOpers
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IOceanDirOper extends Remote {

	/**
	 * Set the DirOperState for a current DirOper.
	 * 
	 * @param id
	 *            the id of the DirOper.
	 * @param state
	 *            the state to set.
	 */
	public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER state) throws RemoteException;
}
