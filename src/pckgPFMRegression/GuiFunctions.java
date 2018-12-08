package pckgPFMRegression;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import pckgSeleniumCommon.CommonFunctions;
import pckgSeleniumCommon.OR_PFM;
//import pckgSeleniumFramework.HashMapNew;
import pckgSeleniumFramework.Reporting;
import pckgSeleniumFramework.Driver.HashMapNew;

/* *** List of Gui Function ***

 * fGuiEnterZipCode()
 * fGuiHandleGetStarted(String strCustomerType)
 * fGuiSelectDevice()
 * fGuiGoToDeviceDetailsPage()
 * fGuiCartSummary()
 * fGuiSelectPlanType()
 * fGuiEnterContactInformation()
 * fGuiBillingAddress()
 * fGuiCreditInformation()
 * fGuiPaymentInformation()
 * fGuiAddAccessories()
 * fGuiAddALineNewCustomer()
 * fGuiSelectService()
 * fGuiOLAMLogin(String strCustomerType)
 * fGuiEnterTransferYourNumber(String strLNPNumber)
 * fGuiHandleThankYouPage()
 * fGuiPhoneNumberDetails()
 * fGuiPersonalAndPaymentInformation()
 * fGuiHandleReviewAndSubmitOrderPage()
 * fGuiAddALineExistingCustomer()
 * fGuiSelectUpgradelines(int IntLinesToUpgrade)
 * fGuiEnterContactInfoExistingCustomer()
 * fGuiHandleDeviceFilter()
 * fGuiSelectAccessoriesByDevice()
 * fGuiOLAMLoginHardRock()
 * fGuiAccountOverview(String strAction)
 * fGuiPutValueToGlbDictCart(String key, String value)
 * fGuiAutoForwardingMenue() - generic function (Shopping Assistance)
 * fGuiStopConflictPopupVerification()
 * fGuiMiniCartActivities()
 * fGuiHandleImportantInfoAboutYourCart(String StrLnkToClick)
 * fGuiHandleAddressNotFoundPopUp()
 * fGuiVerifyConflicPlanPopup(String sAction) - generic function to handle conflict popup when trying to have Individual and family plan in same cart
 * fGuiSelectServiceFromDetailsPage() - select service from service details page
 * fGuiRemoveItemFromGlbDictCart(String key) - mark certain item as 'removed' after removing item from cart
 * fGuiHandleGetStartedForTabletAndInternetDevices(String Flow)
 * fGuiVerifyMiniCartItems() - generic function - verify item exist in mini cart by giving sku id also checking the number of items
 * fGuiMiniCartFeatures() - generic function - check diff links/text exist in Mini Cart
 * fGuiVerifyMiniCarItemDetailes() - verifying diffrent detials in mini cart: price columns/saving and subtotals section/next action
 * fGuiVerifyMinicartItemLinkAndAction() - check item name appear as link and suitable action exist near item name
 * fGuiMiniCartClickItems() - clik on links in mini cart according to specific flow
 * fGuiCompareDevices() 
 * fGuiMiniCartActivities() - click on open/close/open/view full cart links in mini cart
 * fGuiShopByCategory() - generic function - Click on Shop By Category link in global Nav and select to shop according to given category
 * fGuiAddPackage() - generic function - hold some functions to select diff items in package details page(some functions are partialy/no developed yet)
 * fGuiSelectPackages() - generic function - select package and go to package details page
 * fGuiSelectPlanPackage() - select plan in package details page  - partially developed
 * fGuiSelectServicePackage() - select services in package details page  - not developed
 * fGuiSelectAccessoryPackage() - select accessory in package details page  - not developed
 * fGuiVerifyHelpIconsPhoneDetailsPage() - checking tool tip for all help icons in phone details page
 * fGuiVerifyInCartBehavoiur() 
 * fGuiValidatePlanToolTip() - validate tool tip appear for any hovered plan in plans list page
 * fGuiVerifyMultipleAddrMatch() - handle multiple address match popup
 * fGuiNewCustCreateNewLine() - generic function - flow from add device till Cart summary page (good to use if you have many lines to add)
 * fGuiSelectPlanFromListPage() - Select plan on plan list page without going to plan details page 
 */

