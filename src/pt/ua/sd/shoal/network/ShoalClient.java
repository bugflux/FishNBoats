/**
 * 
 */
package pt.ua.sd.shoal.network;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.communication.toshoal.GoToFeedingAreaMessage;
import pt.ua.sd.communication.toshoal.IsTrapped;
import pt.ua.sd.communication.toshoal.PopMessage;
import pt.ua.sd.communication.toshoal.RetrieveTheNetMessage;
import pt.ua.sd.communication.toshoal.ShoalMessage;
import pt.ua.sd.communication.toshoal.TrappedByTheNetMessage;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.IShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.IShoalDirOper;
import pt.ua.sd.shoal.ShoalId;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class ShoalClient implements IShoal, IShoalBoat, IShoalDirOper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1113640032766861068L;

	private ShoalId id;
	private int port;
	private String host;

	public ShoalClient(ShoalId id, int port, String host) {
		this.port = port;
		this.host = host;
		this.id = id;
	}

	public void castTheNet() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id,
					new TrappedByTheNetMessage()));
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
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

	public int retrieveTheNet() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id,
					new RetrieveTheNetMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Integer) ack.getParam("catch");
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return 0;
	}

	int count = 0;

	public void seasonBegin() {
		Socket socket = null;
		try {
			count++;
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id,
					new GoToFeedingAreaMessage()));
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
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
	public ShoalMessage popMsg(boolean blocking) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id,
					new PopMessage(blocking)));
			Acknowledge ack = (Acknowledge) response;
			return (ShoalMessage) ack.getParam("message");
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
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

	@Override
	public void isTrapped(int amount) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new ShoalProtocolMessage(id, new IsTrapped(amount)));
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			Logger.getLogger(ShoalClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			ex.printStackTrace();
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
}
