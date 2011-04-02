/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.gui;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
class Simulation {

	private static Simulation instance;
	private boolean isStart ;
	private Thread simulation = new Thread(){
		public void run(){
			PortoAveiro.getInstance().startSimulation();
		}
	};

	public Simulation() {
	}

	public static Simulation getInstance() {
		if (instance == null) {
			instance = new Simulation();
		}
		return instance;
	}

	public void start(){
		if(!isStart)
			simulation.start();
	}

	public void stop(){
		simulation.interrupt();
	}

}
