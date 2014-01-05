package com.joshuaking.statemachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.joshuaking.input.InputManager;
import com.joshuaking.unit.Unit;

public class StateMachine {

	private Map<String, IState> states = new HashMap<String,IState>();
	private IState currentState = EmptyState.getInstance();
	
	public void update(){
		//Refresh the inputmanager
		InputManager.getInstance().update();
		currentState.update();
	}
	public void render(){
		currentState.render();
	}
	public void change(String state_name, String condition){
		currentState.exit();
		currentState = states.get(state_name);
		currentState.enter(condition);
	}
	public void add(String state_name, IState state){
		states.put(state_name, state);
	}
	public IState getState(){
		return this.currentState;
	}
	public void changeToBattle(String arena, ArrayList<Unit>enemies, ArrayList<Unit>party){
		currentState.exit();
		currentState = states.get("battle");
		currentState.enter(arena, enemies, party);
	}
}
