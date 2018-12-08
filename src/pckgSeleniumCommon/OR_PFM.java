package pckgSeleniumCommon;

import org.openqa.selenium.WebDriver;

import pckgSeleniumFramework.Reporting;


public class OR_PFM {
	//static Reporting Reporter = new Reporting();
	private Reporting Reporter;
	WebDriver driver;
	
	public OR_PFM(WebDriver webDriver, Reporting Report)
	{
		driver = webDriver;
		Reporter = Report;
	}
	       
	//common objects
	public String objLoginContinueButton="xpath:=.//*[@id='wrapper']//a[contains(.,'Continue')]";
	public String objLoginContinueButton1="xpath:=.//*[@id='wrapper']/div/div/div[2]/div[1]/div/div[3]/div[2]/div/div/a";
	public String objLoginAgreeCheckbox="id:=agree";
	public String objLoginDoneButton ="xpath:=.//*[@id='wrapper']//a[contains(.,'Done')]";
	public String oblLoginPageAccountSelected ="xpath:= .//*[@id='wrapper']//span[contains(.,'3 accounts selected')]";
	public String objLoginPageMonthDefaultSelection ="xpath:= .//*[@id='wrapper']//li[@class='button plain selected'][contains(.,'1mo.')]";
	public String objLoginPage6thMonthSelection = "xpath =.//*[@id='wrapper']//li[@class='button plain'][contains(.,'6mos.')]";
	public String objOverviewDateIconOpen ="xpath = .//*[@id='wrapper']//i[@class='material-icons md-18' and contains(.,'expand_more')]";
	public String objOverviewDateIconClose ="xpath = .//*[@id='wrapper']//i[@class='material-icons md-18' and contains(.,'expand_less')]";
	public String objFromDate ="xpath = .//*[@id='wrapper']//label[contains(.,'From')]//..//following-sibling::div/input[@type='text']";
	public String objToDate="xpath =.//*[@id='wrapper']//label[contains(.,'To')]//..//following-sibling::div/input[@type='text']";
	public String objButtonSixMonths="xpath=.//*[@id='wrapper']//li[@class='button plain' and contains(.,'6mos.')]";
	public String objButtonThreeMonths="xpath=.//*[@id='wrapper']//li[@class='button plain' and contains(.,'3mos.')]";
	public String objButtonOneMonth="xpath=.//*[@id='wrapper']//li[@class='button plain' and contains(.,'1mo.')]";
	public String objOverviewHeaderActive="xpath=.//*[@id='wrapper']//a[@class='overview current']";
	public String objOverviewHeaderInActive="xpath=.//*[@id='wrapper']//a[@class='overview']";
	public String objTagsHeaderInactive="xpath=.//*[@id='wrapper']//a[@class='tags']";
	public String objTagsHeaderActive="xpath=.//*[@id='wrapper']/div//a[@class='tags current']";
	public String objFinancialStatusHeaderInactive="xpath=.//*[@id='wrapper']//a[@class='financial_status']";
	public String objAssessmentTimePeriodEnd="xpath=.//*[@id='wrapper']/div/div[2]/div[@class='date-header']/span[2]";
	public String objAssessmentTimePeriodStart="xpath=.//*[@id='wrapper']/div/div[2]/div[@class='date-header']/span[1]";
	public String objZeroIncomeMessage="xpath=.//*[@id='wrapper']//span/div/div//div[contains(.,'close')]/div[contains(.,'There is no income for selected time period')]";
	public String objZeroExpensesMessage="xpath=.//*[@id='wrapper']//span/div/div//div[contains(.,'close')]/div[contains(.,'There are no expenses for selected time period')]";
	public String objHeading1="xpath=.//*[@id='wrapper']//h2[contains(.,'Income')]";
	public String objHeading2="xpath=.//*[@id='wrapper']//h2[contains(.,'Expenses')]";
	public String objCategorySalary="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Salary and pension income')]";
	public String objCategoryInvstmt="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Investment')]";
	public String objCategoryFinInstt="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'FinancialInstitutes')]";
	public String objCategoryResidence="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Residence')]";
	public String objCategoryCarsandTraffic="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Car and Traffic')]";
	public String objCategoryFoodandShop="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Food and groceries shopping')]";
	public String objCategoryHobbies="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Hobbies and Recreation')]";
	public String objCategoryBills="xpath=.//*[@id='wrapper']//div[2]/div[2]/div[contains(.,'Bills')]";
	public String objTotalIncome="xpath=.//*[@id='wrapper']//div[@class='income']//div[@class='amount']/span";
	public String objTotalIncomeAndroid="xpath=.//*[@id='wrapper']//div[@class='income']//div[@class='amount']";
	public String objTotalExpenses="xpath=.//*[@id='wrapper']//h2[contains(.,'Expenses')]//span[@is='null']";
	public String objTotalExpensesAndroid="xpath=.//*[@id='wrapper']//div[@class='expenses']//div[@class='amount']";
	public String objIncomeInHighlights="xpath=.//*[@id='wrapper']//div[contains(.,'Income')]/a/div[@class='amount__value']/span[@is='null']";
	public String objExpensesInHighlights="xpath=.//*[@id='wrapper']//div[contains(.,'Expenses')]/a/div[@class='amount__value']/span[@is='null']";
	public String objMoneyLeftInHighlights="xpath=.//*[@id='wrapper']//div[contains(.,'Money Left')]//div[@class='amount__value amount__value--positive']/span[@is='null']";
	public String objComparisonCheckbox="xpath=.//*[@id='wrapper']//input[@type='checkbox']";
	public String objDateControl="xpath=.//*[@id='wrapper']//label[contains(.,'To')]";

