package com.joshuaking.event;

public interface IEvent {

	/**
	 * An IEvent is a specific event that can triggered throughout the game. While the basic interface is very
	 * simple, all types of events will build from it. An example of a simple event would be talking to an NPC.
	 * When you talk to an NPC, the state is given an event to run with the NPC you talked to. Now, instead of
	 * calling update and render for the state as a whole, all updates and renders will be filtered into the event.
	 * 
	 * The exit function is called when the event is to removed, cleaning up anything left behind. For example, when
	 * talking to an NPC it may turn to face you. When exit is called, it will be set back to its starting direction
	 */
	public void exit();
	
	/**
	 * This function is called instead of a regular update
	 */
	public void update();
	/**
	 * this function is called instead of a regular render
	 */
	public void render();
	
	public void fallBack();
}
