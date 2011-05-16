package pt.ua.sd.boat.rmi;

import java.rmi.Remote;

import pt.ua.sd.diroper.IDirOper;
import pt.ua.sd.diroper.IDirOperBoat;
import pt.ua.sd.diroper.IDirOperShoal;

public interface IRemoteBoat extends Remote, IDirOper, IDirOperBoat, IDirOperShoal {

}
