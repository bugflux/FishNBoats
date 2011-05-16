package pt.ua.sd.diroper.rmi;

import java.rmi.Remote;

import pt.ua.sd.boat.IBoat;
import pt.ua.sd.boat.IBoatDirOper;
import pt.ua.sd.boat.IBoatHelper;

public interface IRemoteDirOper extends Remote, IBoat, IBoatDirOper, IBoatHelper {

}
