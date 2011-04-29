/**
 * 
 */
package pt.ua.sd.boat.network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.MBoat;
import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.communication.toboat.CastTheNetMessage;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.toboat.PopMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class BoatProtocolRunnable implements IProtocolRunnable {

    private static Acknowledge ack = new Acknowledge();
    private static MBoat[][] monitor;
    private Socket socket;

    public BoatProtocolRunnable(Socket socket) {
        this.socket = socket;
    }

    public BoatProtocolRunnable(MBoat[][] monitor) {
        BoatProtocolRunnable.monitor = monitor;
    }

    @Override
    public void run() {
        if (socket == null) {
            throw new RuntimeException("socket not setted");
        }

        IProtocolMessage msg = ProtocolEndPoint.getMessageObject(socket);
        if (msg == null) {
            throw new RuntimeException("Message is null");
        }
        if (msg instanceof BoatProtocolMessage) {
            
            BoatProtocolMessage m = (BoatProtocolMessage) msg;
            
            int doper_id = m.getDirOperId().getId();
            
            if (doper_id > monitor.length) {
                // TODO: error -> no shoal id
                throw new RuntimeException("diroper id not present");
                
            }
            
            int boat_id = m.getBoatId().getBoat();
            if (boat_id > monitor[0].length) {
                // TODO: error -> no shoal id
                throw new RuntimeException("boat id not present");
            }
            switch ((BoatMessage.MESSAGE_TYPE) m.getMessage().getMsgType()) {
                case CastTheNet:
                    CastTheNetMessage bCastNet = ((CastTheNetMessage) m.getMessage());
                    monitor[doper_id][boat_id].castTheNet(bCastNet.getShoal());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case ChangeCourse:
                    ChangeCourseMessage bChageCourse = ((ChangeCourseMessage) m.getMessage());
                    monitor[doper_id][boat_id].changeCourse(bChageCourse.getNewDestination());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case GetId:
                    BoatId id = monitor[doper_id][boat_id].getId();
                    Acknowledge acknowledge = new Acknowledge();
                    acknowledge.setParam("id", id);
                    ProtocolEndPoint.sendMessageObject(socket, acknowledge);
                    break;
                case HelpRequestServed:
                    HelpRequestServedMessage bRequest = ((HelpRequestServedMessage) m.getMessage());
                    monitor[doper_id][boat_id].helpRequestServed(bRequest.getHelper());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case LifeEnd:
                    monitor[doper_id][boat_id].lifeEnd();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case PopMessage:
                    PopMessage bPopMessage = ((PopMessage) m.getMessage());
                    BoatMessage popMsg = monitor[doper_id][boat_id].popMsg(bPopMessage.isBlocking());
                    Acknowledge ackMessage = new Acknowledge();
                    ackMessage.setParam("message", popMsg);
                    ProtocolEndPoint.sendMessageObject(socket, ackMessage);
                    break;
                case ReleaseHelper:
                    monitor[doper_id][boat_id].releaseHelper();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case ReturnToWharf:
                    monitor[doper_id][boat_id].returnToWharf();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case SetToHighSea:
                    monitor[doper_id][boat_id].setToHighSea();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                default:
                    throw new RuntimeException("Message is not defined");
            }
        } else {
            // TODO: error case
            return;
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(BoatProtocolRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
