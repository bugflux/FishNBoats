/**
 * 
 */
package pt.ua.sd.ocean.network;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats.INTERNAL_STATE_BOAT;
import pt.ua.sd.communication.toocean.CompanionDetectedMessage;
import pt.ua.sd.communication.toocean.GetHeightMessage;
import pt.ua.sd.communication.toocean.GetRadarMessage;
import pt.ua.sd.communication.toocean.GetSpawningAreaMessage;
import pt.ua.sd.communication.toocean.GetWharfMessage;
import pt.ua.sd.communication.toocean.GetWidthMessage;
import pt.ua.sd.communication.toocean.SetBoatCatchMessage;
import pt.ua.sd.communication.toocean.SetBoatStateMessage;
import pt.ua.sd.communication.toocean.SetDirOperStateMessage;
import pt.ua.sd.communication.toocean.SetShoalSizeMessage;
import pt.ua.sd.communication.toocean.SetShoalStateMessage;
import pt.ua.sd.communication.toocean.TryMoveBoatMessage;
import pt.ua.sd.communication.toocean.TryMoveShoalMessage;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.network.Acknowledge;
import pt.ua.sd.network.IProtocolMessage;
import pt.ua.sd.network.ProtocolEndPoint;
import pt.ua.sd.ocean.IOceanBoat;
import pt.ua.sd.ocean.IOceanDirOper;
import pt.ua.sd.ocean.IOceanShoal;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats.INTERNAL_STATE_SCHOOL;

/**
 * The interface to communicate with a remote Ocean monitor
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class OceanClient implements IOceanBoat, IOceanShoal, IOceanDirOper, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4327767975492500997L;

	private String host;
	private int port;

	public OceanClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	public Point tryMoveBoat(BoatId id, Point p) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new TryMoveBoatMessage(id, p)));
			Acknowledge ack = (Acknowledge) response;
			return (Point) ack.getParam("point");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Point> getRadar(BoatId id) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new GetRadarMessage(id)));
			Acknowledge ack = (Acknowledge) response;
			return (List<Point>) ack.getParam("radar");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	public int getHeight() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new GetHeightMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Integer) ack.getParam("height");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return -1;
	}

	public int getWidth() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new GetWidthMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Integer) ack.getParam("width");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}

		return -1;
	}

	public void setBoatState(BoatId id, INTERNAL_STATE_BOAT state) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(new SetBoatStateMessage(id,
					state)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public void setBoatCatch(BoatId id, int stored) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(new SetBoatCatchMessage(id,
					stored)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}

	public Point getWharf() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new GetWharfMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Point) ack.getParam("point");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	public IShoalBoat companionDetected(BoatId id, BoatId helper) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new CompanionDetectedMessage(id, helper)));
			Acknowledge ack = (Acknowledge) response;
			return (IShoalBoat) ack.getParam("companion");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	public Point tryMoveShoal(ShoalId id, Point p) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new TryMoveShoalMessage(id, p)));
			Acknowledge ack = (Acknowledge) response;
			return (Point) ack.getParam("point");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	public void setShoalState(ShoalId id, INTERNAL_STATE_SCHOOL state) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(new SetShoalStateMessage(id,
					state)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}

	public void setShoalSize(ShoalId id, int size) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(new SetShoalSizeMessage(id,
					size)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}

	public Point getSpawningArea() {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			IProtocolMessage response = ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(
					new GetSpawningAreaMessage()));
			Acknowledge ack = (Acknowledge) response;
			return (Point) ack.getParam("point");
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
		return null;
	}

	public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER state) {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			ProtocolEndPoint.sendMessageObjectBlocking(socket, new OceanProtocolMessage(new SetDirOperStateMessage(id,
					state)));
		} catch (UnknownHostException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			if (socket != null)
				try {
					socket.close();
				} catch (IOException ex) {
					Logger.getLogger(OceanClient.class.getName()).log(Level.SEVERE, null, ex);
				}
		}
	}
}
