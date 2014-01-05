package com.joshuaking.event;

import java.util.ArrayList;

import com.joshuaking.battle.Battle;
import com.joshuaking.battle.BattleTile;
import com.joshuaking.unit.Unit;

public class MoveAnimation implements IEvent {

	private BattleTile destination;
	private Unit unit;
	private Battle battle;
	private ArrayList<BattleTile> path;
	private int distance;
	private int currTile;
	private int duration;
	public MoveAnimation(BattleTile destination, Unit unit, Battle battle){
		path = BattleTile.getPathOfTiles(battle.getArena(), unit.getTile(), destination);
		unit.setLastTile(unit.getTile());
		this.destination = destination;
		this.unit=unit;
		this.battle=battle;
		unit.getTile().removeThing();
		unit.setTile(destination);
		this.distance = BattleTile.getTileDistance(unit.getTile(), destination);
		duration = 15;
		currTile = 0;
	}
	@Override
	public void exit() {
		//Set the unit on the tile
		this.destination.setThing(unit);
		//The unit has now moved
		unit.setHasMoved(true);
		//Remove this event
		battle.popEvent();
		//End the units turn if he has already done an action
		//Or get an action if he hasnt
		if(unit.hasPerformedAction()){
			battle.popEvent();
			battle.popEvent();
		}else{
			battle.pushEvent(new BasicActionMenu(unit,battle));
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		if(currTile<path.size()){
			if(duration<0){
				currTile++;
				duration = 15;
			}else{
				unit.renderCurrentSprite(path.get(currTile).getXRender(), path.get(currTile).getYRender());
				duration--;
			}
		}else{
			this.exit();
		}
	}

	@Override
	public void fallBack() {
	}

	
	
}
