/**
 * 
 */
package pt.ua.sd.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.configuration.Configs;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 */
class Simulation {

	private static Simulation instance;
	private boolean isStart;
	private Thread simulation;

	public Simulation() {
	}

	public static Simulation getInstance() {
		if (instance == null) {
			instance = new Simulation();
		}
		return instance;
	}

	public void start() {
		if (!isStart) {
			simulation = new Thread() {

				public void run() {
					File c = new File("saved.conf");
					if (!c.exists()) {
						new Alert(PortoAveiroGui.getInstance(), false).setVisible(true);
						return;
					}
					Configs configs;
					try {
						ObjectInputStream in = new ObjectInputStream(new FileInputStream(c));
						configs = (Configs)in.readObject();
					} catch (ClassNotFoundException ex) {
						Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
						return;
					} catch (IOException ex) {
						Logger.getLogger(Simulation.class.getName()).log(Level.SEVERE, null, ex);
						return;
					}

					PortoAveiro.getInstance().startSimulation(configs);
				}
			};
			simulation.start();
		} else {
			
		}
		isStart=true;
	}

	public void stop() {
		simulation.interrupt();
		isStart=false;
	}
}
