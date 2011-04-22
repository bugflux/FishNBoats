/**
 * 
 */
package pt.ua.sd.communication.toocean;

import pt.ua.sd.communication.Message;

/**
 * @author André Prata <andreprata@ua.pt>
 * @author Eriksson Monteiro <eriksson.monteiro@ua.pt>
 */
public abstract class OceanMessage extends Message {

	public enum MESSAGE_TYPE implements Message.MESSAGE_TYPE {
		AddDirOper, SetDirOperState,
		AddBoat, SetBoatState, SetBoatCatch, TryMoveBoat, CompanionDetected, GetRadar,
		AddShoal, SetShoalState, SetShoalSize, TryMoveShoal,
		GetHeight, GetWidth, GetWharf, GetSpawningArea;

		@Override
		public int getPriority() {
			return 0;
		}
	};
	
	
}
