/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.ua.sd.network;

import java.net.Socket;

/**
 *
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public interface IProtocolRunnable extends Runnable {
	public void setConnetion(Socket socket);
}
