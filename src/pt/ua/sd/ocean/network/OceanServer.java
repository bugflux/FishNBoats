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
        
        public OceanServer(int port, OceanProtocolRunnable runnable) {
		 super(port, runnable);
	}    
}
