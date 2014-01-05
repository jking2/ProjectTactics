package com.joshuaking.event;

import com.joshuaking.battle.Battle;
import com.joshuaking.battle.BattleTile;
import com.joshuaking.unit.Unit;

public class BasicAttackAnimation implements IEvent{

	private Battle battle;
	private Unit unit;
	private BattleTile targetTile;
	private int count = 20;
	public BasicAttackAnimation(Battle battle, Unit unit, BattleTile targetTile){
		this.battle=battle;
		this.unit=unit;
		this.targetTile=targetTile;
	}
	@Override
	public void exit() {
		//Combat is taken care of here I guess
		unit.setHasPerformedAction(true);
		battle.popEvent();
		if(unit.hasMoved()){
			battle.popEvent();
			battle.popEvent();
		}else{
			battle.pushEvent(new MoveInputMenu(unit, battle));
		}
	}

	@Override
	public void update() {
		System.out.println("Attacking!");
		count--;
		if(count<0){
			this.exit();
		}
	}

	@Override
	public void render() {
	}

	@Override
	public void fallBack() {
	}

}
