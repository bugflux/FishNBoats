/**
 * 
 */
package pt.ua.sd.shoal.network;

import pt.ua.sd.network.ProtocolServer;
import pt.ua.sd.shoal.MShoal;
import pt.ua.sd.shoal.ShoalStats;
import pt.ua.sd.shoal.TShoal;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalServer extends ProtocolServer {

	protected MShoal[] mShoals;
	protected ShoalStats[] sShoals;
	protected TShoal[] tShoals;

	public ShoalServer(int port, ShoalProtocolRunnable runnable, MShoal[] mShoals, ShoalStats[] sShoals, TShoal[] tShoals) {
		super(port, runnable);
		this.mShoals = mShoals;
		this.sShoals = sShoals;
		this.tShoals = tShoals;
	}

	@Override
	public synchronized void startServer() {
		((ShoalProtocolRunnable) protocol).setShoalsMonitor(mShoals);
		for (int i = 0; i < tShoals.length; i++) {
			tShoals[i].start();
		}

		super.startServer();
	}
}
