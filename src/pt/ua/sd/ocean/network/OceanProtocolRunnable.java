/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.sd.ocean.network;

import java.net.Socket;
import pt.ua.sd.communication.toocean.OceanMessage;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolClient;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public class OceanProtocolRunnable implements IProtocolRunnable {
	private Socket socket;

	public void setConnection(Socket socket) {
		this.socket = socket;
	}

	public void setOceanMonitors(){

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
		if(msg instanceof OceanProtocolMessage){
			//do something
			switch ((OceanMessage.MESSAGE_TYPE) msg.getMessage().getMsgType()) {

			}
		}else{
			//return a error
		}
	}

}
