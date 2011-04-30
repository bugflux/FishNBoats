/**
 * 
 */
package pt.ua.sd.log.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.communication.tologger.GetTickClockMessage;
import pt.ua.sd.communication.tologger.PopContiguousMessage;
import pt.ua.sd.communication.tologger.PushMessage;
import pt.ua.sd.log.ILogger;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.network.ShoalClient;

/**
 * The interface to communicate with a remote Logging monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class LogClient implements ILogger {

	private String host;
	private int port;

	public LogClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public int getClockTick() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new LogProtocolMessage(
					new GetTickClockMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Integer) ack.getParam("tick");
		} catch (UnknownHostException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return -1;
	}

	@Override
	public void push(String type, String entity, String message, int tick) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new LogProtocolMessage(new PushMessage(type, entity,
					message, tick)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	@Override
	public String popContiguous() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new LogProtocolMessage(
					new PopContiguousMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (String) ack.getParam("catch");
		} catch (UnknownHostException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
		return null;
	}
}
