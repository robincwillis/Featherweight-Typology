package typology;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.*;
import elements.*;
import run.*;
import context.*;
public class Space {

	PApplet p;

	public ArrayList<Space> neighbors;
	public String key, type, activity;
	public int color, level, typeColor, activityColor;
	public float rating, collisionRating, coreRating, neighborRating;
	public Point pos;
	public Dimension dim;
	public boolean fixed;
	public boolean rotated;
	public boolean done;
	
	public int CORE_COLOR, HARD_COLOR, SOFT_COLOR, CIRC_COLOR, ENTRY_COLOR, OPEN_COLOR;
	
	public int BASE_COLOR, CAFE_COLOR, GALLERY_COLOR, WORKSHOP_COLOR , SPEC_COLOR, NEIGHBOR_COLOR;
	
	public Space(PApplet parent){
		p = parent;		
		pos = new Point();
		dim = new Dimension();
		key = "newSpace";
		color = p.color(255);
		rating = 0;
		fixed = false;
		rotated = false;
		done = false;
		
		CORE_COLOR = p.color(213,175,202);
		HARD_COLOR = p.color(183,219,223);
		SOFT_COLOR = p.color(175,209,146);
		CIRC_COLOR = p.color(194,130,118);
		ENTRY_COLOR = p.color(233,145,117);
		OPEN_COLOR  = p.color(255,255,103);
		
		BASE_COLOR = p.color(100);
		CAFE_COLOR = p.color(150);
		GALLERY_COLOR = p.color(200);
		WORKSHOP_COLOR = p.color(250);
		SPEC_COLOR = p.color(255,0,0);
		NEIGHBOR_COLOR = p.color(255,255,100);
	}
	
	public Space(PApplet parent,String k,float x, float y, float z, float w, float l, float h){
		p = parent;
		pos = new Point(x,y,z);
		dim = new Dimension(w,l,h);
		this.key = k;
	}
	
