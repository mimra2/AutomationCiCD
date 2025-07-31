package SeleniumFrameworkDesign.PageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFrameworkDesign.AbstractComponents.AbstractComponents;

public class ProductCatalogue extends AbstractComponents {

	WebDriver driver;

	public ProductCatalogue(WebDriver driver) {

		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

	@FindBy(xpath = "//button[contains(@routerlink,'/dashboard/myorders')]")
	WebElement OrderPageXpath;

	By productsBy = By.cssSelector(".mb-3");

	By addToCartBtn = By.cssSelector(".card-body button:last-of-type");

	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {

		waitForElementToAppear(productsBy);
		return products;
	}

	public WebElement getProductByname(String productname) {
		return getProductList().stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals(productname)).findAny()
				.orElse(null);

	}

	public void addProductToCart(String productName) throws InterruptedException {
		getProductByname(productName).findElement(addToCartBtn).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
		Thread.sleep(1000);
	}

	public OrderPage goToOrders() {
		waitForElementToAppear(OrderPageXpath);
		OrderPageXpath.click();

		OrderPage orderpage = new OrderPage(driver);
		return orderpage;
	}

}
