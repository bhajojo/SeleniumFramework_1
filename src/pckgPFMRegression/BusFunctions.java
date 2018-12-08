package pckgPFMRegression;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pckgSeleniumCommon.CommonFunctions;
import pckgSeleniumCommon.OR_PFM;

//import pckgSeleniumFramework.HashMapNew;
//import pckgSeleniumFramework.Parameters;
//import pckgSeleniumFramework.Parameters;
import pckgSeleniumFramework.Reporting;
import pckgSeleniumFramework.Driver.HashMapNew;

/* *** List of Bus Function ***
 
 */

public class BusFunctions {

	private WebDriver driver;
	private String driverType;
	private HashMapNew Dictionary;
	private HashMap <String, String> Environment;
	private OR_PFM objOR;
	private Reporting Reporter;
	private CommonFunctions CommonFunctions;
	private GuiFunctions GuiFunctions;
	
	//creating Global dictionaries for the Cart Summary validations
	HashMap <String, String> GlobalDictionaryCart = new HashMap <String, String>();
	
	public BusFunctions(WebDriver webDriver,String DT,HashMapNew Dict, HashMap <String, String> Env, Reporting Report)
	{
		driver = webDriver;
		driverType = DT;
		Dictionary = Dict;
		Environment = Env;
		Reporter = Report;
		objOR = new OR_PFM(driver, Reporter);
		CommonFunctions = new CommonFunctions(driver, driverType,Reporter);
		GuiFunctions = new GuiFunctions(driver, driverType,Dictionary, Environment,Reporter,CommonFunctions, objOR);
		//OrderGateway = new OrderGateway(driver, driverType,Dictionary, Environment,Reporter,CommonFunctions, objOR);
	}

	
	//*****************************************************************************************
		//*	Name		    : fBusCreateNewPayments
		//*	Description	    : Creates the New Payment (UPG)
		//*	Author		    : Bharat Joshi
		//*	Date Modified	: 01-Sep-2014
		//*	Return Values	: Boolean - Depending on the success
		//*****************************************************************************************
		public boolean fBusCreateNewPayments()
		{
			// Launch the Environment
			if (CommonFunctions.fGuiLaunchEnvironemnt(Environment.get("APP_URL")) == false){
				return false;
			}
			
			//Login into the Application 
			if (GuiFunctions.fGuiPFMLogin()== false){return false;}
			
			/*//Select Menu
			if (GuiFunctions.fGuiSelectMenu() == false){return false;}
			
			//Select the Menu Item
			if (GuiFunctions.fGuiSelectMenuItem(objOR.lnkPayments+";"+objOR.lnkNewPayments) == false){return false;}
			
			//Verify if Page Exist			
			if(CommonFunctions.fGuiIsDisplayed(objOR.wbElmtNewPaymentHeader,"New Payment Header page")==false){return false;}
			
			//Select or Enter the Beneficiary Information
			if  (Dictionary.get("SELECT_BENEFICIARY")!="Y")
			{
				if (GuiFunctions.fGuiEnterBeneficiaryInformation()== false){return false;}
				
			}
			else
			{
				if(GuiFunctions.fGuiFunctionsfGuiSelectBeneficiary()== false){return false;}
			}*/
			             
			return true;

		}
}






