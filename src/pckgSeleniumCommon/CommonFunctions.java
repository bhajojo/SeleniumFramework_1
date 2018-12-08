package pckgSeleniumCommon;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import pckgSeleniumFramework.Parameters;
//import pckgSeleniumFramework.Parameters;
import pckgSeleniumFramework.Reporting;


/* *** List of Common Function ***

 * fGuiLaunchEnvironemnt
 * fGuiIsDisplayed
 * fGuiIsDisplayed (OverRidden)
 * fGuiIsEnabled
 * fGuiClick
 * fGuiClick (Overloaded)
 * fValidatePageDisplayed
 * fGuiSelectionOptionFromList
 * fGuiSelectionOptionFromList (OverRidden)
 * fGuiSetValueEditBox
 * fGuiSetValueEditBox (OverRidden)
 * fGuiValidateSelectedOptionFromList
 * fGuiVerifyApplicationURL
 * fCommonGetWebElement
 * fCommonGetWebElementsList
 * fCommonHighlightElement
 * fCommonSetAttribute
 * fGuiSync(sTime)
 * fCommonDeleteFolder
 * fCommonStringCompare
 * fValidateDynamicPageDisplayed()
 * fCommonGetParentElement()
 * fCommonGetParentElement (Overloaded)
 * fCommonGetChildWebElementsList
 * fGuiValidateEditBoxValue()
 * getKeyByValue()
 * getObject
 * getMultipleObjects
 * getMultipleChildObjects
 * checkObjectExistance
 * fCommonSwitchToWindow(int iIndex)
 */
public class CommonFunctions {
	private WebDriver driver;
	private String driverType;
	private Reporting Reporter;
	
	public CommonFunctions(WebDriver webDriver,String DT, Reporting Report){
		driver = webDriver;
		driverType = DT;
		Reporter = Report;
	}
	
