package pt.ua.sd.distribution;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements Remote {
	private static final long serialVersionUID = 7955288689602858022L;

	protected final Registry registry;

	public RmiServer(String host, int port) throws RemoteException, AlreadyBoundException {
		this.registry = LocateRegistry.createRegistry(port);
		registry.rebind(RmiServer.class.toString(), this);

		Remote thisStub = UnicastRemoteObject.exportObject(this, port);
		registry.rebind(RmiServer.class.toString(), thisStub);
	}
}
