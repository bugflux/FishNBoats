/**
 * 
 */
package pt.ua.sd.shoal;

import java.awt.Point;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class ShoalStats {
	public enum INTERNAL_STATE_SCHOOL {
		spawning, feeding, trapped_by_the_net
	}

	protected INTERNAL_STATE_SCHOOL state;
	protected Point location = new Point();
	protected int size;
	protected final ShoalId id;
	protected final int minDetectable;

	/**
	 * Instantiate a new ShoalStats object with an initial state, location and
	 * size in fish count.
	 * 
	 * @param state
	 *            The initial state.
	 * @param location
	 *            The initial location in the Ocean.
	 * @param size
	 *            The initial fish count in the shoal.
	 */
	public ShoalStats(ShoalId id, INTERNAL_STATE_SCHOOL state, Point location,
			int size, int minDetectable) {
		this.id = id;
		this.state = state;
		this.location = location;
		this.size = size;
		this.minDetectable = minDetectable;
	}

	public void setPosition(int x, int y) {
		location.setLocation(x, y);
	}

	public void setPosition(Point p) {
		location.setLocation(p);
	}

	public Point getPosition() {
		return location;
	}

	public void setState(INTERNAL_STATE_SCHOOL s) {
		this.state = s;
	}

	public INTERNAL_STATE_SCHOOL getState() {
		return state;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Get the Id of this Shoal.
	 * @return the numeric id.
	 */
	public ShoalId getId() {
		return id;
	}

	/**
	 * The shoal is detectable if the size is greater or equal than the
	 * minDetectable size.
	 * 
	 * @return true if it is detectable, false otherwise.
	 */
	public boolean isDetectable() {
		return size >= minDetectable;
	}

	/**
	 * This is calculated with the ShoalId only.
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
	 * ShoalStats with the same ShoalId are equal.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShoalStats other = (ShoalStats) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getId() + ", State " + state + ", Position (" + location.y + ","
				+ location.x + "), Size " + size;
	}
}
