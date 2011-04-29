/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.communication.tologger;

/**
 * 
 * @author eriksson
 */
public class PopContiguousMessage extends LoggerMessage {

	/**
     * 
     */
	private static final long serialVersionUID = 8167486838241346503L;

	/**
	 * @return MESSAGE_TYPE.NoActionMessage
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.popContiguous;
	}
}
