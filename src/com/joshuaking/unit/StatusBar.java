package com.joshuaking.unit;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.joshuaking.renderer.Sprite;
import com.joshuaking.renderer.Texture;

public class StatusBar {

	private Sprite background;
	private Texture texture;
	private float percent;
	private int width;
	private int height;
	
	public StatusBar(int type){
		background = new Sprite("Assets/Units/StatusBarBackground.png");
		if(type == 0){
			try {
				texture = com.joshuaking.renderer.Render.getInstance().getTextureLoader().getTexture("Assets/Units/HealthBar.png");
				width = texture.getImageWidth();
				height = texture.getImageHeight();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(type == 1){
			try {
				texture = com.joshuaking.renderer.Render.getInstance().getTextureLoader().getTexture("Assets/Units/EnergyBar.png");
				width = texture.getImageWidth();
				height = texture.getImageHeight();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void Render(double posX, double posY){
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2d(posX, posY);

			GL11.glTexCoord2f(0, texture.getHeight());
			GL11.glVertex2d(posX, texture.getImageHeight()+posY);

			GL11.glTexCoord2f(texture.getWidth()*percent, texture.getHeight());
			GL11.glVertex2d(texture.getImageWidth()*percent+posX, texture.getImageHeight()+posY);

			GL11.glTexCoord2f(texture.getWidth()*percent, 0);
			GL11.glVertex2d(texture.getImageWidth()*percent+posX, posY);
		}
		GL11.glEnd();
		background.render(posX, posY);
	}
	public void setPercent(float percent){
			this.percent=percent;
	}
}
