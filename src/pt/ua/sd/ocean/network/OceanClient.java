/**
 * 
 */
package pt.ua.sd.ocean.network;

import java.awt.Point;
import java.net.Socket;
import java.util.List;
import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.communication.toocean.TryMoveBoatMessage;
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
public class OceanClient extends ProtocolClient implements IOceanBoat, IOceanShoal, IOceanDirOper{

	public OceanClient(Socket socket) {
		super(socket);
	}

	public OceanClient(int port, String host) {
		super(host, port);
	}
	
	public Point tryMoveBoat(BoatId id, Point p) {
		IProtocolMessage response = this.sendMessageObjectBlocking(new OceanProtocolMessage(new TryMoveBoatMessage(id, p)));
		Acknowledge ack = (Acknowledge)response;
		return (Point)ack.getParam("point");
	}

	public List<Point> getRadar(BoatId id) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getHeight() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getWidth() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setBoatState(BoatId id, INTERNAL_STATE_BOAT state) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setBoatCatch(BoatId id, int stored) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Point getWharf() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public IShoalBoat companionDetected(BoatId id, BoatId helper) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Point tryMoveShoal(ShoalId id, Point p) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setShoalState(ShoalId id, INTERNAL_STATE_SCHOOL state) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setShoalSize(ShoalId id, int size) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Point getSpawningArea() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER state) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
