/**
 * 
 */
package pt.ua.sd.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Extractor of messages from the Log repository.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class TLogFlusher extends Thread {

	protected final OutputStream s;
	protected final OutputStreamWriter out;
	protected boolean stop = false;
	protected final ILogger log;

	public TLogFlusher(OutputStream s, ILogger log) {
		this.s = s;
		this.out = new OutputStreamWriter(s);
		this.log = log;
	}

	@Override
	public void run() {
		String msg;
		while (!interrupted() && !stop) {

			try {
				msg = log.popContiguous();
				if (msg != null) {
					out.append(msg).append('\n').flush();
					System.out.println(msg);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			yield();

		}
	}

	@Override
	public void interrupt() {
		try {
			stop = true;
			out.flush();
			out.close();
			super.interrupt();
		} catch (IOException ex) {
			Logger.getLogger(TLogFlusher.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
