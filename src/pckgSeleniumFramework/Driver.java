package pckgSeleniumFramework;


import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import OTAClient.*;
//import com4j.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.SafariDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class Driver implements Runnable{
	private DesiredCapabilities dc;
	private WebDriver driver;
	private String driverType;
	private HashMap <Integer, String> Temp;
	private HashMapNew Dictionary;
	private String Skip;	
	private HashMap <String, String> Environment;
	private HashMap <String, String> objGlobalDictOriginal;
	
    //Connect to Excel sheet
    private DBActivities objDB = new DBActivities();
    private GlobalFunctions gblFunctions = new GlobalFunctions();
    private Connection objConn = null;
    //Open Dataset
    private ResultSet objRS = null;
    private Reporting Reporter;
	
    //Constructor
    public Driver(String DT){
		driver = null;
		driverType = DT;
		Dictionary = new HashMapNew();
		Environment = new HashMap<String, String>();
		objGlobalDictOriginal = new HashMap<String, String>();
		Temp = new HashMap<Integer, String>();	    
		Reporter = new Reporting(driver, driverType, Dictionary, Environment);			
	}
    
    //Method called from Parallel
    Driver objDriverClass;
    String sReportFile;
    static int iThreadCount = 0;
    static boolean bThreadFlag = false;
    
    //Variables required for QC update using child thread	
    String sStatus;    
    public void main(Driver driverClass, String sReport){
    	objDriverClass = driverClass;
    	sReportFile = sReport;
    	objDriverClass.threadMain();
    }
    
    //Execution Method
	public void threadMain(){
		Thread newChildThread = null;
		int iScriptStartRow, iCurrentHeaderRow,iScriptEndRow;
		String User = System.getProperty("user.name");
        String RootPath = System.getProperty("user.dir");
		
		//******************* Reading Parameters XML file ************************
        try{       
	        String xmlPath = RootPath+"\\src\\Parameters.xml";
		    File fXmlFile = new File(xmlPath);
		    
		    DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		    DocumentBuilder docBuilder = dbFac.newDocumentBuilder();
		    Document xmldoc = docBuilder.parse(fXmlFile);
		    
		    XPathFactory xPathfac = XPathFactory.newInstance();
			XPath xpath = xPathfac.newXPath();
			
			//Get QC details
			XPathExpression expr = xpath.compile("//common");		
			NodeList nl = ((NodeList)expr.evaluate(xmldoc, XPathConstants.NODESET)).item(0).getChildNodes();
			
			for(int child=0; child<nl.getLength(); child++){
				//System.out.println(nl.item(child).getNodeName());
				//System.out.println(nl.item(child).getTextContent());
				Environment.put(nl.item(child).getNodeName(), nl.item(child).getTextContent());
			}
			
			//Get Browser/Thread details
			expr = xpath.compile("//"+driverType.toLowerCase());			
			nl = ((NodeList)expr.evaluate(xmldoc, XPathConstants.NODESET)).item(0).getChildNodes();
			
			for(int child=0; child<nl.getLength(); child++){
				//System.out.println(nl.item(child).getNodeName());
				//System.out.println(nl.item(child).getTextContent());
				Environment.put(nl.item(child).getNodeName(), nl.item(child).getTextContent());
			}
			
        }catch(Exception excep){
        	System.out.println("Exception occurred while reading XML file for Browser "+driverType);
        	System.out.println("Exception :"+excep);
        	while(Thread.currentThread().isAlive()){
        		Thread.currentThread().interrupt();
        	}
        }	
		
        
        //******************* Fetch Current TimeStamp ************************
        java.util.Date today = new java.util.Date();
        Timestamp now = new java.sql.Timestamp(today.getTime());
        String tempNow[] = now.toString().split("\\.");
        String timeStamp = tempNow[0].replaceAll(":", ".").replaceAll(" ", "T");

        
        //********************* Set all paths *****************************
        String Datasheet = Environment.get("calendar");
        String ExecutionFolderPath = RootPath + "\\Execution";
        String StorageFolderPath = RootPath + "\\Storage";
        String DatasheetsPath = StorageFolderPath + "\\DataSheets";
        String EnvironmentXLSPath = StorageFolderPath + "\\Environments\\Environments.xls";
        String AppLibsPath = StorageFolderPath + "\\Libraries\\App";
        String InfraLibsPath = StorageFolderPath + "\\Libraries\\Infra";
        String ORPath = StorageFolderPath + "\\Object Repositories";
        String CurrentExecutionFolder = ExecutionFolderPath + "\\" + Datasheet + "\\" + Environment.get("qcTestSet") + "\\" + User;
        String CurrentExecutionDatasheet = CurrentExecutionFolder + "\\" + Datasheet + ".xlsx";
        String HTMPReports = CurrentExecutionFolder + "\\" + driverType + "\\HTML_REP_" + timeStamp;
        String SnapshotsFolder = HTMPReports + "\\Snapshots";
        //********************* Set all paths *****************************
        
        
        //************** Adding Path to Environment Variables *************        
        Environment.put("ROOTPATH", RootPath);
        Environment.put("EXECUTIONFOLDERPATH", ExecutionFolderPath);
        Environment.put("STORAGEFOLDERPATH", StorageFolderPath);
        Environment.put("DATASHEETSPATH", DatasheetsPath);
        Environment.put("ENVIRONMENTXLSPATH", EnvironmentXLSPath);
        Environment.put("APPLIBSPATH", AppLibsPath);
        Environment.put("INFRALIBSPATH", InfraLibsPath);
        Environment.put("ORPATH", ORPath);
        Environment.put("CURRENTEXECUTIONFOLDER", CurrentExecutionFolder);
        Environment.put("CURRENTEXECUTIONDATASHEET", CurrentExecutionDatasheet);
        Environment.put("HTMLREPORTSPATH", HTMPReports);
        Environment.put("SNAPSHOTSFOLDER", SnapshotsFolder);
        //************** Adding Path to Environment Variables ***************
       
        
        //******************** Create Execution Folder **********************
    	// Create multiple directories
    	boolean success = (new File(SnapshotsFolder)).mkdirs();
    	if (success) {
    		System.out.println("Directories: " + SnapshotsFolder + " created");
    	}
      	
      	//Copy the calendar XLS from storage folder if not present
        if (!(new File(CurrentExecutionDatasheet)).exists()){
        	gblFunctions.fCopyXLS(DatasheetsPath + "\\" + Datasheet + ".xls", CurrentExecutionDatasheet);
        }
        
        //******************** Create Summary report *************************
        Reporter.fnCreateSummaryReport();
        
        //Mutual Exclusion Condition to write into Report file
        while(bThreadFlag){
        	try {
				Thread.sleep(500);
			} catch (Exception e) {}
        }
        bThreadFlag = true;
    	Reporter.fnWriteThreadReport(++iThreadCount, sReportFile, Environment.get("calendar"), Environment.get("HTMLREPORTSPATH")+ "\\SummaryReport.html");
    	bThreadFlag = false; 
        

        //Instance of  class
        Infra objInfra = new Infra(driverType, Dictionary, Environment, objGlobalDictOriginal);
        
        //Clear SKIP column for browser according to the action value if CLEARX is not empty
        if(!Environment.get("clearX").equals("")){
        	objInfra.fClearSkip(Environment.get("clearX"));
    	}

        //Class Definition to store the Record Set in the HaspMap
        {
	    	class RecordSet{
	    		private int sStartRow;
	    		private String sTestName;
	    		private int sEndRow;
	    		
	    		public RecordSet(int sSRow, String sTCName, int sERow){
	    			sStartRow = sSRow;
	    			sTestName = sTCName;
	    			sEndRow = sERow;
	    		}
	    		public int get_sStartRow(){
	    			return this.sStartRow;
    			}
	    		
	    		public String get_sTestName(){
	    			return this.sTestName;
    			}
	    		
	    		public int get_sEndRow(){
	    			return this.sEndRow;
    			}
	    	}
               	
        	try{
        		objConn = objDB.fConnectToXLS(CurrentExecutionDatasheet);
                //SQL 
	            String SQL = "SELECT int(B.ID) As [iCurHID], TEST_NAME, min(A.Nextal) As [iNextHID] " +
	            		"FROM [MAIN$] as B, (SELECT int(ID) As [Nextal] FROM [MAIN$] WHERE HEADER Is Not Null " +
	            		"GROUP BY ID, TEST_NAME) as A WHERE A.Nextal > int(B.ID) And B.HEADER Is Not Null and " +
	            		"SKIP_" + driverType.toUpperCase().replace(" ", "")+ " is null group by int(B.ID), TEST_NAME order by int(B.ID)";
	            objRS = objDB.fExecuteQuery(SQL, objConn);
                
	            //handle null if query fails
                if (objRS == null){
                	objConn.close();
                    objConn = null;
                	return;
                }
                               
            	objRS.next();
            	int rowCount = objRS.getRow();

                if (rowCount==0){
                	System.out.println(driverType + ": No Rows Found");
                	objRS.close();
                    objConn.close();
                    objRS = null;
                    objConn = null;
                	return;
                } 
                RecordSet res1;
                HashMap <Integer, RecordSet> RecordSetMap = new HashMap<Integer, RecordSet>();
                int iRSCount = 1;
                do{
                	RecordSetMap.put(iRSCount, new RecordSet((int)Double.parseDouble(objRS.getString(1)),objRS.getString(2),(int)Double.parseDouble(objRS.getString(3))));
                	iRSCount++;
                }while(objRS.next());
                
                objRS.close();
                objConn.close();
                objRS = null;
                objConn = null;
                iRSCount = 1;
                do{               	
	                //Set iScriptStartrow, iCurrentHeaderRow & iScriptEndRow
                	res1 = (RecordSet)RecordSetMap.get(iRSCount);
	                iScriptStartRow = res1.get_sStartRow();
	                iCurrentHeaderRow = iScriptStartRow;
	                iScriptEndRow = res1.get_sEndRow();	                
	                
	                do{
	                    //Call function to get all params or its values in global dictionary
	                    if (fProcessDataFile(iScriptStartRow,objConn) == 1){
	                    	
	                        //If Skip is null execute the bus function
	                    	if (Skip == ""){             		
	                            
	                            //Call function to create a HTML report
	                            Reporter.fnCreateHtmlReport(Dictionary.get("TEST_NAME") + "-"+ Dictionary.get("ACTION"));                                                       
	                            
	                            //Agile team's package name
	                            if(Environment.get("agilePackage").equals("")){
	                            	System.out.println("TEAM_PACKAGE_NAME is empty");
	                            	Thread.currentThread().interrupt();
	                            }
	                            //Execute command
	                            Boolean result = Execute(Environment.get("agilePackage")+".BusFunctions", Dictionary.get("ACTION"));
	                            
	        					//Determine status based on return value
	                            if (result == true){
	                            	sStatus = "Passed";
                            	}else if(result == false){
                            		sStatus = "Failed";
                        		}else{
                        			sStatus = "No Run";
                    			}	                            
	                            Dictionary.put("RESULT", sStatus);
	                            Environment.put("STEP", Dictionary.get("STEP"));
	                            //Waits until Child Thread is killed
	                            /*while(newChildThread.isAlive()){
	                            	try{
	                            		System.out.println("Waiting for Child Thread for QCUpdate :"+newChildThread.getName());
	                    				Thread.sleep(2000);
	                    			} catch (InterruptedException e) {
	                    				e.printStackTrace();
	                    			}
	                            }*/
	                            
	                            //Multi-Thread to Call function to report into QC                         
	                            newChildThread  = new Thread(objDriverClass);
	                            newChildThread.start();  
	                            
	                            //Enter Summary Report
	                            Reporter.fnCloseHtmlReport();
	                        }
	                    }
	                    //Call function to update the currently executed row with X
	                    objInfra.fUpdateTestCaseRowSkip(iScriptStartRow, objConn);
	                       
	                    //Increment iScriptStartRow
	                    iScriptStartRow++;  
	                }while (iScriptStartRow != iScriptEndRow); //Execute till Start row reaches end row
                
	                //Call function to update the current row
	                objInfra.fUpdateTestCaseRowSkip(iCurrentHeaderRow, objConn);
	                iRSCount++;
	                
                }while(iRSCount <= RecordSetMap.size());
        	}
        	catch (SQLException eSQL){
        		System.err.println(eSQL);
    		}
        }
        //Close Summary Report
        Reporter.fnCloseTestSummary();
        //Delete Cookies before closing the Browser
        driver.manage().deleteAllCookies();
        driver.quit();
        
        //Waits until Child Thread is killed
        while(newChildThread.isAlive()){
        	try{
        		System.out.println("Waiting for Child Thread to get finished :"+newChildThread.getName());
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }           
	}
	
	
	//Multi-Threaded method to perform parallel tasks
	public void run() {
		try{			
			//Call the StartTest function to report the start test information
			//Object sTestid = StartTest(Dictionary.get("TEST_NAME") + "_" + driverType);		
		
			//Call the EndTest function to report into QC
	 		/*if(EndTest(Dictionary.get("TEST_NAME") + "_" + driverType, sTestid, sStatus) == false){
				System.out.println("Error in executing EndTest");
			}*/			
			
		}catch(Exception e){
			System.out.println("Exception in Run Method :"+e);			
		}
	}
	
	
    //***********************************************************************
    //* Name			: fProcessDataFile
    //*	Description	    : Function to execute an query
    //*	Author		    : Anil Agarwal
    //* Input Params	: int row - Row number to skip
    //*                   Connection conn - Connection object
    //*	Return Values	: int
    //***********************************************************************
    public int fProcessDataFile(int rowID, Connection conn){
        //string key;
        DBActivities objDB = new DBActivities();
        int iret = 0;
        
        //Query the excel
        ResultSet rs = null;
        String sSQL = "Select * from [MAIN$] where ID = '" + rowID + "'";
        conn = objDB.fConnectToXLS(Environment.get("CURRENTEXECUTIONDATASHEET"));
        rs = objDB.fExecuteQuery(sSQL, conn);

        //Code to exit the function is there is nothing in the resultset
        if (rs == null){
        	System.out.println("The result set is null");
        	return iret;
        }
        int intCounter = 1;
        
        try{
        	while(rs.next()){
        		
    	        //String temp = rs.getString(8);
    	        String temp1;
        		
	        	ResultSetMetaData rsmd = rs.getMetaData();
	            int intColCount = rsmd.getColumnCount();

                //if (!(temp == null) && (temp.equalsIgnoreCase("HEADER"))){
                if ((rowID%2 == 1)){
                    //Clear hash maps
                    Temp.clear();
                    Dictionary.clear();
                    objGlobalDictOriginal.clear();
                	
                    //Looping through each field in record
                    for(int intLoop = 2; intLoop <= intColCount; intLoop++ ){
                    	temp1 = rs.getString(intLoop);                    	
                    	
                    	if(temp1 == null){
                    		if (rsmd.getColumnName(intLoop).contains(driverType)){
                        		Skip = "";                    		
                        	}
                    		temp1 = rsmd.getColumnName(intLoop);
                    	}                 	
                    	if(temp1 == null){
                    		temp1 = "";        		
                    	}
                    	if (!temp1.equals("")){                    		
                    		Temp.put(intCounter,temp1);
                    		iret = 0;
                    		intCounter++;
                    	}
                    }
                }else{
                	//Looping through each field in record
                    for(int intLoop1 = 2; intLoop1 < intColCount; intLoop1++ ){
                    	temp1 = rs.getString(intLoop1);                    	
                    	
                    	if(temp1 == null){
                    		temp1 = "";
                    		if (rsmd.getColumnName(intLoop1).contains(driverType)){
	                    		Skip = temp1;
                    		}                    		
                    	}
                   	
                    	if (Temp.containsKey((intCounter))){
                    		Dictionary.put(Temp.get(intCounter), temp1);
                    		//Put the same in objGlobalDictOriginal hashmap for use during get and set reference data
                    		objGlobalDictOriginal.put(Temp.get(intCounter), temp1);                    		
                    	}
                    	iret = 1;
                    	intCounter ++;
                    }
                }
            }
        	rs.close();
        	conn.close();
        	rs = null;
            conn = null;
            objDB = null;
        }catch (SQLException eSQL){
        	eSQL.printStackTrace();
        	System.err.println(eSQL);
        	Thread.currentThread().interrupt();
        }
        return iret;      
    }

    //*****************************************************************************************
    //*	Name		    : Execute
    //*	Description	    : Function to give dynamic calls to the methods from BusFunctions Class
    //*	Author		    : Anil Agarwal
    //*	Input Params	: String className, String methodName
    //*	Return Values	: Boolean depending on the success of the Business function invoked
    //*****************************************************************************************
	public boolean Execute(String className, String methodName){
		try{		
			//Close the browser if already open
			if(driver != null){
        		driver.manage().deleteAllCookies();
        		
        		//Temporary file delete for IE
        		if(driverType.contains("IE")){
	        		//Delete TIF and Cookies for IE
	        		gblFunctions.fGlobalDeleteFolder(new File(System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\Windows\\Temporary Internet Files"));
	        		gblFunctions.fGlobalDeleteFolder(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\Microsoft\\Windows\\Cookies"));
	        	}
        		driver.quit();
    	    }
			//Based on the driver type launch the particular driver
	        if (driverType.contains("FIREFOX")){
	        	ProfilesIni prof = new ProfilesIni();
	        	FirefoxProfile profile = prof.getProfile("default");
	        	driver = new FirefoxDriver(profile);   
	        	
	        }else if(driverType.contains("CHROME")){	
		        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		        driver = new ChromeDriver();
		        
	        }else if(driverType.contains("IE")){	        	
	        	System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");
	        	dc = DesiredCapabilities.internetExplorer();
	        	dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		        driver = new InternetExplorerDriver(dc);
		        
	        }else if(driverType.contains("SAFARI")){	        	
	        	driver = new SafariDriver();
		        
	        }
	        else if(driverType.contains("IOS")){ 
				driver = new RemoteWebDriver(new URL(Environment.get("ip")),DesiredCapabilities.ipad());	
				
	        }else if(driverType.contains("ANDROID")){
	        	//driver = new RemoteWebDriver(new URL(Environment.get("ip")),DesiredCapabilities.android());
	        	DesiredCapabilities capabilities = new DesiredCapabilities();
	      		capabilities.setCapability("automationName", "Appium");
	      		capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");  
	      		capabilities.setCapability("deviceName", "YT910CYRFG");  
	      		capabilities.setCapability("appium-version", "1.2.1.0");

	      		capabilities.setCapability("platformName", "Android");
	      		capabilities.setCapability("newCommandTimeout", "6000");
	      		capabilities.setCapability("platformVersion", "4.3");
	      		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);    			 			
	        }else{	
	        	System.out.print("Invalid driver type " + driverType);
	        	Thread.currentThread().interrupt();
	        }
	        
	        //Set the implicit time out for the driver
	        driver.manage().timeouts().implicitlyWait(Integer.parseInt(Environment.get("implicitWait")), TimeUnit.MILLISECONDS);
	        Reporter.driver = driver;
	        
	        //Get the class
			Object params[]= {};			
			Class<?> thisClass = Class.forName(className);
			
			//Get an instance of BusFunction			
			Object busFunctions = thisClass.getConstructor(WebDriver.class, String.class, HashMapNew.class, HashMap.class, Reporting.class).newInstance(driver, driverType, Dictionary, Environment, Reporter);
			
			//get the method
			Method  method = thisClass.getDeclaredMethod(methodName, new Class[] {}); 
				
			//call the method
			Object objReturn  = method.invoke(busFunctions, params);
			
			//Return statement
			if (objReturn.equals(true)){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			Reporter.fnWriteToHtmlOutput("Test Step Failed Due to exception",e.toString(),e.getCause().toString(), "Fail");
			return false;
		}
	}
	
	/*//*****************************************************************************************
	//*	Name		    : fAddTest
	//*	Description	    : Add Test in Test Plan of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Test Object
	//*****************************************************************************************
	public Object fAddTest(String sTestName)
	{
		try{			
			ITDConnection2 QCconn= ClassFactory.createTDConnection();
			QCconn.initConnectionEx(Environment.get("qcUrl"));
			QCconn.login(Environment.get("qcUser"), Environment.get("qcPassword"));
			QCconn.connect(Environment.get("qcDomain"), Environment.get("qcProject"));
			Object[] sTestParam = new Object[4];
			if (QCconn.projectConnected() == true){
				
				ITestFactory sTestFactory = (QCconn.testFactory()).queryInterface(ITestFactory.class);
				ITreeManager sTreeManager = (QCconn.treeManager()).queryInterface(ITreeManager.class);
				ISysTreeNode sSysTreeNode = (sTreeManager.nodeByPath("Subject\\Automation\\Selenium\\" + Environment.get("qcPackage") + "\\Automation_Coverage")).queryInterface(ISysTreeNode.class);
							
				String sTestFactoryFilter = "select TS_TEST_ID from TEST where TS_NAME = '" + sTestName + "' and TS_SUBJECT = " + sSysTreeNode.nodeID();
				IList iListTestCase = sTestFactory.newList(sTestFactoryFilter);

				if (iListTestCase.count() == 0)
				{
					sTestParam[0] = sTestName;
					sTestParam[1] = "QUICKTEST_TEST";
					sTestParam[2] = Environment.get("qcUser");
					sTestParam[3] = sSysTreeNode.nodeID();
					IBaseField sBaseField = (sTestFactory.addItem(sTestParam)).queryInterface(IBaseField.class);
					sBaseField.field("TS_USER_06", "N");
					sBaseField.field("TS_USER_01", "3-Low");
					sBaseField.post();
					return sBaseField.id();	
				}else{
					Com4jObject comObj = (Com4jObject) iListTestCase.item(1);
					ITest sTest = comObj.queryInterface(ITest.class);
					return sTest.id();
				}
			}else{
				System.out.println("QC Connection Failed");
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	//*****************************************************************************************
	//*	Name		    : StartTest
	//*	Description	    : Start Addition of Test in Test Plan of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Test Object
	//*****************************************************************************************
	public Object StartTest(String sTestName){	
		try{
			return fAddTest(sTestName);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//*****************************************************************************************
	//*	Name		    : EndTest
	//*	Description	    : Update Test Case Status in Test Set of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: boolean
	//*****************************************************************************************
	public boolean EndTest(String sTestName, Object sTestid, String sStatus){
		try{
			return fUpdateTestStatQC (sTestName,sTestid,sStatus);			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	//*****************************************************************************************
	//*	Name		    : fUpdateTestStatQC
	//*	Description	    : Update Test Case Status in Test Set of Quality Center
	//*	Author		    : Shraddha Girme
	//*	Input Params	: 
	//*	Return Values	: Boolean
	//*****************************************************************************************
	public boolean fUpdateTestStatQC(String sTestName, Object sTestid, String sStatus){
		try{
			ITDConnection2 QCconn= ClassFactory.createTDConnection();
			QCconn.initConnectionEx(Environment.get("qcUrl"));
			QCconn.login(Environment.get("qcUser"), Environment.get("qcPassword"));
			QCconn.connect(Environment.get("qcDomain"), Environment.get("qcProject"));
			if (QCconn.projectConnected() == true)
			{
				ITestSetFactory sTestSetFactory = (QCconn.testSetFactory()).queryInterface(ITestSetFactory.class);
				ITestSetTreeManager sTestSetTreeManager = (QCconn.testSetTreeManager()).queryInterface(ITestSetTreeManager.class);
				ISysTreeNode sSysTreeNodeTestSet = (sTestSetTreeManager.nodeByPath(Environment.get("qcTestSetFolder"))).queryInterface(ISysTreeNode.class);
				
				String sTestSetFactoryFilter = "select CY_CYCLE_ID from CYCLE where CY_CYCLE = '" + Environment.get("qcTestSet") + "' and CY_FOLDER_ID = " + sSysTreeNodeTestSet.nodeID();
				IList  iListTestSets = sTestSetFactory.newList(sTestSetFactoryFilter);
				
				if (iListTestSets.count() == 0)
				{
					System.out.println("Test Set does not exist");
					return false;
				}else{
					Com4jObject comObjTestSet = (Com4jObject) iListTestSets.item(1);
					ITestSet sTestSet = comObjTestSet.queryInterface(ITestSet.class);
					Com4jObject comObjTSTestSet = (Com4jObject) sTestSet.tsTestFactory();
					IBaseFactory sBaseFactory = (IBaseFactory)comObjTSTestSet.queryInterface(IBaseFactory.class);
					
					String strTestSetTestFactoryFilter = "SELECT *  FROM TESTCYCL WHERE tc_test_id = " + sTestid + " and TC_cycle_id = " + sTestSet.id();
					IList iListTsTest  = sBaseFactory.newList(strTestSetTestFactoryFilter);
					ITSTest sTSTest;
					if (iListTsTest.count() == 0)
					{
						sTSTest = (sBaseFactory.addItem(sTestid)).queryInterface(ITSTest.class);
						sTSTest.status("No Run");
						sTSTest.post();
					}else{
						Com4jObject comObjTSTest = (Com4jObject) iListTsTest.item(1);
						sTSTest = (ITSTest)comObjTSTest.queryInterface(ITSTest.class);
					}
					IRunFactory sRunFactory = (sTSTest.runFactory()).queryInterface(IRunFactory.class);
					IRun sRun = (sRunFactory.addItem(sTSTest.id())).queryInterface(IRun.class);
					sRun.status(sStatus);
					//sRunid = sRun.id();
					sRun.post();
					
					String[] arrTestSteps = Environment.get("STEP").split("\\<NS\\>");
					
					for (int i = 0; i < arrTestSteps.length; i++){
						String[] arrStep = arrTestSteps[i].split("\\<ND\\>");
						if(arrStep.length==4){
							IBaseFactory sTestStepFactory = (sRun.stepFactory()).queryInterface(IBaseFactory.class);
							IStep sTestStep = (sTestStepFactory.addItem(sTestName)).queryInterface(IStep.class);
							//sTestStep.name(sTestName);
							sTestStep.field("ST_STEP_NAME", arrStep[0]);
							sTestStep.field("ST_DESCRIPTION", arrStep[0]);
							sTestStep.field("ST_EXPECTED", arrStep[1]);
							sTestStep.field("ST_ACTUAL", arrStep[2]);
							if(arrStep[3].equals("Pass")){
								sTestStep.status("Passed");
							}else if(arrStep[3].equals("Fail")){
								sTestStep.status("Failed");
							}else{
								sTestStep.status("Passed");
							}
							sTestStep.post();
						}
						else{
							System.out.println("fUpdateTestStatQC: Step data should have 4 parameters. " + arrTestSteps[i]);
						}
					}
				}			
			}else{
				System.out.println("QC Connection Failed");
				return false;
			}
			return true;
		}catch(Exception e){
			System.out.println("Exception occured in fUpdateTestStatQC");
			e.printStackTrace();
			return false;
		}
	}*/
	
	public static class HashMapNew extends HashMap<String,String>{
		
		static final long serialVersionUID = 1L;

		@Override
	    public String get(Object key) {
	        String value = super.get(key);
	        if(value==null){return "";}        
	        return value;        
	    }   
	}
}


	


