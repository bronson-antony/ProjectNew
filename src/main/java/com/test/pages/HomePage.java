package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.test.base.BaseClass;

/**
 * 
 * @author BRONSON
 *
 */
public class HomePage extends BaseClass {

	@FindBy(xpath = "//div[@id='block_top_menu']//li[1]")
	public WebElement womenTopMenu;

	@FindBy(xpath = "//div[@id='block_top_menu']//li[1]//a[@title='Dresses']/../ul//li/a[@title='Summer Dresses']")
	public WebElement subMenu;

	@FindBy(xpath = "//ul[@class='product_list grid row']//li//div[@class='product-image-container']/a[@title='Printed Chiffon Dress']")
	public WebElement printedChiffonDress;

	@FindBy(xpath = "//ul[@class='product_list grid row']//li//div[@class='product-image-container']/a[@title='Printed Chiffon Dress']/..//a[@class='quick-view']")
	public WebElement quickView;

	@FindBy(xpath = "//div[@class='fancybox-wrap fancybox-desktop fancybox-type-iframe fancybox-opened']")
	public WebElement framePopup;

	@FindBy(id = "uniform-group_1")
	public WebElement sizeOption;

	@FindBy(xpath = "//div[@class='primary_block row']//div[@class='box-info-product']//div[@class='box-cart-bottom']//button[@name='Submit']")
	public WebElement addToCart;

	@FindBy(xpath = "//span[@title='Continue shopping']")
	public WebElement continueShopping;

	@FindBy(xpath = "//a[@title='View my shopping cart']")
	public WebElement viewMyCart;

	@FindBy(id = "button_order_cart")
	public WebElement checkOut;

	@FindBy(xpath = "//p[@class='cart_navigation clearfix']//a[@title='Proceed to checkout']")
	public WebElement proceedToCheckout;

	WebDriverWait wait = new WebDriverWait(driver, 15);
	Actions action = new Actions(driver);

	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public void gotoTopMenu() throws InterruptedException {
		action.moveToElement(womenTopMenu).build().perform();

	}

	public void selectSubMenu() {
		wait.until(ExpectedConditions.elementToBeClickable(subMenu)).click();

	}

	public void selectDressFromGridView() {
		action.moveToElement(printedChiffonDress).build().perform();
	}

	public void clickOnQuickView() {
		wait.until(ExpectedConditions.elementToBeClickable(quickView)).click();
	}

	public void selectSize() throws InterruptedException {
		WebElement Frame = driver.findElement(By.className("fancybox-iframe"));
		driver.switchTo().frame(Frame);

		Thread.sleep(4000);

		Select selectSizeOption = new Select(driver.findElement(By.name("group_1")));
		selectSizeOption.selectByVisibleText("M");
	}

	public void clickAddToCart() {

		wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
	}

	public void clickContinueShopping() {
		wait.until(ExpectedConditions.elementToBeClickable(continueShopping)).click();
	}

	public void gotoMyCart() {
		action.moveToElement(viewMyCart).build().perform();
	}

	public void clickCheckOut() {
		wait.until(ExpectedConditions.elementToBeClickable(checkOut)).click();
	}

	public void clickProceedToCheckout() throws InterruptedException {

		wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckout)).click();
	}

}
