/**
 * 
 */
package pt.ua.sd.communication.toboat;

import pt.ua.sd.boat.BoatId;

/**
 * @author André Prata
 * @author Eriksson Monteiro
 * 
 */
public class HelpRequestServedMessage extends BoatMessage {

	protected final BoatId helperId;

	/**
	 * construct a HelpRequestServed message with the id of the boat that is
	 * going to help
	 * 
	 * @param helperId
	 */
	public HelpRequestServedMessage(BoatId helperId) {
		this.helperId = helperId;
	}

	/**
	 * @return helperId
	 */
	public BoatId getHelperId() {
		return helperId;
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
