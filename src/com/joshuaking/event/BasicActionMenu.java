package com.joshuaking.event;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import com.joshuaking.battle.Battle;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;
import com.joshuaking.util.LocationUtil;

public class BasicActionMenu implements IEvent{
	private Unit unit;
	private Sprite background;
	private double xPos;
	private double yPos;
	private ArrayList<Sprite> options;
	private Battle battle;
	private boolean active;
	public BasicActionMenu(Unit unit, Battle battle){
		this.unit=unit;
		this.battle=battle;
		active = true;
		
		background = new Sprite("Assets/Menus/Battle/Template.png");
		
		options = new ArrayList<Sprite>();
		for(int x=0;x<5;x++){
			Sprite temp = new Sprite("Assets/Menus/Battle/Options/Blank.png");
			options.add(temp);
		}
		xPos = (unit.getTile().getXRender()+1)*1.4;
		yPos = (unit.getTile().getYRender()+1)*1.4;
	}
	
	@Override
	public void exit() {
		battle.popEvent();
	}

	@Override
	public void update() {
		if(!active){
			fallBack();
		}
		battle.getCameraControls();
		this.getMouseClick();
	}

	@Override
	public void render() {
		if(active){
			//draw this menu
			background.render(xPos, yPos);
			for(int x = 0;x<options.size();x++){
				options.get(x).render(xPos+2, 2+(yPos+x*10));
			}
		}
	}
	private void getMouseClick(){
		if(Mouse.isButtonDown(0)){
			isWithinOption(Mouse.getX(),Mouse.getY());
		}
	}
	private void isWithinOption(int x, int y){
		Vector3f pos = LocationUtil.getMousePositionIn3dCoords(x, y);
		for(int n =0;n<options.size();n++){
			if(options.get(n).isWithinBB(pos.x,pos.y)){
				active = false;
				doOption(n);
			}
		}
	}
	private void doOption(int optionNumber){
		switch(optionNumber){
		case 0:System.out.println("attack");battle.pushEvent(new BasicAttackInputMenu(battle,unit));active = false;break;
		case 1:System.out.println("ability");Battle.setUnit(unit.getLastTile(), unit);battle.popEvent();break;
		case 2:System.out.println("speel");break;
		case 3:System.out.println("item");break;
		case 4:System.out.println("defend");break;
		}
	}

	@Override
	public void fallBack() {
		active = true;
	}
}
