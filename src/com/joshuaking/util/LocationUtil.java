package com.joshuaking.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

public class LocationUtil {

	   static IntBuffer viewport = 		BufferUtils.createIntBuffer(16);
	   static FloatBuffer modelview = 	BufferUtils.createFloatBuffer(16);
	   static FloatBuffer projection = 	BufferUtils.createFloatBuffer(16);
	   static FloatBuffer winZ = 		BufferUtils.createFloatBuffer(20);
	   static FloatBuffer position = 	BufferUtils.createFloatBuffer(3);

	   static public Vector3f getMousePositionIn3dCoords(int mouseX, int mouseY)
	   {

	      viewport.clear();
	      modelview.clear();
	      projection.clear();
	      winZ.clear();;
	      position.clear();
	      float winX, winY;


	      GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
	      GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
	      GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );

	      winX = (float)mouseX;
	      winY = /* (float)viewport.get(3) -  */  //Uncomment this if you invert Y
	         (float)mouseY;

	      GL11.glReadPixels(mouseX, (int)winY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

	      float zz = winZ.get();

	      GLU.gluUnProject(winX, winY, zz, modelview, projection, viewport, position);



	      Vector3f v = new Vector3f (position.get(0),position.get(1),position.get(2));


	      return v ;
	   }
}
