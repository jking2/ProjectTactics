package com.joshuaking.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {

	private int[] keys = new int[256];
	private boolean[] keyStateUp = new boolean[256]; 
	private boolean[] keyStateDown = new boolean[256]; 
	
	private boolean keyPressed = false;
	
	private boolean keyReleased= false; 
	
	private String keyCache = "";
	
	private static InputManager instance = new InputManager();
	
	
	private InputManager() {
	}

	
	public static InputManager getInstance() {
		if(instance == null){
			instance = new InputManager();
		}
		return instance;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = (int) System.currentTimeMillis();
			keyStateDown[e.getKeyCode()] = true;
			keyStateUp[e.getKeyCode()] = false;
			keyPressed = true;
			keyReleased = false;
		}
	}

	
	@Override
	public void keyReleased(KeyEvent e) {
		if( e.getKeyCode() >= 0 && e.getKeyCode() < 256 ) {
			keys[e.getKeyCode()] = 0;
			keyStateUp[e.getKeyCode()] = true;
			keyStateDown[e.getKeyCode()] = false;
			keyPressed = false;
			keyReleased = true;
		}
	}

	
	@Override
	public void keyTyped(KeyEvent e) {	
		keyCache += e.getKeyChar();
		
	}
	
	
	public boolean isKeyDown( int key ) {
		return keyStateDown[key];
	}
	
	
	public boolean isKeyUp( int key ) {
		return keyStateUp[key];
	}

	
	public boolean isAnyKeyDown() {
		return keyPressed;
	}
	
	
	public boolean isAnyKeyUp() {
		return keyReleased;
	}
	
	
	public void update() {
		//clear out the key up states
		keyStateUp = new boolean[256];
		keyReleased = false;
		if( keyCache.length() > 1024 ) {
			keyCache = "";
		}
	}
}