public class GuiFunctions {
	private static final boolean String = false;
	
	//static Reporting Reporter = new Reporting();
	private DesiredCapabilities dc;
	private WebDriver driver;
	private String driverType;
	//private HashMap <String, String> Dictionary;
	private HashMapNew Dictionary;
	private HashMap <String, String> Environment;
	private OR_PFM objOR;
	private Reporting Reporter;
	private CommonFunctions CommonFunctions;
	//creating Global dictionaries for the Cart Summary validations
	HashMap <String, String> GlobalDictionaryCart = new HashMap <String, String>();
	

	//public GuiFunctions(WebDriver webDriver,String DT,HashMap <String, String> Dict, HashMap <String, String> Env, Reporting Report, CommonFunctions Common, OR_H2 Or)
	public GuiFunctions(WebDriver webDriver,String DT,HashMapNew Dict, HashMap <String, String> Env, Reporting Report, CommonFunctions Common, OR_PFM Or)
	{
		driver = webDriver;
		driverType = DT;
		Dictionary = Dict;
		Environment = Env;
		objOR = Or;
		Reporter = Report;
		CommonFunctions = Common;
		//GlobalDictionaryCart.clear();
	}
	

// *****************************************************************************************
// * Name : fGuiPFMLogin
// * Description : Enter credentials to login to NetBank
// * Author : Bharat Joshi
// * Input Params : None
// * Return Values : Boolean - Depending on the success
// *****************************************************************************************
public boolean fGuiPFMLogin()
{
	//Check if the Page is Loaded
	if(CommonFunctions.checkObjectExistance(objOR.objLoginContinueButton)== false){return false;}		
	
	//Verify the Continue Button and click on it
	if(CommonFunctions.fGuiClick(objOR.objLoginContinueButton, "Continue 1 Button")== false){return false;}	
	
	//Verify the Continue Button and click on it
	if(CommonFunctions.fGuiClick(objOR.objLoginContinueButton, "Continue 2  Button")== false){return false;}
	
	//Verify the Agree Check Box and click on it	
	if(CommonFunctions.fGuiClick(objOR.objLoginAgreeCheckbox, "Agree check Box")== false){return false;}
	
	//Click on Done
	if(CommonFunctions.fGuiClick(objOR.objLoginDoneButton, "Done Button	")== false){return false;}
	
	if (CommonFunctions.checkObjectExistance(objOR.oblLoginPageAccountSelected)== false){return false;}
	
	Reporter.fnWriteToHtmlOutput("PFM Landing Page","Landing Page check","Landing page is opened", "Pass");
	
	return true;
}


//*****************************************************************************************
//* Name : fGuiPFMLogin
//* Description : Enter credentials to login to NetBank
//* Author : Bharat Joshi
//* Input Params : None
//* Return Values : Boolean - Depending on the success
//*****************************************************************************************
public boolean fGuiPFMLogin1()
{
	//Check if the Page is Loaded
	if(CommonFunctions.checkObjectExistance(objOR.objLoginContinueButton)== false){return false;}		
	
	//Verify the Continue Button and click on it
	if(CommonFunctions.fGuiClick(objOR.objLoginContinueButton, "Continue 1 Button")== false){return false;}	
	
	//Verify the Continue Button and click on it
	if(CommonFunctions.fGuiClick(objOR.objLoginContinueButton, "Continue 2  Button")== false){return false;}
	
	//Verify the Agree Check Box and click on it	
	if(CommonFunctions.fGuiClick(objOR.objLoginAgreeCheckbox, "Agree check Box")== false){return false;}
	
	//Click on Done
	if(CommonFunctions.fGuiClick(objOR.objLoginDoneButton, "Done Button	")== false){return false;}
	
	if (CommonFunctions.checkObjectExistance(objOR.oblLoginPageAccountSelected)== false){return false;}
	

	
	return true;
}


}




