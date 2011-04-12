/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.log;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class OnlineLog {

	private static OnlineLog logger;
	private static int currentId = 0;
	private static HashMap<Integer, String> pendingMsg;

	public OnlineLog() {
		pendingMsg = new HashMap<Integer, String>();
	}
	protected static Lock singleton = new ReentrantLock();

	public static OnlineLog getInstance() {
		singleton.lock();
		if (logger == null) {
			logger = new OnlineLog();
		}
		singleton.unlock();

		return logger;
	}

	synchronized public void doLog(String msg, int id) {
		//System.out.println("dolog");
		if (id > currentId) {
			if (id == currentId + 1) {
				System.out.println("(" + id + ")" + msg);
				currentId = id;
				if (pendingMsg.size() > 0) {

					int i = 0;
					String m;
					while ((m = pendingMsg.get(Integer.valueOf(i))) != null) {
						System.out.println("(" + i + ")" + m);
						currentId = i;
						pendingMsg.remove(Integer.valueOf(i));
						i++;
					}
				}
			} else {
				pendingMsg.put(id, msg);
			}
		}
	}
}
