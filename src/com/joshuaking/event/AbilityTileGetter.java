package com.joshuaking.event;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.joshuaking.ability.Ability;
import com.joshuaking.battle.Battle;
import com.joshuaking.battle.BattleTile;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;
import com.joshuaking.util.LocationUtil;

public class AbilityTileGetter implements IEvent{

	private Unit unit;
	private Battle battle;
	private int range;
	private ArrayList<BattleTile> tiles;
	private Sprite indicator;
	private int cooldown;
	private Ability ability;
	public AbilityTileGetter(Unit unit, Battle battle, Ability ability, int range){
		this.unit = unit;
		this.battle = battle;
		this.range = range;
		this.tiles = BattleTile.getTilesWithinRange(battle, unit.getTile(), range);
		indicator = new Sprite("Assets/Test/Square.png");
		cooldown = 2;
		this.ability=ability;
	}
	@Override
	public void exit() {
		battle.popEvent();
	}

	@Override
	public void update() {
		battle.getCameraControls();
		if(Mouse.isButtonDown(1)){
			battle.popEvent();
		}
		if(cooldown <=0){
			getMouseClick();
			cooldown = 2;
		}else{
			cooldown--;
		}
	}

	@Override
	public void render() {
		for(int x=0;x<tiles.size();x++){
			indicator.render(tiles.get(x).getXRender(), (tiles.get(x).getYRender())+21);
		}
	}

	@Override
	public void fallBack() {
	}
	private void getMouseClick(){
		if(Mouse.isButtonDown(0)){
			isWithinTile(Mouse.getX(),Mouse.getY());
		}
	}
	private void isWithinTile(int x, int y){
		Vector3f pos = LocationUtil.getMousePositionIn3dCoords(x, y);
		for(int n = 0; n<tiles.size();n++){
			if(tiles.get(n).isMouseWithinTile(pos.x, pos.y)){
				battle.popEvent();
				ability.setSelectedTile(tiles.get(n));
				break;
			}
		}
	}

}
