package SeleniumFrameworkDesign.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class CheckoutPage extends AbstractComponents {

	WebDriver driver;

	public CheckoutPage(WebDriver driver)

	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//input[@placeholder='Select Country']")
	WebElement selectCountryxp;

	@FindBy(xpath = "(//button[span[contains(text(), 'India')]])[2]")
	WebElement selectItemfromDropDown;

	@FindBy(css = ".action__submit")
	WebElement submitBtn;

	By country = By.xpath("(//button[span[contains(text(), 'India')]])[2]");

	public void selectCountry(String countryName) {
		Actions action = new Actions(driver);
		action.sendKeys(selectCountryxp, countryName).build().perform();
		waitForElementToAppear(country);
		selectItemfromDropDown.click();
	}

	public ConfirmationPage submitOrder() throws InterruptedException {
		
		
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitBtn);
		Thread.sleep(500); // let the page adjust after scroll
		submitBtn.click();

		
	
		ConfirmationPage confirmPage = new ConfirmationPage(driver);
		return confirmPage;

	}

}
