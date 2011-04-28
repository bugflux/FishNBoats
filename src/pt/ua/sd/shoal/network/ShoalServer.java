/**
 * 
 */
package pt.ua.sd.shoal.network;

import pt.ua.sd.network.ProtocolServer;


/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalServer extends ProtocolServer {
        public ShoalServer(int port, ShoalProtocolRunnable runnable) {
		super(port, runnable);
	}
        

}
