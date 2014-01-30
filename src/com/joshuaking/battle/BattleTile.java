package com.joshuaking.battle;

import java.awt.Font;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;

import com.joshuaking.effect.TileEffect;
import com.joshuaking.renderer.Sprite;
import com.joshuaking.unit.Entity;
import com.joshuaking.unit.Unit;

/**
 * A BattleTile is a single tile of an arena. Each tile knows it contains a thing, if its empty, and holds
 * a list of TileEffects, which sis hit like fire and poison gas.
 * @author Josh
 *
 */
public class BattleTile {

	private int xPos;
	private int yPos;
	private int xRender;
	private int yRender;
	private Entity thing;
	private boolean isBlocked;
	private boolean exist;
	private int distanceFromStart;
	private BattleTile previous;
	private int distanceFromGoal;
	private ArrayList<TileEffect> effects;
	public BattleTile(int x, int y, int yOffset){
		xPos = x;
		yPos = y;
		xRender = x+y;
		yRender = yOffset + (x-1) - y;
		xRender *=12;
		yRender *=8;
		effects = new ArrayList<TileEffect>();
		isBlocked = false;
		thing=null;
	}
	private BattleTile getPrevious(){
		return previous;
	}
	private void setPrevious(BattleTile previous){
		this.previous=previous;
	}
	private int getDistanceFromStart() {
		return distanceFromStart;
	}
	private void setDistanceFromStart(int distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
	private int getDistanceFromGoal() {
		return distanceFromGoal;
	}
	private void setDistanceFromGoal(int distanceFromGoal) {
		this.distanceFromGoal = distanceFromGoal;
	}
	//Uses the Norm technique
	private static int getEstimatedDistanceToGoal(BattleTile start, BattleTile goal){
		int dx = goal.getXPos() - start.getXPos();
		int dy = goal.getYPos() - start.getYPos();
		
		int result = (int) (Math.sqrt((dx*dx)+(dy*dy)));
		return result;
	}
	protected void makeNonExistant(){
		exist = false;
		thing = null;
		isBlocked = true;
	}
	public boolean doesExist(){
		return exist;
	}
	public void update(){
		if(!effects.isEmpty()){
			for(TileEffect effect : effects){
				effect.update();
			}
		}
	}
	public void removeThing(){
		thing = null;
		isBlocked = false;
	}
	public void setThing(Entity thing){
		this.thing=thing;
		isBlocked = true;
	}
	public Entity getThing(){
		return thing;
	}
	public boolean isBlocked(){
		return isBlocked;
	}
	public void setBlocked(boolean blocked){
		isBlocked=blocked;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	public ArrayList<TileEffect> getTileEffects(){
		return effects;
	}
	public void addTileEffect(TileEffect effect){
		this.effects.add(effect);
	}
	public void removeLastTileEffect(){
		if(effects.size()>0){
			this.effects.remove(effects.size()-1);
		}
	}
	public void removeAllTileEffects(){
		this.effects.clear();
	}
	public void render(){
		
		if(thing!=null){
			thing.renderCurrentSprite(xRender, yRender);
			if(thing instanceof Unit){
				((Unit) thing).getEnergyBar().Render(xRender, yRender-3);
			}
		}
		/*
		if(!effects.isEmpty()){
			for(TileEffect effect : effects){
				effect.render(xPos, yPos);
			}
		}*/
	}
	public int getXRender() {
		return xRender;
	}
	public int getYRender() {
		return yRender;
	}
	
	public boolean isMouseWithinTile(float mouseX,  float mouseY){
		
		float xBase = xRender;
		float yBase = yRender;
		//float pointsX[] = {xBase,xBase+23,xBase+11,xBase+11};
		//float pointsY[] = {yBase+29,yBase+29,yBase+21,yBase+36};
		float pointsX[] = {xBase+10,xBase+11,xBase+12,xBase+13,xBase+23,xBase+23,xBase+10,xBase+11,xBase+12,xBase+13,xBase,xBase};
		float pointsY[] = {yBase+21,yBase+21,yBase+21,yBase+21,yBase+28,yBase+29,yBase+36,yBase+36,yBase+36,yBase+36,yBase+29,yBase+28};
		int   i, j=11 ;
		boolean  oddNodes=false;

		for (i=0; i<12; i++) {
		if ((pointsY[i]< mouseY && pointsY[j]>=mouseY
		    ||   pointsY[j]< mouseY && pointsY[i]>=mouseY)
		    &&  (pointsX[i]<=mouseX || pointsX[j]<=mouseX)) {
		      oddNodes^=(pointsX[i]+(mouseY-pointsY[i])/(pointsY[j]-pointsY[i])*(pointsX[j]-pointsX[i])<mouseX); }
		    j=i; }

		return oddNodes;
	}
	static public boolean isTileWithinRangeManhattan(BattleTile startTile, BattleTile testTile, int range){
		int dx = testTile.getXPos() - startTile.getXPos();
		int dy = testTile.getYPos() - startTile.getYPos();
		int distance = Math.abs(dx) + Math.abs(dy);
		if(distance<=range){
			return true;
		}
		return false;
	}
	static public boolean isTileWithinRangeEuclidean(BattleTile startTile,BattleTile testTile, int range){
		int dx = testTile.getXPos() - startTile.getXPos();
		int dy = testTile.getYPos() - startTile.getYPos();
		double distance = (Math.sqrt(dx*dx+dy*dy));
		if(distance<=range){
			return true;
		}
		return false;
	}
	static public boolean isTileWithinRangeNorm(BattleTile startTile, BattleTile testTile, int range){
		int dx = testTile.getXPos() - startTile.getXPos();
		int dy = testTile.getYPos() - startTile.getYPos();
		
		int result = (int) (Math.sqrt((dx*dx)+(dy*dy)));
		if(result <=range){
			return true;
		}
		return false;
	}
	static public ArrayList<BattleTile> getMoveableTiles(Arena arena, BattleTile startTile, int range){
		ArrayList<BattleTile>tiles = new ArrayList<BattleTile>();

        

        startTile.setDistanceFromStart(0);
        ArrayList<BattleTile> openList = new ArrayList<BattleTile>();
        ArrayList<BattleTile> closedList = new ArrayList<BattleTile>();
        openList.add(startTile);
        //while we haven't reached the goal yet
        while(openList.size() != 0) {

                BattleTile currTile = openList.get(0);


                //We were able to make it to this tile, so it is good
                openList.remove(currTile);
                closedList.add(currTile);
                if(currTile.distanceFromStart<=range){
                	tiles.add(currTile);
                }

                ArrayList<BattleTile> neighbors = new ArrayList<BattleTile>();
                if(arena.getTile(currTile.getXPos()+1, currTile.getYPos())!=null){
                	neighbors.add(arena.getTile(currTile.getXPos()+1, currTile.getYPos()));
                }
                if(arena.getTile(currTile.getXPos()-1, currTile.getYPos())!=null){
                	neighbors.add(arena.getTile(currTile.getXPos()-1, currTile.getYPos()));
                }
                if(arena.getTile(currTile.getXPos(), currTile.getYPos()+1)!=null){
                	neighbors.add(arena.getTile(currTile.getXPos(), currTile.getYPos()+1));
                }
                if(arena.getTile(currTile.getXPos(), currTile.getYPos()-1)!=null){
                	neighbors.add(arena.getTile(currTile.getXPos(), currTile.getYPos()-1));
                }
                for(BattleTile neighbor : neighbors) {

                        //if we have already searched this Node, don't bother and continue to the next one 
                        if (closedList.contains(neighbor)){
                                continue;
                        }
                        //also just continue if the neighbor is an obstacle
                        if(neighbor.isBlocked){
                        	closedList.add(neighbor);
                        	continue;
                        }
                        if(!openList.contains(neighbor)){
                        	neighbor.setDistanceFromStart(currTile.getDistanceFromStart()+1);
                        	openList.add(neighbor);
                        }
                }
        }
        tiles.remove(startTile);
        return tiles;
	}
	static public int getTileDistance(BattleTile startTile, BattleTile endTile){
		return (endTile.getXPos() - startTile.getXPos()) + (endTile.getYPos() - startTile.getYPos());
	}
	static public ArrayList<BattleTile> getPathOfTiles(Arena arena, BattleTile startTile, BattleTile endTile){
		//https://code.google.com/p/a-star-java/source/browse/AStar/src/CDIO/pathFinder/AStar.java
		ArrayList<BattleTile> closedList = new ArrayList<BattleTile>();
		ArrayList<BattleTile> openList = new ArrayList<BattleTile>();
		ArrayList<BattleTile> path = new ArrayList<BattleTile>();
		for(int x = 0;x<arena.getWidth();x++){
			for(int y =0;y<arena.getHeight();y++){
				arena.getTile(x, y).setPrevious(null);
			}
		}
		startTile.setDistanceFromStart(0);
		openList.add(startTile);
		while(openList.size() != 0){
			BattleTile currTile = openList.get(0);
			if(currTile.equals(endTile)){
				return makePath(currTile);
			}
			openList.remove(currTile);
			closedList.add(currTile);
			ArrayList<BattleTile> neighbors = new ArrayList<BattleTile>();
            if(arena.getTile(currTile.getXPos()+1, currTile.getYPos())!=null){
            	neighbors.add(arena.getTile(currTile.getXPos()+1, currTile.getYPos()));
            }
            if(arena.getTile(currTile.getXPos()-1, currTile.getYPos())!=null){
            	neighbors.add(arena.getTile(currTile.getXPos()-1, currTile.getYPos()));
            }
            if(arena.getTile(currTile.getXPos(), currTile.getYPos()+1)!=null){
            	neighbors.add(arena.getTile(currTile.getXPos(), currTile.getYPos()+1));
            }
            if(arena.getTile(currTile.getXPos(), currTile.getYPos()-1)!=null){
            	neighbors.add(arena.getTile(currTile.getXPos(), currTile.getYPos()-1));
            }
            for(BattleTile neighbor : neighbors){
            	boolean isBetter;
            	if(closedList.contains(neighbor)){
            		continue;
            	}
            	if(!neighbor.isBlocked()){
            		int neighborDistance = (currTile.distanceFromStart + BattleTile.getTileDistance(currTile, neighbor));
            		if(!openList.contains(neighbor)){
            			openList.add(neighbor);
            			isBetter = true;
            		}else if(neighborDistance < currTile.getDistanceFromStart()){
            			isBetter = true;
            		}else{
            			isBetter = false;
            		}
            		if(isBetter){
            			neighbor.setPrevious(currTile);
            			neighbor.setDistanceFromStart(neighborDistance);
            			neighbor.setDistanceFromGoal(getEstimatedDistanceToGoal(neighbor,endTile));
            		}
            	}
            }
		}
		return null;
	}
	private static ArrayList<BattleTile> makePath(BattleTile tile){
		ArrayList<BattleTile> path = new ArrayList<BattleTile>();
		BattleTile iterator = tile;
		while(iterator.previous != null){
			path.add(iterator.previous);
			iterator = iterator.previous;
		}
		Collections.reverse(path);
		return path;
	}
	static public ArrayList<BattleTile> getTilesWithinRange(Battle battle, BattleTile epicenter, int range){
		ArrayList<BattleTile> tiles = new ArrayList<BattleTile>();
		for(int x = epicenter.getXPos()-range;x<=epicenter.getXPos()+range;x++){
			for(int y = epicenter.getYPos()-range;y<=epicenter.getYPos()+range;y++){
				if(battle.getArena().getTile(x, y) != null){
					if(BattleTile.isTileWithinRangeEuclidean(battle.getArena().getTile(x, y), epicenter, range)){
						tiles.add(battle.getArena().getTile(x, y));
					}
				}
			}
		}
		return tiles;
	}
	static public ArrayList<BattleTile> getTilesWithinBasicAttackRange(Battle battle, BattleTile epicenter, int range){
		ArrayList<BattleTile> tiles = new ArrayList<BattleTile>();
		for(int n = 1; n<=range;n++){
			if(battle.getArena().getTile(epicenter.getXPos()+n, epicenter.getYPos())!=null){
				tiles.add(battle.getArena().getTile(epicenter.getXPos()+n, epicenter.getYPos()));
			}
			if(battle.getArena().getTile(epicenter.getXPos()-n, epicenter.getYPos())!=null){
				tiles.add(battle.getArena().getTile(epicenter.getXPos()-n, epicenter.getYPos()));
			}
			if(battle.getArena().getTile(epicenter.getXPos(), epicenter.getYPos()+n)!=null){
				tiles.add(battle.getArena().getTile(epicenter.getXPos(), epicenter.getYPos()+n));
			}
			if(battle.getArena().getTile(epicenter.getXPos(), epicenter.getYPos()-n)!=null){
				tiles.add(battle.getArena().getTile(epicenter.getXPos(), epicenter.getYPos()-n));
			}
		}
		return tiles;
	}
}
