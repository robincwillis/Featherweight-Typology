package run;

import processing.core.PApplet;
import controlP5.*;
import typology.*;
import context.*;
import elements.*;
import elements.ControlCanvas;
import database.*;
import java.util.*;
import peasy.*;
import processing.opengl.*;
import javax.media.opengl.GL;

public class Configure extends PApplet {
	
	PGraphicsOpenGL pgl;
	GL gl;

	public int cellSize = 4;
	public int cellHeight = 12;
	public float scale = 6;
	public int siteWidth = 36;
	public int siteLength = 96;
	public int siteHeight = 36;
	SpaceBuilder spaceBuild;
	ArrayList<Space> spaceSet;
	boolean specLoaded = false;

	PeasyCam cam;
	ControlP5 controlP5;
	GUI gui;
	Site sbo;
	Site3d sbo3d;
	
	public void setup() {
		size(1024,728, OPENGL);
		frameRate(30);
		ortho(-width/2, width/2, -height/2, height/2, -10000, 10000);
		hint(ENABLE_NATIVE_FONTS);
	
		
		controlP5 = new ControlP5(this);
		gui = new GUI(controlP5, this);
		
		cam = new PeasyCam(this, 1000);
		cam.setMinimumDistance(50);
		cam.setMaximumDistance(10000);

		spaceBuild = new SpaceBuilder(this, gui);
		spaceSet = spaceBuild.buildSpaceSet();

		sbo = new Site(this,siteWidth,siteLength, cellSize);
		buildSite(sbo);
		sbo3d = new Site3d(this,siteWidth,siteLength,siteHeight,cellSize, cellHeight);
		buildSite3d(sbo3d);
		initGUI();
		println(sbo.dim.w);
		switchColors(false);
	}
	
	public void initGUI(){
		controlP5.setAutoDraw(false);
		gui.initGUI();
		spaceBuild.spaceGUI(spaceSet);
	}

	public void draw() {
		background(255);
		
		//pgl = (PGraphicsOpenGL)g;
		//gl = pgl.beginGL();
		//gl.glDisable(GL.GL_DEPTH_TEST);
		//gl.glEnable(GL.GL_DEPTH_TEST);
		//gl.glEnable(GL.GL_BLEND);
		//gl.glBlendFunc(GL.GL_SRC_COLOR,GL.GL_ONE_MINUS_DST_COLOR); 
		//gl.setSwapInterval(1);
		//pgl.endGL();
		
		lights();
		ambientLight(25, 25, 25);
		//drawAxis();
		pushMatrix();
		translate(-(sbo.dim.w/2)*scale,-(sbo.dim.l/2)*scale,0);

		sbo3d.draw3d(scale,1);
		
		placeSpaces(spaceSet,sbo3d);
		
		popMatrix();
		noLights();
		cam.beginHUD();	
		controlP5.draw();

		if(spaceBuild.specSpace != null){
			spaceBuild.specSpace.draw3dIsolated(2, (float)260, (float)90);
		}
		
		cam.endHUD();
	}
	
	public void switchColors(boolean typeColor){
		if(typeColor){
			for(int i=0;i<spaceSet.size();i++){
				spaceSet.get(i).setColor(spaceSet.get(i).activityColor);
			}
			typeColor = false;
		}else{
			for(int i=0;i<spaceSet.size();i++){
				spaceSet.get(i).setColor(spaceSet.get(i).typeColor);
			}
			typeColor = true;
		}
	}
	
	
	void drawAxis(){
		pushStyle();
		strokeWeight(1);
		stroke(0);
		line(0,0,0, 100,0,0);
		line(0,0,0, 0,100,0);
		line(0,0,0, 0,0,100);
		popStyle();
	}


	
	public void buildSite(Site site){
		
		site.setCellDim(cellSize, cellSize, 0);
		site.buildCells();
		
	}
	
	public void buildSite3d(Site3d site3d){
		
		site3d.setCellDim(cellSize, cellSize, cellHeight);
		site3d.buildCells();
	}
	
	//******************************
	//ENTER LOOP FOR ALL SPACES HERE
	//******************************
	public void placeSpaces(ArrayList<Space> spaces, Site3d site){
		
		for(int i=0;i<spaces.size();i++){
			//spaces.size()
			//if(spaces.get(i).rating<10){
				placeSpace(spaces.get(i), site);				
			//}
			//else{
			//	spaces.get(i).done = true;
			//}
		
			spaces.get(i).draw3d(scale);

		}

	}
	
