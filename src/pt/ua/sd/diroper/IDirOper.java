/**
 * 
 */
package pt.ua.sd.diroper;

import pt.ua.sd.communication.todiroper.DirOperMessage;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public interface IDirOper {

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
