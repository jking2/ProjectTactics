package com.joshuaking.effect;

import com.joshuaking.battle.BattleTile;
import com.joshuaking.renderer.Sprite;

public class TileEffect implements IEffect {

	private BattleTile tile;
	private Sprite particleEffect;
	private int duration;
	private int progress;
	private int updateAmount;
	public TileEffect(BattleTile tile, int duration, int updateAmount){
		this.tile = tile;
		this.duration = duration*100;
		this.updateAmount=updateAmount;
		this.progress=0;
		this.startEffect();
	}
	public void update(){
		progress+=updateAmount;
		if(progress>=duration){
			this.endEffect();
		}
	}
	@Override
	public void startEffect() {
		System.out.println("TILE EFFECT NOT OVERRIDEN");
	}

	@Override
	public void doEffect() {
		System.out.println("TILE EFFECT NOT OVERRIDEN");
	}

	@Override
	public void endEffect() {
		System.out.println("TILE EFFECT NOT OVERRIDEN");
	}
	public int getDuration(){
		return duration;
	}
	public int getUpdateAmount(){
		return updateAmount;
	}
	public void render(int x, int y){
		particleEffect.render(x*32, y*32);
	}

}
