package com.joshuaking.event;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import com.joshuaking.battle.Battle;
import com.joshuaking.renderer.Render;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;
import com.joshuaking.util.LocationUtil;

/**
 * The BasicBattleMenu is the main menu for picking what a unit will do.
 * This is a menu that will be placed next to the unit and will be responsible
 * for getting user input for what the unit will do.
 * 
 * The options will be: 
 * 								~MOVE~
 * 1. Highlight all the tiles that the unit could potentially move to
 * 2. Wait for user to select an eligible tile.
 * 3. Move the unit to that location.
 * 4. Open up an AfterMoveBattleMenu, which will contain most of the same things as this menu, exempt move
 * 							   ~ATTACK~
 * 1. Highlight the eligible tiles that this unit could attack, based on weapon range
 * 2. Wait for user to select an elibile tile
 * 3. Perform battle with the tile's unit
 * 4. Create a ChangeDirection event for the unit
 * 							   ~ABILITY~
 * 1. Create an AbilitySelectionMenu with this unit and push it to the top of the event stack
 * 								~SPELL~
 * 1. Create a SPellSelectionMenu, same as ability
 * 								~ITEM~
 * 1. Same as ability and spell, but items.
 * 								~DEFEND~
 * 1. Add a Defend buff to the unit and call it a turn
 * @author Josh
 *
 */
public class BasicBattleMenu implements IEvent{

	private Unit unit;
	private Sprite background;
	private double xPos;
	private double yPos;
	private ArrayList<Sprite> options;
	private Battle battle;
	private boolean active;
	public BasicBattleMenu(Unit unit, Battle battle){
		this.unit=unit;
		this.battle=battle;
		active = true;
		
		background = new Sprite("Assets/Menus/Battle/Template.png");
		
		options = new ArrayList<Sprite>();
		for(int x=0;x<6;x++){
			Sprite temp = new Sprite("Assets/Menus/Battle/Options/Blank.png");
			options.add(temp);
		}
		xPos = (unit.getTile().getXRender()+1)*1.4;
		yPos = (unit.getTile().getYRender()+1)*1.4;
		unit.setHasMoved(false);
		unit.setHasPerformedAction(false);
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
		case 0:battle.pushEvent(new MoveInputMenu(unit,battle));active=false;break;
		case 1:battle.pushEvent(new BasicAttackInputMenu(battle,unit));active = false;break;
		case 2:System.out.println("ability");break;
		case 3:System.out.println("speel");break;
		case 4:System.out.println("item");break;
		case 5:System.out.println("defend");break;
		}
	}

	@Override
	public void fallBack() {
		active = true;
	}

}
