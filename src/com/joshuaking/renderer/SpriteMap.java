package com.joshuaking.renderer;

import java.util.HashMap;

public class SpriteMap {

	private HashMap<String, Sprite> sprites;
	private Sprite currSprite;
	public SpriteMap(){
		sprites = new HashMap<String,Sprite>();
		currSprite = null;
	}
	public void addSprite(String name, Sprite sprite){
		if(!sprites.containsKey(name)){
			sprites.put(name, sprite);
		}
	}
	public void setCurrentSprite(String name){
		if(sprites.containsKey(name)){
			currSprite = sprites.get(name);
		}
	}
	public Sprite getCurrentSprite(){
		return currSprite;
	}
	public Sprite getSprite(String name){
		if(sprites.containsKey(name)){
			return sprites.get(name);
		}
		return null;
	}
}
