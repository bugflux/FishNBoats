/**
 * 
 */
package pt.ua.sd.ocean.network;

import java.awt.Point;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.communication.toocean.AddBoatMessage;
import pt.ua.sd.communication.toocean.AddDirOperMessage;
import pt.ua.sd.communication.toocean.AddShoalMessage;
import pt.ua.sd.communication.toocean.CompanionDetectedMessage;
import pt.ua.sd.communication.toocean.GetRadarMessage;
import pt.ua.sd.communication.toocean.OceanMessage;
import pt.ua.sd.communication.toocean.SetBoatCatchMessage;
import pt.ua.sd.communication.toocean.SetBoatStateMessage;
import pt.ua.sd.communication.toocean.SetDirOperStateMessage;
import pt.ua.sd.communication.toocean.SetShoalSizeMessage;
import pt.ua.sd.communication.toocean.SetShoalStateMessage;
import pt.ua.sd.communication.toocean.TryMoveBoatMessage;
import pt.ua.sd.communication.toocean.TryMoveShoalMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.ocean.MOcean;
import pt.ua.sd.shoal.IShoalBoat;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class OceanProtocolRunnable implements IProtocolRunnable {

    private Socket socket;
    private static MOcean ocean;
    private static Acknowledge ack = new Acknowledge();

    
    public OceanProtocolRunnable(Socket socket) {
        this.socket = socket;
    }

    public OceanProtocolRunnable(MOcean ocean) {
        OceanProtocolRunnable.ocean = ocean;
    }

       
    
    public void run() {
        if (socket == null) {
            throw new RuntimeException("socket not setted");
        }
        
        IProtocolMessage msg = ProtocolEndPoint.getMessageObject(socket);

        if (msg == null) {
            throw new RuntimeException("Message is null");
        }
        if (msg instanceof OceanProtocolMessage) {
            //do something
            switch ((OceanMessage.MESSAGE_TYPE) msg.getMessage().getMsgType()) {
                case AddBoat:
                    AddBoatMessage madd = (AddBoatMessage) msg.getMessage();
                    ocean.addBoat(madd.getBoat(), madd.getPoint());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case AddDirOper:
                    AddDirOperMessage maddOper = (AddDirOperMessage) msg.getMessage();
                    ocean.addDirOper(maddOper.getDirOper());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case AddShoal:
                    AddShoalMessage maddShaol = (AddShoalMessage) msg.getMessage();
                    ocean.addShoal(maddShaol.getShoal(), maddShaol.getShoalMonitor(), maddShaol.getPoint());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;
                case CompanionDetected:
                    CompanionDetectedMessage mCompanion = (CompanionDetectedMessage) msg.getMessage();
                    IShoalBoat companionDetected = ocean.companionDetected(mCompanion.getBoat(), mCompanion.getHelper());
                    Acknowledge ackcompanion = new Acknowledge();
                    ackcompanion.setParam("companion", companionDetected);
                    ProtocolEndPoint.sendMessageObject(socket, ackcompanion);
                    break;
                case GetHeight:
                    Integer height = ocean.getHeight();
                    Acknowledge ackheight = new Acknowledge();
                    ackheight.setParam("height", height);
                    ProtocolEndPoint.sendMessageObject(socket, ackheight);
                    break;
                case GetWidth:
                    Integer width = ocean.getWidth();
                    Acknowledge ackwidth = new Acknowledge();
                    ackwidth.setParam("width", width);
                    ProtocolEndPoint.sendMessageObject(socket, ackwidth);
                    break;
                case GetRadar:
                    GetRadarMessage mradar = (GetRadarMessage) msg.getMessage();
                    List<Point> radarPoint = ocean.getRadar(mradar.getBoat());
                    Acknowledge ackradar = new Acknowledge();
                    ackradar.setParam("radar", radarPoint);
                    ProtocolEndPoint.sendMessageObject(socket, ackradar);
                    break;

                case GetSpawningArea:
                    Point spawnPoint = ocean.getSpawningArea();
                    Acknowledge ackspawn = new Acknowledge();
                    ackspawn.setParam("point", spawnPoint);
                    ProtocolEndPoint.sendMessageObject(socket, ackspawn);
                    break;

                case GetWharf:
                    Point wharfPoint = ocean.getWharf();
                    Acknowledge ackwharf = new Acknowledge();
                    ackwharf.setParam("point", wharfPoint);
                    ProtocolEndPoint.sendMessageObject(socket, ackwharf);
                    break;
                case SetBoatCatch:
                    SetBoatCatchMessage mcatch = (SetBoatCatchMessage) msg.getMessage();
                    ocean.setBoatCatch(mcatch.getBoat(), mcatch.getCatched());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;

                case SetBoatState:
                    SetBoatStateMessage mBoatState = (SetBoatStateMessage) msg.getMessage();
                    ocean.setBoatState(mBoatState.getBoat(), mBoatState.getState());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;

                case SetDirOperState:
                    SetDirOperStateMessage mDoperState = (SetDirOperStateMessage) msg.getMessage();
                    ocean.setDirOperState(mDoperState.getDirOper(), mDoperState.getState());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;

                case SetShoalSize:
                    SetShoalSizeMessage mShoalSize = (SetShoalSizeMessage) msg.getMessage();
                    ocean.setShoalSize(mShoalSize.getShoal(), mShoalSize.getSize());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;

                case SetShoalState:
                    SetShoalStateMessage mShoalState = (SetShoalStateMessage) msg.getMessage();
                    ocean.setShoalState(mShoalState.getShoal(), mShoalState.getState());
                    ProtocolEndPoint.sendMessageObject(socket, ack);
                    break;

                case TryMoveBoat:
                    TryMoveBoatMessage mtrymoveboat = (TryMoveBoatMessage) msg.getMessage();
                    Point boatPoint = ocean.tryMoveBoat(mtrymoveboat.getBoat(), mtrymoveboat.getPoint());
                    Acknowledge ackTryMoveBoat = new Acknowledge();
                    ackTryMoveBoat.setParam("point",boatPoint);
                    ProtocolEndPoint.sendMessageObject(socket, ackTryMoveBoat);
                    break;
                case TryMoveShoal:
                    TryMoveShoalMessage mtrymoveshaol = (TryMoveShoalMessage) msg.getMessage();
                    Point shoalPoint = ocean.tryMoveShoal(mtrymoveshaol.getShoal(), mtrymoveshaol.getPoint());
                    Acknowledge ackTryMoveShoal = new Acknowledge();
                    ackTryMoveShoal.setParam("point",shoalPoint);
                    ProtocolEndPoint.sendMessageObject(socket, ackTryMoveShoal);
                    break;
            }
        } else {
            //return a error
            throw new RuntimeException("Message is null");
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(OceanProtocolRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
