/**
 * 
 */
package pt.ua.sd.shoal.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.communication.toshoal.GoToFeedingAreaMessage;
import pt.ua.sd.communication.toshoal.IsTrapped;
import pt.ua.sd.communication.toshoal.PopMessage;
import pt.ua.sd.communication.toshoal.RetrieveTheNetMessage;
import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.communication.toshoal.TrappedByTheNetMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.IShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.IShoalDirOper;
import pt.ua.sd.shoal.ShoalId;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalClient implements IShoal, IShoalBoat, IShoalDirOper {

    private ShoalId id;
    private int port;
    private String host;
    public ShoalClient(ShoalId id, int port, String host) {
        this.port= port;
        this.host = host;
        this.id = id;
    }

    public void castTheNet() {
        try {
            Socket socket = new Socket(host,port);
            ProtocolEndPoint.sendMessageObjectBlocking(socket,new ShoalProtocolMessage(id, new TrappedByTheNetMessage()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int retrieveTheNet() {
        try {
            Socket socket = new Socket(host,port);
            IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket,new ShoalProtocolMessage(id, new RetrieveTheNetMessage()));
            Acknowledge ack = (Acknowledge) response;
            return (Integer) ack.getParam("catch");
        } catch (UnknownHostException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    int count=0;
    public void seasonBegin() {
        try {
            count++;
            Socket socket = new Socket(host,port);
            ProtocolEndPoint.sendMessageObjectBlocking(socket,new ShoalProtocolMessage(id, new GoToFeedingAreaMessage()));
        } catch (UnknownHostException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ShoalMessage popMsg(boolean blocking) {
        try {
            Socket socket = new Socket(host,port);
            IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket,new ShoalProtocolMessage(id, new PopMessage(blocking)));
            Acknowledge ack = (Acknowledge) response;
            return (ShoalMessage) ack.getParam("message");
        } catch (UnknownHostException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void isTrapped(int amount) {
        try {
            Socket socket = new Socket(host,port);
            IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id, new IsTrapped(amount)));
        } catch (UnknownHostException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
