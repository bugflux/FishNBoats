/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.shoal.network;

import java.net.Socket;

import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolClient;
import pt.ua.sd.shoal.MShoal;

/**
 * 
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class ShoalProtocolRunnable implements IProtocolRunnable {

	private Socket socket;
	private MShoal[] mShoals;

	public void setConnection(Socket socket) {
		this.socket = socket;
	}

	public void setShoalsMonitor(MShoal[] shoals) {
		this.mShoals = shoals;
	}

	public void run() {
		if (socket == null) {
			throw new RuntimeException("socket not setted");
		}
		ProtocolClient client = new ProtocolClient(socket);
		IProtocolMessage msg = client.getMessageObject();
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
				break;
			case NoActionMessage:
				break;
			case TrappedByTheNet:
				mShoals[shoal_id].castTheNet();
				client.sendMessageObject(new pt.ua.sd.network.Acknowledge());
				break;
			case RetrieveTheNet:
				int retrieveTheNet = mShoals[shoal_id].retrieveTheNet();
				Acknowledge ack = new Acknowledge();
				ack.setParam("catch", retrieveTheNet + "");
				client.sendMessageObject(ack);
				break;
			default:
				throw new RuntimeException("Message is not defined");
			}
		} else {
			// TODO: error case
		}
	}
}
