package com.joshuaking.statemachine;

import java.util.ArrayList;

import com.joshuaking.event.IEvent;
import com.joshuaking.unit.Unit;


public interface IState {

	/**
	 * Called to up the game data. This is called fairly often and is
	 * used for things like updating positions.
	 * @param elapsed_time
	 */
	public void update();
	/**
	 * Called to update the graphics. This is called hopefully 60 times
	 * a second to make a nice 60fps.
	 */
	public void render();
	/**
	 * This is called right after changing to a state to do some
	 * setup and preparation for the scene.
	 * @param Condition The condition is a string used by the state to determine how it should be
	 * set up. For example when entering a battle state you may pass in a string that says "plains 3 wolf normal"
	 * Something like this would tell the battle state that we need to set up a battle on the map
	 * called "plains" and we have 3 enemy wolves under normal circumstances
	 */
	public void enter(String condition);
	public void enter(String arena, ArrayList<Unit>enemies,ArrayList<Unit>party);
	/**
	 * Exit is called before leaving the state to do some cleanup.
	 */
	public void exit();
	/**
	 * Sets the event for the state when we need one. An event basically overrides the normal update and render
	 * to do specific events
	 * @param event The event we use to run
	 */
	public void setEvent(IEvent event);
	/**
	 * Removes the current event
	 */
	public void removeEvent();
}
