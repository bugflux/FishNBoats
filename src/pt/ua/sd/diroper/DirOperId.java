/**
 * 
 */
package pt.ua.sd.diroper;

import java.io.Serializable;

/**
 * Distinctively identify a DirOper
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DirOperId implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 8677728891865436848L;
	protected final int id;

	public DirOperId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "DirOper(" + id + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DirOperId other = (DirOperId) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + this.id;
		return hash;
	}

}
