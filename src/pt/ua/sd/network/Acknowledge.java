/**
 * 
 */
package pt.ua.sd.network;

import java.util.HashMap;
import pt.ua.sd.communication.Message;

/**
 * A simple Message: Acknowledge
 * 
 * May contain return values, depending on the request that it acknowledges. The
 * requester should know how to handle them.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class Acknowledge implements IProtocolMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 584266944611767194L;
	private HashMap<String, Object> params = new HashMap<String, Object>();
	private static Message ack = new Ack();

	public Message getMessage() {
		return ack;
	}

	/**
	 * Get a parameter
	 * 
	 * @param param
	 *            the name of the parameter
	 * @return the parameter value
	 */
	public Object getParam(String param) {
		return params.get(param);
	}

	/**
	 * Put a parameter
	 * 
	 * @param param
	 *            the parameter name
	 * @param value
	 *            the parameter value
	 */
	public void setParam(String param, Object value) {
		params.put(param, value);
	}

	/**
	 * Get all parameters
	 * 
	 * @return the name-value parameters mapping
	 */
	public HashMap<String, Object> getParams() {
		return params;
	}

	static class Ack extends Message {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4017934159030590661L;

		static public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {

			ACK;

			@Override
			public int getPriority() {
				return 0;
			}
		};

		@Override
		public MESSAGE_TYPE getMsgType() {
			return MESSAGE_TYPE.ACK;
		}
	}
}
