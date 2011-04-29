/**
 * 
 */
package pt.ua.sd.shoal;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class ShoalId {
	protected final int shoal;

	/**
	 * Create a new ShoalId with a shoal integer identifier
	 * 
	 * @param shoal
	 *            the integer identifier
	 */
	public ShoalId(int shoal) {
		this.shoal = shoal;
	}

	/**
	 * Get the integer identifier for this shoal.
	 * 
	 * @return the integer identifier for this shoal.
	 */
	public int getShoal() {
		return shoal;
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
		result = prime * result + shoal;
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
		ShoalId other = (ShoalId) obj;
		if (shoal != other.shoal)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shoal (" + shoal + ")";
	}
}
