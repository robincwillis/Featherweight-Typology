package elements;

import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import processing.core.*;
import controlP5.*;




@SuppressWarnings("rawtypes")
public class GUI {
	
	public ControlP5 gui;
	public PFont Georgia, Helvetica;
	
	PApplet p;
//	ControlFont font, bFont;
	//ControlWindow controlWindow;
	ControlCanvas cc;
	  
	public int actColor,bgColor,fgColor, labColor, valColor;
	public int bHeight, bWidth,stageHeight,stageWidth, margin;
	public int cWindowHeight, cWindowWidth;
	public int tableGUIWidth, configGUIWidth,configControlGUIWidth;
	
	public GUI(ControlP5 controlP5, PApplet parent){
		
		gui = controlP5;
		p = parent;

		
        stageHeight = p.height;
		stageWidth = p.width;
		

		
		margin = 10;
		bHeight = 20;
		bWidth =  100;
		
		actColor = p.color(0xff08a2cf);
		bgColor = p.color(230,230,230);
		fgColor = p.color(0,0,0);
		labColor = p.color(50,50,50);
		valColor = p.color(0,0,0);
		
		tableGUIWidth = p.width/2-margin*2;
		configGUIWidth = (p.width/4) *3 - margin*2;
		configControlGUIWidth = p.width/4 - margin*2;
		
//		Georgia = p.createFont("Georgia",20,true);
//		Helvetica  = p.createFont("Helvetica",18,true);
//		
//		bFont = new ControlFont(Georgia);
//	    font = new ControlFont(Helvetica);
	    
	    
	}
	
	public void initGUI(){
		
		gui.tab("Spaces");
		gui.tab("default").activateEvent(true);			
		gui.tab("default").setActive(false);
		gui.tab("Spaces").setActive(true);
	
		initSpaceGUI("spaceGUI","Spaces",margin, stageWidth-(margin*4));

		setColors();
			
	}
	
	public void setColors(){
		
		//gui.setColorActive(actColor);
		gui.setColorBackground(bgColor);
		gui.setColorForeground(fgColor);
		gui.setColorLabel(valColor);
		gui.setColorValue(valColor);
	}
	
	public void initSpaceGUI(String name,String tab, int xPos, int width){
		 addGroup(name,tab, xPos+margin, margin*4,width);
	}
	
	public void setFontSize(String name, int size){
		gui.controller(name).captionLabel().setControlFontSize(size);
		gui.controller(name).valueLabel().setControlFontSize(size);
	}

	public void addResultTitle(int index,int resultCount, int size){
		int xPos = configGUIWidth/size*index;
		int yPos = margin*21;
		removeController("Result"+index, "configureGUI");
		addTextLabel("Result"+index, "resultCount: "+resultCount,"configureGUI",xPos,yPos);
		
	}
	
	public Controller getController(String name){
		
		return gui.controller(name);
	}
	
	public void addRankList(String name){
		
		removeGroup(name);
		addScrollList(name, "configureGUI", stageWidth-500, margin*10, stageWidth-margin, stageHeight - (margin*30));
		
	}
	
	public void addRankEntry(String name,String listName, int index){
		//addTextLabel(String label, String value, String group, int xPos, int yPos)
			addScrollListEntry(name,listName,index);	
		
	}
	
	public void addScrollList(String name, String group, int xPos, int yPos, int width, int height){
		ScrollList list;
		list = gui.addScrollList(name, xPos, yPos, width, height); 
		list.setItemHeight(bHeight);
		list.setBarHeight(bHeight);
		list.setGroup(group);
	}
	
	public void addScrollListEntry(String name, String listName, int index){
		ScrollList list;
		Button b;
		list = (ScrollList) gui.getGroup(listName);
		b = list.addItem(name,index);
		
	}
	
	public void setTextField(String label,String value){
		Textfield textField;
		textField = (Textfield) gui.controller(label);
		textField.setValue(value);
		textField.setAutoClear(false);
	}
	
	public void addTab(){
		
	}
	
	public void addTableCell(String name, Sheet sheet, String sheetName,Cell cell, String group, int col, int row, float value){
		
		//does this work?
        String header = sheet.getCell(col,0).getContents();
        //v.debug("Header: "+header);
		
        int cWidth = 90; 
		int cHeight = bHeight+10;
		
		if(row == 0){ 
			addTextLabel(
				name +":"+sheetName+":"+cell.getRow()+":"+cell.getColumn(), 
				cell.getContents().toString(), group, 
				(col*cWidth)+(margin*12), 
				(row*cHeight)+(margin*7)
				);
		}else if(header.equals("Value Scaled")){
		//}else if(col ==4){	
			addSlider(
				name +":"+sheetName+":"+cell.getRow()+":"+cell.getColumn(), 
				value, group,1, (col*cWidth)+(margin*12), 
				(row*cHeight)+(margin*6),cWidth-10, 10);
		}else{
			addTextField(
					name +":"+sheetName+":"+cell.getRow()+":"+cell.getColumn(), 
					cell.getContents(),group, 
					(col*cWidth)+(margin*12),(row*cHeight)+(margin*6),
					cWidth-10,bHeight
					);		
		}
	}
	