	//ENTER LOOP FOR SINGLE SPACE
	public void placeSpace(Space space, Site3d site){
		//space.rotateSpace();
		//here is where i set the next loc Randomly
		//TODO add temperature and colling based on placeCount
		double tempX = Math.random() * (site.cellRows);
		double tempY = Math.random() * (site.cellColumns);
		double tempZ = Math.random() * (site.cellLevels);
		println("LEVEL"+ space.level);
		//this is where i get the cell to place the space at		
		SiteCell placeCell = site.getCell((int)tempX,(int)tempY,(int)space.level-1);	
		
		pushMatrix();
			translate(placeCell.pos.a.x*scale,placeCell.pos.a.y*scale, placeCell.pos.a.z*scale);
			fill(0);
			sphere(3);
		popMatrix();

		
		//check if if the space will be in bounds	
		if(checkBounds(placeCell, space, site)){
			
			float newRating =1;
			if(space.fixed){
				
				//place a dummy space to test for new rating
				Space testSpace = new Space(this);
				setTestSpace(testSpace, space);
				testSpace.setPosByCell(placeCell);
				newRating = getRating(testSpace, site);
			}
			
			println(space.key+" New Rating: "+ newRating+" Old Rating: "+ space.rating);
			
			//PLACE BASED ON NEW RATING
			
			if(newRating > space.rating){
				
				space.setRating(newRating);
				if(space.fixed){
					if(space.rotated){
						space.rotateSpace();
						unsetCells(space, site);
						space.rotateSpace();
					}else{
						unsetCells(space, site);
					}
				}
				//this is actually where we place the space
				space.setPosByCell(placeCell);	
				setCells(space,site);
				space.fixSpace();
				
			}else{
				float checkRating;

				if(space.fixed){
					println("inBOUNDS:"+ checkBounds(placeCell, space, site));
					checkRating = getRating(space,site);
					space.setRating(checkRating);
				}
			}
		}
	}
	
	public void setTestSpace(Space testSpace, Space space){
		testSpace.setDim(space.dim.w, space.dim.l, space.dim.h);
		testSpace.key = space.key;
		testSpace.type = space.type;
		testSpace.neighbors = space.neighbors;
	}
		
	//COUNT HOW MANY LOOPS
	//TURN RANDOMNESS DOWN AS COUNT GOES UP
	//RANDOMNESS RANGE SHOULD REFERENCE ORIGIN OF SPACE
	//***should be a method of site
	public void unsetCells(Space space, Site3d site){
		SiteCell[][][] containedCells = space.getCellsWithin(site);
		for (int r=0; r < containedCells.length; r++) {
		    for (int c=0; c < containedCells[r].length; c++) {
		    	for(int l=0;l < containedCells[r][c].length;l++){
		    		containedCells[r][c][l].removeContents(space.key);
		    	}
		    	
	    	}
			   
	    }    	
		
	}
	
	//***should be a method of site
	public void setCells(Space space, Site3d site){
	
		//collisionCheck(space, site) && 
	
			SiteCell[][][] containedCells = space.getCellsWithin(site);
	
			for (int r=0; r < containedCells.length; r++) {
			    for (int c=0; c < containedCells[r].length; c++) {
			    	for(int l=0;l < containedCells[r][c].length;l++){    	
			    	containedCells[r][c][l].setContents(space.key, space.color);
			    	}
		    	}
				   
		    }    	

	}
	
	public Space getSpaceByKey(ArrayList<Space> spaces, String key){
		Space returnSpace = new Space(this);
		for(int i=0;i<spaces.size();i++){
			String spaceKey = spaces.get(i).key;
			if(key.equals(spaceKey)){
				returnSpace = spaces.get(i);
			}else{
			
			}
		}
		return returnSpace;
	}
	
	//******************************
	//RULE METHODS HERE
	//******************************
	//RETURN RATING FOR EACH
	//0-100 test 1 collision
	//0-100 test 2 do any edges touch another space
	//0-100 test if space touches another of the same type

	//NEED A SET OF HARD RULES
	//1-out of bounds
	
