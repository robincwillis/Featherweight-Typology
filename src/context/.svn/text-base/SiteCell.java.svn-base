package context;

import processing.core.PApplet;
import elements.*;
import java.util.*;

public class SiteCell {

	PApplet p;
	public Dimension dim;
	public Point pos;
	public ArrayList<String> contents;
	//public String contains;
	public boolean full;
	public int spaceColor; 
	public SiteCell(PApplet parent){
		p = parent;
		dim = new Dimension();
		pos = new Point();
		
	}
	
	public SiteCell(PApplet parent, Dimension dim, Point pos){
		p = parent;
		spaceColor = 255;
		this.dim = dim;
		this.pos = pos;
		contents = new ArrayList<String>();
	}
	
	//public float proximity(SiteCell)
	//public float proximity(Space)
	//public float proximity(float x, float y)
	
	public void setContents(String key, int color){
		//p.println("bang"+pos.ToString());
		//removeContents();
		contents.add(key);

		full = true;
		spaceColor = color;
		
	}
	
	
	public void setColor(int color){
		
		spaceColor = color;
	}
	
	public void removeContents(String key){
		
		contents.remove(key);
		
		//trying to remove by object, if this doesnt work try by index
		
//		for(int i=0;i<contents.size();i++){
//			String contains = contents.get(i);
//			if(contains.equals(key)){
//				contents.remove(i);
//			}
//		}
		//spaceColor = null;
		
		if (contents.isEmpty()){
			full = false;
		}
	}
	
	public void draw2d(float scale, float offset){
		p.pushStyle();
		p.noStroke();
		if(full && contents.size()>1){
			p.fill(0);
		}
		else if(full && contents.size()<1){
			p.fill(255);
		}
		
		else{
			p.fill(200,100);
		}
		p.rectMode(p.CENTER);
		p.rect((this.pos.a.x*scale)+offset, (this.pos.a.y*scale)+offset, (this.dim.w*scale)-offset, (this.dim.l*scale)-offset);
		p.popStyle();
	}
	
	public void draw3d(float scale, float offset){
		p.pushStyle();
		p.noStroke();
		
		if(full && contents.size()>1){
			p.fill(0);
		}
		else if(full && contents.size()<1){
		
			p.fill(255);
		}
		
		else{
			p.noFill();
			//p.fill(200,100);
		}
		//p.rectMode(p.CENTER);
		p.beginShape();
		p.vertex((this.pos.a.x*scale)-(this.dim.w/2*scale)+offset, (this.pos.a.y*scale)-(this.dim.l/2*scale)+offset, this.pos.a.z*scale);
		p.vertex((this.pos.a.x*scale)+(this.dim.w/2*scale)-offset, (this.pos.a.y*scale)-(this.dim.l/2*scale)+offset, this.pos.a.z*scale);
		p.vertex((this.pos.a.x*scale)+(this.dim.w/2*scale)-offset, (this.pos.a.y*scale)+(this.dim.l/2*scale)-offset, this.pos.a.z*scale);
		p.vertex((this.pos.a.x*scale)-(this.dim.w/2*scale)+offset, (this.pos.a.y*scale)+(this.dim.l/2*scale)-offset, this.pos.a.z*scale);
		p.vertex((this.pos.a.x*scale)-(this.dim.w/2*scale)+offset, (this.pos.a.y*scale)-(this.dim.l/2*scale)+offset, this.pos.a.z*scale);
		p.endShape();
		//p.rect((this.pos.a.x*scale)+offset, (this.pos.a.y*scale)+offset, (this.dim.w*scale)-offset, (this.dim.l*scale)-offset);
		
		p.popStyle();
	}
	
}
