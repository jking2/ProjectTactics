package com.joshuaking.effect;

import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Unit;

/**
 * An effect is something that a combatant can have. This is things like poison, blinded, reduced healing and what have you.
 * Take for example an effect like a burn. Lets say the burn does 10 damage at the end of the players turn for 4 turns and while it persists
 * the combatant takes 25% increased physical damage. The effect will have a couple things:
 * combatant: whom this effect is effecting
 * name: what this effect is called. for our example, it will be called "Burned"
 * description: a short description of the effect. this is used so the player knows what the effect is. For example, there may be
 * 				a display showing all the effects on the combatant. If clicked on, the effect would give its descritpion
 * duration: how many turns this effect lasts. When the doEffect method is called, this is generally reduced by 1. When at 0, the effect ends
 * isBuff: a simple boolean to know if this effect is a buff or debuff. this is mostly for sorting purposes
 * 
 * When the combatant is first burned, the startEffect() is called. This updates the combatant to know it is burned, add in
 * particle effects, and other things. In our case, it's physicalDamageAmp will be increased by 0.25, to increase physical damage. 
 * 
 * Whenver we wish to "burn" the combatant, the doEffect() is called. The effect itself does NOT know when it will be triggered, this
 * makes it easy to put the effect wherever you want, so it can triggered when you want it to. For our example, lets say it is placed
 * within the combatant's endOfTurn list, which is called after the combatant ends its turn. in this case, the doEffect is called
 * everytime the combatant ends its turn. This will then reduce the combatant's health by 10, and decrease the duration by 1.
 * 
 * When duration reaches 0, the effect ends. endEffect is called, which will perform cleanup. Particle effects are removed, the combatant
 * is no longer burned, and its physicalDamageAmp is reduced by 0.25.
 * @author Josh
 *
 */
public class Effect implements IEffect {

	protected Unit combatant;
	protected String name;
	protected String description;
	protected Sprite icon;
	protected int duration;
	protected boolean isBuff;
	public Effect(Unit combatant){
		this.combatant=combatant;
	}
	@Override
	public void startEffect(){
		System.out.println("THIS EFFECTS START EFFECT HAS NOT BEEN OVERRIDEN");
	}
	@Override
	public void doEffect() {
		System.out.println("THIS EFFECT HAS NOT BEEN OVERRIDEN");
	}
	@Override
	public void endEffect(){
		System.out.println("THIS EFFECTS END EFFECT HAS NOT BEEN OVERRIDE");
	}
	public Unit getCombatant() {
		return combatant;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public Sprite getIcon() {
		return icon;
	}
	public boolean isBuff() {
		return isBuff;
	}
	public int getDuration() {
		return duration;
	}

}
