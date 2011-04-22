/**
 * 
 */
package pt.ua.sd.diroper;

import java.io.Serializable;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
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
}
