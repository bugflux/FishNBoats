/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.shoal.network;

import java.net.Socket;
import pt.ua.sd.communication.toshoal.IsTrapped;
import pt.ua.sd.communication.toshoal.PopMessage;

import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.MShoal;

/**
 * 
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class ShoalProtocolRunnable implements IProtocolRunnable {

    private Socket socket;
    private static MShoal[] mShoals;
    private static Acknowledge ack = new Acknowledge();

    public ShoalProtocolRunnable(Socket socket) {
        this.socket=socket;
    }

    public ShoalProtocolRunnable(MShoal[] shoals) {
        this.mShoals = shoals;
    }

    public void run() {
        
            if (socket == null) {
                throw new RuntimeException("socket not setted");
            }
            
            IProtocolMessage msg = ProtocolEndPoint.getMessageObject(socket);
            if (msg == null) {
                throw new RuntimeException("Message is null");
            }
            if (msg instanceof ShoalProtocolMessage) {
                ShoalProtocolMessage m = (ShoalProtocolMessage) msg;
                int shoal_id = m.getShoalId().getShoal();
                if (shoal_id < mShoals.length) {
                    // TODO: error -> no shoal id
                }
                switch ((ShoalMessage.MESSAGE_TYPE) m.getMessage().getMsgType()) {
                    case GoToFeedingArea:
                        mShoals[shoal_id].seasonBegin();
                        
                        ProtocolEndPoint.sendMessageObject(socket, ack);
                        break;
                    case NoActionMessage:
                        ProtocolEndPoint.sendMessageObject(socket,ack);
                        break;
                    case TrappedByTheNet:
                        mShoals[shoal_id].castTheNet();
                        ProtocolEndPoint.sendMessageObject(socket,ack);
                        break;
                    case RetrieveTheNet:
                        int retrieveTheNet = mShoals[shoal_id].retrieveTheNet();
                        Acknowledge acknowledge = new Acknowledge();
                        acknowledge.setParam("catch", retrieveTheNet);
                        ProtocolEndPoint.sendMessageObject(socket,acknowledge);
                        break;
                    case IsTrapped:
                        int trappedvalue = ((IsTrapped) msg.getMessage()).getTrapped();
                        mShoals[shoal_id].isTrapped(trappedvalue);
                        ProtocolEndPoint.sendMessageObject(socket,ack);
                        break;
                    case PopMessage:
                        boolean blocking = ((PopMessage)msg.getMessage()).isBlocking();
                        ShoalMessage popMsg = mShoals[shoal_id].popMsg(blocking);
                        Acknowledge acknowledgepop = new Acknowledge();
                        acknowledgepop.setParam("message",popMsg);
                        ProtocolEndPoint.sendMessageObject(socket,acknowledgepop);
                        break;
                    
                    default:
                        throw new RuntimeException("Message is not defined");
                }
            } else {
                // TODO: error case
            }
    }
}
