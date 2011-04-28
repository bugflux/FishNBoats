/**
 * 
 */
package pt.ua.sd.boat.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class BoatServer extends ProtocolServer {

    public BoatServer(int port, BoatProtocolRunnable runnable) {
        super(port, runnable);
    }
}
