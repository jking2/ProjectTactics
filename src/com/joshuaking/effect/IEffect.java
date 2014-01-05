package com.joshuaking.effect;

/**
 * The interface for an effect. An effects is anything that is called right away, every so often, and ends. Debuffs, buffs, tileffects are
 * all such things
 * @author Josh
 *
 */
public interface IEffect {

	public void startEffect();
	public void doEffect();
	public void endEffect();
}
