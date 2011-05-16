package pt.ua.sd.ocean.rmi;

import java.rmi.Remote;

import pt.ua.sd.diroper.IDirOper;
import pt.ua.sd.diroper.IDirOperBoat;
import pt.ua.sd.diroper.IDirOperShoal;

public interface IRemoteOcean extends Remote, IDirOper, IDirOperBoat, IDirOperShoal {

}
