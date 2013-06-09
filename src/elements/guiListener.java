package elements;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import database.*;
import controlP5.*;

 public class guiListener implements ControlListener{

	  Object[] args;
	  private Method targetMethod;
	  private Object targetObject;
	  Method[] classMethods;
	  Class[] parameterTypes;
	  String valueType;
	  Object[] methodParams;
	  String stringValue = "";
	  float[] arrValue;
	  float floatValue;
	  String groupName;
	  Boolean groupOn;
	  
	public void setMethod(String methodName,String controlValue, Boolean passGroup, Object[] params, Object obj) {
		targetObject = obj;
		
		classMethods = obj.getClass().getDeclaredMethods();
	        
			for(int i = 0; i < classMethods.length; i++){
				//System.out.println(classMethods[i].getName());
	        	if(methodName == classMethods[i].getName()){
	        		targetMethod = classMethods[i];
	        	}
	        		
	        }
	        //methodName didn't hit
	        if(targetMethod == null){
	        	System.out.println("shit");
	        }else{
	        	
	        	parameterTypes = targetMethod.getParameterTypes();
	        	//System.out.println(parameterTypes.length);
	        	args = new Object[parameterTypes.length];
	        	methodParams = params;
	        	valueType = controlValue;
	        	groupOn = passGroup;
	        }
	        //targetMethod = obj.getClass().getMethod(methodName,argTypes);
	        
	}
	
//	public void setMethod(String methodName,String controlValue, Object[] params, Object parentClass){
//		setMethod(methodName,controlValue,false, params,  parentClass);
//	}
	
	
	public void setParam(boolean group, String eventValue, Object[] addParams){
		valueType = eventValue;
		
		//System.out.println(valueType);
	
		int skipCell = 0;
		  if(valueType == "String"){
				 args[0] = stringValue;
				 skipCell ++;
			  }
			  else if(valueType == "Float"){
				  args[0] = floatValue;
				  skipCell ++;
			  }
			  else if(valueType == "Array"){
				  args[0] = arrValue;
				  skipCell ++;
			  }else{
			  
			  }
			
		if(group){
			args[1] = groupName;
			skipCell ++;
		}
		for(int i=0; i<addParams.length;i++){
			args[i+skipCell] = addParams[i];
		}
	}
	
    public void printClassName(Object obj) {
        System.out.println("The class of " + obj +" is " + obj.getClass().getName());

    }
    
    
    
	public void controlEvent(ControlEvent theEvent) {
		 
		  //System.out.println(stringValue);
		  arrValue = theEvent.controller().arrayValue();
		  stringValue = theEvent.controller().label();
		  floatValue = theEvent.controller().value();
		 
		  ControllerInterface group = theEvent.controller().parent();
		  groupName = group.name();
		  
		  //System.out.println(groupOn+" : "+valueType+" : "+methodParams);
		  setParam(groupOn, valueType, methodParams);
	
		  //System.out.println(targetObject+" : "+ targetMethod +" : "+ args);
		  invokeMethod(targetObject, targetMethod, args);
		  
		  
		   
		   
		   }
	private void invokeMethod(final Object theObject,final Method theMethod,final Object[] theParam){
		try {
			if (theParam[0] == null) {
				theMethod.invoke(theObject, new Object[0]);
			} else {
				theMethod.invoke(theObject, theParam);
			}
		} catch (IllegalArgumentException e) {
			System.out.println("### ERROR @ guiListener.invokeMethod " + theMethod.toString() + "  "
			        + theMethod.getName() + " " + theParam.length + " " + e);
			/**
			 * @todo thrown when plugging a String method/field.
			 */
		} catch (IllegalAccessException e) {
			printMethodError(theMethod, e);
		} catch (InvocationTargetException e) {
			printMethodError(theMethod, e);
		} catch (NullPointerException e) {
			printMethodError(theMethod, e);
		}

	}
	
	private void printMethodError(
	        Method theMethod,
	        Exception theException) {
		System.out.println("ERROR. an error occured while forwarding a Controller value\n "
		        + "to a method in your program. please check your code for any \n"
		        + "possible errors that might occur in this method .\n "
		        + "e.g. check for casting errors, possible nullpointers, array overflows ... .\n" + "method: "
		        + theMethod.getName() + "\n" + "exception:  " + theException);
	}
}
