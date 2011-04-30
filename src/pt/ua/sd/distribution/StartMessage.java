/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;

import pt.ua.sd.communication.Message;

/**
 * A specialized Message: Start
 * 
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class StartMessage extends DistributionMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2500978428730291086L;

	protected HashMap<Entity, InetSocketAddress> machines;
	protected final DistributionConfig config;

	/**
	 * Create a new StartMessage with distribution configuration. You mustn't
	 * send this message before all fields are set!!
	 * 
	 * @param config
	 *            the running configurations
	 */
	public StartMessage(DistributionConfig config) {
		this.config = config;
		this.machines = new HashMap<Entity, InetSocketAddress>(9);
	}

	/**
	 * Set the Ocean's machine address and port. This isn't validated, it's up
	 * to you to prevent collisions!
	 * 
	 */
	public void setMOcean(InetSocketAddress address) {
		machines.put(Entity.MOcean, address);
	}

	/**
	 * Set the Log's machine address and port. This isn't validated, it's up to
	 * you to prevent collisions!
	 */
	public void setMLog(InetSocketAddress address) {
		machines.put(Entity.MLog, address);
	}

	/**
	 * Set the TLogFlusher's machine address and port. This isn't validated,
	 * it's up to you to prevent collisions!
	 */
	public void setTLogFlusher(InetSocketAddress address) {
		machines.put(Entity.TLogFlusher, address);
	}

	/**
	 * Set the DirOper's machine address and port. This isn't validated, it's up
	 * to you to prevent collisions!
	 */
	public void setMDirOper(InetSocketAddress address) {
		machines.put(Entity.MDirOper, address);
	}

	/**
	 * Set the TDirOper's machine address and port. This isn't validated, it's
	 * up to you to prevent collisions!
	 */
	public void setTDirOper(InetSocketAddress address) {
		machines.put(Entity.TDirOper, address);
	}

	/**
	 * Set the Boat's machine address and port. This isn't validated, it's up to
	 * you to prevent collisions!
	 */
	public void setMBoat(InetSocketAddress address) {
		machines.put(Entity.MBoat, address);
	}

	/**
	 * Set the TBoat's machine address. This isn't validated, it's up to you to
	 * prevent collisions!
	 */
	public void setTBoat(InetSocketAddress address) {
		machines.put(Entity.TBoat, address);
	}

	/**
	 * Set the Shoal's machine address and port. This isn't validated, it's up
	 * to you to prevent collisions!
	 */
	public void setMShoal(InetSocketAddress address) {
		machines.put(Entity.MShoal, address);
	}

	/**
	 * Set the TShoal's machine address. This isn't validated, it's up to you to
	 * prevent collisions!
	 */
	public void setTShoal(InetSocketAddress address) {
		machines.put(Entity.TShoal, address);
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getMOceanAddress() {
		assert machines.containsKey(Entity.MOcean);
		return machines.get(Entity.MOcean).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getMLogAddress() {
		assert machines.containsKey(Entity.MLog);
		return machines.get(Entity.MLog).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getTLogFlusherAddress() {
		assert machines.containsKey(Entity.TLogFlusher);
		return machines.get(Entity.TLogFlusher).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getMDirOperAddress() {
		assert machines.containsKey(Entity.MDirOper);
		return machines.get(Entity.MDirOper).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getTDirOperAddress() {
		assert machines.containsKey(Entity.TDirOper);
		return machines.get(Entity.TDirOper).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getMBoatAddress() {
		assert machines.containsKey(Entity.MBoat);
		return machines.get(Entity.MBoat).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getTBoatAddress() {
		assert machines.containsKey(Entity.TBoat);
		return machines.get(Entity.TBoat).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getMShoalAddress() {
		assert machines.containsKey(Entity.MShoal);
		return machines.get(Entity.MShoal).getAddress();
	}

	/**
	 * @return the address. fails if not set.
	 */
	public InetAddress getTShoalAddress() {
		assert machines.containsKey(Entity.TShoal);
		return machines.get(Entity.TShoal).getAddress();
	}

	/**
	 * @return the port. fails if not set.
	 */
	public int getMOceanPort() {
		assert machines.containsKey(Entity.MOcean);
		return machines.get(Entity.MOcean).getPort();
	}

	/**
	 * @return the port. fails if not set.
	 */
	public int getMLogPort() {
		assert machines.containsKey(Entity.MLog);
		return machines.get(Entity.MLog).getPort();
	}

	/**
	 * @return the port. fails if not set.
	 */
	public int getMDirOperPort() {
		assert machines.containsKey(Entity.MDirOper);
		return machines.get(Entity.MDirOper).getPort();
	}

	/**
	 * @return the port. fails if not set.
	 */
	public int getMBoatPort() {
		assert machines.containsKey(Entity.MBoat);
		return machines.get(Entity.MBoat).getPort();
	}

	/**
	 * @return the port. fails if not set.
	 */
	public int getMShoalPort() {
		assert machines.containsKey(Entity.MShoal);
		return machines.get(Entity.MShoal).getPort();
	}

	/**
	 * @return the config
	 */
	public DistributionConfig getConfig() {
		return config;
	}

	/**
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 * @return MESSAGE_TYPE.Start
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.Start;
	}

	public enum Entity {
		MOcean, MDirOper, TDirOper, MBoat, TBoat, MShoal, TShoal, MLog, TLogFlusher
	};

	class Pair {
		InetAddress address;
		int port;

		public Pair(InetAddress address, int port) {
			this.address = address;
			this.port = port;
		}
	}

	@Override
	public Message getMessage() {
		return this;
	}
}