	public String objOverviewIncome="xpath=.//*[@id='wrapper']//h2[contains(.,'Income')]";
	public String objOverviewIncomeInvestmentCat="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Investment')]";
	public String objOverviewIncomeInvestShareSubCat="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Investment')]//ancestor::div[contains(@class,'category list-item-expanded')]//div[@class='title' and contains(.,'Shares')]";
	public String objOverviewIncomeTags="xpath=.//*[@id='wrapper']//div[@class='subcategory list-item-expanded list-item']//div[@class='list-item-content']//div[@class='transaction income list-item']/a//div[@class='beneficiary']//div[@class='tag']";
	public String objOverviewInvestShareFirstTransaction="xpath=//div[@class='transactions list-view']/div[1]/a[@class='list-item-content']";
	public String objTransactionWindowAddTag="xpath=.//*[@id='wrapper']//div[@class='autocomplete_field']/form/input[@placeholder='Add tag']";
	public String objTransactionWindowAddButton="xpath=.//*[@id='wrapper']//div[@class='autocomplete_field']/form/input[@value='Add']";
	public String objTransactionWindowAddedTag="xpath=.//*[@id='wrapper']//div[@class='autocomplete_field']/a[@class='tag removable']";
	public String objTransactionWindowTagExist="xpath=//div[@class='rc-tooltip-inner']//li[contains(.,'This tag is already present')]";
	public String objTransactionWindowTagName="xpath=.//*[@id='wrapper']//div[@class='autocomplete_field']/a[@class='tag removable' and contains(.,'NewTag')]";
	public String objTransactionWindowCloseButton="xpath=.//*[@id='wrapper']//div/a/i[contains(.,'close')]";
	public String objTransactionWindowViewAllTagsLink="xpath=html/body//a[contains(.,'View all tags')]";
	public String objTransactionWindowDeleteButton="xpath=html/body//a[contains(.,'Delete')]";
	public String objTagsSubCategaoriesNewTag="xpath=.//*[@id='wrapper']//div[@class='category list-item-expanded list-item']//div[contains(.,'NewTag')]//..//div[@class='subcategory list-item-collapsed list-item']";
	public String objTagFirstTransaction="xpath=.//*[@id='wrapper']//div[1][@class='subcategory list-item-expanded list-item']/div[@class='list-item-content']//div[1]/a";

	public String objAccountsSelectionLink="xpath=.//*[@id='wrapper']//span[contains(.,'3 accounts selected')]";


	public String objSavingsAcc1="xpath=.//*[@id='wrapper']//span[@class='title' and contains(.,'Savings Account #1')]";
	public String objSavingsAcc2="xpath=.//*[@id='wrapper']//span[@class='title' and contains(.,'Savings Account #2')]";
	public String objSavingsAcc3="xpath=.//*[@id='wrapper']//span[@class='title' and contains(.,'Savings Account #3')]";
	public String objSelectedSavingsAcc3="xpath=.//*[@id='wrapper']//div[@class='account selected']/span[contains(.,'Savings Account #3')]";
	public String objUnselectedSavingsAcc3="xpath=.//*[@id='wrapper']//div[@class='account']/span[contains(.,'Savings Account #3')]";
	public String objSvgAcc3AmtTop="xpath=.//*[@id='wrapper']//div[@class='account active-accounts']/span[contains(.,'Savings Account #3')]//..//span[@class='balance']/span";
	public String objTwoAcctSelctedAmtTop="xpath=.//*[@id='wrapper']//div[@class='account active-accounts']/span[contains(.,'2 accounts selected')]//..//span[@class='balance']/span";

