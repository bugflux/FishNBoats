/**
 * 
 */
package pt.ua.sd.communication.toshoal;

/**
 * A specialized Message: Go to Feeding Area
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
public class GoToFeedingAreaMessage extends ShoalMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6538407188546880194L;

	/**
	 * @return MESSAGE_TYPE.GoToFeedingArea
	 * @see pt.ua.sd.communication.Message#getMsgType()
	 */
	@Override
	public MESSAGE_TYPE getMsgType() {
		return MESSAGE_TYPE.GoToFeedingArea;
	}

}