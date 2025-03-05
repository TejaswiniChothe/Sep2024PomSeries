package com.qa.opencart.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionManager {

	private Properties prop;
	private ChromeOptions co;
	private FirefoxOptions fo;
	private EdgeOptions eo;
	private static final Logger log = LogManager.getLogger(OptionManager.class);

	public OptionManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {
		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			// System.out.println("== Running in headless mode ==");
			log.info("==Running in HeadLess Mode===");

			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			//System.out.println("== Running in incognito mode ==");
			log.info("==Running in Incognito Mode===");
			co.addArguments("--incognito");
		}
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			co.setCapability("browserName", "chrome");
			co.setBrowserVersion(prop.getProperty("browserversion").trim());

			Map<String, Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("screenResolution", "1280x1024x24");
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("name", prop.getProperty("testname"));
			co.setCapability("selenoid:options", selenoidOptions);
		}
		
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			// System.out.println("== Running in headless mode ==");
			log.info("==Running in HeadLess Mode===");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			//System.out.println("== Running in incognito mode ==");
			log.info("==Running in Incognito Mode===");
			fo.addArguments("--incognito");
		}
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			fo.setCapability("browserName", "firefox");
			fo.setBrowserVersion(prop.getProperty("browserversion").trim());

			Map<String, Object> selenoidOptions = new HashMap<>();
			selenoidOptions.put("screenResolution", "1280x1024x24");
			selenoidOptions.put("enableVNC", true);
			selenoidOptions.put("name", prop.getProperty("testname"));
			fo.setCapability("selenoid:options", selenoidOptions);
		}
		return fo;
	}

	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();

		if (Boolean.parseBoolean(prop.getProperty("headless"))) {
			// System.out.println("== Running in headless mode ==");
			log.info("==Running in HeadLess Mode===");

			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {
			//System.out.println("== Running in incognito mode ==");
			log.info("==Running in Incognito Mode===");
			eo.addArguments("--inPrivate");
		}
		if (Boolean.parseBoolean(prop.getProperty("remote"))) {
			eo.setCapability("browserName", "edge");
		}
		return eo;
	}

}
