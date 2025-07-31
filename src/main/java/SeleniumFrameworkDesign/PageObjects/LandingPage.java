package SeleniumFrameworkDesign.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class LandingPage extends AbstractComponents {

	// Declare a WebDriver variable to interact with the browser
	WebDriver driver;

	// Constructor that accepts a WebDriver instance as an argument
	public LandingPage(WebDriver driver)// This is a constructor, which gets executed when an object of the LandingPage
										// class is created. It receives a WebDriver object (used for browser
										// automation) as an argument.
	{

		super(driver);

		this.driver = driver; // Assigns the incoming driver instance to the class’s own driver variable, so
								// it can be used throughout the class.

		PageFactory.initElements(driver, this); // This is a Selenium utility method that initializes all the @FindBy
												// annotated WebElements. It maps the elements defined with @FindBy to
												// actual elements on the webpage using the provided WebDriver.
	}

	// WebElement representing the email input field on the landing page
	// Located by the ID 'userEmail'
	@FindBy(id = "userEmail")
	WebElement userEmail;

	// WebElement representing the password input field on the landing page
	// Located by the ID 'userPassword'
	@FindBy(id = "userPassword")
	WebElement userPassword;

	@FindBy(id = "login")
	WebElement loginBtn;
	
	@FindBy(xpath="(//*[contains(@class,'toast')]/div)[1]//*")
	WebElement errorMsgXpath;

	public ProductCatalogue loginApplication(String userName, String password) {
		// TODO Auto-generated method stub
		userEmail.sendKeys(userName);
		userPassword.sendKeys(password);
		loginBtn.click();

		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	
	public String errorValidation()
	{
		waitForElementToAppear(errorMsgXpath);
		
		String errormsg = errorMsgXpath.getText().trim();
		return errormsg;
	}

	public String baseurl = "https://rahulshettyacademy.com/client/";

	public void goTo() throws InterruptedException {
		// TODO Auto-generated method stub
		{
			driver.get(baseurl);
			System.out.println(driver.getCurrentUrl());
			Thread.sleep(5000);
		}

	}

}