	public String objAccTotalSelectedAmt="xpath=.//*[@id='wrapper']//span[contains(.,'accounts selected')]//..//span[@class='balance']/span";
	public String objSavingsAcc1Amt="xpath=.//*[@id='wrapper']//div[@class='account-dropdown']//span[contains(.,'Savings Account #1')]//..//span[@class='balance']/span";
	public String objSavingsAcc2Amt="xpath=.//*[@id='wrapper']//div[@class='account-dropdown']//span[contains(.,'Savings Account #2')]//..//span[@class='balance']/span";
	public String objSavingsAcc3Amt="xpath=.//*[@id='wrapper']//div[@class='account-dropdown']//span[contains(.,'Savings Account #3')]//..//span[@class='balance']/span";
	public String objHeaderSelectedSavingsAcc3="xpath=.//*[@id='wrapper']//span[@class='title']/span[contains(.,'Savings Account #3')]";
	public String objHeaderSelectedSavingsAcc2="xpath=.//*[@id='wrapper']//span[@class='title']/span[contains(.,'Savings Account #2')]";
	public String objHeaderSelectedSavingsAcc1="xpath=.//*[@id='wrapper']//span[@class='title']/span[contains(.,'Savings Account #1')]";
	public String objNoAccountSelected="xpath=.//*[@id='wrapper']//span[@class='title']/span[contains(.,'No accounts selected')]";
	public String objNoTxnMsg="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'You have no transactions for selected time period')]";
	public String objZeroBalance="xpath=.//*[@id='wrapper']//span[@class='balance']/span[contains(.,'€0.00')]";
	public String objSharesIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Shares')]//..//div[@class='amount']/span";
	public String objPropertyIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Property')]//..//div[@class='amount']/span";
	public String objInterestIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Interest')]//..//div[@class='amount']/span";
	public String objArrearsIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Arrears')]//..//div[@class='amount']/span";
	public String objIncentiveIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Incentive')]//..//div[@class='amount']/span";
	public String objWagesIncome="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Wages')]//..//div[@class='amount']/span";
	public String objGamesExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Games Accesories')]//..//div[@class='amount']/span";
	public String objRestrauExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Cafes and Restaurants')]//..//div[@class='amount']/span";
	public String objNovelsExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Novels')]//..//div[@class='amount']/span";
	public String objNovelsExpenseAndroid="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Novels')]//..//div[@class='amount']";
	public String objRentExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Rent')]//..//div[@class='amount']/span";
	public String objRentExpenseAndroid="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Rent')]//..//div[@class='amount']";
	public String objFuelsExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Fuels')]//..//div[@class='amount']/span";
	public String objGroceryExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Grocery stores and supermarkets')]//..//div[@class='amount']/span";
	public String objGroceryExpenseAndroid="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Grocery stores and supermarkets')]//..//div[@class='amount']";
	public String objKiosksExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Kiosks')]//..//div[@class='amount']/span";
	public String objKiosksExpenseAndroid="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Kiosks')]//..//div[@class='amount']";
	public String objElectricExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Electric')]//..//div[@class='amount']/span";
	public String objPropertyTaxExpense="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'Property Tax')]//..//div[@class='amount']/span";
	public String objInvestmentIncome="xpath=.//*[@id='wrapper']//div[contains(.,'Investment')]/div[@class='amount']/span[@is='null']";
	public String objInvestmentIncomeAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Investment')]/div[@class='amount']";
	public String objSalaryAndPensionIncome="xpath=.//*[@id='wrapper']//div[contains(.,'Salary and pension income')]/div[@class='amount']/span[@is='null']";
	public String objSalaryAndPensionIncomeAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Salary and pension income')]/div[@class='amount']";
	public String objFinancialInstitutesIncome="xpath=.//*[@id='wrapper']//div[contains(.,'FinancialInstitutes')]/div[@class='amount']/span[@is='null']";
	public String objFinancialInstitutesIncomeAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'FinancialInstitutes')]/div[@class='amount']";
	public String objHobbiesExpenses="xpath=.//*[@id='wrapper']//div[contains(.,'Hobbies and Recreation')]/div[@class='amount']/span[@is='null']";
	public String objHobbiesExpensesAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Hobbies and Recreation')]/div[@class='amount']";
	public String objResidenceExpenses="xpath=.//*[@id='wrapper']//div[contains(.,'Residence')]/div[@class='amount']/span[@is='null']";
	public String objResidenceExpensesAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Residence')]/div[@class='amount']";
	public String objCarandTrafficExpenses="xpath=.//*[@id='wrapper']//div[contains(.,'Car and Traffic')]/div[@class='amount']/span[@is='null']";
	public String objCarandTrafficExpensesAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Car and Traffic')]/div[@class='amount']";
	public String objBillsExpenses="xpath=.//*[@id='wrapper']//div[contains(.,'Bills')]/div[@class='amount']/span[@is='null']";
	public String objBillsExpensesAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Bills')]/div[@class='amount']";
	public String objGroceryExpenses="xpath=.//*[@id='wrapper']//div[contains(.,'Food and groceries shopping')]/div[@class='amount']/span[@is='null']";
	public String objGroceryExpensesAndroid="xpath=.//*[@id='wrapper']//div[contains(.,'Food and groceries shopping')]/div[@class='amount']";

	public String objPopupTxnHeader="xpath=.//*[@id='wrapper']//h1[@class='overlay-title' and contains(.,'Transaction')]";
	public String objPopupTxnAmt="xpath=.//*[@id='wrapper']//p[@class='amount']/span[@is='null']";
	public String objPopupTxnAmtAndroid="xpath=.//*[@id='wrapper']//p[@class='amount']";
	public String objPopupTxnBeneficiary="xpath=.//*[@id='wrapper']//p[@class='to']";
	public String objPopupClose="xpath=.//*[@id='wrapper']//a[@class='close close_x']";

	public String objTxnTags="xpath=.//*[@id='wrapper']//div[1]/a//div[@Class='tag']";

	public String objExpensesTxnBeneficiaries="xpath=.//*[@id='wrapper']//div[@class='subcategory list-item-expanded list-item']//div[@class='beneficiary']";
	public String objExpensesTxnBeneficiariesxpath=".//*[@id='wrapper']//div[@class='subcategory list-item-expanded list-item']//div[@class='beneficiary']";
	public String objMostExpensesBenefxpath=".//*[@id='wrapper']//span[contains(.,'Most expenses')]/div//div[@class='beneficiary']";	

	public String objExpensesGamesTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Games Accesories')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesRestrauTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Cafes and Restaurants')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesNovelsTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Novels')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesRentTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Rent')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesFuelsTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Fuels')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesElectricTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Electric')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesPropertyTaxTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Property Tax')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesGrcryTaxTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Grocery stores')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";
	public String objExpensesKiosksTaxTxnBeneficiaryxpath=".//*[@id='wrapper']//div[@class='list-item-toggle' and contains(.,'Kiosks')]//..//following-sibling::div[@class='list-item-content']//div[@class='transaction expenses list-item']//div[@class='beneficiary']";



	public String objOverviewCatAutomation="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'TESTAUTOMATION')]";
	public String objOverviewSubCatTestPFM="xpath=.//*[@id='wrapper']//div[@class='title' and contains(.,'TESTPFM')]";
	public String objTransactionChangeCategory="xpath=.//*[@id='wrapper']//a[@class='change-category-split']/span[contains(.,'Change category')]";
	public String objBiggestTransactionLabel="xpath =.//*[@id='wrapper']//h2[contains(.,'Biggest expenses')]";
	public String objTopTabMobile = "xpath =.//*[@id='wrapper']//span[contains(.,'Top')]";
	public String objBiggestTransactionLabelMobile="xpath =.//*[@id='wrapper']//h2[contains(.,'Biggest transactions')]";


	//Time Based Comparision
	public String objTimeBasedComparisonSectionHeading="xpath=.//*[@id='wrapper']//h2[contains(.,'Time based Comparison')]";
	public String objComareToDropdown="xpath=.//*[@id='dropdown']";
	public String objCompareToDropdownYearSelection="xpath=.//*[@id='dropdown']//option[@Value='previous_year' and contains(.,'Previous Year')]";
	public String objCalender = "xpath = .//*[@id='wrapper']//li[@class='button plain selected']";
	public String objTransactionSplitButton = "xpath =.//*[@id='wrapper']//a[@class='change-category-split']/span[contains(.,'Split')]";
	public String objTransactionSplitDialogBackButton = "xpath = .//*[@id='wrapper']//h1//i[@class= 'material-icons md-36']";
	public String objTransactionSplitDialogHeader = "xpath = .//*[@id='wrapper']//h1[contains(.,'Split Transaction')]";
	public String objTransactionSplitDialogCloseButton = "xpath = .//*[@id='wrapper']//i[@class='material-icons md-36'][contains(.,'close')]";
	public String objTransactionSplitDialogSplitButton = "xpath = .//*[@id='wrapper']//input[@value='Split']";
	public String objTransactionSplitDialogAmountTextBox = "xpath = .//*[@id='wrapper']//input[@placeholder='Enter amount']";
	public String objTransactionSplitDialogAmount = "xpath = .//*[@id='wrapper']//div[@class='category__amount']/span";

