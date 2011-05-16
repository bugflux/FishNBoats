package pt.ua.sd.ocean.rmi;

import java.rmi.Remote;

import pt.ua.sd.ocean.IOceanBoat;
import pt.ua.sd.ocean.IOceanDirOper;
import pt.ua.sd.ocean.IOceanShoal;

public interface IRemoteOcean extends Remote, IOceanBoat, IOceanDirOper, IOceanShoal {

}
