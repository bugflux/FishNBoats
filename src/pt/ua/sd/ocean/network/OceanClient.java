/**
 * 
 */
package pt.ua.sd.ocean.network;

import java.awt.Point;
import java.net.Socket;
import java.util.List;
import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.communication.toocean.CompanionDetectedMessage;
import pt.ua.sd.communication.toocean.GetHeightMessage;
import pt.ua.sd.communication.toocean.GetRadarMessage;
import pt.ua.sd.communication.toocean.GetSpawningAreaMessage;
import pt.ua.sd.communication.toocean.GetWharfMessage;
import pt.ua.sd.communication.toocean.GetWidthMessage;
import pt.ua.sd.communication.toocean.SetBoatCatchMessage;
import pt.ua.sd.communication.toocean.SetBoatStateMessage;
import pt.ua.sd.communication.toocean.SetDirOperStateMessage;
import pt.ua.sd.communication.toocean.SetShoalSizeMessage;
import pt.ua.sd.communication.toocean.SetShoalStateMessage;
import pt.ua.sd.communication.toocean.TryMoveBoatMessage;
import pt.ua.sd.communication.toocean.TryMoveShoalMessage;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolClient;
import pt.ua.sd.ocean.IOceanBoat;
import pt.ua.sd.ocean.IOceanDirOper;
import pt.ua.sd.ocean.IOceanShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class OceanClient extends ProtocolClient implements IOceanBoat, IOceanShoal, IOceanDirOper {

    public OceanClient(Socket socket) {
        super(socket);
    }

    public OceanClient(int port, String host) {
        super(host, port);
    }

    public Point tryMoveBoat(BoatId id, Point p) {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new TryMoveBoatMessage(id, p)));
        Acknowledge ack = (Acknowledge) response;
        return (Point) ack.getParam("point");
    }

    public List<Point> getRadar(BoatId id) {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new GetRadarMessage(id)));
        Acknowledge ack = (Acknowledge) response;
        return (List<Point>) ack.getParam("radar");
    }

    public int getHeight() {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new GetHeightMessage()));
        Acknowledge ack = (Acknowledge) response;
        return (Integer) ack.getParam("height");
    }

    public int getWidth() {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new GetWidthMessage()));
        Acknowledge ack = (Acknowledge) response;
        return (Integer) ack.getParam("width");
    }

    public void setBoatState(BoatId id, INTERNAL_STATE_BOAT state) {
        this.sendMessageObjectBlocking(new OceanProtocolMessage(new SetBoatStateMessage(id, state)));
    }

    public void setBoatCatch(BoatId id, int stored) {
        this.sendMessageObjectBlocking(new OceanProtocolMessage(new SetBoatCatchMessage(id, stored)));
    }

    public Point getWharf() {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new GetWharfMessage()));
        Acknowledge ack = (Acknowledge) response;
        return (Point) ack.getParam("point");
    }

    public IShoalBoat companionDetected(BoatId id, BoatId helper) {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new CompanionDetectedMessage(id, helper)));
        Acknowledge ack = (Acknowledge) response;
        return (IShoalBoat) ack.getParam("companion");
    }

    public Point tryMoveShoal(ShoalId id, Point p) {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new TryMoveShoalMessage(id, p)));
        Acknowledge ack = (Acknowledge) response;
        return (Point) ack.getParam("point");
    }

    public void setShoalState(ShoalId id, INTERNAL_STATE_SCHOOL state) {
        this.sendMessageObjectBlocking(new OceanProtocolMessage(new SetShoalStateMessage(id, state)));
    }

    public void setShoalSize(ShoalId id, int size) {
        this.sendMessageObjectBlocking(new OceanProtocolMessage(new SetShoalSizeMessage(id, size)));
    }

    public Point getSpawningArea() {
        IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new GetSpawningAreaMessage()));
        Acknowledge ack = (Acknowledge) response;
        return (Point) ack.getParam("point");
    }

    public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER state) {
        this.sendMessageObjectBlocking(new OceanProtocolMessage(new SetDirOperStateMessage(id, state)));
    }
}
