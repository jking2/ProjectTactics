package com.joshuaking.battle;

import java.util.ArrayList;

import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;
import com.joshuaking.util.Direction;

public class ArenaMaker {

	public static Arena makeArena(String arenaName, ArrayList<Unit>enemies,ArrayList<Unit>party){
		switch(arenaName){
		case "test" : return makeTestArena(enemies,party);
		default : return makeTestArena(enemies,party);
		}
	}
	private static Arena makeTestArena(ArrayList<Unit>enemies,ArrayList<Unit>party){
		
		Arena arena = new Arena();
		ArrayList<ArrayList<BattleTile>> tiles = new ArrayList<ArrayList<BattleTile>>();
		for(int y=0;y<15;y++){
			ArrayList<BattleTile> list = new ArrayList<BattleTile>();
			for(int x=0;x<15;x++){
				BattleTile tile = new BattleTile(x,y,13);
				list.add(tile);
			}
			tiles.add(list);
		}
		arena.setArena(tiles);
		Battle.setUnit(arena.getTile(3, 0), enemies.get(0));
		Battle.setUnit(arena.getTile(7, 8), enemies.get(1));
		Battle.setUnit(arena.getTile(7, 9), party.get(0));
		Battle.setUnit(arena.getTile(8, 9), party.get(1));
		Sprite sprite = new Sprite("Assets/Arenas/Test.png");
		arena.setSprite(sprite);
		return arena;
	}
}
