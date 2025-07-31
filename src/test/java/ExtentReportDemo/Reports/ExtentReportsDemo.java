package ExtentReportDemo.Reports;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportsDemo {

	ExtentReports extent;
	WebDriver driver;
	ExtentTest test;

	@BeforeTest
	public void config() {
		String path = System.getProperty("user.dir") + "//src//test//java//ExtentReportDemo//Reports//index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test results");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
		extent.flush();
	}

	@Test
	public void reportDemo() {

		test = extent.createTest("InitialDemo");
		driver = new ChromeDriver();
		driver.get("https://google.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		/*
		 * SoftAssert soft = new SoftAssert(); soft.assertTrue(false);
		 */
		test.addScreenCaptureFromPath(
				System.getProperty("user.dir") + "//src//test//java//ExtentReportDemo//Reports//screenshot.jpg");
		test.fail("this test has failed");
	}

}
