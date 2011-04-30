/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.communication.Message;

/**
 * Ancestor of all Ocean messages.
 * 
 * @author André Prata
 * @author Eriksson Monteiro
 */
@SuppressWarnings("serial")
// abstract class
public abstract class OceanMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		ACK, AddDirOper, SetDirOperState, AddBoat, SetBoatState, SetBoatCatch, TryMoveBoat, CompanionDetected, GetRadar, AddShoal, SetShoalState, SetShoalSize, TryMoveShoal, GetHeight, GetWidth, GetWharf, GetSpawningArea;

		@Override
		public int getPriority() {
			return 0;
		}
	};

}
