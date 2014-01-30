package com.joshuaking.renderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Render {

	private final double PAN_SPEED = 5.0d;
	private final float ZOOM_SPEED = 0.1f;
	private double xCenter = 0;
	private double yCenter = 0;
	private final int WIDTH = 1200; //Default 800
	private final int HEIGHT = 800; //Default 600
	private TextureLoader textureLoader;
	private static Render instance = null;
	private Render(){
		textureLoader = new TextureLoader();
	}
	public static Render getInstance(){
		if(instance == null){
			instance = new Render();
		}
		return instance;
	}
	public TextureLoader getTextureLoader(){
		return textureLoader;
	}
	public void createDisplay(){
		try{

			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(-WIDTH, WIDTH, HEIGHT, -HEIGHT, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        
	        
		}catch(Exception e){
			System.out.println("Could not create the display and/or set it to a displayMode");
			e.printStackTrace();
			System.exit(0);
		}
	}
	public void updateDisplay(){
		if(Display.isCloseRequested()){
			Display.destroy();
			System.exit(0);
		}else{
			Display.update();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
	}
	public void placeCamera(double x, double y){
		//Pan camera to this position.Moves faster the further away the camera is.
	}
	public void zoomOut(){
		GL11.glTranslated(xCenter, yCenter, 0);
		GL11.glScalef(1-ZOOM_SPEED,1-ZOOM_SPEED,1-ZOOM_SPEED);
		GL11.glTranslated(-xCenter, -yCenter, 0);
	}
	public void zoomIn(){
		GL11.glTranslated(xCenter, yCenter, 0);
		GL11.glScalef(1+ZOOM_SPEED, 1+ZOOM_SPEED, 1+ZOOM_SPEED);
		GL11.glTranslated(-xCenter, -yCenter, 0);
	}
	public void moveCamera(double x, double y){
		xCenter-=x;
		yCenter+=y;
		GL11.glTranslated(x, -y, 0);
	}
	public double getXCenter(){
		return xCenter;
	}
	public double getYCenter(){
		return yCenter;
	}
}
