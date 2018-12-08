package pckgSeleniumFramework;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import pckgSeleniumFramework.Driver.HashMapNew;

public class Infra {

	private HashMapNew Dictionary;
	private HashMap <String, String> Environment = new HashMap<String, String>();
	private HashMap <String, String> objGlobalDictOriginal = new HashMap<String, String>();
	private String driverType;
	
	public Infra(String DT,HashMapNew Dict, HashMap <String, String> Env, HashMap <String, String> DictOriginal){
		Dictionary = Dict;
		Environment = Env;
		objGlobalDictOriginal = DictOriginal;
		driverType = DT;
	}

    //*****************************************************************************************
    //*	Name		    : fUpdateTestCaseRowSkip
    //*	Description	    : Function to execute an query
    //*	Author		    : Anil Agarwal
    //* Input Params	: int row - Row number to skip
    //*                   String xlsPath - Calendar path
    //*	Return Values	: None
    //***********************************************************************
    public void fUpdateTestCaseRowSkip(int row, Connection conn2)
    {   
    	DBActivities objDB2 = new DBActivities();
    	String calendar = Environment.get("CURRENTEXECUTIONDATASHEET");
    	conn2 = null;
    	conn2 = objDB2.fConnectToXLS(calendar);
    	Statement stmt= null;
    	
    	String sUpdateChar="X";
	    if(Dictionary.get("RESULT").equals("")==false){	    	
	    	if(Dictionary.get("RESULT").equals("Passed")){
	    		sUpdateChar="P";
    		}
	    	if(Dictionary.get("RESULT").equals("Failed")){
	    		sUpdateChar="F";
    		}
	    	if(Dictionary.get("RESULT").equals("No Run")){
	    		sUpdateChar="N";
    		}
	    	Dictionary.put("RESULT","");
    	}
    	
    	try{            
    		String sSQL = "Update [MAIN$] Set SKIP_" + driverType.toUpperCase().replace(" ", "")+ " = '"+ sUpdateChar + "' where ID = '" + row +"'";            
    		stmt = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
    		int i = stmt.executeUpdate(sSQL);
    		conn2.commit();  
    		conn2.close();
    		conn2 = null;
    		if (i !=1){
    			System.out.println("SKIP not set in the calendar");
    			stmt.close();            
    			Thread.currentThread().interrupt();
    		}            
    		//Closing the connections	
    		stmt.close();            
        }catch (SQLException eSQL) {
			System.err.println(eSQL);
			Thread.currentThread().interrupt();
		}catch (Exception e) {
			System.err.println(e);
			Thread.currentThread().interrupt();
		}
    }
   

