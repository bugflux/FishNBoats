package pt.ua.sd.shoal.rmi;

import java.rmi.Remote;

import pt.ua.sd.shoal.IShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.IShoalDirOper;

public interface IRemoteShoal extends Remote, IShoal, IShoalBoat, IShoalDirOper {

}
