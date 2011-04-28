/**
 * 
 */
package pt.ua.sd.log.network;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import pt.ua.sd.log.TLogFlusher;
import pt.ua.sd.network.ProtocolServer;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class LogServer extends ProtocolServer {
    private TLogFlusher logFlusher;
    private String logFile;
    public LogServer(int port, LogProtocolRunnable runnable, String logFile) {
        super(port, runnable);
    }

    @Override
    public synchronized void startServer() {
        if (logFile == null) {
            logFlusher = new TLogFlusher(System.out);
        } else {
            try {
                logFlusher = new TLogFlusher(new FileOutputStream(logFile));
            } catch (FileNotFoundException e) {
                System.out.println("Error opening file for logging");
                System.exit(-1);
            }
        }
        logFlusher.start();
        super.startServer();
    }
    
    
}
