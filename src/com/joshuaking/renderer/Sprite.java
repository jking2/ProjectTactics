package com.joshuaking.renderer;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class Sprite {

	private Texture texture;
	private int width;
	private int height;
	private double renderedPosX;
	private double renderedPosY;
	
	public Sprite(String name){
		renderedPosX=0;
		renderedPosY=0;
		try{
			texture = Render.getInstance().getTextureLoader().getTexture(name);
			width = texture.getImageWidth();
			height = texture.getImageHeight();
		} catch(IOException e){
			System.out.println("Failed to load a texture for this sprite: "+this.toString());
			System.out.println("We tried to load at location: "+name);
			e.printStackTrace();
			System.exit(0);
		}
	}
	public int getWidth(){
		return texture.getImageWidth();
	}
	public int getHeight(){
		return texture.getImageHeight();
	}
	public void render(double x, double y){
		renderedPosX = x;
		renderedPosY = y;
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2d(x, y);

			GL11.glTexCoord2f(0, texture.getHeight());
			GL11.glVertex2d(x, texture.getImageHeight()+y);

			GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
			GL11.glVertex2d(texture.getImageWidth()+x, texture.getImageHeight()+y);

			GL11.glTexCoord2f(texture.getWidth(), 0);
			GL11.glVertex2d(texture.getImageWidth()+x, y);
		}
		GL11.glEnd();
	}
	public boolean isWithinBB(double mouseX, double mouseY){
		if(mouseX>renderedPosX && mouseX < renderedPosX+width){
			if(mouseY > renderedPosY && mouseY < renderedPosY+height){
				return true;
			}
		}
		return false;
	}
}
