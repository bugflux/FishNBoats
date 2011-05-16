/**
 * 
 */
package pt.ua.sd.diroper;

import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.diroper.rmi.IRemoteDirOper;

/**
 * Exposes all methods that DirOpers should access in their monitors.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface IDirOper extends IRemoteDirOper {

	/**
	 * Retrieve the message that is at the head of the message FIFO. This method
	 * always blocks.
	 * 
	 * @return the first message in the queue.
	 */
	public DirOperMessage popMsg();

	/**
	 * Clears the list of pending messages for the Thread.
	 */
	public void clearMessages();
}
