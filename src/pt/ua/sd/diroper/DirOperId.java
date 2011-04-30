/**
 * 
 */
package pt.ua.sd.diroper;

/**
 * Distinctively identify a DirOper
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class DirOperId {
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
