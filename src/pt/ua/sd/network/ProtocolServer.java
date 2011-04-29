/**
 * 
 */
package pt.ua.sd.network;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ProtocolServer {

    protected ServerSocket server;
    protected IProtocolRunnable protocol;
    protected boolean isClosed = false;

    public ProtocolServer(int port, IProtocolRunnable protocol) {

        this.protocol = protocol;

        try {
            this.server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    synchronized public void startServer() {
        try {
            System.out.println("Server started");
            do {
                Socket accept = server.accept();
                protocol = protocol.getClass().getConstructor(Socket.class).newInstance(accept);
                new Thread(protocol).start();

            } while (!isClosed);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException();
        }
    }

    synchronized public void stopServer() {
        this.isClosed = true;
    }
}
