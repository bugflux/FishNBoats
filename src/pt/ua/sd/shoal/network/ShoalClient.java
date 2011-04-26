/**
 * 
 */
package pt.ua.sd.shoal.network;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import pt.ua.sd.communication.todiroper.LifeEndMessage;
import pt.ua.sd.communication.todiroper.SeasonEndMessage;
import pt.ua.sd.communication.toocean.SetShoalSizeMessage;
import pt.ua.sd.communication.toocean.SetShoalStateMessage;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.network.DirOperProtocolMessage;
import pt.ua.sd.network.ProtocolClient;
import pt.ua.sd.ocean.network.OceanProtocolMessage;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalClient {
	
	public static void sendEndSeason(HashMap<Integer,Socket> socket){
		for(Entry<Integer,Socket> s : socket.entrySet()){
			ProtocolClient client = new ProtocolClient(s.getValue());
			client.sendMessageObject(new DirOperProtocolMessage(new DirOperId(s.getKey()), new SeasonEndMessage()));
			client.disconnect();
		}
	}
	public static void sendEndLife(HashMap<Integer,Socket> socket){
		for(Entry<Integer,Socket> s : socket.entrySet()){
			ProtocolClient client = new ProtocolClient(s.getValue());
			client.sendMessageObject(new DirOperProtocolMessage(new DirOperId(s.getKey()), new LifeEndMessage()));
			client.disconnect();
		}
	}

	public static void changeSize(Socket socket ,ShoalId id, int size){
		ProtocolClient client = new ProtocolClient(socket);
		client.sendMessageObject(new OceanProtocolMessage(new SetShoalSizeMessage(id, size)));
		client.disconnect();
	}

	public static void changeSize(Socket socket ,ShoalId id, INTERNAL_STATE_SCHOOL state){
		ProtocolClient client = new ProtocolClient(socket);
		client.sendMessageObject(new OceanProtocolMessage(new SetShoalStateMessage(id, state)));
		client.disconnect();
	}


}
