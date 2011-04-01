/**
 * 
 */
package pt.ua.sd.communication.toboat;

import pt.ua.sd.boat.IBoatHelper;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class HelpRequestServedMessage extends BoatMessage {

	protected final IBoatHelper helper;

	/**
	 * construct a HelpRequestServed message with the id of the boat that is
	 * going to help
	 * 
	 * @param helperId
	 */
	public HelpRequestServedMessage(IBoatHelper helper) {
		this.helper = helper;
	}

	/**
	 * @return helperId
	 */
	public IBoatHelper getHelper() {
		return helper;
	}

	/**
	 * @return MESSAGE_TYPE.HelpRequestServed
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.HelpRequestServed;
	}
}