    //*******************************************************************************************************
    //*	Name		    : fClearSkip
    //*	Description	    : Clears the SKIP column in the data table
    //*	Author		    : Dina Dodin
    //* Input Params	: sActionValue - The action to clear the skip field (A - Clear All, F - Clear Failed, 
    //*									 No Run, and X, S - Clear Skipped and X, ABS - Clear all but Skipped) 
    //*	Return Values	: None
    //********************************************************************************************************
    public void fClearSkip(String sActionValue)
    {
    	DBActivities objDB2 = new DBActivities();
    	String calendar = Environment.get("CURRENTEXECUTIONDATASHEET");
    	Connection conn2 = null;
    	conn2 = objDB2.fConnectToXLS(calendar);
    	Statement stmt= null;
    	String sSQL = "";
    	
    	try{  
    		if(sActionValue.equals("A"))
    		{
    			sSQL = "Update [MAIN$] Set SKIP_" + driverType.toUpperCase().replace(" ", "")+ " = ' '";            
    		}
    		
    		else if(sActionValue.equals("F"))
    		{
    			sSQL = "Update [MAIN$] Set SKIP_" + driverType.toUpperCase().replace(" ", "")+ " = ' ' where SKIP_" + driverType.toUpperCase().replace(" ", "") + " in ('F', 'f', 'N', 'n', 'X', 'x')";            
    		}
    		
    		else if(sActionValue.equals("S"))
    		{
    			sSQL = "Update [MAIN$] Set SKIP_" + driverType.toUpperCase().replace(" ", "")+ " = ' ' where SKIP_" + driverType.toUpperCase().replace(" ", "") + " in ('S', 's', 'X', 'x')";            
    		}
    		
    		else if(sActionValue.equals("ABS"))
    		{
    			sSQL = "Update [MAIN$] Set SKIP_" + driverType.toUpperCase().replace(" ", "")+ " = ' ' where SKIP_" + driverType.toUpperCase().replace(" ", "") + " not in ('S', 's')";            
    		}
    		else
    		{
    			System.out.println("Update SKIP Column in Data Table - The Action: " + sActionValue + " is not valid, the valid actions to be performed are A, F, S, or ABS");
    		}
    		
    		stmt = conn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
    		stmt.executeUpdate(sSQL);
    		
    		//Closing the connections	
    		conn2.commit();
    		conn2.close();
    		conn2 = null;
    		stmt.close();
    		
        }catch (SQLException eSQL) {
			System.err.println(eSQL);
			Thread.currentThread().interrupt();
		}catch (Exception e) {
			System.err.println(e);
			Thread.currentThread().interrupt();
		}
    }
   
    
     //*****************************************************************************************
    //*	Name		    : fGetReferenceData
    //*	Description	    : Fetch the data from keep refer sheet
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean 
    //*****************************************************************************************	
	public boolean fGetReferenceData()
	{
		//Declare few variables
		String key, value;
    	Map.Entry me;
		//Set the calendar xls
		String calendar = Environment.get("CURRENTEXECUTIONDATASHEET");
		//Get a set of the entries 
    	Set set = Dictionary.entrySet(); 
    	//Get an iterator 
    	Iterator i = set.iterator(); 
    	//Looping through the iterator
    	while(i.hasNext()) 
    	{ 	
    		try
    		{
	    		me = (Map.Entry)i.next();
	    		key = me.getKey().toString();
	    		value = me.getValue().toString();
	    		//If we need to get data from KEEP refer sheet
	    		if (value.startsWith("&",0))
	    		{
	    			value = value.substring(1);
	    			String sSQL = "SELECT KEY_VALUE FROM [KEEP_REFER_" + driverType.toUpperCase() + "$] WHERE KEY_NAME = '" + value + "'";
	    	        //Connect to Excel sheet
	    	        DBActivities objDB = new DBActivities();
	    	        Connection objConn2 = null;
	    	        objConn2 = objDB.fConnectToXLS(calendar);
	    	        //Handle null if connection fails
	    	        if (objConn2 == null)
	    	        {
	    	        	//Write the code to exit in case of any failures
	    	        	System.out.println("Could not connect to Excel " + calendar);
	    	        	return false;
	    	        }
	    	        //Open dataset
	    	        ResultSet RS = null;
	    	        //Function call to execute the query
	    	        RS = objDB.fExecuteQuery(sSQL, objConn2);
	    	        try{
		    	        //handle null if exeuction of query fails
		    	        if (RS != null)
		    	        {
		    	        	RS.next();
				        	value = RS.getString(1);
		    		        //Close datasets and connections
		    		        RS.close();
		    		        objConn2.close();
		    		        RS = null;
		    		        objConn2 = null;
		    	        }
		    	        else
		    		    {
		    	        	value = Environment.get(key); //Fetch the details from the environment hashmap
		    	        }
		    	        //Assign the value to Dictionary key
		    	        //Global.Dictionary.remove(key);
		    	        me.setValue(value);
		    	        //Global.Dictionary.put(key, value);
	    	        }catch(Exception e)
	    	        {	
	    	        	System.out.print("Exception is " + e);
	    	        	return false;
	    	        }
	    			
	    		}else if (value.startsWith("@",0))
	    		{
	    	        me.setValue(value); 			
	    		}
    		} catch (Exception err)
    		{
	        	System.out.print("Exception is " + err);
	        	return false;
    		}
    	}
		return true;
    }	
	
