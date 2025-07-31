package SeleniumFrameworkDesign.Tests;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import SeleniumFrameworkDesign.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String args[]) throws InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://rahulshettyacademy.com/client/");

		LandingPage lp = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("emraan.coolm@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Abcd1234$");
		driver.findElement(By.id("login")).click();

		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

		WebElement prod = products.stream()
				.filter(product -> product.findElement(By.cssSelector("b")).getText().equals("ADIDAS ORIGINAL"))
				.findAny().orElse(null);

		System.out.println(prod.findElement(By.tagName("b")).getText());
		Thread.sleep(2000);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='toast-container']")));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@routerlink='/dashboard/cart']")));
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

		// WebElement cart = driver.findElement(By.xpath("//h1[text()='My Cart']"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='My Cart']")));

		List<WebElement> cartProducts = driver.findElements(By.xpath("//div[@class='cartSection']//h3"));

		Boolean b = cartProducts.stream().anyMatch(s -> s.getText().equalsIgnoreCase("Adidas Original"));

		Assert.assertTrue(b);

		System.out.println("Adidas Original " + "Product is present in the cart");

		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Select Country']")));

		// div[@class='cartSection']//h3

		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "India").build()
				.perform();

		// action.sendKeys(inputElement, Keys.ENTER).build().perform();
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[span[contains(text(), 'India')]])[2]")));

		driver.findElement(By.xpath("(//button[span[contains(text(), 'India')]])[2]")).click();

		action.click(driver.findElement(By.xpath("//a[contains(text(),'Place Order')]"))).build().perform();

		// SoftAssert sa = new SoftAssert();

		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText().trim();

		Assert.assertTrue(confirmMessage.equalsIgnoreCase("thankyou for the order."));

		System.out.println("test");

		// a[contains(text(),'Place Order')]

		// driver.quit();

	}
}
