/**
 * 
 */
package pt.ua.sd.boat;

import java.io.Serializable;

/**
 * The BoatId class distinctively identifies a Boat in the world.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class BoatId implements Serializable {
	private static final long serialVersionUID = 4641229688893370756L;
	protected final int company, boat;

	public BoatId(int company, int boat) {
		this.company = company;
		this.boat = boat;
	}

	/**
	 * @return the company
	 */
	public int getCompany() {
		return company;
	}

	/**
	 * @return the boat
	 */
	public int getBoat() {
		return boat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + boat;
		result = prime * result + company;
		return result;
	}

	/*
	 * (non-Javadoc)
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
		BoatId other = (BoatId) obj;
		if (boat != other.boat)
			return false;
		if (company != other.company)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Boat (" + company + "," + boat + ")";
	}
}
