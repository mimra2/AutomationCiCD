package SeleniumFrameworkDesign.Tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumFrameworkDesign.PageObjects.CartPage;
import SeleniumFrameworkDesign.PageObjects.CheckoutPage;
import SeleniumFrameworkDesign.PageObjects.ConfirmationPage;
import SeleniumFrameworkDesign.PageObjects.ProductCatalogue;
import SeleniumFrameworkDesign.TestComponents.BaseTest;

public class Errorvalidation extends BaseTest {

	@Test(groups = {"ErrorHandling"})
	public void loginErrorValidation() throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication("emraan.coolm@gmail.com", "Abcd123$");

		AssertJUnit.assertEquals("Incorrect email or password.", landingPage.errorValidation());
	}

	@Test
	public void proderrorVal() throws IOException, InterruptedException {

		String productName = "ADIDAS ORIGINAL";

		ProductCatalogue productCatalogue = landingPage.loginApplication("emraan.coolm@gmail.com", "Abcd1234$");

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
}
