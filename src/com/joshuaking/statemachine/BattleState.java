package com.joshuaking.statemachine;

import java.util.ArrayList;

import com.joshuaking.battle.Arena;
import com.joshuaking.battle.ArenaMaker;
import com.joshuaking.battle.Battle;
import com.joshuaking.event.IEvent;
import com.joshuaking.unit.Unit;

public class BattleState implements IState{

	private Battle battle;
	private static BattleState instance = null;
	private BattleState(){}
	public static BattleState getInstance(){
		if(instance == null){
			instance = new BattleState();
		}
		return instance;
	}
	@Override
	public void update() {
		battle.update();
	}
	@Override
	public void render() {
		battle.render();
	}
	@Override
	public void enter(String condition) {
		System.out.println("We have entered the battle state by using a String condition. This will be used for scripted battles");
	}
	@Override
	public void enter(String arena, ArrayList<Unit>enemies, ArrayList<Unit>party){
		Arena theArena = ArenaMaker.makeArena(arena,enemies,party);
		battle = new Battle(theArena,enemies,party);
	}
	@Override
	public void exit() {
	}
	@Override
	public void setEvent(IEvent event) {
	}
	@Override
	public void removeEvent() {
	} 
}
