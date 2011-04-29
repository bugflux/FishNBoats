/**
 * 
 */
package pt.ua.sd.boat;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class BoatStats implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2196857245971079652L;

	public enum INTERNAL_STATE_BOAT {
		at_the_wharf, searching_for_fish, tracking_a_school, joining_a_companion, returning_to_wharf, boat_full
	}

	protected INTERNAL_STATE_BOAT state;
	protected Point location;
	protected final int capacity;
	protected int stored;
	protected final BoatId id;

	/**
	 * Instantiate a new BoatStats object with a given state, starting location
	 * and maximum fish capacity.
	 * 
	 * @param state
	 *            The initial state.
	 * @param location
	 *            The location in the Ocean.
	 * @param capacity
	 *            The maximum capacity of fish storage.
	 */
	public BoatStats(BoatId id, INTERNAL_STATE_BOAT state, Point location, int capacity) {
		this.id = id;
		this.state = state;
		this.location = location;
		this.capacity = capacity;
	}

	/**
	 * set the current position of the boat
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		location.setLocation(x, y);
	}

	/**
	 * set the actual positon of the boat
	 * 
	 * @param p
	 */
	public void setPosition(Point p) {
		location.setLocation(p);
	}

	/**
	 * get the actual position of the boat
	 * 
	 * @return boat's postition
	 */
	public Point getPosition() {
		return location;
	}

	/**
	 * get the amount of fish captured
	 * 
	 * @return number of fish captured
	 */
	public int getCatch() {
		return stored;
	}

	/**
	 * set the amount of fish captured
	 * 
	 * @param catched
	 */
	public void setCatch(int catched) {
		if (catched <= capacity) {
			this.stored = catched;
		} else {
			this.stored = capacity;
		}
	}

	public boolean isFull() {
		return stored == capacity;
	}

	/**
	 * set the actual state of the boat
	 * 
	 * @param s
	 */
	public void setState(INTERNAL_STATE_BOAT s) {
		this.state = s;
	}

	/**
	 * get the actual state of the boat
	 * 
	 * @return INTERNAL_STATE_BOAT
	 */
	public INTERNAL_STATE_BOAT getState() {
		return state;
	}

	/**
	 * get the boat stock capacity
	 * 
	 * @return capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * get the boat id
	 * 
	 * @return boat id
	 */
	public BoatId getId() {
		return id;
	}

	/**
	 * Only the BoatId is used to calculate the hashcode.
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Boats with the same BoatId are equal.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BoatStats other = (BoatStats) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return getId() + ", State " + state + ", Position (" + location.y + "," + location.x + "), Catch " + stored
				+ ", Capacity " + capacity;
	}

	@Override
	public Object clone() {
		return new BoatStats(id, state, (Point) location.clone(), capacity);
	}
}
