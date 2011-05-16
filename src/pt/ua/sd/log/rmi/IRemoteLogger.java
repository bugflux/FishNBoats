package pt.ua.sd.log.rmi;

import java.rmi.Remote;

import pt.ua.sd.shoal.IShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.IShoalDirOper;

public interface IRemoteLogger extends Remote, IShoal, IShoalBoat, IShoalDirOper {

}
