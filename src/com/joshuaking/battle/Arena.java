package com.joshuaking.battle;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;

/**
 * an arena is a map that combat is performed on. Its basically a 2d arraylist of BattleTiles
 * @author Josh
 *
 */
public class Arena {

	private ArrayList<ArrayList<BattleTile>> arena;
	private Sprite sprite;
	
	public Arena(){}
	protected void setArena(ArrayList<ArrayList<BattleTile>> newArena){
		this.arena=newArena;
	}
	public BattleTile getTile(int x, int y){
		if(x<0 || y<0 || x>arena.size()-1||y>arena.get(0).size()-1){
			return null;
		}
		return arena.get(y).get(x);
	}
	public int getWidth(){
		return arena.size();
	}
	public int getHeight(){
		return arena.get(0).size();
	}
	protected void setSprite(Sprite sprite){
		this.sprite=sprite;
	}
	public void renderTiles(){
		//starting from top to bottom, render each tile's things starting with units and
		for(int x=0;x<this.getHeight();x++){
			for(int y=this.getWidth()-1;y>=0;y--){
				getTile(x,y).render();
			}
		}
	}
	public void renderBackground(){
		sprite.render(0f,5f ); //PREVIOUSLY AT 4
	}
	
	
}
