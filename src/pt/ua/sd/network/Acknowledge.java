/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.network;

import java.util.HashMap;
import pt.ua.sd.communication.Message;

/**
 * 
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
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

	public Object getParam(String param) {
		return params.get(param);
	}

	public void setParam(String param, Object value){
		if(param.contains(param)){
			params.remove(param);
		}
		params.put(param, value);
	}

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