	public void setColor(int color){
		this.color = color;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setNeighbors(ArrayList<Space> neighbors){
		this.neighbors = neighbors;
	}
	
	public void setDim(float w, float l, float h){
		setWidth(w);
		setLength(l);
		setHeight(h);
	}
	
	public void setWidth(float w){	
	 	this.dim.w = w;
	}
	public void setLength(float l){	
		this.dim.l = l;
	}
	public void setHeight(float h){	
		this.dim.h = h;
	}
	
	public void setActivity(String title){
		this.activity = title;
	}
	
	public void setRating(float newRating){
		rating = newRating;
	}
	
	public void setPos(float x, float y, float z){
		setXPos(x);
		setYPos(y);
		setZPos(z);
	}
	
	public void setPosByCell(SiteCell cell){
		
		setXPos(cell.pos.a.x-cell.dim.w/2);
		setYPos(cell.pos.a.y-cell.dim.w/2);
		setZPos(cell.pos.a.z);
		
	}
	
	public void fixSpace(){
		this.fixed = true;
	}
	
	public void unfixSpace(){
		this.fixed = false;
	}
	
	public void setPosByIndex(int i, int j, float cellSize){
		setXPos((i*cellSize)-(cellSize/2));
		setYPos((j*cellSize)-(cellSize/2));
		setZPos(0);
	}
	
	public int getIndexXPos(float cellSize){
		return (int) ((int)(this.pos.a.x+cellSize/2)/cellSize);
	}
	
	public int getIndexYPos(float cellSize){
		return (int) ((int)(this.pos.a.y+cellSize/2)/cellSize);
		
	}
	
	public int getIndexZPos(float cellHeight){
		return (int) ((int)(this.pos.a.z+cellHeight/2)/cellHeight);
		
	}
	
	public Point getCentroid(){
		Point centroid = new Point();
		centroid.a.x = this.pos.a.x+(this.dim.w/2); 
		centroid.a.y = this.pos.a.x+(this.dim.l/2);
		//PVector sumOfAllVerts = new PVector(0f, 0f, 0f);
		return centroid;
	}
	
	public String getNeighborKeys(){
		String neighborString = "";
		for(int i=0;i<neighbors.size();i++){
			neighborString += neighbors.get(i).key;
			neighborString += " ";
		}
		return neighborString;
	}
	
	public ArrayList<String> getNeighborKeyList(){
		ArrayList<String> neighborKeys = new ArrayList<String>();
		if(neighbors != null){
			for(int i=0;i<neighbors.size();i++){
				neighborKeys.add(neighbors.get(i).key);
			}
		}
		
		return neighborKeys;
	}
	
	public void rotateSpace(){
		float width = this.dim.w;
		float length = this.dim.l;
		
		this.dim.w = length;
		this.dim.l = width;
		if(rotated){
			rotated = false;
		}else if(!rotated){
			rotated = true;
		}
	}
	
	public SiteCell[][][] getCellsWithin(Site3d site){
		

		int cellXCount = this.cellWidth(site.cellSize);
		int cellYCount = this.cellLength(site.cellSize);
		int cellZCount = this.cellHeight(site.cellHeight);
		int cellXPos = getIndexXPos(site.cellSize);
		int cellYPos = getIndexYPos(site.cellSize);
		int cellZPos = getIndexZPos(site.cellHeight);
		SiteCell[][][] containedCells = new SiteCell[cellXCount][cellYCount][cellZCount];

			for(int i = cellXPos;i<cellXPos+cellXCount;i++){
				for(int j=cellYPos;j<cellYPos+cellYCount;j++){
					//p.println(i+","+j);
						//p.println();
					for(int k=cellZPos;k<cellZPos+cellZCount;k++){
						containedCells[i-cellXPos][j-cellYPos][k-cellZPos] = site.getCell(i, j, k);
					}
						
						//p.println(site.getCell(i, j).full);
				}
	
		}

		return containedCells; 
	}
	
	public void setXPos(float x){
		this.pos.a.x = x;
	}
	public void setYPos(float y){
		this.pos.a.y = y;
	}
	public void setZPos(float z){
		this.pos.a.z = z;
	}
	
	public void setKey(String k){
		this.key = k;
	}
	
	public float getDistance( Space bSpace){
		Point aCentroid = this.getCentroid();
		Point bCentroid = bSpace.getCentroid();
		return aCentroid.distanceTo(bCentroid);
	}
	
	
	public int cellLength(float cellLength){
		int cellCount = (int) (this.dim.l/cellLength);
		return cellCount;
	}
	
	public int cellWidth(float cellWidth){
		int cellCount = (int) (this.dim.w/cellWidth);
		return cellCount;
	}
	
	public int cellHeight(float cellHeight){
		int cellCount = (int) (this.dim.h/cellHeight);
		return cellCount;
	}
	
	public void draw2dIsolated(float scale, float xPos, float yPos){
		p.pushStyle();	
		p.stroke(0);
		p.strokeWeight(1);
		p.fill(color,240);
		p.rect(xPos, yPos, this.dim.w*scale, this.dim.l*scale);
		
		p.fill(0);
		p.text(key+":"+rating,xPos, yPos+this.dim.l*scale+15);
		if(done){
			p.fill(0);
			p.rect(xPos+ this.dim.w*scale+10,yPos,this.dim.w*scale, this.dim.l*scale);	
		}
		p.popStyle();
	}
	
	public void draw3dIsolated(float scale, float xPos, float yPos){
		
		p.pushMatrix();
		p.translate(xPos, yPos, 0);
		p.rotateX(120);
		p.rotateZ(45);
		//p.rotateY(30);
		p.stroke(0);
		p.strokeWeight(1);
		p.fill(color,240);
		p.box( this.dim.w*scale, this.dim.l*scale, this.dim.h*scale);
		
		
		p.popMatrix();
		
	}
	
	public void draw2d(float scale){
		p.pushStyle();
		p.stroke(0);
		p.strokeWeight(1);
		//p.rectMode(p.CENTER);
		p.fill(color,100);
		p.rect(this.pos.a.x*scale, this.pos.a.y*scale, this.dim.w*scale, this.dim.l*scale);

		p.popStyle();
	}
	
	public void draw3d(float scale){
	//	color = typeColor;
		int offset = 2;
		p.pushMatrix();
		p.translate((this.pos.a.x+(this.dim.w/2))*scale,(this.pos.a.y+(this.dim.l/2))*scale,(this.pos.a.z+ (this.dim.h/2))*scale);
		p.pushStyle();
		p.stroke(0);
		p.strokeWeight(1);
		//p.noStroke();
		p.fill(color,200);
		
		p.box((this.dim.w*scale)-offset, (this.dim.l*scale)-offset, (this.dim.h*scale)-offset);
	
		p.popStyle();
		p.popMatrix();
	}
	
}
