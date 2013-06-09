package database;

import processing.core.*;

import java.io.IOException;
import java.util.*;

import controlP5.ControlWindowCanvas;
import jxl.*;
import jxl.read.biff.BiffException;
import typology.*;
import elements.*;

public class SpaceBuilder {

	PApplet p;
	Parse parser;
	GUI gui;
	guiListener spaceEntryListener;
	Read spaceReader, configReader;
	String spacePath, configPath;
	public Space specSpace;
	ArrayList<String> activityTitleSet; 
	ArrayList<Workbook> activityWorkbookSet, configWorkbookSet;
	public boolean typeColor = false;
	private int spaceSheetIndex;
	private int activityCount;
	
	
	public SpaceBuilder(PApplet parent, GUI gui){
		p = parent;
		this.gui = gui;
		parser = new Parse();
		spacePath = "data/Activities/";
		configPath = "data/";
		
		
		
		spaceReader = new Read(spacePath);
		configReader = new Read(configPath);
		
		spaceReader.getFiles();
		configReader.getFiles();
		
		activityTitleSet = spaceReader.getTitles();
		
		activityCount = activityTitleSet.size();
		spaceSheetIndex = 1;
		spaceEntryListener = new guiListener();
		
		try {
			activityWorkbookSet = spaceReader.read();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			configWorkbookSet =  configReader.read();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	

	
	
	public ArrayList buildSpaceSet(){
		ArrayList spaces = new ArrayList();
		
		for (int i=0;i<activityCount;i++){
			String activityTitle = activityTitleSet.get(i); 
			Sheet spaceSheet = activityWorkbookSet.get(i).getSheet(spaceSheetIndex);
			buildSpaces(spaces, activityTitle, spaceSheet);
		}
		p.println(spaces.size());
		return spaces;
	}
	
	public void buildSpaces(ArrayList<Space> spaces, String title, Sheet sheet){

		int startRow = 2;
		
		int spaceTitleColumn = 0;		
		int countColumn = 1;
		int areaColumn = 2;
		int widthColumn = 3;
		int lengthColumn = 4;
		int heightColumn = 5;
		int typeColumn = 6;
		int levelColumn = 7;
		
		//sheet.
		for(int i= startRow;i<sheet.getRows();i++){
			
			int randColor = p.color(p.random(100,255),p.random(100,255),p.random(100,255),100);
			
			Space space = new Space(p);
			space.setWidth(parser.toFloat(sheet.getCell(widthColumn,i)));
			space.setLength(parser.toFloat(sheet.getCell(lengthColumn,i)));
			space.setHeight(parser.toFloat(sheet.getCell(heightColumn,i)));
			space.setKey(title+":"+parser.toString(sheet.getCell(spaceTitleColumn,i)));
			space.setType(parser.toString(sheet.getCell(typeColumn,i)));
			space.setLevel(parser.toInt(sheet.getCell(levelColumn,i)));
			space.setYPos(i*20);
			space.setColor(randColor);
			space.setActivity(title);
			setTypeColor(space);
			setActivityColor(space);
			space.setColor(space.activityColor);
			spaces.add(space);
		}
		buildAdjacencies(spaces);
	}

	
	public void setTypeColor(Space space){
		if(space.type.equals("Core")){
			space.typeColor = space.CORE_COLOR;
		}
		if(space.type.equals("Soft")){
			space.typeColor = space.SOFT_COLOR;		
		}
		if(space.type.equals("Hard")){
			space.typeColor = space.HARD_COLOR;
		}
		if(space.type.equals("Circ")){
			space.typeColor = space.CIRC_COLOR;
		}
		if(space.type.equals("Open")){
			space.typeColor = space.OPEN_COLOR;
		}
	}
	
	public void setActivityColor(Space space){
		if(space.activity.equals("Base")){
			space.activityColor = space.BASE_COLOR;
		}
		if(space.activity.equals("Cafe")){
			space.activityColor = space.CAFE_COLOR;
		}
		if(space.activity.equals("Gallery")){
			space.activityColor = space.GALLERY_COLOR;
		}
		if(space.activity.equals("Workshop")){
			space.activityColor = space.WORKSHOP_COLOR;
		}
	}
	
	
	//CONFIG BOOK ADJACENCY MATRIX
	public void buildAdjacencies(ArrayList<Space> spaces){
		
		Workbook configBook = configWorkbookSet.get(0);
		int columnCount = configBook.getSheet(1).getColumns() - 7;
		int rowCount = configBook.getSheet(1).getRows();
		// Get List of adjacencies nested in list of spaces
		//Loop through keys
		//If key == adjacency Key then apply adjacecencies to List
		//ArrayList<ArrayList<Space>> adjacencyList = new ArrayList();
		ArrayList<String> spaceKeyList = new ArrayList<String>();
		ArrayList<String> matrixKeyList = nodeList(rowCount, columnCount,configBook);
		ArrayList<ArrayList<Space>> adjacencyList = adjacencyList(rowCount,columnCount,matrixKeyList,spaces, configBook);
		//p.println(spaceKeyList);
		//p.println(matrixKeyList.toString());
		for(int i=0;i<spaces.size();i++){
			String key = spaces.get(i).key;
			p.println(key);
			spaceKeyList.add(key);
			for(int j=0;j<matrixKeyList.size();j++){
				if(key.equals(matrixKeyList.get(j))){
					spaces.get(i).setNeighbors(adjacencyList.get(j));
				}
			}
		}
		
		for(int i=0;i<spaces.size();i++){
			
		}
		
	}
	//CONFIG BOOK KEYS
	public ArrayList<String> nodeList(int rCount, int cCount, Workbook mBook) {
		ArrayList<String> nodeList = new ArrayList<String>();
		int KEY_COL = 1; // title of space
		int SHEET = 1;
		int ACT_COL = 2;
		for (int i = 0; i < rCount; i++) {
			String keyLabel = mBook.getSheet(SHEET).getCell(cCount + KEY_COL, i).getContents();
			String activityLabel = mBook.getSheet(SHEET)
					.getCell(cCount + ACT_COL, i).getContents();
			String nodeLabel = activityLabel + ":" + keyLabel;
			
			nodeList.add(nodeLabel);

		}

		return nodeList;
	}
	
	public ArrayList<ArrayList<Space>> adjacencyList(int rCount, int cCount,ArrayList<String> nodeList,ArrayList<Space> spaces, Workbook mBook) {

		ArrayList<ArrayList<Space>> adjacencyList = new ArrayList<ArrayList<Space>>();

		for (int i = 0; i < cCount; i++) {

			String nodeLabel = nodeList.get(i);

			ArrayList<Space> adjacencySet = new ArrayList<Space>();

			for (int j = 0; j < rCount; j++) {
				Cell cell = mBook.getSheet(1).getCell(j, i);
				int adjacency = parser.toInt(cell);

				if (adjacency == 1) {
					String aLabel = nodeList.get(j);
					
					for(int k = 0; k<spaces.size();k++){
						if(aLabel.equals(spaces.get(k).key)){
							adjacencySet.add(spaces.get(k));
						}
						
					}
					
				}

			}
			adjacencyList.add(adjacencySet);
		}

		return adjacencyList;
	}
	
	public void spaceGUI(ArrayList<Space> spaces){
		int col = 100;
		int margin = 15;
		gui.addTextLabel("Space-Header", "Spaces Title", "spaceGUI", 0, margin);
		gui.addTextLabel("Space-Sub-Head","Loaded: "+spaces.size()+" Spaces", "spaceGUI", 0, margin*2);
		
		gui.addTextLabel("specTitle","", "spaceGUI",           0, margin*3);
		gui.addTextLabel("specWidth", "Width:", "spaceGUI",    0, margin*4);
		gui.addTextLabel("specHeight", "Length:", "spaceGUI",  0, margin*5);
		gui.addTextLabel("specLength", "Height:", "spaceGUI",  0, margin*6);	
		gui.addTextLabel("specRating", "Rating:", "spaceGUI", 0, margin*7);
		gui.addTextLabel("specNeighbors", "Neighbors","spaceGUI",0,margin*8);
		
		gui.addTextLabel("rateNeighbors", "Neighbors","spaceGUI",0,margin*9);
		gui.addTextLabel("rateCores", "Neighbors","spaceGUI",0,margin*9);
		gui.addTextLabel("rateCollision", "Neighbors","spaceGUI",0,margin*10);
		
		gui.addScrollList("Space-List", "spaceGUI", 780, 40, 200, 500);
		
		for(int i =0;i<spaces.size();i++){
			Space space = spaces.get(i);
			gui.addScrollListEntry(space.key,"Space-List", i);
			gui.setListener(space.key, spaceEntryListener);
		}
		spaceEntryListener.setMethod("loadSpaceSpecs","String",false, new Object[]{spaces}, this);

	}
	
	public void highlightNeighbor(Space space){
		for(int i=0;i<space.neighbors.size();i++){
			Space neighbor = space.neighbors.get(i);
			neighbor.setColor(neighbor.NEIGHBOR_COLOR);
		}
	}
	
	public void loadSpaceSpecs(String key, ArrayList spaces){
	    //gui.removeController("specTitle", "spaceGUI");
	    if(specSpace != null){
	    	if(typeColor){
	    		specSpace.setColor(specSpace.typeColor);
	    		for(int i=0;i<specSpace.neighbors.size();i++){
	    			Space neighbor = specSpace.neighbors.get(i);
	    			neighbor.setColor(neighbor.typeColor);
	    		}
	    		//typeColor = false;
	    	}else{
	    		specSpace.setColor(specSpace.activityColor);
	    		for(int i=0;i<specSpace.neighbors.size();i++){
	    			Space neighbor = specSpace.neighbors.get(i);
	    			neighbor.setColor(neighbor.activityColor);
	    		}
	    	}
	    	
	    }
		//GET THE SPACE
	    specSpace = getSpace(key, spaces);
	    //HIGHLIGHT SPACE
	    //HIGHLIGHT NEIGHBORS
	   specSpace.setColor(specSpace.SPEC_COLOR); 
	   highlightNeighbor(specSpace);
	    //LOAD VALUES
		gui.setValue("specTitle",key);
		gui.setValue("specWidth", "Width: "+specSpace.dim.w);
		gui.setValue("specHeight", "Length: "+specSpace.dim.l);
		gui.setValue("specLength", "Height: "+specSpace.dim.h);
		gui.setValue("specRating", "Rating: "+specSpace.rating);
		gui.setValue("specNeighbors", "Neighbors: "+ specSpace.getNeighborKeys());
		
		gui.setValue("rateNeighbors", "Neighbors: "+ specSpace.getNeighborKeys());
		gui.setValue("rateCore", "Neighbors: "+ specSpace.getNeighborKeys());
		gui.setValue("rateCollision", "Neighbors: "+ specSpace.getNeighborKeys());
		//gui.setValue("spec",);
		//specSpace.draw2d(6);
		
		//GUI TO SET RATING BIAS (OPTIMIZATION FOR CERTAIN CONDITION) 
		
		//p.println(key);
	}
	
	public Space getSpace(String key, ArrayList<Space> spaces){
		Space space = new Space(p);
		for(int i=0;i< spaces.size();i++){
			if(key.equals(spaces.get(i).key)){
				space = spaces.get(i);
			}
		}
		return space;
	}

	
	public void saveSpaceState(){
		
	}

	//----testing
	public void drawSomething(PApplet d){
		d.pushStyle();
		d.rect(100, 100, 100, 100);
		d.pushMatrix();
	}
	
	public void drawSpaceStats(ArrayList<Space> spaces, PApplet d){
		   int row =0;
		   int col =0;
		  // d.rect(10, b, c, d)
		   for (int i=0;i<spaces.size();i++){
			   spaces.get(i).draw2dIsolated(1,240+(row*100),10+(col*65));
		     if (row<2){row ++;}
		     else{row = 0;col ++;}
		 }
		
	}

}


