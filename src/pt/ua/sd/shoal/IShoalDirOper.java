/**
 * 
 */
package pt.ua.sd.shoal;

import pt.ua.sd.shoal.rmi.IRemoteShoal;

/**
 * Exposes Shoal methods to DirOpers
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IShoalDirOper extends IRemoteShoal {

	/**
	 * Informs the Shoal that the DirOpers are ready to proceed for the next
	 * campaign and that the Shoal should go to the high sea.
	 */
	public void seasonBegin();
}
