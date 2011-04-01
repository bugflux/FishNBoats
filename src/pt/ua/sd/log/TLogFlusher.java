/**
 * 
 */
package pt.ua.sd.log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class TLogFlusher extends Thread {
	protected final OutputStream s;
	protected final OutputStreamWriter out;
    protected boolean stop=false;

	public TLogFlusher(OutputStream s) {
		this.s = s;
		this.out = new OutputStreamWriter(s);
	}
	
	@Override
	public void run() {
		MLog log = MLog.getInstance();
		String msg;
		while(!interrupted()&&!stop) {
			try {
				msg = log.popContiguous();
				if(msg!=null)
				out.append(msg).append('\n').flush();
			} catch (IOException e) {
				throw new RuntimeException("I/O error writing log");
			}
		}
	}

	@Override
	public void interrupt() {
		stop=true;
		super.interrupt();
	}


}
