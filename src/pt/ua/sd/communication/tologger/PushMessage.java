/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ua.sd.communication.tologger;

/**
 * 
 * @author eriksson
 */
public class PushMessage extends LoggerMessage {

	/**
     * 
     */
	private static final long serialVersionUID = 8167486838241346503L;
	private final String type, entity, message;
	private final int clockTick;

	public PushMessage(String type, String entity, String message, int clockTick) {
		this.type = type;
		this.entity = entity;
		this.message = message;
		this.clockTick = clockTick;
	}

	public int getClockTick() {
		return clockTick;
	}

	public String getEntity() {
		return entity;
	}

	public String getMessage() {
		return message;
	}

	public String getType() {
		return type;
	}

	/**
	 * @return MESSAGE_TYPE.NoActionMessage
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.pushMessage;
	}
}
