package pt.ua.sd.boat.rmi;

import java.rmi.Remote;

import pt.ua.sd.boat.IBoat;
import pt.ua.sd.boat.IBoatDirOper;
import pt.ua.sd.boat.IBoatHelper;

public interface IRemoteBoat extends Remote, IBoat, IBoatDirOper, IBoatHelper {

}
