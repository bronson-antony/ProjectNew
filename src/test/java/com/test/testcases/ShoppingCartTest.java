package com.test.testcases;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.test.base.BaseClass;
import com.test.pages.OrderPage;
import com.test.pages.HomePage;
import com.test.utils.TestUtil;
/**
 * Test class from where execution starts
 * @author BRONSON
 *
 */
public class ShoppingCartTest extends BaseClass {
	HomePage homePage;
	OrderPage orderPage;
	TestUtil util;

	String sheetName = "CustomerInfo";
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir") + "/src/main/resources/testdata.xlsx";

	public ShoppingCartTest() {
		super();
	}
	
	@DataProvider
	public Object[][] getAccountDetailsTestData(){
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@BeforeTest
	public void setUp() {
		initialization();
		homePage = new HomePage();
		orderPage = new OrderPage();

	}

	@DataProvider
	public Object[][] getAccountTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 1)
	public void selectCategory() throws InterruptedException {
		homePage.gotoTopMenu();
		homePage.selectSubMenu();
		// Assert.assertEquals(title, "# Customer Relationship Software");
	}

	@Test(priority = 2)
	public void addDressToCart() throws InterruptedException {
		homePage.selectDressFromGridView();
		homePage.clickOnQuickView();

		homePage.selectSize();
		homePage.clickAddToCart();
		homePage.clickContinueShopping();
		homePage.gotoMyCart();
		homePage.clickCheckOut();
		homePage.clickProceedToCheckout();
	}
	
	
	
	@Test(priority = 3)
	public void enterEmailAndSubmit() {
		System.out.println("Title****" + driver.getTitle());
		orderPage.enterEmail();
		orderPage.createAccount();

	}

	@Test(priority = 4, dataProvider = "getAccountTestData")
	public void validateCreateNewAccount(String firstName, String lastName, String Password, String Passwordaddress,
			String city, String postCode, String phone, String alianEmail) {

		orderPage.enterCustomerInformation(firstName, lastName, Password, Passwordaddress, city, postCode, phone,
				alianEmail);

	}

	@Test(priority = 5)
	public void submitInfo() {
		orderPage.submitAccountDetails();
	}
	
	@Test(priority = 6)
	public void agreeTermsOfService() {
		orderPage.agreeTerms();
	}
	
	@Test(priority = 7)
	public void verifyOrder() {
		String productName=prop.getProperty("productName");
		String productQuantity=prop.getProperty("quantity");
		orderPage.confirmOrder(productName,productQuantity);
	
	}
	
	@AfterClass
	public void endTest() {
		tearDown();
	}


	
}
