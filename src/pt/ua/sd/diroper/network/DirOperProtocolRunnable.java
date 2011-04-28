/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.diroper.network;

import java.net.Socket;
import pt.ua.sd.communication.todiroper.BackAtWharfMessage;
import pt.ua.sd.communication.todiroper.BoatFullMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.communication.todiroper.FishingDoneMessage;
import pt.ua.sd.communication.todiroper.RequestHelpMessage;
import pt.ua.sd.diroper.MDirOper;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 *
 * @author eriksson
 */
public class DirOperProtocolRunnable implements IProtocolRunnable {

    private Socket socket;
    private static MDirOper[] monitor;
    private static Acknowledge ack = new Acknowledge();

    public DirOperProtocolRunnable(Socket socket) {
        this.socket = socket;
    }

    public DirOperProtocolRunnable(MDirOper[] monitor) {
        this.monitor = monitor;
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

        if (msg instanceof DirOperProtocolMessage) {
            DirOperProtocolMessage m = (DirOperProtocolMessage) msg;
            int diroper_id = m.getId().getId();
            if (diroper_id > monitor.length) {
                // TODO: error -> no shoal id
            }
            switch ((DirOperMessage.MESSAGE_TYPE) m.getMessage().getMsgType()) {
                case BackAtWharf:
                    BackAtWharfMessage bAtwharf = ((BackAtWharfMessage) m.getMessage());
                    monitor[diroper_id].backAtWharf(bAtwharf.getBoatId(), bAtwharf.getStored());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case BoatFull:
                    BoatFullMessage bfull = ((BoatFullMessage) m.getMessage());
                    monitor[diroper_id].boatFull(bfull.getId());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case ClearMessage:
                    monitor[diroper_id].clearMessages();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case FishingDone:
                    FishingDoneMessage bfishingdone = ((FishingDoneMessage) m.getMessage());
                    monitor[diroper_id].fishingDone(bfishingdone.getId());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case LifeEnd:
                    monitor[diroper_id].endLife();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case PopMessage:
                    DirOperMessage popMsg = monitor[diroper_id].popMsg();
                    Acknowledge acknowledge = new Acknowledge();
                    acknowledge.setParam("message", popMsg);
                    ProtocolEndPoint.sendMessageObject(socket, acknowledge);
                    break;
                case RequestHelp:
                    RequestHelpMessage bhelp = ((RequestHelpMessage) m.getMessage());
                    monitor[diroper_id].requestHelp(bhelp.getBoatId(), bhelp.getLocation());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case SeasonEnd:
                    monitor[diroper_id].endSeason();
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                default:
                    throw new RuntimeException("Message is not defined");
            }
        } else {
            // TODO: error case
        }
    }
}
