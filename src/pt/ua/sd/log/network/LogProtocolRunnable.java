package pt.ua.sd.log.network;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.communication.tologger.LoggerMessage;
import pt.ua.sd.communication.tologger.PushMessage;
import pt.ua.sd.log.MLog;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.IProtocolRunnable;
import pt.ua.sd.network.ProtocolEndPoint;

/**
 * The Log message handler at the server side
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class LogProtocolRunnable implements IProtocolRunnable {

	private Socket socket;
	private static MLog logger = MLog.getInstance();
	private static Acknowledge ack = new Acknowledge();

	public LogProtocolRunnable(Socket socket) {
		this.socket = socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public LogProtocolRunnable() {
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
		if (msg instanceof LogProtocolMessage) {
			LogProtocolMessage m = (LogProtocolMessage) msg;

			switch ((LoggerMessage.MESSAGE_TYPE) m.getMessage().getMsgType()) {
			case getTickClock:
				int tick = logger.getClockTick();
				Acknowledge acknowledgetick = new Acknowledge();
				acknowledgetick.setParam("tick", tick);
				ProtocolEndPoint.sendMessageObject(socket, acknowledgetick);
				break;
			case popContiguous:
				String popmsg = logger.popContiguous();
				Acknowledge acknowledgemsg = new Acknowledge();
				acknowledgemsg.setParam("tick", popmsg);
				ProtocolEndPoint.sendMessageObject(socket, acknowledgemsg);
				break;
			case pushMessage:
				PushMessage push = ((PushMessage) m.getMessage());
				logger.push(push.getType(), push.getEntity(), push.getMessage(), push.getClockTick());
				ProtocolEndPoint.sendMessageObject(socket, ack);
				break;
			default:
				throw new RuntimeException("Message is not defined");
			}
		} else {
			// TODO: error case
		}
		try {
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(LogProtocolRunnable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
