/**
 * 
 */
package pt.ua.sd.communication.tologger;

import pt.ua.sd.communication.toboat.*;
import pt.ua.sd.communication.todiroper.*;


/**
 * @author André Prata
 * @author Eriksson Monteiro
 *
 */
public class GetTickClockMessage extends LoggerMessage {

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
        return MESSAGE_TYPE.getTickClock;
    }
}
