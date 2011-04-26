/**
 * 
 */
package pt.ua.sd.shoal.network;

import java.net.Socket;
import pt.ua.sd.communication.toshoal.GoToFeedingAreaMessage;
import pt.ua.sd.communication.toshoal.RetrieveTheNetMessage;
import pt.ua.sd.communication.toshoal.TrappedByTheNetMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolClient;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.IShoalDirOper;
import pt.ua.sd.shoal.ShoalId;


/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalClient extends ProtocolClient implements IShoalBoat, IShoalDirOper{
	ShoalId id;
	public ShoalClient(ShoalId id, int port, String host) {
		super(host, port);
		this.id=id;
	}
	public ShoalClient(ShoalId id, Socket socket) {
		super(socket);
		this.id=id;
	}
	
	public void castTheNet() {
		this.sendMessageObjectBlocking(new ShoalProtocolMessage(id, new TrappedByTheNetMessage()));
	}

	public int retrieveTheNet() {
		IProtocolMessage response = this.sendMessageObjectBlocking(new ShoalProtocolMessage(id, new RetrieveTheNetMessage()));
		Acknowledge ack =(Acknowledge)response;
		return (Integer)ack.getParam("catch");
	}

	public void seasonBegin() {
		this.sendMessageObjectBlocking(new ShoalProtocolMessage(id, new GoToFeedingAreaMessage()));
	}

}
