/**
 * 
 */
package pt.ua.sd.ocean.network;

import pt.ua.sd.network.ProtocolServer;
import pt.ua.sd.ocean.MOcean;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class OceanServer extends ProtocolServer{
        private MOcean ocean;
        public OceanServer(int port, OceanProtocolRunnable runnable,MOcean oceano) {
		 super(port, runnable);
                 this.ocean=oceano;
	}

    @Override
    public synchronized void startServer() {
        ((OceanProtocolRunnable)protocol).setOceanMonitors(ocean);
        super.startServer();
    }
	
        
}
