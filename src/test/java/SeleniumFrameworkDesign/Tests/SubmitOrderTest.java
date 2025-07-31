package SeleniumFrameworkDesign.Tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import SeleniumFrameworkDesign.PageObjects.CartPage;
import SeleniumFrameworkDesign.PageObjects.CheckoutPage;
import SeleniumFrameworkDesign.PageObjects.ConfirmationPage;
import SeleniumFrameworkDesign.PageObjects.LandingPage;
import SeleniumFrameworkDesign.PageObjects.OrderPage;
import SeleniumFrameworkDesign.PageObjects.ProductCatalogue;
import SeleniumFrameworkDesign.TestComponents.BaseTest;
import SeleniumFrameworkDesign.TestComponents.Retry;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {
	String productName = "ADIDAS ORIGINAL";

	@Test(dataProvider = "getData", groups = { "Purchase" }, retryAnalyzer = Retry.class)
	public void submitOrder(String username, String password, String productName)
			throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(username, password);

		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(productName);

		CartPage cp = productCatalogue.goToCart();

		AssertJUnit.assertTrue(cp.verifycartElements(productName));

		CheckoutPage checkoutPage = cp.clickCheckoutBtn();

		checkoutPage.selectCountry("India");
		ConfirmationPage confirmPage = checkoutPage.submitOrder();

		String conformationMessage = confirmPage.getConfirmationMessage();

		AssertJUnit.assertTrue(conformationMessage.equalsIgnoreCase("thankyou for the order."));

	}

	@Test(dataProvider = "getDataFromJson", groups = { "Purchase" }, retryAnalyzer = Retry.class)
	public void submitOrderHash(HashMap<String, String> input) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProductToCart(input.get("productName"));

		CartPage cp = productCatalogue.goToCart();

		AssertJUnit.assertTrue(cp.verifycartElements(input.get("productName")));

		CheckoutPage checkoutPage = cp.clickCheckoutBtn();

		checkoutPage.selectCountry("India");
		ConfirmationPage confirmPage = checkoutPage.submitOrder();

		String conformationMessage = confirmPage.getConfirmationMessage();

		AssertJUnit.assertTrue(conformationMessage.equalsIgnoreCase("thankyou for the order."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void verifyOrder() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("emraan.coolm@gmail.com", "Abcd1234$");
		OrderPage order = productCatalogue.goToOrders();
		Assert.assertTrue(order.verifyOrder(productName));

	}

	@DataProvider
	public Object[][] getData() {
		return new Object[][] { { "emraan.coolm@gmail.com", "Abcd1234$", "ADIDAS ORIGINAL" },
				{ "emraan.coolm@gmail.com", "Abcd1234$", "ZARA COAT 3" } };

	}

	@DataProvider
	public Object[][] getDataUsingHashMap() {

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "emraan.coolm@gmail.com");
		map.put("password", "Abcd1234$");
		map.put("productName", "ADIDAS ORIGINAL");

		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("email", "emraan.coolm@gmail.com");
		map2.put("password", "Abcd1234$");
		map2.put("productName", "ADIDAS ORIGINAL");
		return new Object[][] { { map }, { map2 } };

	}

	@DataProvider
	public Object[][] getDataUsingHashMap2() {
		HashMap<String, String> map1 = new HashMap<>();
		map1.put("email", "emraan.coolm@gmail.com");
		map1.put("password", "Abcd1234$");
		map1.put("productName", "ADIDAS ORIGINAL");

		HashMap<String, String> map2 = new HashMap<>();
		map2.put("email", "emraan.coolm@gmail.com");
		map2.put("password", "Abcd1234$");
		map2.put("productName", "ZARA COAT 3");

		return new Object[][] { { map1 }, { map2 } };
	}

	@DataProvider
	public Object[][] getDataFromJson() throws IOException {
		List<HashMap<String, String>> data = getJSONDataToMap(System.getProperty("user.dir")
				+ "//src//test//java//SeleniumFrameworkDesign//data//PurchaseOrder.json");

		return new Object[][] { { data.get(0) }, { data.get(1) } };

	}

}
