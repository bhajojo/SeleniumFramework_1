package pckgSeleniumFramework;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Date;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Parallel{   
  String sReportFile;
  
  @Test(dataProvider = "dp" )	
  public void f(Integer n, String s) throws MalformedURLException {
	try{			
        if (s.contains("FIREFOX")){			
			String driverType = s.toUpperCase();		
			Driver objDriver = new Driver(driverType);
			objDriver.main(objDriver, sReportFile);
			
        }else if(s.contains("CHROME")){
			String driverType = s.toUpperCase();
			Driver objDriver = new Driver(driverType);	
			objDriver.main(objDriver, sReportFile);
			
        }else if(s.contains("IE")){
        	//Kill any instances of IEDriverServer.exe
        	Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");        	
        	//Delete Temporary Internet Files folder for IE
        	//CommonFunctions.fCommonDeleteFolder(new File(System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\Windows\\Temporary Internet Files"));
        	//CommonFunctions.fCommonDeleteFolder(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Cookies"));

			String driverType = s.toUpperCase();			
			Driver objDriver = new Driver(driverType);
			objDriver.main(objDriver, sReportFile);
			
        }else if(s.contains("SAFARI")){        	
			String driverType = s.toUpperCase();
			Driver objDriver = new Driver(driverType);
			objDriver.main(objDriver, sReportFile);
			
        }else if (s.contains("ANDROID")){
			String driverType = s.toUpperCase();
			Driver objDriver = new Driver(driverType);
			objDriver.main(objDriver, sReportFile);
			
        }else if (s.contains("IOS")){
			String driverType = s.toUpperCase();
			Driver objDriver = new Driver(driverType);
			objDriver.main(objDriver, sReportFile);
			
        }else{
        	System.out.print("Invalid String " + s);  	
        }
        
	}catch (Exception e){
			System.out.print("Exception is " + e);
	}
  }
   
  
  @DataProvider(parallel =true)
   Object[][] dp() {
	  try{
		  //******************* Fetch Current TimeStamp ************************
	      java.util.Date today = new java.util.Date();
	      Timestamp now = new java.sql.Timestamp(today.getTime());
	      String tempNow[] = now.toString().split("\\.");
	      final String sStartTime = tempNow[0].replaceAll(":", ".").replaceAll(" ", "T");
	      
		  String ReportFilePath = System.getProperty("user.dir") + "\\Reports";
		  sReportFile = ReportFilePath+"\\Report_"+sStartTime+".html";
			
		  if(!new File(ReportFilePath).exists()){
			  new File(ReportFilePath).mkdirs();
		  }
		  
		  //Create report file                  
		  FileOutputStream foutStrm = new FileOutputStream(sReportFile, true);
	           
		  //Write in Report file
          new PrintStream(foutStrm).println("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100% BGCOLOR=BLACK>");
		  new PrintStream(foutStrm).println("<TR><TD WIDTH=90% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=ORANGE SIZE=3><B>AMDOCS</B></FONT></TD></TR><TR><TD ALIGN=CENTER BGCOLOR=ORANGE><FONT FACE=VERDANA COLOR=WHITE SIZE=3><B>Selenium Framework Reporting</B></FONT></TD></TR></TABLE><TABLE CELLPADDING=3 WIDTH=100%><TR height=30><TD WIDTH=100% ALIGN=CENTER BGCOLOR=WHITE><FONT FACE=VERDANA COLOR=//0073C5 SIZE=2><B>&nbsp; Automation Result : " + new Date() + " on Machine " + InetAddress.getLocalHost().getHostName() + " by user " + System.getProperty("user.name") + "</B></FONT></TD></TR><TR HEIGHT=5></TR></TABLE>");  
          new PrintStream(foutStrm).println("<TABLE  CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");           
          new PrintStream(foutStrm).println("<TR COLS=4 BGCOLOR=ORANGE><TD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Thread No.</B></FONT></TD><TD WIDTH=35%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Browser Name</B></FONT></TD><TD  WIDTH=35%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Calendar</B></FONT></TD><TD  WIDTH=20%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Summary</B></FONT></TD></TR>");
	        
          //Close the object
          foutStrm.close();	      
		  System.out.println("Report File Path :"+sReportFile);
		  
	  }catch(Exception e){
		  System.out.println("Exception occurred :"+e);
	  }
	
	
    return new Object[][] {
    		//new Object[] { 1, "ANDROID1" },    	
    		//new Object[] { 2, "ANDROID2" },
    		//new Object[] { 3, "ANDROID3" },
    		//new Object[] { 4, "IOS1" },
    		//new Object[] { 5, "IOS2" },
    		//new Object[] { 6, "IOS3" },
    		new Object[] { 7, "FIREFOX1" },
    		//new Object[] { 8, "FIREFOX2" },
    		//new Object[] { 9, "CHROME1" },
    		//new Object[] { 10, "CHROME2" },
    		//new Object[] { 11, "CHROME3" },
    		//new Object[] { 12, "IE1" },
    		//new Object[] { 13, "IE2" }, 
    		//new Object[] { 14, "SAFARI1" },
    		//new Object[] { 15, "SAFARI2" },
    		
    };
  }
}
