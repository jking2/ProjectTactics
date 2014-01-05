package com.joshuaking.ability;

import com.joshuaking.battle.BattleTile;
import com.joshuaking.event.IEvent;
import com.joshuaking.unit.Unit;

public class Ability {

	private Unit owner;
	private int range;
	private AfflictedTiles aTiles;
	private IEvent animation;
	private BattleTile epicenter;
	
	public Ability(Unit owner){
		this.owner=owner;
	}
	////Select ability in battle
	//push a new ability tile getter with abiliy's range
	//the getter will set our epicenter then pop
	//Within the set epicenter we will then push an event to confirm the area afflicted
	//that will call the play animation function, which will carry out the ability by
	//pushing an animation event
	//that animation event will then tell the unit it performed an action and then
	//clean up the stack

	public void setSelectedTile(BattleTile battleTile) {
		// TODO Auto-generated method stub
		
	}
}
