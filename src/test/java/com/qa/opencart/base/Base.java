package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import com.qa.opecart.pages.CommonPage;
import com.qa.opecart.pages.HomePage;
import com.qa.opecart.pages.LoginPage;
import com.qa.opecart.pages.ProductInfoPage;
import com.qa.opecart.pages.RegistrationPage;
import com.qa.opecart.pages.SearchResultsPage;
import com.qa.opencart.factory.DriverFactory;

//@Listeners(ChainTestListener.class)
public class Base {

	WebDriver driver;
	DriverFactory df;
	protected LoginPage loginPage;
	protected HomePage homePage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected Properties prop;
	protected CommonPage commonPage;
	protected RegistrationPage registartionPage;

	@Parameters({ "browser" })
	// @Parameters({ "browser", "browserversion", "testname" })
	@BeforeTest(description = "setup: init the driver and properties")
	// public void setup(String browserName, String browserVersion, String testName)
	// {
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		if (browserName != null) {
			prop.setProperty("browser", browserName);
//			prop.setProperty("browserversion", browserVersion);
//			prop.setProperty("testname", testName);
		}
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		registartionPage = new RegistrationPage(driver);
		commonPage = new CommonPage(driver);
		ChainPluginService.getInstance().addSystemInfo("Build#", "1.0");
		ChainPluginService.getInstance().addSystemInfo("Headless#", prop.getProperty("headless"));
		ChainPluginService.getInstance().addSystemInfo("Incognito#", prop.getProperty("incognito"));
		ChainPluginService.getInstance().addSystemInfo("Owner#", "Naveen Automation Labs");
	}

	@AfterMethod
	public void attachScreenshot(ITestResult result) {
		if (!result.isSuccess()) {// only for failuer test cases
			// ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
			// ChainTestListener.embed(DriverFactory.getScreenshotByte(), "image/png");
			ChainTestListener.embed(DriverFactory.getScreenshotBase64(), "image/png");
		}
	}

	@AfterTest(description = "closing the browser...")
	public void tearDown() {
		driver.quit();
	}

}
