/**
 * 
 */
package pt.ua.sd.log;

import java.util.HashMap;
import java.util.Map;

/**
 * Point of synchronization to orderly record log messages
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class MLog implements ILogger {
	protected Map<Integer, String> list;
	protected int nextContiguousIndex;

	protected int tick = 0;

	/**
	 * get a clock tick on the global clock
	 * 
	 * @return int the clock tick id
	 */
	synchronized public int getClockTick() {
		return ++tick;
	}

	public MLog() {
		this.list = new HashMap<Integer, String>();
		this.nextContiguousIndex = 1;
	}

	/**
	 * push a message to be logged
	 * 
	 * @param type
	 *            the type of message
	 * @param entity
	 *            the entity to which the log message refers
	 * @param message
	 *            message to be logged
	 * @param tick
	 *            global clock tick
	 */
	synchronized public void push(String type, String entity, String message, int tick) {
		assert tick >= nextContiguousIndex; // uniqueness
		assert !list.containsKey(tick); // uniqueness

		list.put(tick, String.format("%7s: %13s %-25s %-40s", String.valueOf(tick), entity + ",", type + ",", message));
		// System.out.println(list.get(tick));

		notify();
	}

	/**
	 * this method retreive the next message to be flushed to the output
	 * 
	 * @return next message to be logged
	 */
	synchronized public String popContiguous() {
		while (!list.containsKey(nextContiguousIndex)) {
			try {
				wait();
			} catch (InterruptedException e) {
				return null;
			}
		}

		String message = list.remove(nextContiguousIndex);
		nextContiguousIndex++;

		return message;
	}
}