	//*****************************************************************************************
    //*	Name		    : fGuiLaunchEnvironemnt
    //*	Description	    : Launch env for any URL
    //*	Author		    : Dina dodin
    //*	Input Params	: strUrl - url need to open 
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiLaunchEnvironemnt(String strUrl){
		try {
			//delete cookies
			driver.manage().deleteAllCookies();
			if(driverType.contains("IOS")){
				try{
					((JavascriptExecutor) driver).executeScript("window.sessionStorage.clear();");
					//((JavascriptExecutor) driver).executeScript("window.localStorage.clear();");
				}catch(Exception e){
					//Do nothing
				}							
			}
	    	
			driver.get(strUrl);
			Reporter.fnWriteToHtmlOutput("Launch: "+strUrl, strUrl+" should be launched", strUrl+ " is launched successfully", "Pass");
			return true;
			
		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("Launch: "+strUrl, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}       
    }	
	
    //*****************************************************************************************
    //*	Name		    : fGuiIsDisplayed
    //*	Description	    : Check if the obect is displayed or not
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement - The webelement for which we need to check
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsDisplayed(String webElmtProp, String objName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,objName);		
    		
    		//Check if the Webelement is enabled or displayed    		
    		boolean bIsDisplayed = false;	        
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed) && (intCount <=3)){
	        	try {
	        		bIsDisplayed = webElement.isDisplayed();
	        		if(!bIsDisplayed){
	        			fGuiSync(5000);
	        			webElement = getObject(webElmtProp,objName);
	        		}
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,objName);
	    	    }catch (WebDriverException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    }catch (NullPointerException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    	if(webElement == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed)){	        	
	        	Reporter.fnWriteToHtmlOutput(objName, objName+ " should be displayed", objName+ " is not Displayed", "Fail");
	            return false;
	        }	        
	        Reporter.fnWriteToHtmlOutput(objName, objName+ " should be displayed", objName+ " is Displayed successfully", "Done");
	        return true;
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception :" + e, "Fail");			
			return false;		
    	}    	
    }	
    
  //*****************************************************************************************
    //*	Name		    : fGuiIsDisplayed(OverRidden)
    //*	Description	    : Check if the object is displayed(Function is overridden to take screenPrint)
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement - The webelement for which we need to check
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsDisplayed(String webElmtProp, String objName, boolean takeScreenPrint){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,objName);  		
    		
    		//Check if the WebElement is displayed    		
    		boolean bIsDisplayed = false;	        
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed) && (intCount <=3)){
	        	try {
	        		bIsDisplayed = webElement.isDisplayed();
	        		if(!bIsDisplayed){
	        			fGuiSync(5000);
	        			webElement = getObject(webElmtProp,objName);
	        		}
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,objName);
	    	    }catch (WebDriverException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    }catch (NullPointerException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    	if(webElement == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed)){	        	
	        	Reporter.fnWriteToHtmlOutput(objName, objName+ " should be displayed", objName+ " is not Displayed", "Fail");
	            return false;
	        }	        
	        Reporter.fnWriteToHtmlOutput(objName, objName+ " should be displayed", objName+ " is Displayed successfully", "Pass");
	        return true;
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception :" + e, "Fail");			
			return false;		
    	}    	
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiIsEnabled
    //*	Description	    : Check if the obect is enabled or not
    //*	Author		    : Anil Agarwal
    //*	Input Params	: WebElement - The webelement for which we need to check
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiIsEnabled(String webElmtProp, String strObjName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,strObjName);
    		
	        //Get the title of the current window
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsEnabled) && (intCount <=3)){
	        	try {	        				
	        		bIsEnabled = webElement.isEnabled();
	        		break;
        	    }catch (StaleElementReferenceException e){  	       
	    	       webElement = getObject(webElmtProp,strObjName);	    	       
	    	    }catch (NullPointerException e){	 
	    	       break;
	    	    }	    	    	
				intCount++;			
	        }	
	        //Validate
	        if (!(bIsEnabled)){
	        	//Reporter.fnWriteToHtmlOutput(strObjName, strObjName+ " should be enabled", strObjName+ " is not enabled", "Fail");
	            return false;
	        }
	        //Reporter.fnWriteToHtmlOutput(strObjName, strObjName+ " should be enabled", strObjName+ " is enable", "Done");
	        return true;
	        
    	}catch (Exception e) {
			//Reporter.fnWriteToHtmlOutput(strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}  
    }	
    
    //*****************************************************************************************
    //*	Name		    : fGuiClick
    //*	Description	    : Click on the webelement
    //*	Author		    : Anil Agarwal
    //*	Input Params	: WebElement Properties - The webelement for which we need to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClick(String webElmtProp, String strObjName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,strObjName);
    		
    		if(webElement == null){
    			Reporter.fnWriteToHtmlOutput("fGuiClick: " + strObjName ,"Object should be found", "Object was not found. Null value returned", "Fail");
    			return false;
    		}
	        //Click on the WebElement    		
	        int intCount = 1;        
	        while (intCount<=3){
	        	try {	 
	        		//fCommonHighlightElement(webElement);	        		
	        		if(driverType.contains("ANDROID") || driverType.contains("IOS")){
	        			webElement.getLocation();
		        		webElement.click();
		        	}else{
	        			((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
		        	}
	        		break;
	        		
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
	    	    }catch (WebDriverException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
		        }catch (NullPointerException e){	    
	    	    	break;
	    	    }catch (Exception e){ 
			        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
			        
	            	if(checkObjectExistance("linktext:=No Thanks")){
	    				intCount--;
	        			driver.findElement(By.linkText("No Thanks")).click();
	        			Reporter.fnWriteToHtmlOutput("getObject", "Chat Pop Up Exist", "Chat Pop up Handled", "Done");
	        			webElement = getObject(webElmtProp,strObjName);
	            	}	
	    	    }
	    	    if(intCount==3){
	        		Reporter.fnWriteToHtmlOutput(strObjName,"Click :"+strObjName, strObjName+" is not clicked", "Fail");
	    			return false;
	        	}
	    	    intCount++;
	        }	        
	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, strObjName+ " should be clicked", strObjName+ " is clicked successfully", "Done");
	        return true;
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}         
    }	
    
    //*****************************************************************************************
    //*	Name		    : fGuiClick (Overloaded)
    //*	Description	    : Click on the webelement
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement - The webelement for which we need to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClick(WebElement webElement, String strObjName){   	 		
        //Click on the WebElement    		
        int intCount = 1;        
        while (intCount<=4){
        	try {
        		//webElement = fCommonGetParentElement(webElement, "", 4);
        		//fCommonHighlightElement(webElement);
        		if(driverType.contains("FIREFOX")){
        			chekcAlert("decline");
    			}
        		if(driverType.contains("ANDROID") || driverType.contains("IOS")){
        			webElement.getLocation();
        			//((JavascriptExecutor) driver).executeScript("return arguments[0].click();", webElement);
	        		webElement.click();
	        	}else{
	        		//webElement.click(); //09-April-2013 - Dina - when object not visible the click is not working
        			((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
	        	}
        		break;
	        }catch (Exception e){    
	        	if(intCount==4){
	    	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
	    			return false;
	        	}
    	    }  	    
    	    intCount++;
        }	        
    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, strObjName+ " should be clicked", strObjName+ " is clicked successfully", "Done");
        return true;    	       
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiClick (Overloaded)
    //*	Description	    : Click on the webelement for a New Driver passed as an argument
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement - The webelement for which we need to click, new Web Driver
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClick(WebDriver newDriver,String webElmtProp, String strObjName){   	 		
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(newDriver,webElmtProp,strObjName);
    		    		
    		if(webElement == null){
    			Reporter.fnWriteToHtmlOutput("fGuiClick: " + strObjName ,"Object should be found", "Object was not found. Null value returned", "Fail");
    			return false;
    		}
	        //Click on the WebElement    		
	        int intCount = 1;        
	        while (intCount<=3){
	        	try {	 
	        		//fCommonHighlightElement(webElement);
	        		if(driverType.contains("FIREFOX")){
	        			chekcAlert("decline");
        			}
	        		if(driverType.contains("ANDROID") || driverType.contains("IOS")){
	        			webElement.getLocation();
		        		webElement.click();
		        	}else{
	        			((JavascriptExecutor) newDriver).executeScript("return arguments[0].click()", webElement);
		        	}
	        		break;
	        		
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
	    	    }catch (WebDriverException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
		        }catch (NullPointerException e){	    
	    	    	break;
	    	    }    
	    	    if(intCount==3){
	        		Reporter.fnWriteToHtmlOutput(strObjName,"Click :"+strObjName, strObjName+" is not clicked", "Fail");
	    			return false;
	        	}
	    	    intCount++;
	        }	        
	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, strObjName+ " should be clicked", strObjName+ " is clicked successfully", "Done");
	        return true;
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}         
    }	
    //*****************************************************************************************
    //*	Name		    : fGuiClickToHandlePopup
    //*	Description	    : Click on the Webelement and Handle System Popup
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement Properties - The webelement for which we need to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClickToHandlePopup(String webElmtProp, String strObjName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,strObjName);
    		
    		if(webElement == null){
    			Reporter.fnWriteToHtmlOutput("fGuiClick: " + strObjName ,"Object should be found", "Object was not found. Null value returned", "Fail");
    			return false;
    		}
	        //Click on the WebElement    		
	        int intCount = 1;      
	        while (intCount<=3){
	        	try {	        		
        			webElement.getLocation();
	        		webElement.click();
	        		
	        		//Check system popup
	        		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	        		WebElement sysPopup;
	        		try{
	        			sysPopup = driver.findElement(By.linkText("No, thanks"));
	        		}catch(Exception e){
	        			sysPopup = null;
	        		}
	        		
		        	if(sysPopup != null){
		        		sysPopup.click();
		        		
		        		webElement = getObject(webElmtProp,strObjName);
		        		if(webElement != null){
		        			webElement.click();
		        		}
		        	}
		        	driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	        		break;
	        		
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
	    	    }catch (WebDriverException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
		        }catch (NullPointerException e){    
	    	    	break;
	    	    }    
	    	    if(intCount==3){
	        		Reporter.fnWriteToHtmlOutput(strObjName,"Click :"+strObjName, strObjName+" is not clicked", "Fail");
	    			return false;
	        	}
	    	    intCount++;
	        }	        
	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, strObjName+ " should be clicked", strObjName+ " is clicked successfully", "Done");
	        return true;
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}         
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiClickToHandlePopup (Overloaded)
    //*	Description	    : Click on the Webelement and Handle System Popup
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement Properties - The webelement for which we need to click
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiClickToHandlePopup(WebElement webObject, String strObjName){
    	try{
    		//Check WebElement
    		if(webObject == null){
    			Reporter.fnWriteToHtmlOutput("fGuiClick: " + strObjName ,"Object should be found", "Object was not found. Null value returned", "Fail");
    			return false;
    		}
	        //Click on the WebElement    		
	        int intCount = 1;      
	        while (intCount<=3){
	        	try {	        		
	        		webObject.getLocation();
	        		webObject.click();
	        		
	        		//Check system popup
	        		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
	        		WebElement sysPopup;
	        		try{
	        			sysPopup = driver.findElement(By.linkText("No, thanks"));
	        		}catch(Exception e){
	        			sysPopup = null;
	        		}
	        		
		        	if(sysPopup != null){
		        		sysPopup.click();		        		
		        		try{
		        			if(webObject.isDisplayed()){
			        			webObject.click();
			        		}
		        		}catch(Exception e){
		        			//Do nothing
		        		}		        		
		        	}
		        	driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	        		break;
	        		
        	    }catch (Exception e){
        	    	if(intCount==2){
    	    	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
    	    			return false;
    	        	}
	    	    }
	    	    intCount++;
	        }	        
	    	Reporter.fnWriteToHtmlOutput("Click: "+strObjName, strObjName+ " should be clicked", strObjName+ " is clicked successfully", "Done");
	        return true;
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput("Click: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}         
    }	
    //*****************************************************************************************
    //*	Name		    : fGuiClickToHandlePopup (Overloaded)
    //*	Description	    : Just waits for the popuop if not present procceds
    //*	Author		    : Meena
    //*	Input Params	: 
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
	  public boolean fGuiClickToHandlePopup(){     
			
			//Check system popup
			driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
			WebElement sysPopup;
			try{
				sysPopup = driver.findElement(By.linkText("No, thanks"));
			}catch(Exception e){
				sysPopup = null;
			}
			
	    	if(sysPopup != null){
	    		sysPopup.click();
	    	} 		
	    	  
	    	driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	    	Reporter.fnWriteToHtmlOutput("Click: " , " should be clicked",  " is clicked successfully", "Done");
	        return true;       
	}
	  
	  
    //*****************************************************************************************
    //*	Name		    : fValidatePageDisplayed
    //*	Description	    : Function to validate the current window title
    //*	Author		    : Anil Agarwal
    //*	Input Params	: 
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fValidatePageDisplayed(String webElmtProp, String strTitle){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp, strTitle);
    		
			//Get the title of the current window
			String strActualTitle = "";			
			int intCount = 1;	
			while (!(strActualTitle.equalsIgnoreCase(strTitle))){
				try {					
					strActualTitle = webElement.getText();
					//Validate if the page is displayed
					if (!(strActualTitle.equalsIgnoreCase(strTitle))){
						if(intCount == 5){
							Reporter.fnWriteToHtmlOutput("Validate Page Title", "Title should be " + strTitle, "Title is " + strActualTitle, "Fail");
							return false;
						}
						webElement = getObject(webElmtProp, strTitle);
					}
				}catch (StaleElementReferenceException e){  	       
	    	       webElement = getObject(webElmtProp, strTitle);	    	       
	    	    }catch (InvalidElementStateException e){	
	    	    	webElement = getObject(webElmtProp, strTitle);
	    	    }catch (NullPointerException e){	 
	    	       //break;
	    	    	webElement = getObject(webElmtProp, strTitle);
	    	    	if(intCount == 5){
						Reporter.fnWriteToHtmlOutput("Validate Page Title", "Title should be " + strTitle, "Title is " + strActualTitle, "Fail");
						return false;
					}
	    	    }	    	    
				intCount++;
			}	
			Reporter.fnWriteToHtmlOutput("Validate Page Title", "Title should be " + strTitle, "Title is " + strActualTitle, "Done");
			return true;
			
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput(strTitle, "Exception occurred","Exception :" + e, "Fail");
			return false;
		} 
	}
    
    //*****************************************************************************************
    //*	Name		    : fGuiSelectionOptionFromList
    //*	Description	    : Function to select the specified option from the list
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSelectionOptionFromList(String webElmtProp, String strObjName, String strText){
    	try{
    		//Get WebElement
    		WebElement objList = getObject(webElmtProp,strObjName); 
	    	//strText=objList.findElements(By.tagName("option")).get(1).getText()
	    	//Set Select Element and value	    	    	 		
	        int intCount = 1;
	        Select select = null;
	        while (intCount<=3){
	        	try {
	        		if(driverType.contains("ANDROID")){
	        			List <WebElement> listOptions = objList.findElements(By.tagName("option"));
	        			
	        			for(int i = 0; i< listOptions.size();i++){
	        				if(listOptions.get(i).getText().equalsIgnoreCase(strText)){	        					
	        					((JavascriptExecutor)driver).executeScript("return arguments[0].selected=true", listOptions.get(i));
	        				    break;
	        				}
	        			}	        			
	        		}else{
	        			select = new Select(objList);
		        		if(driverType.contains("FIREFOX")){
		        			chekcAlert("decline");
	        			}
		        		select.selectByVisibleText(strText);
	        		}	        		
	        		break;
        	    }catch (StaleElementReferenceException e){	
        	    	objList = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
	    	    	objList = getObject(webElmtProp,strObjName);
	    	    }catch (WebDriverException e){	
	    	    	objList = getObject(webElmtProp,strObjName);
	        	}catch (NullPointerException e){	    
	    	    	break;
	    	    }
	    	    intCount++;
	    	    //Validate if the value is selected successfully
        		/*if(select.getFirstSelectedOption().getAttribute("value").equals(strText)){
        			break;
    	        }else{
    	        	if(intCount==3){
    	        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Select: " + strText, strText +" is not selected" , "Fail");
    	    			return false;
    	        	}
    	        }*/	
	        }	        
        	Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Select: " + strText, strText +" is selected successfully" , "Done");
        	return true;
    		
		} catch (Exception e){
			Reporter.fnWriteToHtmlOutput("Weblist :"+strObjName, "Exception occurred","Exception: " + e, "Fail");				
			return false;
		}
    } 
    
    //*****************************************************************************************
	//*	Name		    : fGuiSelectionOptionFromList
	//*	Description	    : Function to handle VOIP setup
	//*	Author		    : Abhishek Pandey
    //* Parameters		: sSelectionMethod (ByValue,ByVisibleText). If a value other than these, ByVisibleText is used
	//* Dictionary		: 
	//*	Date Modified	: 30-Aug-12
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
    public boolean fGuiSelectionOptionFromList(String webElmtProp, String strObjName, String strText, String sSelectionMethod){
    	try{
    		//Get WebElement
    		WebElement objList = getObject(webElmtProp,strObjName);
    		
	    	//Set Select Element and value	    	    	 		
	        int intCount = 1;
	        Select select;
	        while (intCount<=3){
	        	try {
	        		if(driverType.contains("FIREFOX")){
	        			chekcAlert("decline");;
        			}
	        		if(driverType.contains("ANDROID")){
	        			if(sSelectionMethod.equals("ByClick")){
	        				List <WebElement> listOptions = objList.findElements(By.tagName("option"));		        			
		        			for(int i = 0; i< listOptions.size();i++){
		        				if(listOptions.get(i).getText().equalsIgnoreCase(strText)){
		        					listOptions.get(i).click();
		        					break;
		        				}
		        			}
	        			}	        				        			
	        		}else{
	        			select = new Select(objList);
	        			if(sSelectionMethod.equals("ByValue")){
		        			select.selectByValue(strText);
	        			}else{
	        				select.selectByVisibleText(strText);
	    				}
	        		}	        		
	        		break;
        	    }catch (StaleElementReferenceException e){	
        	    	objList = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
	    	    	objList = getObject(webElmtProp,strObjName);
	    	    }catch (WebDriverException e){	
	    	    	objList = getObject(webElmtProp,strObjName);
	        	}catch (NullPointerException e){	    
	    	    	break;
	    	    }
	    	    intCount++;
	    	    //Validate if the value is selected successfully
        		/*if(select.getFirstSelectedOption().getAttribute("value").equals(strText)){
        			break;
    	        }else{
    	        	if(intCount==3){
    	        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Select: " + strText, strText +" is not selected" , "Fail");
    	    			return false;
    	        	}
    	        }*/	
	        }	        
        	Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Select: " + strText, strText +" is selected successfully" , "Done");
        	return true;
    		
		} catch (Exception e){
			Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName, "Exception occurred","Exception: " + e, "Fail");				
			return false;
		}
    } 
     
    
    //*****************************************************************************************
    //*	Name		    : fGuiSetValueEditBox
    //*	Description	    : Set value in Edit box
    //*	Author		    : Dina Dodin
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSetValueEditBox(String webElmtProp, String strObjName, String strValue){
    	try{
    		//Get WebElement
    		WebElement objWebEdit = getObject(webElmtProp,strObjName);
    		
	    	//Checks if input parameter is Null
	    	if (strValue == null){
	    		strValue = "";
	    	}
	    	//Checks if object is enabled
	    	if (fGuiIsEnabled(webElmtProp,strObjName) == false){
	    		Reporter.fnWriteToHtmlOutput("EditBox: "+strObjName,strObjName + " should be Enabled", strObjName + " is not Enabled" , "Fail");
	        	return false;
	    	}
	    	//Set value to the Editbox	    	    	 		
	        int intCount = 1;        
	        while (intCount<=5){
	        	try {
	        		if(driverType.contains("FIREFOX")){
	        			chekcAlert("decline");
        			}
	        		objWebEdit.clear();
	    	    	objWebEdit.sendKeys(strValue); 
	        	}catch (StaleElementReferenceException e){	
        	    	objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
        	    	objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (NullPointerException e){	    
	    	    	break;
	    	    }catch (Exception e)
	    		{	
	        		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
	        
	            	if(checkObjectExistance("linktext:=No, thanks")){
	    				intCount--;
	        			driver.findElement(By.linkText("No, thanks")).click();
	        			Reporter.fnWriteToHtmlOutput("getObject", "Survey Pop Up Exist", "Survey Pop up Handled", "Done");
	            	}	
	    		}
	    	    //Validate if the value is selected successfully
        		if(objWebEdit.getAttribute("value").equals(strValue)){
        			break;
    	        }else{
    	        	Thread.sleep(2000);
    	        	objWebEdit = getObject(webElmtProp,strObjName);

    	        	if(intCount==5){
    	        		Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName,"Set: " + strValue, strValue +" is not set" , "Fail");
    	    			return false;
    	        	}
    	        }
        		intCount++;
	        }
	    	Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, strValue+" should be set in "+strObjName, strValue+ " is set in "+strObjName+" successfully", "Done");    	
	    	return true;
	    	
    	}catch (Exception e)
		{	
    		
    		Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
        	
		}
    	
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiSetValueEditBox (OverRidden)
    //*	Description	    : Set value in Edit box (Function overloaded if entered value should not be verified)
    //*	Author		    : Anup Agarwal
    //*	Input Params	: String webElmtProp, String strObjName, String strValue, String DontVerify
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSetValueEditBox(String webElmtProp, String strObjName, String strValue, String strSkipVerify){
    	try{
    		//Get WebElement
    		WebElement objWebEdit = getObject(webElmtProp,strObjName);
    		
	    	//Checks if input parameter is Null
	    	if (strValue == null){
	    		strValue = "";
	    	}
	    	//Checks if object is enabled
	    	if (fGuiIsEnabled(webElmtProp,strObjName) == false){
	    		Reporter.fnWriteToHtmlOutput("EditBox: "+strObjName,strObjName + " should be Enabled", strObjName + " is not Enabled" , "Fail");
	        	return false;
	    	}
	    	//Set value to the Editbox	    	    	 		
	        int intCount = 1;        
	        while (intCount<=3){
	        	try {	        		
	        		objWebEdit.clear();
	    	    	objWebEdit.sendKeys(strValue);
	    	    	break;
	        	}catch (StaleElementReferenceException e){	
        	    	objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
        	    	objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (NullPointerException e){	    
	    	    	break;
	    	    }   	    
        		intCount++;
	        }
	    	Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, strValue+" should be set in "+strObjName, strValue+ " is set in "+strObjName+" successfully", "Done");    	
	    	return true;
	    	
    	}catch (Exception e)		{
    		Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiJavascriptClick
    //*	Description	    : Select the Sub Menu Item from Global Navigation
    //*	Author		    : Shraddha
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiJavascriptClick(String webElmtProp, String strObjName){
    	try{
    		fGuiSync(5000);
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,strObjName);
    		if(webElement == null){
    			Reporter.fnWriteToHtmlOutput("fGuiJavascriptClick", "Object not Found","Object: " + strObjName + " doesn't exist", "Fail");
    			return false;  
    		}
	        //Click on the WebElement    		
	        int intCount = 1;        
	        while (intCount<=3){
	        	try {	        					
	        		//fCommonHighlightElement(webElement);
	        		if(driverType.contains("FIREFOX")){
	        			chekcAlert("decline");
        			}
	        		((JavascriptExecutor) driver).executeScript("return arguments[0].click()", webElement);
	        		break;	        		
        	    }catch (StaleElementReferenceException e){
	    	        webElement = getObject(webElmtProp,strObjName);
	    	        intCount++;
	    	    }catch (InvalidElementStateException e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
	    	        intCount++;
	    	    }catch (NullPointerException e){	    
	    	    	break;
	    	    }
	    	    catch (Exception e){	
	    	    	webElement = getObject(webElmtProp,strObjName);
	    	        intCount++;
	    	    }
	    	    if(intCount==3){
	        		Reporter.fnWriteToHtmlOutput(strObjName,"Click: "+strObjName, strObjName+" is not clicked", "Fail");
	    			return false;
	        	}
	        }
			Reporter.fnWriteToHtmlOutput(strObjName, strObjName+" should be clicked",strObjName+" is clicked successfully", "Done");
			return true;
			
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput("Click "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;    		
    	}
    }
    
    //*****************************************************************************************
    //*	Name		    : fGuiValidateSelectedOptionFromList
    //*	Description	    : Function to validate the selected option in the list
    //*	Author		    : Anup Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiValidateSelectedOptionFromList(String webElmtProp, String strObjName, String expectedValue){
    	try{
    		//Get WebElement
    		WebElement objList = getObject(webElmtProp,strObjName);
    		
        	//Set Select Element
    		if(driverType.contains("FIREFOX")){chekcAlert("decline");}
        	Select select = new Select(objList);
        	//Get the selected value from the drop down
        	String actualValue = select.getFirstSelectedOption().getAttribute("value");
        	System.out.println("actual valueeeee "+select.getFirstSelectedOption().getText());
        	System.out.println("actual value "+actualValue);
        	System.out.println("expected value "+expectedValue);
        	
        	//Check if actual selected value is equal to expected value
        	if(actualValue.equals(expectedValue)){
        		
        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
            	return true;        		
        	}else{
        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Selected value: " + actualValue, "Expected value: " + expectedValue , "Fail");
            	return false;
        	}        	
    		
		} catch (Exception e){
			Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}
    } 
    
    
    
    
    //*****************************************************************************************
    //*	Name		    : fGuiValidateSelectedOptionFromList
    //*	Description	    : Function to validate the selected option in the list
    //*	Author		    : Anup Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiValidateSelectedOption(String webElmtProp, String strObjName, String expectedValue){
    	try{
    		//Get WebElement
    		WebElement objList = getObject(webElmtProp,strObjName);
    		
        	//Set Select Element
    		if(driverType.contains("FIREFOX")){chekcAlert("decline");}
        	Select select = new Select(objList);
        	//Get the selected value from the drop down
        	String actualValue = select.getFirstSelectedOption().getText();
        	System.out.println("actual valueeeee "+select.getFirstSelectedOption().getText());
        	System.out.println("actual value "+actualValue);
        	System.out.println("expected value "+expectedValue);
        	
        	//Check if actual selected value is equal to expected value
        	if(actualValue.equals(expectedValue)){
        		
        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Selected value: " + actualValue, "Expected value: " + expectedValue , "Done");
            	return true;        		
        	}else{
        		Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName,"Selected value: " + actualValue, "Expected value: " + expectedValue , "Fail");
            	return false;
        	}        	
    		
		} catch (Exception e){
			Reporter.fnWriteToHtmlOutput("Weblist: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}
    } 
    
 // *****************************************************************************************
	// * Name : fGuiVerifyApplicationURL
	// * Description : verify that the current URL is the same as the original
	// * Author : Dina Dodin
	// * Update Date : 10-July-2012
	// * Input Params : 
	// * Return Values : No return value
	// *********************************************************************************
	
	public Boolean fGuiVerifyApplicationURL(String strURL, String bFlag){	
		String strCurrentURL = "";
		
		//taking time to next page to be loaded, so in order it will take the actual current URL need to wait 
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		try{
			strCurrentURL = driver.getCurrentUrl();
			if(strCurrentURL.equals("")){
				Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Curent URL parameter strExistingURL should have a value", "Existing URL parameter is empty", "Fail");
				return false;
			}			
			System.out.println(strCurrentURL);
		}
		catch (Exception e){
			Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Problem occured", "There is no option to fetch the current URL from the Browser", "Fail");
			System.out.println(e);
			return false;
		}
			
		//Validates the existing URL
	    if(!strURL.equals("")){
		   strURL = strURL.replace("http://","").replace("https://","");
		   String arrURL[] = strURL.split("/");
		   if(bFlag.equalsIgnoreCase("true")) {	
				if(strCurrentURL.contains(arrURL[0])== false){
					Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Current URL should be the same as expected URL", "Current URL ie " + strCurrentURL + " is different from Expected URL ie " + strURL, "Fail");
					return false;					
				}
		   }
		   else if(bFlag.equalsIgnoreCase("false")){	
			   if(strCurrentURL.contains(arrURL[0])== true){
					Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Current URL should be different from the origional URL", "Current URL ie " + strCurrentURL + " contains original URL ie " + strURL + " which is not expected", "Fail");
					return false;					
				}
		   }
		   else{
			   Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Valid value should be passed for bFlag parameter", "Invalid value " + bFlag + " passed for the bFLag parameter", "Fail");
			   return false;			   
		   }
	    }
	    else{	
	    	Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Search URL parameter should not be null", "Search URL parameter is null", "Fail");
			return false;
	    }		
	    Reporter.fnWriteToHtmlOutput("fGuiVerifyApplicationURL","Existing URL should be verified successfully", "Existing URL was verified successfully", "Done");	
		return true;
	}
	
	//*****************************************************************************************
    //*	Name		    : fCommonGetWebElement
    //*	Description	    : Check if the webelement is properly identified
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement properties, WebElement name
    //*	Return Values	: WebElement Object
    //*****************************************************************************************
    public WebElement fCommonGetWebElement(String webElmtProp, String objName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,objName);  		
    		
    		//Check if the Webelement is enabled or displayed    		
    		boolean bIsDisplayed = false;
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed || bIsEnabled) && (intCount <=3)){
	        	try {
	        		bIsDisplayed = webElement.isDisplayed();					
					bIsEnabled = webElement.isEnabled();
        	    }catch (StaleElementReferenceException e){	
	    	        webElement = getObject(webElmtProp,objName);
	    	    }catch (WebDriverException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    }catch (NullPointerException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    	if(webElement == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed || bIsEnabled)){	        	
	            return null;
	        }	        
	        return webElement;
    	}catch(Exception e){
    		//Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception: " + e, "Fail");			
			return null;  		
    	}
    }	
    
    //*****************************************************************************************
    //*	Name		    : fCommonGetWebElementsList
    //*	Description	    : Check if the webelements are identified properly 
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement properties, WebElement name
    //*	Return Values	: WebElement List
    //*****************************************************************************************
    public List<WebElement> fCommonGetWebElementsList(String webElmtProp, String objName){
    	try{
    		//Get WebElement
    		List<WebElement> webElements = getMultipleObjects(webElmtProp,objName);
    		
    		//Check if the Webelement is enabled or displayed    		
	        boolean bIsDisplayed = false;
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed || bIsEnabled) && (intCount <=3)){
	        	try {	        					
	        		if(webElements.size() != 0){
	        			bIsDisplayed = webElements.get(0).isDisplayed();
						bIsEnabled = webElements.get(0).isEnabled();
	        		}					
        	    }catch (StaleElementReferenceException e){	
        	    	webElements = getMultipleObjects(webElmtProp,objName);
	    	    }catch (WebDriverException e){	    
	    	    	webElements = getMultipleObjects(webElmtProp,objName);
	    	    }catch (NullPointerException e){	    
	    	    	webElements = getMultipleObjects(webElmtProp,objName);
	    	    	if(webElements == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed || bIsEnabled)){
	        	//Reporter.fnWriteToHtmlOutput(objName, objName+ " should be displayed", objName+ " is not displayed", "Fail");
	            return null;
	        }	        
	        return webElements;
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception: " + e, "Fail");			
			return null;    		
    	}
    }	
    
    //*****************************************************************************************
    //*	Name		    : fCommonHighlightElement
    //*	Description	    : Highlights the webElement 
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement Object
    //*	Return Values	: Void
    //*****************************************************************************************   
    public void fCommonHighlightElement(WebElement element) {	
  	  final int wait = 100;	
  	  String originalStyle= "";	
  	  try {	
  		originalStyle = element.getAttribute("style");	
  		fCommonSetAttribute(element, "style", "color: yellow; border: 5px solid yellow; background-color: black;");	
  	    Thread.sleep(wait);	
  	    fCommonSetAttribute(element, "style", "color: black; border: 5px solid black; background-color: yellow;");	
  	    Thread.sleep(wait);	
  	    fCommonSetAttribute(element, "style", "color: yellow; border: 5px solid yellow; background-color: black;");	
  	    Thread.sleep(wait);	
  	    fCommonSetAttribute(element, "style", "color: black; border: 5px solid black; background-color: yellow;");	
  	    Thread.sleep(wait);
  	    fCommonSetAttribute(element, "style", originalStyle);
  	  } catch (Throwable threw) {
  		  System.out.println("Exception in fCommonHighlightElement: "+threw);
  		  threw.printStackTrace();
  		  fCommonSetAttribute(element, "style", originalStyle);
  	  }
  	
 	}
  	
    //*****************************************************************************************
    //*	Name		    : fCommonSetAttribute
    //*	Description	    : Set a Value to an Attribute of a WebElement 
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement Object, Attribute Name, Attribute Value
    //*	Return Values	: Void
    //*****************************************************************************************   
    public void fCommonSetAttribute(WebElement element, String attributeName, String attributeValue) {
		try{
			((JavascriptExecutor) driver).executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, attributeValue);
		}catch(Throwable thr){
			System.out.println("Exception in fCommonSetAttribute: "+thr);
			thr.printStackTrace();
		}		
  	}

    
  //*****************************************************************************************
    //*	Name		    : fGuiSync
    //*	Description	    : waiting the specified time
    //*	Author		    : Dina Dodin
    //*	Input Params	: sTime - time to wait
    //*	Return Values	: Void
    //*****************************************************************************************  
    public void fGuiSync(long sTime)
    {
    	try {
			Thread.sleep(sTime);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
    }
    
    //*****************************************************************************************
	//*	Name		    : fCommonDeleteFolder
	//*	Description	    : Deletes a folder after deleting all its sub-folders and files
	//*	Author		    : Abhishek Pandey
	//* Parameters		: FolderPath
	//* Dictionary		: 
	//*	Date Modified	: 2-Sep-12
	//*	Return Values	: None
	//*****************************************************************************************
	public static void fCommonDeleteFolder(File FolderPath) {
		if (FolderPath.isDirectory()) {
		    String[] arrChildNodes = FolderPath.list();
		    for (int i=0; i<arrChildNodes.length; i++) {
		    	fCommonDeleteFolder(new File(FolderPath, arrChildNodes[i]));
		    }
		}
		FolderPath.delete();
	}
	
	//*****************************************************************************************
	//* Name : fCommonStringCompare
	//* Description : Checks for spelling
	//* Author : Naveena Basetty
	//* Input Params : webElmtProp,objName, objName
	//* Return Values : Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fCommonStringCompare(String webElmtProp, String objName, String Exptval)
	{
		try {
			WebElement webElement = getObject(webElmtProp, objName);
			String TextwebElement = webElement.getText();
			if (TextwebElement.equals(Exptval))
			{
				Reporter.fnWriteToHtmlOutput("fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check done successfully", "Done");
			return true;
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("fCommonStringCompare", "Spell check performed on " +Exptval ,"Spell check wasn't done successfully", "Fail");
				return false;
			}
				

		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("fCommonStringCompare", "Incorrect spelling" ,objName, "Fail");
			System.out.print("Exception :" + e);
			return false;
		}
		
	}
	
	
	/**
	 * ********************************************************************************************
	 * @Description       : Checks wheather Expected value is present in text of given webelement or not
	 * @Author            : PRAVEENA KALAPALA
	 * @Date              : May 16, 2013
	 * @param webElmtProp : Source Element on which operation need to perform.
	 * @param objName     : Logical Name of object 
	 * @param Exptval     : Expected Value
	 * @return            :  
	 *********************************************************************************************
	 */
	public boolean fCommonStringMatches(String webElmtProp, String objName, String Exptval)
	{
		
	
		try {
			WebElement webElement = getObject(webElmtProp, objName);
			String TextwebElement = webElement.getText().replace("\n", " ").replace("\r", " ");
			if (TextwebElement.equals(Exptval))
			{
				Reporter.fnWriteToHtmlOutput("fCommonStringMatches", Exptval ,TextwebElement +" Matches "+Exptval , "Pass");
			return true;
			}
			else
			{
				Reporter.fnWriteToHtmlOutput("fCommonStringMatches", Exptval ,TextwebElement +" does not match "+Exptval, "Fail");
				return false;
			}				

		} catch (Exception e) {
			Reporter.fnWriteToHtmlOutput("fCommonStringMatches", " Does not Match " ,objName, "Fail");
			System.out.print("Exception :" + e);
			return false;
		}		
	}
	//*****************************************************************************************
    //*	Name		    : fValidateDynamicPageDisplayed
    //*	Description	    : Function to validate dynamic web page title
    //*	Author		    : Anup Agarwal
    //*	Input Params	: 
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fValidateDynamicPageDisplayed(String webElmtProp, String objName, String DifferentDynamicPageTitles){
    	try{
    		//Get WebElement
    		WebElement objPageTitle = fCommonGetWebElement(webElmtProp, objName); 		
    		
    		//Get Actual Page title
    		String ActualPageTitle = null;  		
    		int intCount = 1;        
 	        while (intCount<=3){
 	        	try { 	        		
 	        		ActualPageTitle = objPageTitle.getText().trim();
 	        		
 	        		if(DifferentDynamicPageTitles.contains(ActualPageTitle) && !ActualPageTitle.equals(""))
 	        		{
 	        			Reporter.fnWriteToHtmlOutput(ActualPageTitle+" page", "Page should be displayed", ActualPageTitle +" page is displayed", "Pass");
 	        			break;
 	        		}
 	        		fGuiSync(5000);
 	        		objPageTitle = fCommonGetWebElement(webElmtProp, objName);  
 	        		
         	    }catch (StaleElementReferenceException e){
         	    	fGuiSync(3000);
         	    	objPageTitle = getObject(webElmtProp,objName);
 	    	    }catch (WebDriverException e){
 	    	    	fGuiSync(3000);
 	    	    	objPageTitle = getObject(webElmtProp,objName);
 	    	    }catch (NullPointerException e){	    
 	    	    	objPageTitle = getObject(webElmtProp,objName);
 	    	    }   	    
 				intCount++;
 				if(intCount == 4){
 					Reporter.fnWriteToHtmlOutput(objName+" page", "Page should be displayed", "Page is not displayed, the actual page displayed is: " + ActualPageTitle, "Fail");
 	    			return false;
 				}
 	        }    		
    		//Validate page title
    		/*if(DifferentDynamicPageTitles.contains(ActualPageTitle)){
    			Reporter.fnWriteToHtmlOutput(ActualPageTitle+" page", "Page should be displayed", ActualPageTitle +" page is displayed", "Pass");
    		}else{
    			Reporter.fnWriteToHtmlOutput(objName+" page3", objName+" page should be displayed", "Page is not displayed", "Fail");
    			return false;
    		}*/		
			return true;
			
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception :" + e, "Fail");
			return false;
		} 
	}
    
    //*****************************************************************************************
    //*	Name		    : fCommonGetParentElement
    //*	Description	    : Function to get Parent Web Element
    //*	Author		    : Anup Agarwal
    //*	Input Params	: 
    //*	Return Values	: Web Element - Depending on the success
    //*****************************************************************************************
    public WebElement fCommonGetParentElement(WebElement childElement, String objName, int ParentLevel){
    	try{
    		//Define Parent xpath
    		String strParentXpath = "..";
    		if(ParentLevel > 1){
    			for(int count=2; count<=ParentLevel; count++){
    				strParentXpath = strParentXpath+"/..";
	    		}	
    		}
    		//Get Parent WebElement
    		WebElement parentElement = childElement.findElement(By.xpath(strParentXpath));   		
			return parentElement;			
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception :" + e, "Fail");
			return null;
		} 
	}
    
  //*****************************************************************************************
    //*	Name		    : fCommonGetParentElement
    //*	Description	    : Function to get Parent Web Element - Overloaded function
    //*	Author		    : Dina
    //*	Input Params	: 
    //*	Return Values	: Web Element - Depending on the success
    //*****************************************************************************************
    public WebElement fCommonGetParentElement(String childElement, String objName, int ParentLevel){
    	try{
    		//get the object 
    		WebElement childObject = fCommonGetWebElement(childElement, objName);
    		if(childObject==null)
    		{
    			return null;
    		}
    		//Define Parent xpath
    		String strParentXpath = "..";
    		if(ParentLevel > 1){
    			for(int count=2; count<=ParentLevel; count++){
    				strParentXpath = strParentXpath+"/..";
	    		}	
    		}
    		//Get Parent WebElement
    		WebElement parentElement = childObject.findElement(By.xpath(strParentXpath));   		
			return parentElement;			
    	}catch (Exception e) {			
			Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception :" + e, "Fail");
			return null;
		} 
	}
    
    //*****************************************************************************************
    //*	Name		    : fCommonGetChildWebElementsList
    //*	Description	    : Get child webelements under parent webelement
    //*	Author		    : Anup Agarwal
    //*	Input Params	: Parent WebElement, WebElement properties, WebElement name
    //*	Return Values	: ChildWebElements List
    //*****************************************************************************************
    public List<WebElement> fCommonGetChildWebElementsList(WebElement Parent, String childElmtProp, String childName){
    	try{
    		//Get WebElement    		
    		List<WebElement> childWebElements = getMultipleChildObjects(Parent, childElmtProp, childName);
    		    		
	        //Check if the WebElement is enabled or displayed    		
	        boolean bIsDisplayed = false;
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed || bIsEnabled) && (intCount <=3)){
	        	try {	        					
	        		if(childWebElements.size() != 0){
	        			bIsDisplayed = childWebElements.get(0).isDisplayed();
						bIsEnabled = childWebElements.get(0).isEnabled();
	        		}					
        	    }catch (StaleElementReferenceException e){	
        	    	childWebElements = getMultipleChildObjects(Parent, childElmtProp, childName);
	    	    }catch (WebDriverException e){	    
	    	    	childWebElements = getMultipleChildObjects(Parent, childElmtProp, childName);
	    	    }catch (NullPointerException e){	    
	    	    	childWebElements = getMultipleChildObjects(Parent, childElmtProp, childName);
	    	    	if(childWebElements == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed || bIsEnabled)){	        	
	            return null;
	        }	        
	        return childWebElements;
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput(childName, "Exception occurred","Exception: " + e, "Fail");			
			return null;    		
    	}
    }
    
    //*****************************************************************************************
    //*	Name		    : fCommonGetSingleChildWebElement
    //*	Description	    : Get 1 child webelement under parent webelement - use this function once you know for sure there is 1 child only of what you look for
    //*	Author		    : Dina 
    //*	Input Params	: Parent WebElement, WebElement properties, WebElement name
    //*	Return Values	: ChildWebElements List
    //*****************************************************************************************
    public WebElement fCommonGetSingleChildWebElement(WebElement Parent, String childElmtProp, String childName){
    	try{
    		//Get WebElement    		
    		WebElement childWebElements = getSingleChildObject(Parent, childElmtProp, childName);
    		    		
	        //Check if the WebElement is enabled or displayed    		
	        boolean bIsDisplayed = false;
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed || bIsEnabled) && (intCount <=3)){
	        	try {	        					
	        		if(childWebElements != null){
	        			bIsDisplayed = childWebElements.isDisplayed();
						bIsEnabled = childWebElements.isEnabled();
	        		}					
        	    }catch (StaleElementReferenceException e){	
        	    	childWebElements = getSingleChildObject(Parent, childElmtProp, childName);
	    	    }catch (WebDriverException e){	    
	    	    	childWebElements = getSingleChildObject(Parent, childElmtProp, childName);
	    	    }catch (NullPointerException e){	    
	    	    	childWebElements = getSingleChildObject(Parent, childElmtProp, childName);
	    	    	if(childWebElements == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed || bIsEnabled)){	        	
	            return null;
	        }	        
	        return childWebElements;
    	}catch(Exception e){
    		Reporter.fnWriteToHtmlOutput(childName, "Exception occurred","Exception: " + e, "Fail");			
			return null;    		
    	}
    }
	//*****************************************************************************************
	//*	Name		    : fGuiValidateEditBoxValue
	//*	Description	    : Validates value in Editbox
	//*	Author		    : Naveena Basetty
	//*	Input Params	: None
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
	public boolean fGuiValidateEditBoxValue(String webElmtProp, String strObjName, String strValue){	
		try{
			//Get WebElement
			WebElement Editbox = getObject(webElmtProp,strObjName);
			String actualValue = Editbox.getAttribute("value").toLowerCase();

			//validate webedit value expected value
			if(actualValue.equals(strValue.toLowerCase())){
				Reporter.fnWriteToHtmlOutput("WebEdit: "+strObjName,"value: " + actualValue, "Expected value: " + strValue , "Done");
				return true;        		
			}else{
				System.out.println("actualValue is :"+actualValue);
				System.out.println("ExpectedVal is :"+strValue);
				Reporter.fnWriteToHtmlOutput("WebEdit: "+strObjName,"value: " + actualValue, "Expected value: " + strValue , "Fail");
				return false;
			}        	

		} catch (Exception e){
			Reporter.fnWriteToHtmlOutput("WebEdit: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}
	}	

	 
	
	//*****************************************************************************************
	//*	Name		    : getKeyByValue
	//*	Description	    : get key of given value
	//*	Author		    : Dina dodin
	//*	Input Params	: map - hash map name to search in
	//* 				  value - value in hash map to find its key
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
	public <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (value.equals(entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	//*****************************************************************************************
	//*	Name		    : chekcAlert
	//*	Description	    : check if Alert popup is comming and click on OK (accept) button
	//*	Author		    : Dina dodin
	//*	Input Params	: none
	//*	Return Values	: none
	//*****************************************************************************************
	
	public void chekcAlert(String sAction)
	{
		try{
			WebDriverWait wait = new WebDriverWait(driver,1);
			wait.until(ExpectedConditions.alertIsPresent());
			
			fGuiSync(100);
			
			Alert alert = driver.switchTo().alert();
			//((JavascriptExecutor) driver).executeScript("focus()");
			if(sAction.equalsIgnoreCase("accept"))
			{
				alert.accept();
				fGuiSync(100);
			}
			else if(sAction.equalsIgnoreCase("decline"))
			{
				alert.dismiss();
				//fGuiSync(100);
			}
			
		}
		catch (Exception e){
			//exceptions handling
		}
	}
	
	//*****************************************************************************************
    //*	Name		    : fCommonVerifyObjectNotExist
    //*	Description	    : return true if object not exist, and false if object exist
    //*	Author		    : Dina dodin
    //*	Input Params	: WebElement properties, WebElement name
    //*	Return Values	: WebElement Object
    //*****************************************************************************************
    public boolean fCommonVerifyObjectNotExist(String webElmtProp, String objName){
    	try{
    		//Get WebElement
    		WebElement webElement = getObject(webElmtProp,objName);  		
    		
    		//Check if the Webelement is enabled or displayed    		
    		boolean bIsDisplayed = false;
	        boolean bIsEnabled = false;
	        
	        int intCount = 1;        
	        while (!(bIsDisplayed || bIsEnabled) && (intCount <=1)){
	        	try {
	        		bIsDisplayed = webElement.isDisplayed();					
					bIsEnabled = webElement.isEnabled();
        	    }catch (StaleElementReferenceException e){	
	    	        webElement = getObject(webElmtProp,objName);
	    	    }catch (WebDriverException e){	    
	    	    	webElement = getObject(webElmtProp,objName);
	    	    }catch (NullPointerException e){	    
	    	    	//webElement = getObject(webElmtProp,objName);
	    	    	if(webElement == null){
	    	    		break;
	    	    	}
	    	    }	    	    
				intCount++;			
	        }
	
	        //Validate if the element is displayed
	        if (!(bIsDisplayed || bIsEnabled)){	        	
	            return true;
	        }	        
	        return false;
    	}catch(Exception e){
    		//Reporter.fnWriteToHtmlOutput(objName, "Exception occurred","Exception: " + e, "Fail");			
			return true;  		
    	}
    }
	//*****************************************************************************************
	//*	Name		    : fGuiBrowserBackButton
	//*	Description	    :navigate back to previous page
	//*	Author		    : Naveena Basetty 
	//*	Input Params	: 
	//*	Return Values	: Boolean - Depending on the success
	//*****************************************************************************************
	public void fGuiBrowserBackButton() {			
			driver.navigate().back();
			Reporter.fnWriteToHtmlOutput("fGuiBrowserBackButton", "Navigate back to previous page using back button","Navigated to previous page" , "Done");
		    
	} 
	
	//*****************************************************************************************
    //*	Name		    : getObject(String objDesc, String objName)
    //*	Description	    : Method to get object
    //*	Author		    : Anup Agarwal
    //*	Input Params	: String
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	        
    public WebElement getObject(String objDesc, String objName){
    	//Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value
        String FindBy, val;
        if(arrFindByValues.length==2){
        	FindBy = arrFindByValues[0];
            val = arrFindByValues[1];        	
        }
        else{
        	Reporter.fnWriteToHtmlOutput("getObject", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
        	return null;
        }
        int intcount = 1;	            
        while (intcount <= 2) {	            	
            try{
                //Handle all FindBy cases
            	String strElement = FindBy.toLowerCase();
            	if (strElement.equalsIgnoreCase("linktext")){
            		return driver.findElement(By.linkText(val));
            	}
            	else if (strElement.equalsIgnoreCase("xpath")){
            		return driver.findElement(By.xpath(val));
            	}
            	else if (strElement.equalsIgnoreCase("name")){
            		return driver.findElement(By.name(val));
            	}
            	else if (strElement.equalsIgnoreCase("id")){
            		return driver.findElement(By.id(val));
            	}
            	else if (strElement.equalsIgnoreCase("classname")){
            		return driver.findElement(By.className(val));
            	}
            	else if (strElement.equalsIgnoreCase("cssselector")){
            		return driver.findElement(By.cssSelector(val));
            	}	
	           	else if (strElement.equalsIgnoreCase("partialLinkText")){
	            		return driver.findElement(By.partialLinkText(val));		            		
            	}
            	else{
            		Reporter.fnWriteToHtmlOutput("Object Identification", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
            		return null;
            	}		            	
            }
            catch(Exception e){
            	driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            	if(checkObjectExistance("linktext:=No, thanks")){
            		intcount--;
        			driver.findElement(By.linkText("No, thanks")).click();
        			Reporter.fnWriteToHtmlOutput("getObject", "Survey Pop Up Exist", "Survey Pop up Handled", "Done");
        		}else if (checkObjectExistance("classname:=fsrInvite")){
            		intcount--;
            		driver.findElement(By.className("fsrInvite")).sendKeys(Keys.ESCAPE);
            		Reporter.fnWriteToHtmlOutput("getObject", "Survey Pop Up Exist", "Survey Pop up Handled by send keys 'ESC'", "Done");
            	}else if(checkObjectExistance("xpath:=//a[@jsvalues='href:reloadUrl']")){
        			intcount--;
            		driver.navigate().refresh();
        			Reporter.fnWriteToHtmlOutput("getObject", "Page Not available exist", "Page was refreshed", "Done"); 
        		}else if(checkObjectExistance("linktext:=No Thanks")){
        			intcount--;
        			driver.findElement(By.linkText("No Thanks")).click();
        			Reporter.fnWriteToHtmlOutput("getObject", "Chat Pop Up Exist", "Chat Pop up Handled", "Done");
            	}
            	if (intcount == 1){
            		//Reporter.fnWriteToHtmlOutput("Object :"+objName, objName+" should be present", objName+" is not present", "Fail");
            		//Reporter.fnWriteToHtmlOutput("Object :"+objName, "Exception occurred","Exception :" + e.toString(), "Fail");		            		
            		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
            		return null;
            	}		            	
            	intcount = intcount + 1;
            	driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
            	//Select browser in focus
        		((JavascriptExecutor) driver).executeScript("focus()");
            }		            
        }
		return null;	           
    }
    
    //*****************************************************************************************
    //*	Name		    : getObject
    //*	Description	    : Method to get object (OverRidden Function to get object using passed WebDriver)
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebDriver newDriver, String objDesc, String objName
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	        
    public WebElement getObject(WebDriver newDriver, String objDesc, String objName){
    	//Check WebDriver
    	if(newDriver == null){
    		Reporter.fnWriteToHtmlOutput("New WebDriver", "WebDriver should not be null","WebDriver is null", "Fail");
        	return null;
    	}
    	
    	//Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value
        String FindBy, val;
        if(arrFindByValues.length==2){
        	FindBy = arrFindByValues[0];
            val = arrFindByValues[1];        	
        }else{
        	Reporter.fnWriteToHtmlOutput("getObject", "objDesc should be valid","objDesc is not valid: " + objDesc, "Fail");
        	return null;
        }
    	String strElement = FindBy.toLowerCase();
    	WebElement objElement=null;
    	boolean bIsDisplayed = false;
        boolean bIsEnabled = false;
        int intCount = 1;
        
        while (intCount<=3){
	        try{
	    		//Get WebElement
	        	if (strElement.equalsIgnoreCase("linktext")){
	        		objElement = newDriver.findElement(By.linkText(val));
	        	}
	        	else if (strElement.equalsIgnoreCase("xpath")){
	        		objElement = newDriver.findElement(By.xpath(val));
	        	}
	        	else if (strElement.equalsIgnoreCase("name")){
	        		objElement = newDriver.findElement(By.name(val));
	        	}
	        	else if (strElement.equalsIgnoreCase("id")){
	        		objElement = newDriver.findElement(By.id(val));
	        	}
	        	else if (strElement.equalsIgnoreCase("classname")){
	        		objElement = newDriver.findElement(By.className(val));
	        	}
	        	else if (strElement.equalsIgnoreCase("cssselector")){
	        		objElement = newDriver.findElement(By.cssSelector(val));
	        	}	
	           	else if (strElement.equalsIgnoreCase("partialLinkText")){
	           		objElement = newDriver.findElement(By.partialLinkText(val));		            		
	        	}
	        	else{
	        		Reporter.fnWriteToHtmlOutput("Object Identification", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
	        		return null;
	        	} 		
	    		
	    		//Check if the Webelement is enabled or displayed    		
	    		if(objElement != null){
	    			bIsDisplayed = objElement.isDisplayed();					
					bIsEnabled = objElement.isEnabled();
					
					//Validate if the element is displayed/enabled
			        if (bIsDisplayed || bIsEnabled){	        	
			            break;
			        }
	    		}	        	    
				intCount++;			
	        }catch (Exception e){
	        	newDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
            	if(newDriver.findElement(By.linkText("No, thanks")) != null){
            		newDriver.findElement(By.linkText("No, thanks")).click();
        			Reporter.fnWriteToHtmlOutput("Survey Popup", "Survey Pop Up Exist", "Survey Pop up Handled", "Done");
        		}
            	newDriver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
	        	intCount++;
    	    }
        }
	    return objElement;    		           
    }  
    
    
    //*****************************************************************************************
    //*	Name		    : getMultipleObjects(String objDesc, String objName)
    //*	Description	    : Method to get multiple objects having same property
    //*	Author		    : Anup Agarwal
    //*	Input Params	: String
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	        
    public List<WebElement> getMultipleObjects(String objDesc, String objName){
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        int intcount = 1;	            
        while (intcount <= 2){	            	
            try{
                //Handle all FindBy cases
            	String strElement = FindBy.toLowerCase();
            	if (strElement.equalsIgnoreCase("linktext")){
            		return driver.findElements(By.linkText(val));
            	}
            	else if (strElement.equalsIgnoreCase("xpath")){
            		return driver.findElements(By.xpath(val));
            	}
            	else if (strElement.equalsIgnoreCase("name")){
            		return driver.findElements(By.name(val));
            	}
            	else if (strElement.equalsIgnoreCase("id")){
            		return driver.findElements(By.id(val));
            	}
            	else if (strElement.equalsIgnoreCase("classname")){
            		return driver.findElements(By.className(val));
            	}
            	else if (strElement.equalsIgnoreCase("cssselector")){
            		return driver.findElements(By.cssSelector(val));
            	}
            	else if (strElement.equalsIgnoreCase("tagname")){
            		return driver.findElements(By.tagName(val));
            	}
            	else{
            		Reporter.fnWriteToHtmlOutput("Object Identification", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
            		return null;
            	}		            	
            }
            catch(Exception e){		            	
            	if (intcount == 2){
            		Reporter.fnWriteToHtmlOutput("Object :"+objName, objName+" should be present", objName+" is not present", "Fail");
            		Reporter.fnWriteToHtmlOutput("Object :"+objName, "Exception occurred","Exception :" + e.toString(), "Fail");		            		
	            	return null;
            	}		            	
            	intcount = intcount + 1;
            	//Select browser in focus
        		((JavascriptExecutor) driver).executeScript("focus()");
            }		            
        }
		return null;	           
    }
    
    //*****************************************************************************************
    //*	Name		    : getSingleChildObject
    //*	Description	    : Method to get single child object under a parent object
    //*	Author		    : Dina
    //*	Input Params	: WebElement parent, String objDesc, String objName
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	        
    public WebElement getSingleChildObject(WebElement parent, String objDesc, String objName){
    	//Verify parent element
    	if(parent == null){
    		return null;
    	}
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];	            
        val = val.replaceAll("//", "./");
        
        int intcount = 1;	            
        while (intcount <= 2){	            	
            try{
                //Handle all FindBy cases
            	String strElement = FindBy.toLowerCase();
            	if (strElement.equalsIgnoreCase("linktext")){
            		return parent.findElement(By.linkText(val));
            	}
            	else if (strElement.equalsIgnoreCase("xpath")){
            		return parent.findElement(By.xpath(val));
            	}
            	else if (strElement.equalsIgnoreCase("name")){
            		return parent.findElement(By.name(val));
            	}
            	else if (strElement.equalsIgnoreCase("id")){
            		return parent.findElement(By.id(val));
            	}
            	else if (strElement.equalsIgnoreCase("classname")){
            		return parent.findElement(By.className(val));
            	}
            	else if (strElement.equalsIgnoreCase("cssselector")){
            		return parent.findElement(By.cssSelector(val));
            	}
            	else if (strElement.equalsIgnoreCase("tagname")){
            		return parent.findElement(By.tagName(val));
            	}
            	else{
            		Reporter.fnWriteToHtmlOutput("getSingleChildObject", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
            		return null;
            	}		            	
            }
            catch(Exception e){		            	
            	if (intcount == 2){
            		//Reporter.fnWriteToHtmlOutput("Object :"+objName, objName+" is not identified", "Exception :" + e.toString(), "Fail");
            		return null;
            	}		            	
            	intcount = intcount + 1;
            	//Select browser in focus
        		((JavascriptExecutor) driver).executeScript("focus()");
            }		            
        }
		return null;	           
    }
    
    
    //*****************************************************************************************
    //*	Name		    : getMultipleChildObjects
    //*	Description	    : Method to get multiple child objects under a parent object
    //*	Author		    : Anup Agarwal
    //*	Input Params	: WebElement parent, String objDesc, String objName
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************	        
    public List<WebElement> getMultipleChildObjects(WebElement parent, String objDesc, String objName){
    	//Verify parent element
    	if(parent == null){
    		return null;
    	}
        //Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];	            
        val = val.replaceAll("//", "./");
        
        int intcount = 1;	            
        while (intcount <= 2){	            	
            try{
                //Handle all FindBy cases
            	String strElement = FindBy.toLowerCase();
            	if (strElement.equalsIgnoreCase("linktext")){
            		return parent.findElements(By.linkText(val));
            	}
            	else if (strElement.equalsIgnoreCase("xpath")){
            		return parent.findElements(By.xpath(val));
            	}
            	else if (strElement.equalsIgnoreCase("name")){
            		return parent.findElements(By.name(val));
            	}
            	else if (strElement.equalsIgnoreCase("id")){
            		return parent.findElements(By.id(val));
            	}
            	else if (strElement.equalsIgnoreCase("classname")){
            		return parent.findElements(By.className(val));
            	}
            	else if (strElement.equalsIgnoreCase("cssselector")){
            		return parent.findElements(By.cssSelector(val));
            	}
            	else if (strElement.equalsIgnoreCase("tagname")){
            		return parent.findElements(By.tagName(val));
            	}
            	else{
            		Reporter.fnWriteToHtmlOutput("Object Identification", "Property name :" + FindBy,"Property name specified for object " + objName + " is invalid", "Fail");
            		return null;
            	}		            	
            }
            catch(Exception e){		            	
            	if (intcount == 2){
            		//Reporter.fnWriteToHtmlOutput("Object :"+objName, objName+" is not identified", "Exception :" + e.toString(), "Fail");
            		return null;
            	}		            	
            	intcount = intcount + 1;
            	//Select browser in focus
        		((JavascriptExecutor) driver).executeScript("focus()");
            }		            
        }
		return null;	           
    }
    
    //*****************************************************************************************
    //*	Name		    : checkObjectExistance
    //*	Description	    : Set value in Edit box
    //*	Author		    : Shraddha Girme
    //*	Input Params	: None
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean checkObjectExistance(String objDesc){        
        boolean bStatus = false;
        
    	//Delimiters
        String[] delimiters = new String[] {":="};
        String[] arrFindByValues = objDesc.split(delimiters[0]);
        //Get Findby and Value 
        String FindBy = arrFindByValues[0];
        String val = arrFindByValues[1];
        int intcount = 1;	            
        while (intcount <= 3){
        	//Set the implicit time-out for the driver
            driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
            try{
                //Handle all FindBy cases
            	String strElement = FindBy.toLowerCase();
            	if (strElement.equalsIgnoreCase("linktext")){
            		bStatus = driver.findElement(By.linkText(val)).isEnabled();
            	}
            	else if (strElement.equalsIgnoreCase("xpath")){
            		bStatus = driver.findElement(By.xpath(val)).isEnabled();
            	}
            	else if (strElement.equalsIgnoreCase("name")){
            		bStatus = driver.findElement(By.name(val)).isEnabled();
            	}
            	else if (strElement.equalsIgnoreCase("id")){
            		bStatus = driver.findElement(By.id(val)).isEnabled();
            	}
            	else if (strElement.equalsIgnoreCase("classname")){
            		bStatus = driver.findElement(By.className(val)).isEnabled();
            	}
            	else if(strElement.equalsIgnoreCase("cssselector")){
            		bStatus = driver.findElement(By.cssSelector(val)).isEnabled();
            	}
            	driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
            	return bStatus;
            	
            }catch(Exception e){
            	if (intcount == 3){
                    driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
            		return false;
            	}	
            	intcount = intcount + 1;
            	//Select browser in focus
        		((JavascriptExecutor) driver).executeScript("focus()");
            }
        }
        driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);
        return false;
    }

	public void fCommonStringContains(String lblUVerseRedAddress,String string, String string2) {	
		
	}	  

    //*****************************************************************************************
    //*	Name		    : ExplicitwaitForWebElement
    //*	Description	    : waiting the specified Webelement
    //*	Author		    : Meena
    //*	Input Params	:driver and WebElement
    //*	Return Values	: Void
    //*****************************************************************************************  
    public void ExplicitwaitForWebElement(long sTime,String  locator){
    	  String[] delimiters = new String[] {":="};
          String[] arrFindByValues = locator.split(delimiters[0]);
          String val = arrFindByValues[1];
        
    	//locator=locator.replace("XPath:=", "");   	
    
    	WebDriverWait wait = new WebDriverWait(driver, sTime);
    	if(arrFindByValues[0].equals("Xpath")){
    		WebElement elmt = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(val))));
    	}
    	else if(arrFindByValues[0].equalsIgnoreCase("cssselector")){
    		WebElement elmt = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(val))));
    	}
    	else if(arrFindByValues[0].equalsIgnoreCase("id")){
    		WebElement elmt = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(val))));
    	}
    }
    
	//*****************************************************************************************
	//*	Name		    : fCommonSwitchToWindow
	//*	Description	    : switch to window based on index
	//*	Author		    : Abhishek Pandey
	//*	Input Params	: iIndex
	//*	Date Created	: 25-Jun-13
	//*****************************************************************************************
	public void fCommonSwitchToWindow(int iIndex){
		Set<String> collWindowHandles = driver.getWindowHandles();
		if(collWindowHandles.size() < iIndex+1){
			Reporter.fnWriteToHtmlOutput("fCommonSwitchToWindow", "Specified index out of range.", "Available Windows: " + collWindowHandles.size() + "Specified Index: " + iIndex , "Fail");
		}
		else{
			Iterator<String> iter = collWindowHandles.iterator();
			for(int i=0;i<collWindowHandles.size();i++){    			
				String sWindowHandle = iter.next();
				if(i==iIndex){driver.switchTo().window(sWindowHandle);}
			}
		}
	}

	public boolean fGuiSetValueEditBox(WebElement objWebEdit,
			String strObjName, String strValue) {
		// TODO Auto-generated method stub
		return false;
	}
	
	//*****************************************************************************************
    //*	Name		    : fGuiSetValueEditBox (OverRidden)
    //*	Description	    : Set value in Edit box (Function overloaded if entered value should not be verified)
    //*	Author		    : Anup Agarwal
    //*	Input Params	: String webElmtProp, String strObjName, String strValue, String DontVerify
    //*	Return Values	: Boolean - Depending on the success
    //*****************************************************************************************
    public boolean fGuiSetValueEditBox(WebElement objWebEdit, String strObjName, String strValue, String strSkipVerify){
    	try{
    		//Get WebElement
    		if(objWebEdit==null){return false;}
    		
	    	//Checks if input parameter is Null
	    	if (strValue == null){
	    		strValue = "";
	    	}
	    	//Checks if object is enabled
	    	
	    	//Set value to the Editbox	    	    	 		
	        int intCount = 1;        
	        while (intCount<=3){
	        	try {	        		
	        		objWebEdit.clear();
	    	    	objWebEdit.sendKeys(strValue);
	    	    	break;
	        	}catch (StaleElementReferenceException e){	
        	    	//objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (InvalidElementStateException e){	
        	    	//objWebEdit = getObject(webElmtProp,strObjName);
	    	    }catch (NullPointerException e){	    
	    	    	break;
	    	    }   	    
        		intCount++;
	        }
	    	Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, strValue+" should be set in "+strObjName, strValue+ " is set in "+strObjName+" successfully", "Done");    	
	    	return true;
	    	
    	}catch (Exception e)		{
    		Reporter.fnWriteToHtmlOutput("Editbox: "+strObjName, "Exception occurred","Exception: " + e, "Fail");
			return false;
		}
    }

}


