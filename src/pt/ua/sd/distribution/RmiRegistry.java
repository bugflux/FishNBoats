package pt.ua.sd.distribution;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiRegistry implements IRmiRegistry {

	protected final int port;
	protected final String host;
	
	public RmiRegistry(String host, int port) throws RemoteException {
		this.host = host;
		this.port = port;
	}

	@Override
	public void bind(String name, Remote ref) throws RemoteException, AlreadyBoundException {
		System.out.println("Remote registry binding: " + name);
		Registry registry = LocateRegistry.getRegistry(host, port);
		registry.bind(name, ref);
		System.out.println("Remote registry binded: " + name);
	}

	@Override
	public void unbind(String name) throws RemoteException, NotBoundException {
		System.out.println("Remote registry unbinding: " + name);
		Registry registry = LocateRegistry.getRegistry(host, port);
		registry.unbind(name);
		System.out.println("Remote registry unbinded: " + name);
	}

	@Override
	public void rebind(String name, Remote ref) throws RemoteException {
		System.out.println("Remote registry unbinding: " + name);
		Registry registry = LocateRegistry.getRegistry(host, port);
		registry.rebind(name, ref);
		System.out.println("Remote registry rebinded: " + name);
	}
}
