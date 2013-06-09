package elements;

import processing.core.PApplet;
import controlP5.ControlWindowCanvas;
import database.*;

public class ControlCanvas extends ControlWindowCanvas{
	
	SpaceBuilder ss;
	
	public ControlCanvas(){
		
	}
	
//	public ControlCanvas(SpaceBuilder s){
//		ss = s;
//	}
	
	@Override
	public void draw(PApplet c) {

	}
	
	public void setSpaceBuilder(SpaceBuilder s){
		ss = s;
	}
	

	
}
