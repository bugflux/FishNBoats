/**
 * 
 */
package pt.ua.sd.diroper.network;

import java.awt.Point;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import pt.ua.sd.boat.BoatId;
import pt.ua.sd.communication.todiroper.BackAtWharfMessage;
import pt.ua.sd.communication.todiroper.BoatFullMessage;
import pt.ua.sd.communication.todiroper.ClearAllMessage;
import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.communication.todiroper.FishingDoneMessage;
import pt.ua.sd.communication.todiroper.LifeEndMessage;
import pt.ua.sd.communication.todiroper.PopMessage;
import pt.ua.sd.communication.todiroper.RequestHelpMessage;
import pt.ua.sd.communication.todiroper.SeasonEndMessage;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.IDirOper;
import pt.ua.sd.diroper.IDirOperBoat;
import pt.ua.sd.diroper.IDirOperShoal;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.shoal.network.ShoalClient;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class DirOperClient implements IDirOper, IDirOperShoal, IDirOperBoat {

	private String host;
	private int port;
	private DirOperId id;

	public DirOperClient(DirOperId id, String host, int port) {
		this.host = host;
		this.port = port;
		this.id = id;
	}

	@Override
	public DirOperMessage popMsg() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(
					id, new PopMessage(true)));
			Acknowledge ack = (Acknowledge) response;
			return (DirOperMessage) ack.getParam("message");
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
	public void clearMessages() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(id, new ClearAllMessage()));
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
	public void endSeason() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(id, new SeasonEndMessage()));
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
	public void endLife() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(id, new LifeEndMessage()));
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
	public void backAtWharf(BoatId id, int stored) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(this.id,
					new BackAtWharfMessage(id, stored)));
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
	public void requestHelp(BoatId id, Point p) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(this.id,
					new RequestHelpMessage(id, p)));
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
	public void fishingDone(BoatId id) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(this.id,
					new FishingDoneMessage(id)));
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
	public void boatFull(BoatId id) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new DirOperProtocolMessage(this.id, new BoatFullMessage(
					id)));
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
