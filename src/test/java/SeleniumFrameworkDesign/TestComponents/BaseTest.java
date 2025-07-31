package SeleniumFrameworkDesign.TestComponents;

import org.testng.annotations.AfterMethod;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SeleniumFrameworkDesign.PageObjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;

	public LandingPage landingPage;

	public WebDriver initializeDriver() throws IOException {

		Properties prop = new Properties();
		System.out.println(System.getProperty("user.dir"));
		FileInputStream fileInput = new FileInputStream(System.getProperty("user.dir")
				+ "//src/main//java//SeleniumFrameworkDesign//resources//GlobalData.properties");
		prop.load(fileInput);

		// String browserName = System.getProperty("browser") != null ?
		// System.getProperty("browser")
		// : prop.getProperty("browser");

		String browserName;
		if (System.getProperty("browser") != null) {
			browserName = System.getProperty("browser");
		} else {
			browserName = prop.getProperty("browser");
		}

		if (browserName.contains("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			if (browserName.contains("headless")) {

				options.addArguments("--headless");
			}
			// not safe for production
			options.addArguments("--disable-features=SSLCommonNameMismatchHandling");

			driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440, 900));

			// WebDriverManager.chromedriver().setup();

			// driver.manage().deleteAllCookies();
			// ((JavascriptExecutor)driver).executeScript("window.localStorage.clear();
			// window.sessionStorage.clear();");

		}

		else if (browserName.equalsIgnoreCase("firefox")) {

			System.out.println("firefox is executed");
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();

		}

		else if (browserName.equalsIgnoreCase("edge")) {

		}

		else if (browserName.equalsIgnoreCase("safari")) {

			driver = new SafariDriver();

		}

		// driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();
		return driver;

	}

	public List<HashMap<String, String>> getJSONDataToMap(String filePath) throws IOException

	{

		// ReadingJson to String
		String jSonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// String to Hashmap
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jSonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;

	}

	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		/*
		 * File file = new File( System.getProperty("user.dir") + "//Reports" +
		 * testCaseName + ".png"); FileUtils.copyFile(source, file); return
		 * System.getProperty("user.dir") + "//Reports" + testCaseName + ".png";
		 */

		String destinationPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator
				+ testCaseName + ".png";
		File file = new File(destinationPath);

		// Ensure the directory exists
		file.getParentFile().mkdirs();

		FileUtils.copyFile(source, file);
		return destinationPath;

	}

	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException, InterruptedException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {

		driver.quit();
	}

}