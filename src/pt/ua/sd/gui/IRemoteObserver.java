/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.gui;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author eriksson
 * @author andré prata
 */
public interface IRemoteObserver extends Remote{
    public enum SERVER_ID{
        log,shoal,boat,ocean,diroper,tshoal,tboat,tdiroper
    }
    public void notifyAlive(Integer id) throws  RemoteException;
}