	//***should be a method of site
	//THIS IS A RULE
	public boolean checkBounds(SiteCell cell, Space space, Site3d site){
		
		int cellXCount = space.cellWidth(site.cellSize);
		int cellYCount = space.cellLength(site.cellSize);
		int cellZCount = space.cellLength(site.cellHeight);
		int cellXPos = site.getXindexOfCell(cell);
		int cellYPos = site.getYindexOfCell(cell);
		int cellZPos = site.getZindexOfCell(cell);
		
		if(site.checkInBounds(cellXPos, cellYPos,cellZPos, cellXCount, cellYCount, cellZCount)){
					return true;
				}else{
					return false;
				}
	}
	
	//THIS IS A RULE
	//TODO THIS IS BROKEN, WHEN IT FINDS A CELL THAT IS AT A CORNER(IE MATCHES TWO DIRECTIONAL CONDITIONS) ITS GOING TO OVERWRITE WHICH DIRECTION TO CHECK
	public float checkNeighbor(Site3d site, Space space){
		float rating = 0;
		float rateCount = 0;
		float checkCount = 0;
		int step = 1;
		SiteCell[][][] spaceCells = space.getCellsWithin(site);
		//println("ARRAY I LENGTH "+ spaceCells.length);
		//println("SPACE DIMENSION"+ space.dim.w);
		SiteCell returnCell = new SiteCell(this);
		ArrayList<String> neighborKeys = space.getNeighborKeyList();
		
		for(int i=0;i<spaceCells.length;i++){
			for(int j=0;j<spaceCells[i].length;j++){
				
			//	println("ARRAY J LENGTH "+ spaceCells[i].length);
				//println("ARRAY K LENGTH "+ spaceCells[i][j].length);
				for(int k=0;k<spaceCells[i][j].length;k++){
					
					ArrayList<SiteCell> returnCells= new ArrayList<SiteCell>();
					if (i == 0){
						returnCell = checkWest(site,spaceCells[i][j][k],step);
						returnCells.add(returnCell);
					}
					if (i == spaceCells.length-1){
						//check East
						returnCell = checkEast(site,spaceCells[i][j][k],step);
						returnCells.add(returnCell);
					}
					if (j == 0){
						//check South
						returnCell = checkSouth(site,spaceCells[i][j][k],step);
						returnCells.add(returnCell);
					}
					if (j == spaceCells[i].length){
						//check North
						returnCell = checkNorth(site,spaceCells[i][j][k],step);
						returnCells.add(returnCell);
					}
					//RATE RETURN CELL
					for(int h =0;h<returnCells.size();h++){
					
						if(returnCells.get(h) != null){
							checkCount = checkCount +1;
							if(returnCells.get(h).full){
								//RATING GOES UP
								rateCount = rateCount +(float)0.5;
								//get a list of keys
								ArrayList<String> returnKeys = returnCells.get(h).contents;
								for(int l=0;l<neighborKeys.size();l++){
									if(returnKeys.contains(neighborKeys.get(l))){
										//RATING GOES UP
										rateCount = rateCount +(float)0.5;
									}
								}
							}
						}
					}
					//-------
				}
			}
		}
		
		rating = rateCount / checkCount;
		
		return rating;
		
	}
	
	//THIS IS A RULE
	//TODO return rating if Cores are Stacked
	public float checkCore(Site3d site, Space space){
		float rating = 0;
		float rateCount = 0;
		float checkCount = 0;
		int step = 1;
		SiteCell[][][] spaceCells = space.getCellsWithin(sbo3d);
		SiteCell returnCell = new SiteCell(this);
		
		String type = space.type;
		
		for(int i=0;i<spaceCells.length;i++){
			for(int j=0;j<spaceCells[i].length;j++){
				for(int k=0;k<spaceCells[i][j].length;k++){
					ArrayList<SiteCell> returnCells = new ArrayList<SiteCell>();
					if(k==spaceCells[i][j].length-1){
					//Check Above	
						returnCell = checkAbove(site, spaceCells[i][j][k],step);
						returnCells.add(returnCell);
						//returnCell 
					}
					
					if(k==0){
					//Check Below	
						returnCell = checkBelow(site, spaceCells[i][j][k],step);
						returnCells.add(returnCell);
					}
					
					for(int h=0;h<returnCells.size();h++){
						checkCount = checkCount +1;
						if(returnCells.get(h) != null){
							if(returnCells.get(h).full){
								rateCount = rateCount +(float)0.5;
								
								if(space.type.equals("Core")){
									//look up space with key
									ArrayList<String> returnKeys = returnCells.get(h).contents;
									for(int l=0;l<returnKeys.size();l++){
										Space testSpace = getSpaceByKey(spaceSet,returnKeys.get(l));
										if(testSpace.type.equals("Core")){
											//RATING GOES UP
											rateCount = rateCount +(float)0.5;
										}
									}
									
										
										
									
									//get type
									//get keys
								}
								if(space.type.equals("Circ")){
									//look up space with key
									ArrayList<String> returnKeys = returnCells.get(h).contents;
									for(int l=0;l<returnKeys.size();l++){
										Space testSpace = getSpaceByKey(spaceSet,returnKeys.get(l));
										if(testSpace.type.equals("Circ")){
											//RATING GOES UP
											rateCount = rateCount +(float)0.5;
										}
									}
								}else{
									//rateCount = rateCount +(float)0.25;
								}
								
							}
						}
					}
					
				}
			}
		}
		rating = rateCount/checkCount;
		return rating;
	}
	
