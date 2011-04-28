/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eriksson
 */
public class ProtocolEndPoint {

     public static IProtocolMessage sendMessageObjectBlocking(Socket socket, IProtocolMessage msg) {
        IProtocolMessage ret = null;
        
        try {
            ObjectOutputStream objOutputStream = new ObjectOutputStream(socket.getOutputStream());


            objOutputStream.writeObject(msg);
            objOutputStream.flush();
            //objOutputStream.close();
            ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream());
            ret = (IProtocolMessage) objInputStream.readObject();
            //objInputStream.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProtocolEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProtocolEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public static void sendMessageObject(Socket socket, IProtocolMessage msg) {
        
        try {
            ObjectOutputStream objOutputStream = new ObjectOutputStream(socket.getOutputStream());
            
            objOutputStream.writeObject(msg);
            objOutputStream.flush();
            //objOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ProtocolEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static IProtocolMessage getMessageObject(Socket socket) {
        ObjectInputStream objInputStream=null;
        try {
            
            objInputStream = new ObjectInputStream(socket.getInputStream());
            //objInputStream.close();
            return (IProtocolMessage) objInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ProtocolEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProtocolEndPoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
