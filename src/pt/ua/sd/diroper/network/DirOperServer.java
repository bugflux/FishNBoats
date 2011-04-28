/**
 * 
 */
package pt.ua.sd.diroper.network;

import pt.ua.sd.network.ProtocolServer;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class DirOperServer extends ProtocolServer{

    public DirOperServer(int port, DirOperProtocolRunnable protocol) {
        super(port, protocol);
    }
     
}
