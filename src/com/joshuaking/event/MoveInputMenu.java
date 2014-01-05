package com.joshuaking.event;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.joshuaking.battle.Battle;
import com.joshuaking.battle.BattleTile;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;
import com.joshuaking.util.LocationUtil;

public class MoveInputMenu implements IEvent{

	private Battle battle;
	private Unit unit;
	private Sprite moveIndicator;
	private ArrayList<BattleTile> tiles;
	private int cooldown;
	private boolean canGoBack;
	public MoveInputMenu(Unit unit, Battle battle){
		this.battle=battle;
		this.unit=unit;
		moveIndicator = new Sprite("Assets/Test/Square.png");
		tiles = BattleTile.getMoveableTiles(battle.getArena(), unit.getTile(), 6);
		cooldown = 2;
		canGoBack = true;
	}
	public void setCanGoBack(boolean goBack){
		this.canGoBack=goBack;
	}
	@Override
	public void exit() {
		battle.popEvent();
	}
	@Override
	public void update() {
		battle.getCameraControls();
		if(Mouse.isButtonDown(1) && canGoBack){
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
			moveIndicator.render(tiles.get(x).getXRender(), (tiles.get(x).getYRender())+21);
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
				battle.pushEvent(new MoveAnimation(tiles.get(n),unit,battle));
				break;
			}
		}
	}
}
