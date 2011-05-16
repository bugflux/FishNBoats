/**
 * 
 */
package pt.ua.sd.ocean;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import pt.ua.sd.boat.BoatId;
import pt.ua.sd.boat.BoatStats;
import pt.ua.sd.diroper.DirOperId;
import pt.ua.sd.diroper.DirOperStats;
import pt.ua.sd.diroper.DirOperStats.INTERNAL_STATE_DIROPER;
import pt.ua.sd.log.ILogger;
import pt.ua.sd.shoal.IShoalBoat;
import pt.ua.sd.shoal.ShoalId;
import pt.ua.sd.shoal.ShoalStats;

/**
 * Point of synchronization of all Ocean actions, including state updates
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class MOcean implements ICompleteOcean {

	protected final int height, width;
	protected final int maxShoalPerSquare, maxBoatsPerSquare;
	protected final HashMap<BoatId, BoatStats> boatsPosition;
	protected final HashMap<ShoalId, ShoalStats> shoalsPosition;
	protected final HashMap<ShoalId, IShoalBoat> mshoals;
	protected final HashMap<DirOperId, DirOperStats> dirOperStats;
	protected final Cell map[][];
	protected int ncompanies = 0;
	protected final FishingGBoard gmap;
	protected final Point wharf;
	protected final Point reproducingZone;
	protected final ILogger log;

	/**
	 * Create a new MOcean object with the given parameters.
	 * 
	 * @param height
	 *            The height of the Ocean, in squares.
	 * @param width
	 *            The width of the Ocean, in squares.
	 * @param maxShoalPerSquare
	 *            The maximum number of shoals that can occupy one square.
	 * @param maxBoatsPerSquare
	 *            The maximum number of boats that can occupy one square.
	 */
	public MOcean(int height, int width, int maxShoalPerSquare, int maxBoatsPerSquare, Point wharf,
			Point reproducingZone, ILogger log) {
		assert (height > 0);
		assert (width > 0);

		assert (maxShoalPerSquare > 0); // there's no infinity
		assert (maxBoatsPerSquare > 0);

		this.height = height;
		this.width = width;

		try {
			assert isValid(wharf);
			assert isValid(reproducingZone);
		} catch (RemoteException e) {

		}
		this.log = log;
		this.wharf = wharf;
		this.reproducingZone = reproducingZone;

		this.map = new Cell[height][width];
		for (int r = 0; r < map.length; r++) {
			for (int s = 0; s < map[r].length; s++) {
				map[r][s] = new Cell(maxBoatsPerSquare, maxShoalPerSquare);
			}
		}
		map[wharf.y][wharf.x] = new Cell(Integer.MAX_VALUE, 0);
		map[reproducingZone.y][reproducingZone.x] = new Cell(0, Integer.MAX_VALUE);
		// yeah, yeah, the first is discarded...

		this.maxBoatsPerSquare = maxBoatsPerSquare;
		this.maxShoalPerSquare = maxShoalPerSquare;

		this.boatsPosition = new HashMap<BoatId, BoatStats>();
		this.shoalsPosition = new HashMap<ShoalId, ShoalStats>();
		this.mshoals = new HashMap<ShoalId, IShoalBoat>();
		this.dirOperStats = new HashMap<DirOperId, DirOperStats>();

		//
		gmap = new FishingGBoard(width, height, (int) Math.ceil((maxBoatsPerSquare + maxShoalPerSquare) / 2.0));
	}

	public void addDirOper(DirOperStats s) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			dirOperStats.put(s.getId(), s);

			logTick = log.getClockTick();
			logMessage = s.toString();
		}

		log.push("Add", s.getId().toString(), logMessage, logTick);
	}

	/**
	 * Update the state of a given DirOper.
	 * 
	 * @param id
	 *            the id of the diroper.
	 * @param s
	 *            the new state to set.
	 */
	public void setDirOperState(DirOperId id, INTERNAL_STATE_DIROPER s) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert dirOperStats.containsKey(id);
			logTick = log.getClockTick();
			logMessage = dirOperStats.get(id).getState().toString() + " > ";

			dirOperStats.get(id).setState(s);

			logMessage += s;
		}

		log.push("Set DirOper state", id.toString(), logMessage, logTick);
	}

	/**
	 * Add a boat at position p
	 * 
	 * @param boat
	 *            the id of the boat.
	 * @param p
	 *            the position to add the boat to.
	 */
	public void addBoat(BoatStats boat, Point p) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert isValid(p);
			assert !map[p.y][p.x].isBoatFull();

			ncompanies = Math.max(ncompanies, boat.getId().getCompany() + 1);

			boatsPosition.put(boat.getId(), boat);
			map[p.y][p.x].addBoat(boat.getId());
			boat.setPosition(p);

			logTick = log.getClockTick();
			logMessage = boat.toString();
		}

		log.push("Add", boat.getId().toString(), logMessage, logTick);

		gmap.draw(boat.getId(), p);
	}

	/**
	 * @see #addShoal(ShoalStats, IShoalBoat, Point)
	 */
	public void addShoal(ShoalStats shoal, IShoalBoat mshoal, int x, int y) throws RemoteException {
		synchronized (this) {
			addShoal(shoal, mshoal, new Point(x, y));
		}
	}

	/**
	 * Add a shoal at positon p.
	 * 
	 * @param shoal
	 *            the id of the shoal.
	 * @param p
	 *            the position to add the shoal to.
	 */
	public void addShoal(ShoalStats shoal, IShoalBoat mshoal, Point p) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert isValid(p);
			assert !map[p.y][p.x].isShoalFull();

			shoalsPosition.put(shoal.getId(), shoal);
			map[p.y][p.x].addShoal(shoal.getId());
			shoal.setPosition(p);

			logTick = log.getClockTick();
			logMessage = shoal.toString();

			mshoals.put(shoal.getId(), mshoal);
		}

		log.push("Add", shoal.getId().toString(), logMessage, logTick);

		gmap.draw(shoal.getId(), p);
	}

	/**
	 * Update state the of a given boat.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @param state
	 *            the new state to set.
	 */
	@Override
	public void setBoatState(BoatId id, BoatStats.INTERNAL_STATE_BOAT state) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert boatsPosition.containsKey(id);
			logMessage = boatsPosition.get(id).getState().toString() + " > ";

			boatsPosition.get(id).setState(state);

			logTick = log.getClockTick();
			logMessage += boatsPosition.get(id).getState().toString();
		}

		log.push("Set state", id.toString(), logMessage, logTick);
	}

	/**
	 * @see IOceanBoat#setBoatCatch(BoatId, int)
	 */
	@Override
	public void setBoatCatch(BoatId id, int stored) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			logTick = log.getClockTick();
			logMessage = boatsPosition.get(id).getCatch() + " > ";

			boatsPosition.get(id).setCatch(stored);

			logMessage += stored;
		}

		log.push("Set catch", id.toString(), logMessage, logTick);
	}

	/**
	 * Update the state of a given shoal.
	 * 
	 * @param id
	 *            the id of the shoal.
	 * @param state
	 *            the new state to set.
	 */
	@Override
	public void setShoalState(ShoalId id, ShoalStats.INTERNAL_STATE_SCHOOL state) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert shoalsPosition.containsKey(id);
			logMessage = shoalsPosition.get(id).getState().toString() + " > ";

			shoalsPosition.get(id).setState(state);

			logTick = log.getClockTick();
			logMessage += shoalsPosition.get(id).getState().toString();
		}

		log.push("Set state", id.toString(), logMessage, logTick);
	}

	/**
	 * Update the size of a shoal.
	 * 
	 * @param id
	 *            the id of the shoal.
	 * @param size
	 *            the size of the shoal.
	 */
	@Override
	public void setShoalSize(ShoalId id, int size) throws RemoteException {
		int logTick;
		String logMessage;

		synchronized (this) {
			assert size > 0;
			assert shoalsPosition.containsKey(id);
			logMessage = shoalsPosition.get(id).getSize() + " > ";

			shoalsPosition.get(id).setSize(size);

			logTick = log.getClockTick();
			logMessage += shoalsPosition.get(id).getSize();
		}

		log.push("Set size", id.toString(), logMessage, logTick);
	}

	/**
	 * Attempt to move boat BoatId one square in the direction of point p.
	 * Movements are taxicab based (90 degree only).
	 * 
	 * If two cells are available to perform a movement in the desired
	 * direction, a random one will be chosen. If none can be taken, the boat
	 * will stay in the same position. If the position is its current, no
	 * movement will be performed.
	 * 
	 * @param id
	 *            the boat to move.
	 * @param p
	 *            the destination point.
	 * @return the new coordinate for boat id.
	 */
	@Override
	public Point tryMoveBoat(BoatId id, Point p) throws RemoteException {
		int logTick;
		String logMessage;

		Point r;
		Point c;

		synchronized (this) {
			assert isValid(p);
			assert boatsPosition.containsKey(id);

			c = new Point(boatsPosition.get(id).getPosition());
			r = c;

			logMessage = "(" + c.y + "," + c.x + ") > ";

			// if it is in the desired position already
			if (c.equals(p)) {
				r = new Point(c); // return the same
			} // if it is in the desired width
			else if (p.y == c.y) {
				// wants to move west
				if (p.x > c.x) {
					if (!map[c.y][c.x + 1].isBoatFull()) {
						r = new Point(c.x + 1, c.y);
					}
				} // wants to move east
				else {
					if (!map[c.y][c.x - 1].isBoatFull()) {
						r = new Point(c.x - 1, c.y);
					}
				}
			} // if it is in the desired column
			else if (p.x == c.x) {
				// wants to move north
				if (p.y > c.y) {
					if (!map[c.y + 1][c.x].isBoatFull()) {
						r = new Point(c.x, c.y + 1);
					}
				} // wants to move south
				else {
					if (!map[c.y - 1][c.x].isBoatFull()) {
						r = new Point(c.x, c.y - 1);
					}
				}
			} // has to change row and column
			else {
				Random rand = new Random(new Date().getTime() * Thread.currentThread().getId() * id.hashCode());
				List<Point> available = new ArrayList<Point>();

				// up and right
				if (p.y > c.y && p.x > c.x) {
					// up
					if (!map[c.y + 1][c.x].isBoatFull()) {
						available.add(new Point(c.x, c.y + 1));
					}

					// right
					if (!map[c.y][c.x + 1].isBoatFull()) {
						available.add(new Point(c.x + 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // up and left
				else if (p.y > c.y && p.x < c.x) {
					// up
					if (!map[c.y + 1][c.x].isBoatFull()) {
						available.add(new Point(c.x, c.y + 1));
					}

					// left
					if (!map[c.y][c.x - 1].isBoatFull()) {
						available.add(new Point(c.x - 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // down and right
				else if (p.y < c.y && p.x > c.x) {
					// down
					if (!map[c.y - 1][c.x].isBoatFull()) {
						available.add(new Point(c.x, c.y - 1));
					}

					// right
					if (!map[c.y][c.x + 1].isBoatFull()) {
						available.add(new Point(c.x + 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // down and left
				else /* if(p.y < c.y && p.x < c.x) */{
					// down
					if (!map[c.y - 1][c.x].isBoatFull()) {
						available.add(new Point(c.x, c.y - 1));
					}

					// left
					if (!map[c.y][c.x - 1].isBoatFull()) {
						available.add(new Point(c.x - 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				}
			}

			if (!r.equals(c)) {
				moveBoat(id, r);
			}

			logTick = log.getClockTick();
			logMessage += "(" + r.y + "," + r.x + ")";
		}

		if (!r.equals(c)) {
			gmoveBoat(id, c, r);
		}

		log.push("Move", id.toString(), logMessage, logTick);

		return r;
	}

	/**
	 * Internal moveBoat. Assertions have been made, the boat can effectively
	 * move there!
	 * 
	 * @param id
	 *            the boat.
	 * @param p
	 *            the point to teleport it to.
	 */
	protected void moveBoat(BoatId id, Point p) throws RemoteException {
		assert isValid(p);
		assert boatsPosition.containsKey(id);

		Point c = boatsPosition.get(id).getPosition();
		assert map[c.y][c.x].getBoats().contains(id);

		map[c.y][c.x].removeBoat(id);
		map[p.y][p.x].addBoat(id);
		boatsPosition.get(id).setPosition(p);
	}

	/**
	 * @see IOceanBoat#companionDetected(BoatId, BoatId)
	 */
	public IShoalBoat companionDetected(BoatId id, BoatId helper) throws RemoteException {
		int logTick;
		String logShoal = "Shoal NOT present";
		String logCompanion = "Companion NOT present";

		IShoalBoat shoal = null;
		synchronized (this) {
			assert boatsPosition.containsKey(id);
			assert boatsPosition.containsKey(helper);

			logTick = log.getClockTick();

			Point p = boatsPosition.get(id).getPosition();
			if (p.equals(boatsPosition.get(helper).getPosition())) {
				for (ShoalId s : map[p.y][p.x].getShoals()) {
					ShoalStats stats = shoalsPosition.get(s);
					if (shoalsPosition.get(s).isDetectable()) {
						shoal = mshoals.get(stats.getId());
						logShoal = "Shoal present";
					} else {
						logShoal = "Shoal present but undetectable (too small or trapped by the net)";
					}
				}
				logCompanion = "Companion present";
			}
		}

		log.push("Scan companion", id.toString(), logCompanion + ", " + logShoal, logTick);

		return shoal;
	}

	/**
	 * Get a list of points where the radar of a given boat BoatId managed to
	 * find fish.
	 * 
	 * @param id
	 *            the id of the boat.
	 * @return a list of points with fish, at the current moment.
	 */
	@Override
	public List<Point> getRadar(BoatId id) throws RemoteException {
		int logTick;

		List<Point> points;

		synchronized (this) {
			assert boatsPosition.containsKey(id);

			logTick = log.getClockTick();

			points = new ArrayList<Point>();

			// check center
			Point c = boatsPosition.get(id).getPosition();
			for (ShoalId s : map[c.y][c.x].getShoals()) {
				ShoalStats stats = shoalsPosition.get(s);
				if (stats.isDetectable()) {
					points.add(new Point(c));
				}
			}

			// check north
			if (c.y > 0) {
				for (ShoalId s : map[c.y - 1][c.x].getShoals()) {
					ShoalStats stats = shoalsPosition.get(s);
					if (stats.isDetectable()) {
						points.add(new Point(c.x, c.y - 1));
					}
				}
			}

			// check south
			if (c.y < height - 1) {
				for (ShoalId s : map[c.y + 1][c.x].getShoals()) {
					ShoalStats stats = shoalsPosition.get(s);
					if (stats.isDetectable()) {
						points.add(new Point(c.x, c.y + 1));
					}
				}
			}

			// check east
			if (c.x > 0) {
				for (ShoalId s : map[c.y][c.x - 1].getShoals()) {
					ShoalStats stats = shoalsPosition.get(s);
					if (stats.isDetectable()) {
						points.add(new Point(c.x - 1, c.y));
					}
				}
			}

			// check west
			if (c.x < width - 1) {
				for (ShoalId s : map[c.y][c.x + 1].getShoals()) {
					ShoalStats stats = shoalsPosition.get(s);
					if (stats.isDetectable()) {
						points.add(new Point(c.x + 1, c.y));
					}
				}
			}

			// detect only if there's space for two boats or if there's space
			// for
			// one boat and there's already one boat from the same company
			// there. But not two from the same company!
			List<Point> toRemove = new ArrayList<Point>();
			for (Point p : points) {
				if (map[p.y][p.x].getMaxBoats() - map[p.y][p.x].boatsCount() < 1) {
					toRemove.add(p);
				} else if (map[p.y][p.x].getMaxBoats() - map[p.y][p.x].boatsCount() < 2) {
					int companions = 0;
					for (BoatId b : map[p.y][p.x].getBoats()) {
						if (b.getCompany() == id.getCompany()) {
							companions++;
						}
					}

					if (companions != 1) {
						toRemove.add(p);
					}
				}
			}

			points.removeAll(toRemove);
		}

		// log
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("Boat(" + id.getCompany() + "-" + id.getBoat() + ") ");
		if (points.isEmpty()) {
			logMessage.append("fish NOT found");
		} else {
			logMessage.append("fish found: ");
			logMessage.append("(" + points.get(0).y + "," + points.get(0).x + ")");
			for (int r = 1; r < points.size(); r++) {
				logMessage.append(", (" + points.get(r).y + "," + points.get(r).x + ")");
			}
		}

		log.push("Scan shoal", id.toString(), logMessage.toString(), logTick);

		return points;
	}

	/**
	 * Attempt to move shoal ShoalId one square in the direction of point p.
	 * Movements are taxicab based (90 degree only).
	 * 
	 * If two cells are available to perform a movement in the desired
	 * direction, a random one will be chosen. If none can be taken, the shoal
	 * will stay in the same position. If the position is its current, no
	 * movement will be performed.
	 * 
	 * @param id
	 *            the boat to move.
	 * @param p
	 *            the destination point.
	 * @return the new coordinate for boat id.
	 */
	@Override
	public Point tryMoveShoal(ShoalId id, Point p) throws RemoteException {
		int logTick;
		String logMessage;

		Point c, r;

		synchronized (this) {
			assert isValid(p);
			assert shoalsPosition.containsKey(id);

			c = new Point(shoalsPosition.get(id).getPosition());
			r = c;

			logMessage = "(" + c.y + "," + c.x + ") > ";

			boolean canMove = true;
			if (shoalsPosition.get(id).isDetectable()) {
				int companyCounters[] = new int[ncompanies];
				if (shoalsPosition.get(id).isDetectable()) {
					for (BoatId b : map[c.y][c.x].getBoats()) {
						if (++companyCounters[b.getCompany()] > 1) {
							canMove = false;
							break;
						}
					}
				}
			}

			// if it is in the desired position already
			if (!canMove || c.equals(p)) {
				r = new Point(c); // return the same
			} // if it is in the desired width
			else if (p.y == c.y) {
				// wants to move west
				if (p.x > c.x) {
					if (!map[c.y][c.x + 1].isShoalFull()) {
						r = new Point(c.x + 1, c.y);
					}
				} // wants to move east
				else {
					if (!map[c.y][c.x - 1].isShoalFull()) {
						r = new Point(c.x - 1, c.y);
					}
				}
			} // if it is in the desired column
			else if (p.x == c.x) {
				// wants to move north
				if (p.y > c.y) {
					if (!map[c.y + 1][c.x].isShoalFull()) {
						r = new Point(c.x, c.y + 1);
					}
				} // wants to move south
				else {
					if (!map[c.y - 1][c.x].isShoalFull()) {
						r = new Point(c.x, c.y - 1);
					}
				}
			} // has to change row and column
			else {
				Random rand = new Random(new Date().getTime() * Thread.currentThread().getId() * id.hashCode());
				List<Point> available = new ArrayList<Point>();

				// up and right
				if (p.y > c.y && p.x > c.x) {
					// up
					if (!map[c.y + 1][c.x].isShoalFull()) {
						available.add(new Point(c.x, c.y + 1));
					}

					// right
					if (!map[c.y][c.x + 1].isShoalFull()) {
						available.add(new Point(c.x + 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // up and left
				else if (p.y > c.y && p.x < c.x) {
					// up
					if (!map[c.y + 1][c.x].isShoalFull()) {
						available.add(new Point(c.x, c.y + 1));
					}

					// left
					if (!map[c.y][c.x - 1].isShoalFull()) {
						available.add(new Point(c.x - 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // down and right
				else if (p.y < c.y && p.x > c.x) {
					// down
					if (!map[c.y - 1][c.x].isShoalFull()) {
						available.add(new Point(c.x, c.y - 1));
					}

					// right
					if (!map[c.y][c.x + 1].isShoalFull()) {
						available.add(new Point(c.x + 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				} // down and left
				else /* if(p.y < c.y && p.x < c.x) */{
					// down
					if (!map[c.y - 1][c.x].isShoalFull()) {
						available.add(new Point(c.x, c.y - 1));
					}

					// left
					if (!map[c.y][c.x - 1].isShoalFull()) {
						available.add(new Point(c.x - 1, c.y));
					}

					if (available.size() > 0) {
						r = available.get(rand.nextInt(available.size()));
					}
				}
			}

			if (!r.equals(c)) {
				moveShoal(id, r);
			}

			logTick = log.getClockTick();
			logMessage += "(" + r.y + "," + r.x + ")";
		}

		if (!r.equals(c)) {
			gmoveShoal(id, c, r);
		}

		log.push("Move", id.toString(), logMessage, logTick);

		return r;
	}

	/**
	 * Internal moveShoal. Assertions have been made, the shoal can effectively
	 * move there!
	 * 
	 * @param id
	 *            the shoal.
	 * @param p
	 *            the point to teleport it to.
	 */
	protected void moveShoal(ShoalId id, Point p) throws RemoteException {
		assert isValid(p);
		assert shoalsPosition.containsKey(id);

		Point c = shoalsPosition.get(id).getPosition();
		assert map[c.y][c.x].getShoals().contains(id);

		map[c.y][c.x].removeShoal(id);
		map[p.y][p.x].addShoal(id);
		shoalsPosition.get(id).setPosition(p);
	}

	private void gmoveShoal(ShoalId id, Point o, Point p) {
		gmap.erase(id, o);
		gmap.draw(id, p);
	}

	private void gmoveBoat(BoatId id, Point o, Point p) {
		gmap.erase(id, o);
		gmap.draw(id, p);
	}

	/**
	 * Test if a point is valid.
	 * 
	 * @param p
	 *            the point.
	 * @return true if the point is within 0:height and 0:width-1
	 */
	protected boolean isValid(Point p) throws RemoteException {
		boolean b = p != null && p.x >= 0 && p.y >= 0 && p.x < width && p.y < height;
		if (!b) {
			b = false;
		}
		return b;
	}

	/**
	 * @see IOceanBoat#getHeight()
	 * @see IOceanShoal#getHeight()
	 */
	@Override
	// no need to synch this, it's final!
	public int getHeight() throws RemoteException {
		return height;
	}

	/**
	 * @see IOceanBoat#getWidth()
	 * @see IOceanShoal#getWidth()
	 */
	@Override
	// no need to synch this, it's final!
	public int getWidth() throws RemoteException {
		return width;
	}

	/**
	 * @see IOceanBoat#getWharf()
	 */
	@Override
	// no need to synch this, it's final!
	public Point getWharf() throws RemoteException {
		return new Point(wharf);
	}

	/**
	 * @see IOceanShoal#getSpawningArea()
	 */
	@Override
	// no need to synch this, it's final!
	public Point getSpawningArea() throws RemoteException {
		return reproducingZone;
	}

	protected class Cell {

		final int maxBoats, maxShoals;
		ArrayList<BoatId> boats;
		ArrayList<ShoalId> shoals;

		/**
		 * Create a new Cell with the given limits.
		 * 
		 * @param maxBoats
		 * @param maxShoals
		 */
		Cell(int maxBoats, int maxShoals) {
			this.maxBoats = maxBoats;
			this.maxShoals = maxShoals;
			this.boats = new ArrayList<BoatId>();
			this.shoals = new ArrayList<ShoalId>();
		}

		/**
		 * @return the maxBoats
		 */
		public int getMaxBoats() {
			return maxBoats;
		}

		/**
		 * @return the maxShoals
		 */
		public int getMaxShoals() {
			return maxShoals;
		}

		/**
		 * @return the boats
		 */
		public List<BoatId> getBoats() {
			return boats;
			// TODO consider passing a copy constructor rather than original
			// reference!
		}

		/**
		 * @return the shoals
		 */
		public List<ShoalId> getShoals() {
			return shoals;
			// TODO consider passing a copy constructor rather than original
			// reference!
		}

		/**
		 * Adds shoal with Id id to this cell. Must have space left!
		 * 
		 * @param id
		 *            the id of the shoal
		 */
		public void addShoal(ShoalId id) {
			assert (shoals.size() < maxShoals);
			shoals.add(id);
		}

		/**
		 * 
		 * @param id
		 */
		public void addBoat(BoatId id) {
			assert (boats.size() < maxBoats);
			boats.add(id);
		}

		/**
		 * 
		 * @param id
		 */
		public void removeShoal(ShoalId id) {
			assert (shoals.contains(id));
			shoals.remove(id);
		}

		/**
		 * 
		 * @param id
		 */
		public void removeBoat(BoatId id) {
			assert (boats.contains(id));
			boats.remove(id);
		}

		/**
		 * @return the number of shoals in this cell.
		 */
		public int shoalsCount() {
			return shoals.size();
		}

		/**
		 * @return the number of boats in this cell.
		 */
		public int boatsCount() {
			return boats.size();
		}

		/**
		 * @return true if no more shoals can enter this cell.
		 */
		public boolean isShoalFull() {
			return shoals.size() == maxShoals;
		}

		/**
		 * @return true if no more boats can enter this cell.
		 */
		public boolean isBoatFull() {
			return boats.size() == maxBoats;
		}
	}
}
