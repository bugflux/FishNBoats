/**
 * 
 */
package pt.ua.sd.log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class MLog {

	protected static MLog instance;
	protected Map<Integer, String> list;
	protected int nextContiguousIndex;

	private MLog() {
		this.list = new HashMap<Integer, String>();
		this.nextContiguousIndex = 1;
	}
	protected static Lock singleton = new ReentrantLock();

	/**
	 * get a Logger's instance
	 * @return MLog instance
	 */
	synchronized public static MLog getInstance() {
		singleton.lock();
		if (instance == null) {
			instance = new MLog();
		}
		singleton.unlock();

		return instance;
	}

	/**
	 * push a message to be logged
	 * @param str message to be logged
	 * @param tick global clock tick
	 */
	synchronized public void push(String type, String entity, String message, int tick) {
		assert tick >= nextContiguousIndex; // uniqueness
		assert !list.containsKey(tick); // uniqueness

		list.put(tick, String.format("%7s: %13s %-25s %-40s", String.valueOf(tick), entity + ",", type + ",", message));
		//System.out.println(list.get(tick));

		notify();
	}

	/**
	 * this method retreive the next message to be flushed
	 * to the output
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
