package pt.ua.sd.diroper.rmi;

import java.rmi.Remote;

import pt.ua.sd.diroper.IDirOper;
import pt.ua.sd.diroper.IDirOperBoat;
import pt.ua.sd.diroper.IDirOperShoal;

public interface IRemoteDirOper extends Remote, IDirOper, IDirOperBoat, IDirOperShoal {

}