//Monthly Budget
	public String objSetSubcategoryBudgetWindowCloseICon="xpath=.//*[@id='wrapper']//a[@class='close close_x']/i[@class='material-icons md-36' and contains(.,'close')]";
	public String objSubcategoryBudgetWindowCurrentLabel="xpath=.//*[@id='wrapper']//div[@class='category__manage__budget']//span[@class='category__month' and contains(.,'Current')]";
	public String objSetSubcategoryBudgetWindowNoBudgetLabel="xpath=.//*[@id='wrapper']//div[@class='category__manage__budget']//span[@class='category__monthly__budget' and contains(.,'No budget')]";
	public String objSetSubcategoryBudgetWindowCurrentMonthBudget="xpath=.//*[@id='wrapper']//div[@class='category__manage__budget']//span[@class='category__no__budget']";
	public String objSetSubcategoryBudgetWindowSetBudgetButton="xpath=.//*[@id='wrapper']//div[@class='overlay-content']//div[@class='adjust__category']";
	public String objSetSubcategoryBudgetWindowLeftArrowIcon="xpath=.//*[@id='wrapper']//div//..//h1//i[@class='material-icons md-36']";
	public String objSetBudgetIon = "xpath=.//*[@id='wrapper']//div[@class='adjust__category']//span[@class='adjust__category__text' and contains(.,'Set budget')]";
	public String objSetSubcategoryBudgetWindowCurrentLabel = "xpath=.//*[@id='wrapper']//div[@class='overview']//div[@class='category__display__splits']//span[@class='category__month' and contains(.,'Current')]";
	public String objSetSubcategoryBudgetWindowMonthlyBudgetAmount = "xpath=.//*[@id='wrapper']//div[@class='overview']//div[@class='category__display__splits']//span[@class='category__monthly__budget' and contains(.,'€0.00')]";
	public String objSetSubcategoryBudgetWindowMonthlyExpenseAmount = "xpath=.//*[@id='wrapper']//div[@class='overlay open setcategory-window']//div[@class='overlay open setcategory-window']//div[@class='category__display__splits']//span[@class='category__set__budget']";
	public String objSetSubcategoryBudgetWindowProgressLine ="xpath=.//*[@id='wrapper']//div[@class='overview']//div[@class='bar']";
	public String objSetSubcategoryBudgetWindowMinusSign ="xpath=.//*[@id='wrapper']//div[@class='addSubtractBudgetamount']/span[@class='subtract' and contains(.,'-')]";
	public String objSetSubcategoryBudgetWindowPlusSign ="xpath=.//*[@id='wrapper']//div[@class='addSubtractBudgetamount']/span[@class='add' and contains(.,'+')]";
	public String objSetSubcategoryBudgetWindowSetAmount ="xpath=.//*[@id='wrapper']//div[@class='addSubtractBudgetamount']/span[@class='display']";
	public String objSetSubcategoryBudgetWindowDoneButton ="xpath=.//*[@id='wrapper']//div[@class='category__done-button']//input[@class='button' and  @value='Done']";
	public String objSetSubcategoryBudgetWindowMonthlyExpenseAmount1=" xpath=.//*[@id='wrapper']//div[@class='overlay open setcategory-window']//div[@class='overlay open setcategory-window']//div[@class='category__display__splits']/span[2]";





	//Split Transanction

	public String objCalenderExpandMore = "xpath =  .//*[@id='wrapper']//li[@class='button plain']/i[contains(.,'expand_more')]";
	public String objCalenderExpandLess = "xpath =  .//*[@id='wrapper']//li[@class='button plain']/i[contains(.,'today')]";
		
	public String objTransactionSplitDialogDoneButton = "xpath = .//*[@id='wrapper']//input[@value='Done']";
	public String objTransactionSplitIcon = "xpath = .//*[@id='wrapper']//p[@class='amount']/i[contains(.,'call_split')]";
	public String objTransactionSplitDeleteToolTip = "xpath = .//a[contains(.,'Delete')]";
	public String objTransactionSplitOriginalAmt = "xpath = .//*[@id='wrapper']//div[@class= 'category__original-amount']/span";
	public String objTransactionSplitMoreThan10ErrorMessage = "xpath = .//*[@id='wrapper']//label[contains(.,'** Maximum 10 splits allowed from Parent transaction.')]";

	       

	}


