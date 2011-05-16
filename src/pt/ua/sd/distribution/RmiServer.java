package pt.ua.sd.distribution;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiServer implements Remote {
	private static final long serialVersionUID = 7955288689602858022L;

	protected final int port;
	protected final Registry registry;

	public RmiServer(int port) throws RemoteException {
		this.port = port;
		this.registry = LocateRegistry.createRegistry(port);
		System.out.println("rmi registry created");
		registry.rebind(RmiServer.class.toString(), this);
	}
}
