/**
 * 
 */
package pt.ua.sd.log;

import pt.ua.sd.log.rmi.IRemoteLogger;

/**
 * Exposes the Logging methods
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public interface ILogger extends IRemoteLogger {

	/**
	 * Gets a contiguous tick. All following messages will get queued until this
	 * tick is used!
	 * 
	 * @return the tick value.
	 */
	public int getClockTick();

	/**
	 * Push a new message to this log.
	 * 
	 * @param type
	 *            the type of action to log
	 * @param entity
	 *            the entity to/from which the action refers
	 * @param message
	 *            a more detailed message describing the action
	 * @param tick
	 *            the tick acquired to do this
	 */
	public void push(String type, String entity, String message, int tick);

	/**
	 * Pop the next contiguous message in this logging queue.
	 * 
	 * @return the next message, ready to print with all the fields
	 */
	public String popContiguous();
}
