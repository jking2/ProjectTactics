package com.joshuaking.unit;

import com.joshuaking.battle.BattleTile;
import com.joshuaking.renderer.SpriteMap;

public abstract class Thing {

	protected BattleTile tile;
	protected SpriteMap spriteMap;
	protected String name;
	protected String className;
	public Thing(){
		tile = null;
		spriteMap = new SpriteMap();
		name = "null_name";
		className = "null_class_name";
	}
	public BattleTile getTile() {
		return tile;
	}
	public SpriteMap getSpriteMap() {
		return spriteMap;
	}
	public void setTile(BattleTile tile) {
		this.tile = tile;
	}
	public void renderCurrentSprite(int x, int y){
		spriteMap.getCurrentSprite().render(x, y);
	}
}
