package pt.ua.sd.shoal.rmi;

import java.rmi.Remote;

import pt.ua.sd.ocean.IOceanBoat;
import pt.ua.sd.ocean.IOceanDirOper;
import pt.ua.sd.ocean.IOceanShoal;

public interface IRemoteShoal extends Remote, IOceanBoat, IOceanDirOper, IOceanShoal {

}
