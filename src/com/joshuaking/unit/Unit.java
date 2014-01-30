package com.joshuaking.unit;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Color;

import com.joshuaking.ability.Ability;
import com.joshuaking.armor.Accessory;
import com.joshuaking.armor.Chest;
import com.joshuaking.armor.Head;
import com.joshuaking.armor.Legs;
import com.joshuaking.battle.Battle;
import com.joshuaking.battle.BattleTile;
import com.joshuaking.effect.Effect;
import com.joshuaking.event.BasicBattleMenu;
import com.joshuaking.item.Item;
import com.joshuaking.main.MainGame;
import com.joshuaking.renderer.Render;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.spell.Spell;
import com.joshuaking.util.Direction;
import com.joshuaking.weapon.Weapon;

/**
 * A unit is anyone involved in a battle. All combatants are the same. A warrior you control is the same
 * as a wolf you fight. The differences are not the classes, but the data that makes it up. While the wolf only has
 * a single attack and is called a wolf, the warrior may be called Chuck and have many abilities, however this data is
 * stored the same.
 * @author Josh
 *
 */
public class Unit extends Entity {
	//***************************************************************************************************************
	//Cooldowns on actions.If a CD is at 0, they may use that action. At the end of the combatants turn all their CD's
	//are reduced by 1. This is for things like silences. A 3 turn silence will increase your spellCD to 3.
	//***************************************************************************************************************
	protected int actionCD  = 0;
	protected int moveCD    = 0;
	protected int attackCD  = 0;
	protected int abilityCD = 0;
	protected int spellCD   = 0;
	//*********************************************************
	//passive effects. These are specifically for calculations
	//*********************************************************
	//Amplifications of various effects. If a unit is taking half damage from physical attacks, the physicalDamageAmp would be 0.50
	protected double physicalDamageAmp = 1.0;
	protected double magicalDamageAmp  = 1.0;
	protected double healingAmp        = 1.0;
	//*************************************************************************************************************
	//Lists of effects.These are effects that take place at certain times such as at the start and end of the turn
	//*************************************************************************************************************
	protected ArrayList<Effect> startOfTurn;
	protected ArrayList<Effect> afterMoving;
	protected ArrayList<Effect> beforeAttacking;
	protected ArrayList<Effect> beforeBeingHit;
	protected ArrayList<Effect> afterAttacking;
	protected ArrayList<Effect> afterBeingHit;
	protected ArrayList<Effect> endOfTurn;
	//*********************************************************
	//List of cosmetic effects
	//*********************************************************
	protected HashMap<String,Sprite> cosmeticEffects;
	//***************
	//Stats
	//***************
	protected int level;
	protected boolean isAlive =true;
	
	protected int currHealth;
	protected int maxHealth; // 4 times your fortitude
	protected int currMana;
	protected int maxMana; // 3 times your willpower
	
	protected int strength;
	protected int agility;
	protected int intelligence;
	protected int fortitude;
	protected int willpower;
	protected int cunning;
	protected int speed;

	protected double physicalDamage;
	protected double resolution; 
	protected double initiative;
	protected double accuracy; 
	protected double magicalDamage;
	protected double perception;
	protected double physicalResist; 
	protected double magicResist; 
	protected double dodge; 
	protected double efficiency; 
	
	protected int armor = 0;
	protected double armorReduction;
	
	protected ArrayList<Ability> abilities;
	protected ArrayList<Spell> spells;
	protected ArrayList<Item> items;
	
	protected Head head            = null;
	protected Chest chest          = null;
	protected Legs legs            = null;
	protected Accessory accessory1 = null;
	protected Accessory accessory2 = null;
	protected Weapon weapon        = null;
	//***************************************************
	//Logistics
	//***************************************************
	protected BattleTile lastTile;
	protected Ability lastAbilityUsed;
	protected int energy = 0;
	protected Direction direction;
	protected Direction lastDirection;
	private boolean hasPerformedAction = false;
	private boolean hasMoved = false;
	
	protected StatusBar healthBar;
	protected StatusBar energyBar;
	
	public Unit(){
		super();
		lastTile = null;
		startOfTurn     = new ArrayList<Effect>();
		afterMoving     = new ArrayList<Effect>();
		beforeAttacking = new ArrayList<Effect>();
		beforeBeingHit  = new ArrayList<Effect>();
		afterAttacking  = new ArrayList<Effect>();
		afterBeingHit   = new ArrayList<Effect>();
		endOfTurn       = new ArrayList<Effect>();
		
		cosmeticEffects = new HashMap<String,Sprite>();
		
		abilities       = new ArrayList<Ability>();
		spells          = new ArrayList<Spell>();
		items           = new ArrayList<Item>();
		
		weapon = new Weapon();
		
		healthBar = new StatusBar(0);
		energyBar = new StatusBar(1);
	}
	@Override
	public void setTile(BattleTile tile){
		if(lastTile == null){
			lastTile = tile;
		}else{
			lastTile = this.tile;
		}
		super.setTile(tile);
	}
	public void setDirection(Direction direction){
		lastDirection = this.direction;
		this.direction=direction;
	}
	public boolean increaseEnergy(){
		if(!isAlive){
			return false;
		}
		energy+=(int)this.initiative;
		if(energy>=1000*MainGame.GAME_SPEED){
			energy=0;
			if(actionCD==0){
				return true;
			}
		}
		return false;
	}
	public void getAction(Battle battle){
		//Focus camera on the unit
		Render.getInstance().placeCamera(this.tile.getXRender(), this.tile.getYRender());
		//Create an event for the battle
		BasicBattleMenu event = new BasicBattleMenu(this,battle);
		////Add the event to the battle
		battle.pushEvent(event);
	}
	public void calcStats(){
		 physicalDamage = 20;
		 resolution     = (agility/level) * 2;
		 initiative     = (agility/level) * 8; 
		 accuracy       = (agility/(level*0.9) *0.1); 
		 magicalDamage  = 20;
		 perception     = (intelligence/level) * 0.1; 
		 physicalResist = (fortitude/level) * 0.15; 
		 magicResist    = (willpower/level) * 0.2; 
		 dodge          = (cunning/(level*0.9)) *0.08; 
		 efficiency     = (cunning/(level*0.8)) *0.07; 
		 armorReduction = 50 / (50+armor);
		 maxHealth      = fortitude * 4;
		 maxMana        = willpower * 3;
	}
	public double getInitiative(){
		return this.initiative;
	}
	public BattleTile getLastTile() {
		return lastTile;
	}
	public void setLastTile(BattleTile lastTile) {
		this.lastTile = lastTile;
	}
	public Weapon getWeapon(){
		return weapon;
	}
	public boolean hasMoved(){
		return hasMoved;
	}
	public void setHasMoved(boolean hasMoved){
		this.hasMoved = hasMoved;
	}
	public boolean hasPerformedAction(){
		return this.hasPerformedAction;
	}
	public void setHasPerformedAction(boolean hasPerformed){
		this.hasPerformedAction=hasPerformed;
	}
	public StatusBar getHealthBar(){
		healthBar.setPercent(((this.currHealth/this.maxHealth)));
		return healthBar;
	}
	public StatusBar getEnergyBar(){
		float d = (float) energy / (1000*MainGame.GAME_SPEED);
		if(d == 0.0f){
			if(!hasMoved || !hasPerformedAction){
				d = 1.0f;
			}
		}
		energyBar.setPercent(d);
		return energyBar;
	}
}
