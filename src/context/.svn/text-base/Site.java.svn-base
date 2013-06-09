package context;

import processing.core.PApplet;
import elements.*;

public class Site {
	
	PApplet p;
	Edge[] edges;
	SiteCell[][] cellTable;
	public Dimension dim, cellDim;
	public float cellWidth,cellLength;
	public int cellSize;
	public int cellRows,cellColumns;
	
	public Site(PApplet parent){
		p = parent;
		dim = new Dimension();
		cellDim = new Dimension();
		
	}
	
	public Site(PApplet parent, float w, float l, int cS){
		p = parent;
		dim = new Dimension(w,l,0);
		cellDim = new Dimension();
		cellSize = cS;
	}
	
	public void setDim(float w, float l, float h){
		cellWidth = w;
		cellLength = l;
		
		this.dim.w = w;
		this.dim.l = l;
		this.dim.h = h;
	}
	
	public void setCellDim(float w, float l, float h){
		this.cellDim.w = w;
		this.cellDim.l = l;
		this.cellDim.h = h;
	}
	
	public void buildCells(){
		cellRows = (int) ((int) this.dim.w / this.cellDim.w);
		cellColumns =(int) ((int) this.dim.l / this.cellDim.l);
		cellTable = new SiteCell[cellRows][cellColumns];
		
		
		for(int i=0;i<cellRows;i++){
			for (int j=0;j<cellColumns;j++){
			Point position = new Point(i*this.cellDim.w+cellDim.w/2,j*this.cellDim.l+cellDim.w/2,0);
			cellTable[i][j] = new SiteCell(p,this.cellDim,position);	
			
			
			}
		}
	}
	
	public boolean checkInBounds(int x, int y, int w, int l){
		//p.println("x:"+x+" y:"+y+" w:"+w+" l:"+l+" cC:"+cellColumns+" cR:"+cellRows);
		if(x+w<=cellRows && y+l<=cellColumns){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkOpenCellSet(int x, int y, int w, int l ){
		boolean setPass = true;
		//p.println("x:"+x+" y:"+y+" w:"+w+" l:"+l);
		for(int i = x; i<x+w;i++){
			for(int j = y;j<y+l;j++){
			//	p.println("checkOpenCell"+checkOpenCell(i,j));
				if (!checkOpenCell(i,j)){
					setPass = false;
					//SiteCell f = this.getCell(i, j);
					//f.setColor(0);
				//	p.println("setPass1"+setPass);
					return setPass;
					//break;
				}
			}
		}
		//p.println("setPass2"+setPass);
		return setPass;
	}
	
	public boolean checkOpenCell(int x, int y){
		
		SiteCell checkCell = getCell(x, y); 
		if(checkCell != null){
			if(checkCell.full){
				return false;	
			}else{
				return true;
			}
		}else{
			return false;
		}
	}
	
	public SiteCell getCell(int x, int y){
		//p.println(cellTable.length);
		if(x<cellRows && y<cellColumns){
			return cellTable[x][y];
		}else{
			return null;
		}
			
	}
	
	public int getXindexOfCell(SiteCell cell){
		for(int i=0;i<cellTable.length;i++){
			for(int j=0;j<cellTable[i].length;j++){
				if (cellTable[i][j] == cell){
					return i;
				}
			}
		}
		return -1;
	}
	
	public int getYindexOfCell(SiteCell cell){
		for(int i=0;i<cellTable.length;i++){
			for(int j=0;j<cellTable[i].length;j++){
				if (cellTable[i][j] == cell){
					return j;
				}
			}
		}
		return -1;
	}
	
	public void saveCellState(){
		
	}
	
	public void draw2d(float scale, float offset){
		for(int i=0;i<cellRows;i++){
			for (int j=0;j<cellColumns;j++){
				cellTable[i][j].draw2d(scale, offset);
				cellTable[i][j].pos.draw2d(p, false, scale);
			}
		}
	}
	
	public void draw3d(float scale, float offset){

		for(int i=0;i<cellRows;i++){
			for (int j=0;j<cellColumns;j++){
				cellTable[i][j].draw2d(scale, offset);
				cellTable[i][j].pos.draw3d(p, true, scale);
			}
		
		
		}
		
	}
	
}
