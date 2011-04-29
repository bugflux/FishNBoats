/**
 * 
 */
package pt.ua.sd.boat.network;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.IBoat;
import pt.ua.sd.boat.IBoatDirOper;
import pt.ua.sd.boat.IBoatHelper;
import pt.ua.sd.communication.toboat.BoatMessage;
import pt.ua.sd.communication.toboat.CastTheNetMessage;
import pt.ua.sd.communication.toboat.ChangeCourseMessage;
import pt.ua.sd.communication.toboat.GetIdMessage;
import pt.ua.sd.communication.toboat.HelpRequestServedMessage;
import pt.ua.sd.communication.toboat.LifeEndMessage;
import pt.ua.sd.communication.toboat.PopMessage;
import pt.ua.sd.communication.toboat.ReleaseHelperMessage;
import pt.ua.sd.communication.toboat.ReturnToWharfMessage;
import pt.ua.sd.communication.toboat.SetToHighSeaMessage;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.network.ShoalClient;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class BoatClient implements IBoat, IBoatDirOper, IBoatHelper, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6323734479640900664L;

	private BoatId id;
	private DirOperId dOperId;
	private String host;
	private int port;

	public BoatClient(DirOperId dOperId, BoatId id, String host, int port) {
		this.id = id;
		this.host = host;
		this.port = port;
		this.dOperId = dOperId;
	}

	@Override
	public BoatMessage popMsg(boolean blocking) {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id,
					dOperId, new PopMessage(blocking)));
			Acknowledge ack = (Acknowledge) response;
			return (BoatMessage) ack.getParam("message");
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

	@Override
	public void setToHighSea() {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new SetToHighSeaMessage()));
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
	public void returnToWharf() {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new ReturnToWharfMessage()));
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
	public BoatId getId() {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id,
					dOperId, new GetIdMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (BoatId) ack.getParam("id");
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

	@Override
	public void lifeEnd() {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new LifeEndMessage()));
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
	public void helpRequestServed(IBoatHelper helper) {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new HelpRequestServedMessage(helper)));
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
	public void changeCourse(Point p) {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new ChangeCourseMessage(p)));
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
	public void castTheNet(IShoalBoat s) {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new CastTheNetMessage(s)));
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
	public void releaseHelper() {

		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new BoatProtocolMessage(id, dOperId,
					new ReleaseHelperMessage()));
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
}