	public boolean checkGap(SiteCell cell, Space space){
		return false;
	}
	
	public SiteCell checkNorth(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		
		northCell = site.getCell(xPos, yPos+step, zPos);
		
		return northCell;
	}
	
	public SiteCell checkSouth(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		
		northCell = site.getCell(xPos, yPos-step, zPos);
		
		return northCell;
	}
	
	public SiteCell checkEast(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		
		northCell = site.getCell(xPos+step, yPos, zPos);
		
		return northCell;
	}
	
	public SiteCell checkWest(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		northCell = site.getCell(xPos-step, yPos, zPos);
		
		return northCell;
	}
	
	public SiteCell checkAbove(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		
		northCell = site.getCell(xPos, yPos, zPos+step);
		
		return northCell;
	}
	
	public SiteCell checkBelow(Site3d site,SiteCell queryCell,int step){
		SiteCell northCell = new SiteCell(this);
		int xPos = site.getXindexOfCell(queryCell);
		int yPos = site.getYindexOfCell(queryCell);
		int zPos = site.getZindexOfCell(queryCell);
		
		northCell = site.getCell(xPos, yPos, zPos-step);
		
		return northCell;
	}
	
	
	
	//THIS IS A RULE
	public boolean checkCollision(Space space, Site site){
		
		int cellXCount = space.cellWidth(site.cellSize);
		int cellYCount = space.cellLength(site.cellSize);
		
		int cellXPos = space.getIndexXPos(site.cellSize);
		int cellYPos = space.getIndexYPos(site.cellSize);
		
		if(site.checkOpenCellSet(cellXPos,cellYPos,cellXCount,cellYCount)){
			return true;
		}else{
			return false;
		}
	
		
	}
	
	//For some reason I am just doing the collison check in here
	
	public float getRating(Space space, Site3d site){
		//get a space
		println("-------------------------");
		println("getRating: "+space.key);
		println("current Rating: "+space.rating);
		int overlapCount = 0;
		float rating = 0;
		float overlapRating = 0;
		SiteCell[][][] cellSet = space.getCellsWithin(site);
		//println("getRating: "+cellSet.length);
		//check how many cells within that space overlap with another one
		for(int i=0;i<cellSet.length;i++){
			for (int j=0;j<cellSet[i].length;j++){
				for(int k=0;k<cellSet[i][j].length;k++){
				//println(cellSet[i][j][k].contents);
				if(!cellSet[i][j][k].contents.isEmpty()){
					
					for(int h=0;h<cellSet[i][j][k].contents.size();h++){
						if(!cellSet[i][j][k].contents.get(h).equals(space.key)){
							overlapCount++;
							
						}
						}
					}
					
				}
			}
		}
		
		float neighborRating = checkNeighbor(site, space);
		float coreRating = checkCore(site, space);
		//float neighborRating = (float)0.5;
		//println("overlapCount "+overlapCount);
		//println("size "+space.cellLength(cellSize)*space.cellWidth(cellSize) );
		overlapRating = 10 - ((float)overlapCount /(float) space.cellLength(cellSize)*(float)space.cellWidth(cellSize));
		//println("neighborRating "+ neighborRating);
		neighborRating = neighborRating * 10;
		coreRating = coreRating * 10;
		rating = (coreRating+overlapRating+neighborRating)/3;
		println("coreRating "+ coreRating);
		println("neighborRating"+ neighborRating);
		println("overlapRating "+overlapRating);
		println("newRating "+rating);
		return rating;
		//change rating
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { run.Configure.class.getName() });
	}
}
