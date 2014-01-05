package com.joshuaking.statemachine;

import java.util.ArrayList;

import com.joshuaking.event.IEvent;
import com.joshuaking.unit.Unit;

public class EmptyState implements IState {

	private static EmptyState instance = null;
	private EmptyState(){}
	public static EmptyState getInstance(){
		if(instance == null){
			instance = new EmptyState();
		}
		return instance;
	}
	@Override
	public void update() {
	}

	@Override
	public void render() {
	}

	@Override
	public void enter(String condition) {
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
	@Override
	public void enter(String arena, ArrayList<Unit> enemies,
			ArrayList<Unit> party) {
	}

}
