/**
 * 
 */
package pt.ua.sd.distribution;

import java.net.InetSocketAddress;

import pt.ua.sd.communication.Message;

/**
 * A specialized Message: Start
 * 
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class StartMessage extends DistributionMessage {
	private static final long serialVersionUID = 4855171593473404356L;

	public enum Entity {
		RmiServer, MOcean, MDirOper, TDirOper, MBoat, TBoat, MShoal, TShoal, MLog, TLogFlusher
	};

	protected final Entity entity;
	protected final DistributionConfig config;
	protected final InetSocketAddress rmiRegistryAddress;

	/**
	 * Create a new StartMessage with distribution configuration.
	 * 
	 * @param config
	 *            the running configurations
	 */
	public StartMessage(DistributionConfig config, Entity entity, InetSocketAddress rmiRegistryAddress) {
		this.config = config;
		this.entity = entity;
		this.rmiRegistryAddress = rmiRegistryAddress;
	}

	/**
	 * @return the rmiRegistryAddress
	 */
	public Entity getEntity() {
		return this.entity;
	}

	/**
	 * @return the rmiRegistryAddress
	 */
	public InetSocketAddress getRmiRegistryAddress() {
		return this.rmiRegistryAddress;
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

	@Override
	public Message getMessage() {
		return this;
	}
}