    //*****************************************************************************************
    //*	Name		    : fSetReferenceData
    //*	Description	    : Set the data in keep refer sheet
    //*	Author		    : Anil Agarwal
    //*	Input Params	: None
    //*	Return Values	: Boolean 
    //*****************************************************************************************	
	public boolean fSetReferenceData()
	{
		//Declare few variables
		String key, value, tempKey, tempValue;
    	Map.Entry me;
    	int Field_Count;
    	
		String calendar = Environment.get("CURRENTEXECUTIONDATASHEET");
		//Get a set of the entries 
    	Set set = objGlobalDictOriginal.entrySet();
    	//Get an iterator 
    	Iterator i = set.iterator(); 
    	//Looping through the iterator
    	while(i.hasNext()) 
    	{ 	
    		try
    		{
	    		me = (Map.Entry)i.next();
	    		key = me.getKey().toString();
	    		value = me.getValue().toString();
	    		//If we need to get data from KEEP refer sheet
	    		if (value.startsWith("@",0))
	    		{
	    			tempKey = value.substring(1);
	    			//if Dictionary item has been changed from the objGlobalDictOriginal item
	    			if (!(Dictionary.get(key)).equalsIgnoreCase(objGlobalDictOriginal.get(key).substring(1)))
	    			{
	    				tempValue = Dictionary.get(key);
	    			}
	    			else
	    			{
	    				tempValue ="";
	    			}
	    			String sSQL = "SELECT count(*) as COUNT FROM [KEEP_REFER_" + driverType.toUpperCase() + "$] WHERE KEY_NAME = '" + tempKey + "'";
	    	        //Connect to Excel sheet
	    	        DBActivities objDB = new DBActivities();
	    	        Connection objConn2 = null;
	    	        objConn2 = objDB.fConnectToXLS(calendar);
	    	        //Handle null if connection fails
	    	        if (objConn2 == null)
	    	        {
	    	        	//Write the code to exit in case of any failures
	    	        	System.out.println("Could not connect to Excel " + calendar);
	    	        	return false;
	    	        }
	    	        //Open dataset
	    	        ResultSet RS2 = null;
	    	        //Function call to execute the query
	    	        RS2 = objDB.fExecuteQuery(sSQL, objConn2);
	    	        if (RS2 == null){
	    	        	return false;
	    	        }

	    	        try{
	    	        	RS2.next();

		    	        //handle null if exeuction of query fails
		    	        if (RS2.getLong(1)==0)		    	        {
		    				sSQL = "INSERT INTO [KEEP_REFER_" + driverType.toUpperCase() + "$](KEY_NAME , KEY_VALUE) VALUES ('" + tempKey + "','" +  tempValue  + "')";
		    	        }else{
		    	        	sSQL = "UPDATE [KEEP_REFER_" + driverType.toUpperCase() + "$] SET KEY_VALUE ='" + tempValue + "' WHERE KEY_NAME ='" + tempKey + "'";		    	        
		    	        }
	    		        //Close datasets and connections
		    	        RS2.close();
	    		        objConn2.close();
	    		        RS2 = null;
	    		        objConn2 = null;

	    		        //Connect the calendar to update/inset key and value in keep refer
		    	        objConn2 = objDB.fConnectToXLS(calendar);	
		    	        //Handle null if connection fails
		    	        if (objConn2 == null){
		    	        	//Write the code to exit in case of any failures
		    	        	System.out.println("Could not connect to Excel " + calendar);
		    	        	return false;
		    	        }		    	        
	    	    		Statement stmt = objConn2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE); 
	    	    		int cnt = stmt.executeUpdate(sSQL);
	    	    		
	    	    		objConn2.commit(); 
	    		        objConn2.close();
	    		        objConn2 = null;
	    	    		if (cnt !=1){
	    	    			System.out.println("Failed to execute query " + sSQL);
	    	    			stmt.close();            
	    	    			return false;
	    	    		}            
	    	    		//Closing the connections	
	    	    		stmt.close();            

		    	        
	    	        }catch(Exception e)
	    	        {	
	    	        	System.out.print("Exception is " + e);
	    	        	return false;
	    	        }
	    			
	    		}
    		} catch (Exception err)
    		{
	        	System.out.print("Exception is " + err);
	        	return false;
    		}
    	}
		return true;
    }

}
