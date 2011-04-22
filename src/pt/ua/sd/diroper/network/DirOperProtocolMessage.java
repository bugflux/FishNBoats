/**
 * 
 */
package pt.ua.sd.diroper.network;

import pt.ua.sd.communication.todiroper.DirOperMessage;
import pt.ua.sd.diroper.DirOperId;

/**
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 * @author André Prata <andreprata@ua.pt>
 */
public class DirOperProtocolMessage {

	protected final DirOperMessage m;
	protected final DirOperId id;

	/**
	 * Construct a new DirOperProtocolMessage given a destination Shoal and the
	 * message to deliver to it.
	 * 
	 * @param id
	 *            the shoal to deliver the message to.
	 * @param m
	 *            the messsage to deliver.
	 */
	public DirOperProtocolMessage(DirOperId id, DirOperMessage m) {
		this.m = m;
		this.id = id;
	}

	/**
	 * @return the message
	 */
	public DirOperMessage getMessage() {
		return m;
	}

	/**
	 * @return the diroper id
	 */
	public DirOperId getId() {
		return id;
	}
}
