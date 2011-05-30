/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.gui;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author eriksson
 * @author andré prata
 */
public class RemoteObserver extends Observable implements IRemoteObserver {
	private boolean[] alive;
	Timer t;

	private class MonitoringTask extends TimerTask {

		public MonitoringTask() {
		}

		@Override
		public void run() {
			for (int i = 0; i < alive.length; i++) {
				if (!alive[i]) {
					notifyObservers(new String[] { "dead", "" + i });
				}
				alive[i] = false;
			}
		}
	}

	public RemoteObserver(int nServers) {
		alive = new boolean[nServers];

	}

	@Override
	public void notifyAlive(Integer id) {
		if (id < 0 || id >= alive.length)
			return;
		alive[id] = true;
		notifyObservers(new String[] { "alive", "" + id });
	}

	public void startMonitoring(long timeout) {
		if (t != null) {
			throw new RuntimeException("Monitoring already started.");
		}

		t = new Timer();
		t.schedule(new MonitoringTask(), 0, timeout);
	}

	@Override
	public void notifyObservers(Object arg) {
		setChanged();
		super.notifyObservers(arg);
	}

}
