package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opecart.constants.AppConstant;
import com.qa.opencart.exception.FrameworkException;

import io.qameta.allure.Step;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionManager optionManager;
	public static String highlight;
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	@Step("init the driver using properties : {0}")
	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		// System.out.println("BrowserName is : " + browserName);
		log.info("browser name is : " + browserName);
		optionManager = new OptionManager(prop);
		highlight = prop.getProperty("highlight");
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionManager.getChromeOptions()));
			// driver = new ChromeDriver(optionManager.getChromeOptions());
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionManager.getFirefoxOptions()));
			// driver = new FirefoxDriver(optionManager.getFirefoxOptions());
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionManager.getEdgeOptions()));
			// driver = new EdgeDriver(optionManager.getEdgeOptions());
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			// driver = new SafariDriver();
			break;
		default:
			// System.out.println("Please pass correct browser : " + browserName);
			log.error("plz pass the valid browser name.." + browserName);
			throw new FrameworkException("==Invalid browser==");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	public Properties initProp() {
		String envName = System.getProperty("env");
		System.out.println("running test suite on env: " + envName);
		FileInputStream ip = null;
		prop = new Properties();

		try {
			if (envName == null) {
				// System.out.println("no env is passed, hence running test suite on qa env..");
				log.warn("no env is passed, hence running test suite on qa env..");
				ip = new FileInputStream(AppConstant.CONFIG_QA_PROP_FILE_PATH);
			} else {
				switch (envName.trim().toLowerCase()) {
				case "qa":
					ip = new FileInputStream(AppConstant.CONFIG_QA_PROP_FILE_PATH);
					break;
				case "dev":
					ip = new FileInputStream(AppConstant.CONFIG_DEV_PROP_FILE_PATH);
					break;
				case "stage":
					ip = new FileInputStream(AppConstant.CONFIG_STAGE_PROP_FILE_PATH);
					break;
				case "uat":
					ip = new FileInputStream(AppConstant.CONFIG_UAT_PROP_FILE_PATH);
					break;
				case "prod":
					ip = new FileInputStream(AppConstant.CONFIG_PROD_PROP_FILE_PATH);
					break;

				default:
					// System.out.println("plz pass the right env name..." + envName);
					log.error("plz pass the right env name..." + envName);

					throw new FrameworkException("===INVALID ENV===");
				}
			}

			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * get driver using threadlocal
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + "_" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}

}
