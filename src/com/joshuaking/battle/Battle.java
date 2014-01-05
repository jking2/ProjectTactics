package com.joshuaking.battle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.joshuaking.event.IEvent;
import com.joshuaking.renderer.Render;
import com.joshuaking.unit.Unit;


public class Battle {

	private ArrayList<Unit> enemyUnits;
	private ArrayList<Unit> playerUnits;
	// A Queue for units whos turn it is. A unit is entered into the queue when its iniative is full
	private Queue<Unit> actionQueue;
	// The tiles that this battle is fought on
	private Arena arena;
	// A stack of events that basically make up the bulk of the battle.
	// The event stack works like this for a basic turn.
	/* 1. A unit is found to be at the front of the Queue
	 * 2. The unit is asked for its input as to what it wants to do.
	 * 2a. A human controlled unit will put an event on the stack
	 * 2b. A computer controlled unit will make the decision on what to do, then put those events on the stack
	 * 3. For a human controlled unit under normal circumstances will go through these events.
	 * 4. A BasicBattleMenu will be pushed to the stack. This handles getting what the user whats to do, such as move or defend
	 * 5. If a unit wants to move, a MoveInputMenu will be pushed on the stack. This handles getting what tile the unit wishes to move to
	 * 6. When a valid tile is selected, a MoveAnimation event will be pushed. This  handles moving the unit an showing it visually.
	 * 7. WHen it arrives, an ActionBattleMenu will be pushed, to get the  ation of the unit.
	 * 
	 * The top of the stack is always updated, and the entirety of the stack is rendered. For this reason, things that need to be hidden
	 * are marked to not rendered.
	 * With this system, should the user ever decide to undo an action, such as clicking move when they wanted to defend, all
	 * we need to do is pop the stack.
	 * */
	 
	private Stack<IEvent> events;
	
	public Battle(Arena arena, ArrayList<Unit>enemyUnits, ArrayList<Unit>playerUnits){
		this.arena=arena;
		this.enemyUnits=enemyUnits;
		this.playerUnits=playerUnits;
		actionQueue = new LinkedList<Unit>();
		events = new Stack<IEvent>();
	}
	public void update(){
		//Update the top-most event on the stack if there is one
		if(!events.isEmpty()){
			events.peek().update();
		}else{ 
			//Decide if a Unit needs to go
			if(!actionQueue.isEmpty()){
				//Get and do what the unit wants
				actionQueue.poll().getAction(this);
			}else{
				//Allow the user to move the camera
				getCameraControls();
				//A list of who reached 100 energy
				ArrayList<Unit> whoCanGo = new ArrayList<Unit>();
				//Increase energy of all the enemies
				for(int x=0;x<enemyUnits.size();x++){
					//Increases here, returns true if they hit or exceeded 100 energy
					if(enemyUnits.get(x).increaseEnergy()){
						whoCanGo.add(enemyUnits.get(x));
					}
				}
				//Do the same for players units
				for(int x=0;x<playerUnits.size();x++){
					if(playerUnits.get(x).increaseEnergy()){
						whoCanGo.add(playerUnits.get(x));
					}
				}
				//Sort the list of who can go by their agility, then add to queue
				if(whoCanGo.size()>1){
					boolean swapped=true;
					while(swapped){
						swapped=false;
						for(int x=0;x<whoCanGo.size()-1;x++){
							if(whoCanGo.get(x).getInitiative()<whoCanGo.get(x+1).getInitiative()){
								Collections.swap(whoCanGo, x, x+1);
								swapped=true;
							}
						}
					}
				}
				//add to queue
				for(int x=0;x<whoCanGo.size();x++){
					actionQueue.add(whoCanGo.get(x));
				}
			}
		}
	}
	public void render(){
		//Render the background
		arena.renderBackground();
		//Render all the events
		if(!events.isEmpty()){
			for(int x = 0;x<events.size();x++){
				events.get(x).render();
			}
		}
		//render whats on the tiles
		arena.renderTiles();
	}
	static public void setUnit(BattleTile tile, Unit unit){
		unit.setLastTile(unit.getTile());
		unit.setTile(tile);
		unit.getLastTile().removeThing();
		unit.getTile().setThing(unit);
		
	}
	public void pushEvent(IEvent event){
		events.push(event);
	}
	public void popEvent(){
		if(!events.isEmpty()){
			events.pop();
		}
	}
	public void removeAllEvents(){
		while(!events.isEmpty()){
			events.pop();
		}
	}
	//Gets basic camera control functions
	public void getCameraControls(){
		int yDir = Mouse.getDY();
		int xDir = Mouse.getDX();
		if((yDir != 0 || xDir !=0) && Mouse.isButtonDown(0)){
			Render.getInstance().moveCamera(xDir, yDir);
		}
		if(Mouse.isButtonDown(1) && yDir<0){
			Render.getInstance().zoomOut();
		}else if(yDir>0 && Mouse.isButtonDown(1) ){
			Render.getInstance().zoomIn();
		}
	}
	public Arena getArena(){
		return arena;
	}
}
