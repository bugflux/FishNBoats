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

	private HashMap<String, String> params = new HashMap<String, String>();
	private static Message ack = new Ack();

	public

	 Message getMessage() {
		return ack;
	}

	public String getParam(String param) {
		return params.get(param);
	}

	public void setParam(String param, String value){
		if(param.contains(param)){
			params.remove(param);
		}
		params.put(param, value);
	}

	public HashMap<String, String> getParams() {
		return params;
	}

	static class Ack extends Message {

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