	public void removeTable(String name, Sheet sheet, String group, String sheetName){
		for (int i=0; i < sheet.getColumns();i++){
			for (int j=0; j<sheet.getRows(); j++){
				Cell cell = sheet.getCell(i,j);
				removeController(name +":"+sheetName+":"+cell.getRow()+":"+cell.getColumn(),group);
			}
		}
	}
	
	public void addGroup(String name, String tab, int xPos, int yPos, int width){
		ControlGroup group;
		group = gui.addGroup(name, xPos, yPos, width);
		group.moveTo(tab);
		//group.moveTo(tab,controlWindow);
		//group.captionLabel().setControlFont(font);
		//group.captionLabel().setControlFontSize(8);
	}
	
	public void addBang(String name, String label, String group, int xPos, int yPos,int width,int height){
		 Bang bang;
		 bang = gui.addBang(label, xPos, yPos, width,height);
		 bang.setGroup(group);
		// bang.captionLabel().setControlFont(font);
		 //bang.captionLabel().setControlFontSize(10);
	}
	
	public void addList(String name,String group, int xPos,int yPos){
		MultiList multilist;
		multilist = gui.addMultiList(name,xPos, yPos,bWidth,bHeight);
		multilist.setGroup(group);
		//multilist.captionLabel().setControlFont(font);
		//multilist.captionLabel().setControlFontSize(10);
	}
	
	public void addListbutton(String listName, String name, String label, int index){
		MultiList List;
		MultiListButton Button;
		((MultiList) gui.controller(listName)).add(name,index);
		gui.controller(name).setLabel(label);
		//gui.controller(name).captionLabel().setControlFont(font);
		//gui.controller(name).captionLabel().setControlFontSize(8);
	}
	
	public void addSubListbutton(String parentButton, String name,  String label, int index,int subIndex){
		MultiListButton Button;
		MultiListButton subButton;
		Button = (MultiListButton) gui.controller(parentButton);
		subButton = Button.add(name,subIndex);
		subButton.setLabel(label);
		float[] arrIndex = {index, subIndex}; 
		subButton.setArrayValue(arrIndex);	
		//subButton.captionLabel().setControlFont(font);
		//subButton.captionLabel().setControlFontSize(8);
	}

	public void addTextField(String label, String value, String group, int xPos, int yPos, int width, int height){
		Textfield textFeild;
		textFeild = gui.addTextfield(label, xPos, yPos, width,height);
		textFeild.setGroup(group);
		textFeild.setValue(value);
		textFeild.captionLabel().setVisible(false);
		textFeild.valueLabel().setFont(ControlP5.standard56);
		textFeild.valueLabel().toUpperCase(true);
	}
	
	public void addButton(String name, String label, float value, String group, int xPos, int yPos){
		Button button;
		button = gui.addButton(name, value, xPos, yPos,  bWidth, bHeight);
		button.setGroup(group);
		button.setLabel(label);
		//button.captionLabel().setControlFont(font);
	}
	
	public void addTextLabel(String label, String value, String group, int xPos, int yPos){
	   Textlabel textLabel;
	   textLabel = gui.addTextlabel(label,value,xPos,yPos);
	   textLabel.setGroup(group);
	   //textLabel.valueLabel().setControlFont(font);
	   textLabel.valueLabel().setColor(labColor);
	//subButton.captionLabel().setControlFontSize(8);
	}
	
	public void addSlider(String label, float value, String group, int max, int xPos, int yPos, int w, int h){
		Slider slider;
		slider = gui.addSlider(label, 0, max,value, xPos, yPos, w, h);
		slider.setGroup(group);
		slider.captionLabel().setVisible(false);
		slider.alignValueLabel(gui.BOTTOM);
	}
	
	public void addTitle(String label, String value, String group){
	Textlabel textLabel;
    textLabel =	gui.addTextlabel(label, value, 0, margin*2);
    textLabel.setGroup(group);
   // textLabel.valueLabel().setControlFont(font);
    //textLabel.valueLabel().setControlFontSize(18);
    textLabel.valueLabel().setColor(labColor);
    
	}
	
	public void addToggle(String name, String label, boolean state,String group, int xPos, int yPos){
		Toggle toggle;
		toggle = gui.addToggle(name,state,xPos,yPos,bHeight,bHeight);
		toggle.setLabel(label);
		toggle.setGroup(group);
		//toggle.captionLabel().setControlFont(font);
		toggle.captionLabel().setControlFontSize(8);
		//toggle.valueLabel().setColor(labColor);
	}
	
	public void removeController(String label, String group){
		if(gui.controller(label) != null ){
		
			if(gui.controller(label).parent().name() == group){
				gui.controller(label).remove();
			}
		//gui.group(group).controller(label).remove();
		}
	}
	
	public void removeGroup(String label){
		
		if(gui.group(label) != null ){
		
		gui.group(label).remove();
		}
	}
	
	public void setListener(String name, guiListener listener){
		gui.controller(name).addListener(listener);
	}
	
	public void setId(String name, int i) {
		// TODO Auto-generated method stub
		gui.controller(name).setId(i);
	}
   
	public void setValue(String name, String value){
		gui.controller(name).setValueLabel(value);
	}
	
	
}
