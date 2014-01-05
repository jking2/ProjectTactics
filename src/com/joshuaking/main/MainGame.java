package com.joshuaking.main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.newdawn.slick.opengl.renderer.Renderer;

import com.joshuaking.renderer.Render;
import com.joshuaking.statemachine.BattleState;
import com.joshuaking.statemachine.EmptyState;
import com.joshuaking.statemachine.StateMachine;
import com.joshuaking.unit.Unit;
import com.joshuaking.unit.UnitMaker;


public class MainGame extends Thread{

	//How fast the combat is
	public final static int GAME_SPEED = 1;
	//Whether or not the game should be running
	private AtomicBoolean running;
	//Sets the FPS of the game. The game will try to run at 60fps and no more
	private final int FPS = 60;
	private final double TIME_BETWEEN_RENDERS = 1000000000 / FPS;
	//How often updates will be called
	private final double HERTZ = 30.0;
	private final double TIME_BETWEEN_UPDATES = 1000000000 / HERTZ;
	//The max number of updates that can be backed up before a render is made
	private final double MAX_UPDATES = 5;
	
	private StateMachine stateMachine;
	
	private static MainGame instance = null;
	public static MainGame GetInstance(){
		if(instance == null){
			instance = new MainGame();
		}
		return instance;
	}
	private MainGame(){
		super();
		Render.getInstance().createDisplay();
		stateMachine = new StateMachine();
		//Add in the various states the game will use
		stateMachine.add("empty", EmptyState.getInstance());
		stateMachine.add("battle", BattleState.getInstance());
		//Change the state to the starting state.
		//**********************************************************
		//THIS SHIT IS TEMPORARY CHANGE IT TO MAIN MENU IN TIME
		ArrayList<Unit> enemy = new ArrayList<Unit>();
		ArrayList<Unit> party = new ArrayList<Unit>();
		Unit enemy1 = UnitMaker.makeUnit("test","Chuck");
		Unit enemy2 = UnitMaker.makeUnit("test", "Sally");
		enemy.add(enemy1);
		enemy.add(enemy2);
		Unit party1 = UnitMaker.makeUnit("test", "Bob");
		Unit party2 = UnitMaker.makeUnit("test", "Susan");
		party.add(party2);
		party.add(party1);
		stateMachine.changeToBattle("test", enemy, party);
		//**********************************************************
		//TODO make it change to the state we loaded from when loading and saving is done
		running = new AtomicBoolean();
		running.set(true);
	}
	@Override
	public void run(){
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		running.set(true);
		while(running.get()){
			double currentTime = System.nanoTime();
			int updateCount = 0;
			//Do as many updates as needed
			//We always do more updates if we are behind in updates so we update
			//more than render
			while(currentTime - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES){
				//Only update if we are still running. This prevents the game from continueing when we are
				//changing the state
				if(running.get()){
					stateMachine.update();
				}
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				updateCount++;
			}
			if(currentTime - lastUpdateTime > TIME_BETWEEN_UPDATES){
				lastUpdateTime = currentTime - TIME_BETWEEN_UPDATES;
			}
			//Only render the state if we are still running. This prevents us from rendering a state
			//That we have just entered and is not ready yet
			if(running.get()){
				stateMachine.render();
				Render.getInstance().updateDisplay();
			}
			lastRenderTime = currentTime;
			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if(thisSecond > lastSecondTime){
				lastSecondTime = thisSecond;
			}
			//Sleep while we wait to be start over. This keeps the game from running too fast.
			while(currentTime - lastRenderTime < TIME_BETWEEN_RENDERS && currentTime - lastUpdateTime < TIME_BETWEEN_UPDATES){
			Thread.yield();
			try{
				Thread.sleep(1);
			} catch(Exception e){}
			currentTime = System.nanoTime();
			}
			
		}
	}
}
