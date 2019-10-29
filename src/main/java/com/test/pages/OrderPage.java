package com.test.pages;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.test.base.BaseClass;

public class OrderPage extends BaseClass {

	@FindBy(id = "email_create")
	public WebElement emailField;

	@FindBy(id = "SubmitCreate")
	public WebElement createAccount;

	@FindBy(xpath = "//input[@id='id_gender1']")
	public WebElement selectGender;

	@FindBy(xpath = "//input[@id='customer_firstname']")
	public WebElement customerFirstName;

	@FindBy(xpath = "//input[@id='customer_lastname']")
	public WebElement customerLastName;

	@FindBy(xpath = "//input[@id='passwd']")
	public WebElement accountPassword;

	@FindBy(xpath = "//input[@id='address1']")
	public WebElement customerAddress1;

	@FindBy(xpath = "//input[@id='city']")
	public WebElement customerCity;

	@FindBy(id = "id_state")
	public WebElement state;

	@FindBy(id = "postcode")
	public WebElement postalCode;

	@FindBy(id = "id_country")
	public WebElement country;

	@FindBy(id = "phone_mobile")
	public WebElement phoneNumber;

	@FindBy(id = "submitAccount")
	public WebElement submitAccount;

	@FindBy(id = "alias")
	public WebElement aliasEmailAddress;

	@FindBy(xpath = "//p[@class='cart_navigation clearfix']//button[@type='submit']")
	public WebElement proceedToCheckout;

	@FindBy(xpath = "//div[@class='order_carrier_content box']//input[@type='checkbox']")
	public WebElement clickCheckBox;

	@FindBy(xpath = "//table[@id='cart_summary']/tbody//td[2]/p[@class='product-name']")
	public WebElement productNameLocated;

	@FindBy(xpath = "//table[@id='cart_summary']/tbody//td[5]")
	public WebElement productQuantityLocated;

	WebDriverWait wait = new WebDriverWait(driver, 15);
	Actions action = new Actions(driver);

	public OrderPage() {
		PageFactory.initElements(driver, this);
	}

	public void enterEmail() {

		String generatedValue = RandomStringUtils.randomAlphabetic(10);
		String username = generatedValue.concat("@gmail.com");
		wait.until(ExpectedConditions.elementToBeClickable(emailField)).sendKeys(username);

	}

	public void createAccount() {
		wait.until(ExpectedConditions.elementToBeClickable(createAccount)).click();
	}

	public void selectCustomerGender() {
		wait.until(ExpectedConditions.elementToBeClickable(selectGender)).click();
	}

	public void enterCustomerInformation(String firstName, String lastName, String Password, String Passwordaddress,
			String city, String postCode, String phone, String alianEmail) {

		wait.until(ExpectedConditions.elementToBeClickable(customerFirstName)).sendKeys(firstName);
		wait.until(ExpectedConditions.elementToBeClickable(customerLastName)).sendKeys(lastName);
		wait.until(ExpectedConditions.elementToBeClickable(accountPassword)).sendKeys(Password);
		wait.until(ExpectedConditions.elementToBeClickable(customerAddress1)).sendKeys(Passwordaddress);
		wait.until(ExpectedConditions.elementToBeClickable(customerCity)).sendKeys(city);
		Select selectSizeOption = new Select(driver.findElement(By.id("id_state")));
		selectSizeOption.selectByVisibleText("California");
		wait.until(ExpectedConditions.elementToBeClickable(postalCode)).sendKeys(postCode);
		wait.until(ExpectedConditions.elementToBeClickable(phoneNumber)).sendKeys(phone);
		wait.until(ExpectedConditions.elementToBeClickable(aliasEmailAddress)).clear();
		wait.until(ExpectedConditions.elementToBeClickable(aliasEmailAddress)).sendKeys(alianEmail);

	}

	public void submitAccountDetails() {
		wait.until(ExpectedConditions.elementToBeClickable(submitAccount)).click();
	}

	public void agreeTerms() {
		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout)).click();
		try {
			wait.until(ExpectedConditions.elementToBeClickable(clickCheckBox)).click();
		} catch (Exception e) {
			action.moveToElement(clickCheckBox).click().build().perform();
		}

		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout)).click();

	}

	public void confirmOrder(String productName, String productQuantity) {

		Assert.assertEquals(productNameLocated.getText(), productName);
		log.debug("Product Name : " + productNameLocated.getText());

		Assert.assertEquals(productQuantityLocated.getText(), productQuantity);
		log.debug("Product quantity  : " + productQuantityLocated.getText());

	}

}
