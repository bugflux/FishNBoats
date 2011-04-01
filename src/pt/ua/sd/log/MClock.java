/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class MClock {

	protected static MClock clock;
	protected int tick = 0;

	private MClock() {
	}
	protected static Lock singleton = new ReentrantLock();

	/**
	 * get an instance of the global clock
	 * @return MClock
	 */
	public static MClock getClock() {
		singleton.lock();
		if (clock == null) {
			clock = new MClock();
		}
		singleton.unlock();

		return clock;
	}

	/**
	 * get a clock tick on the global clock
	 * @return int the clock tick id
	 */
	synchronized public int getClockTick() {
		System.out.println("\t\t\t\t\t\t\t\t\ttick: " + tick);
		Thread.dumpStack();
		return ++tick;
	}
}
