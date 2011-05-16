package pt.ua.sd.ocean;

import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;

import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.ShoalStats;

public interface IOcean extends Remote {
	public void addDirOper(DirOperStats s) throws RemoteException;
	public void addBoat(BoatStats boat, Point p) throws RemoteException;
	public void addShoal(ShoalStats shoal, IShoalBoat mshoal, Point p) throws RemoteException;
}
