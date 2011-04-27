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

    synchronized public static IProtocolMessage sendMessageObjectBlocking(Socket socket, IProtocolMessage msg) {
        IProtocolMessage ret = null;
        System.out.println("socket sending object blocking");
        try {
            ObjectOutputStream objOutputStream = new ObjectOutputStream(socket.getOutputStream());


            objOutputStream.writeObject(msg);
            objOutputStream.flush();

            ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream());
            ret = (IProtocolMessage) objInputStream.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    synchronized public static void sendMessageObject(Socket socket, IProtocolMessage msg) {
        System.out.println("socket sending object");
        try {
            ObjectOutputStream objOutputStream = new ObjectOutputStream(socket.getOutputStream());

            objOutputStream.writeObject(msg);
            objOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    synchronized public static IProtocolMessage getMessageObject(Socket socket) {
        try {
            System.out.println("socket receiving object");
            ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream());

            return (IProtocolMessage) objInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProtocolClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
