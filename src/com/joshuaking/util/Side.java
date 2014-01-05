package com.joshuaking.util;

public enum Side {

	SIDE,FRONT,BACK;
	/** 
	 * Returns the side of the defender that was hit given the direction the attacker is facing and the direction
	 * the defender is facing
	 * @param attacker The direction the attacker is facing
	 * @param defender The direction the defender is facing
	 * @return Which side the defender will be hit on
	 */
	public static Side getSideHit(Direction attacker, Direction defender){
		if(attacker == defender){
			return BACK;
		}else{
			switch(attacker){
			case N: switch(defender){
				case S:return FRONT;
				default:return SIDE;
			}
			case E: switch(defender){
				case W: return FRONT;
				default:return SIDE;
			}
			case S: switch(defender){
				case N: return FRONT;
				default:return SIDE;
			}
			case W: switch(defender){
				case E: return FRONT;
				default:return SIDE;
			}
			}
		}
		return FRONT;
	}
}
