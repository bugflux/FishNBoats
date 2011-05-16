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
	private static final long serialVersionUID = 8677728891865436848L;
	protected final int id;

	public DirOperId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirOperId other = (DirOperId) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirOper (" + id + ")";
	}
}
