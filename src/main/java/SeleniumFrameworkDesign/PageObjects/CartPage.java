package SeleniumFrameworkDesign.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class CartPage extends AbstractComponents {

	WebDriver driver;

	public CartPage(WebDriver driver)

	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@class='cartSection']//h3")
	List<WebElement> productTitles;

	@FindBy(xpath = "//button[contains(text(),'Checkout')]")
	WebElement checkoutBtn;

	public boolean verifycartElements(String productName) {

		return productTitles.stream().anyMatch(s -> s.getText().equalsIgnoreCase(productName));
	}

	public CheckoutPage clickCheckoutBtn() {
		checkoutBtn.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}

	

	// Assert.assertTrue(b);

	// System.out.println("Adidas Original " + "Product is present in the cart");

	// driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();

}
