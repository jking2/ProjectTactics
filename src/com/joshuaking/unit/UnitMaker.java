package com.joshuaking.unit;

import com.joshuaking.renderer.Sprite;
import com.joshuaking.renderer.SpriteMap;

public class UnitMaker {

	public static Unit makeUnit(String className,String name){
		switch(className){
		case "test" : return makeTest(name);
		default     : return makeTest(name);
		}
	}
	private static Unit makeTest(String name){
		Unit unit = new Unit();
		
		unit.name      = name;
		unit.className = "Test Unit";
		
		unit.spriteMap = new SpriteMap();
		unit.getSpriteMap().addSprite("normal", new Sprite("Assets/Units/Classes/Executioner/Front_Left.png"));
		unit.getSpriteMap().setCurrentSprite("normal");
		
		unit.level        = 10;
		unit.strength     = 25;
		unit.agility      = 25;
		unit.intelligence = 25;
		unit.fortitude    = 25;
		unit.willpower    = 25;
		unit.cunning      = 25;
		unit.speed        = 5;
		
		unit.calcStats();
		
		return unit;
	}
}
