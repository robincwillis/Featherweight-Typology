package database;

import java.io.File;

import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;

import jxl.common.Logger;
import jxl.read.biff.BiffException;
import jxl.*;
@SuppressWarnings("rawtypes")
public class Read {
	
	private static Logger logger = Logger.getLogger(Read.class);
	
	private File dataPath;
	private File[] fileList;
	//private Workbook[] workbookList;
	private ArrayList workbookList;
	private ArrayList workbookTitleList;
	private WorkbookSettings wbSettings;
	
	public Read(String input){
				
	    logger.setSuppressWarnings(Boolean.getBoolean("jxl.nowarnings"));
	   // logger.info("Input file:  " + input);    
		
		dataPath = new File(input);
		workbookList = new ArrayList(); 
		
		wbSettings = new WorkbookSettings();
		wbSettings.setIgnoreBlanks(true);
	}
	
	public ArrayList read() throws IOException, BiffException{
		
	    logger.info("Reading...");
	    for(int i = 0; i< fileList.length; i++){	
	    	
	    	workbookList.add(Workbook.getWorkbook(fileList[i], wbSettings));
	    }
	    //System.out.println("woot:read success");
	    return workbookList;
	}
	
	public ArrayList getTitles(){
		
		workbookTitleList = new ArrayList();
		
		for (int i = 0; i < fileList.length;i++){
			String fileName = fileList[i].getName();
			String[] splitName = fileName.split("\\.");
			workbookTitleList.add(splitName[0]);
			
		}
		//System.out.println("woot:getTitles success");
		return workbookTitleList;
	}

//	public int getWorksheetCount(Workbook w2){
//		return w2.getNumberOfSheets();
//	}
	
	public void getFiles(){
		
		FileFilter fileFilter = new FileFilter(){
			public boolean accept(File file){
				
				if( file.isFile() && !file.isHidden()){
					return true;
				}else{
					return false;
				}
			}
		};
		fileList = dataPath.listFiles(fileFilter);
		for(int i=0; i<fileList.length;i++){
			
		}
	}

	
}
