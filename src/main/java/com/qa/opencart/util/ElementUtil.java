package com.qa.opencart.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.factory.DriverFactory;

import io.qameta.allure.Step;

public class ElementUtil {

	private WebDriver driver;
	private JavaScriptUtil jsUtil;

	private void nullcheck(CharSequence... value) {
		if (value == null) {
			throw new RuntimeException("==Vale/Prop/Attribute can not be null==");
		}
	}

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		jsUtil = new JavaScriptUtil(driver);
	}

	private void highlightelement(WebElement element) {
		if (Boolean.parseBoolean(DriverFactory.highlight)) {
			jsUtil.flash(element);
		}

	}

	public WebElement getElement(By locator) {
		// return waitForWebElementVisible(locator,10);//it will wait for all web
		// element for 10 second
		WebElement element = driver.findElement(locator);
		highlightelement(element);
		return element;
	}

	@Step("In element :{0} enter the value as {1} ")

	public void doSendkeys(By locator, CharSequence... value) {
		nullcheck(value);
		WebElement element = getElement(locator);
		element.clear();
		element.sendKeys(value);
	}

	public void doSendKeys(WebElement element, CharSequence... value) {
		nullcheck(value);
		element.clear();
		element.sendKeys(value);
	}

	@Step("Click on element :{0} ")
	public void doClick(By locator) {
		getElement(locator).click();

	}

	public String getElementText(By locator) {
		return getElement(locator).getText();

	}

	public By getLocator(String locatorType, String loactorValue) {
		By locator = null;

		switch (locatorType.toUpperCase().trim()) {
		case "ID":
			locator = By.id(loactorValue);
			break;
		case "NAME":
			locator = By.name(loactorValue);
			break;
		case "CLASSNAME":
			locator = By.className(loactorValue);
			break;
		case "XPATH":
			locator = By.xpath(loactorValue);
			break;
		case "CSSSELECTOR":
			locator = By.cssSelector(loactorValue);
			break;
		case "LINKTEXT":
			locator = By.linkText(loactorValue);
			break;
		case "PARTIALLINKTEXT":
			locator = By.partialLinkText(loactorValue);
			break;
		case "TAGNAME":
			locator = By.tagName(loactorValue);
			break;

		default:
			System.out.println("invalid locator, please use the right locator...");
			break;

		}
		return locator;

	}

	public void doSendKeys(String locatorType, String locatorValue, CharSequence... value) {
		nullcheck(value);
		WebElement element = getElement(getLocator(locatorType, locatorValue));
		element.clear();
		element.sendKeys(value);
	}

	public void doClick(String locatorType, String locatorValue) {
		getElement(getLocator(locatorType, locatorValue)).click();
	}

	public String getElementText(String locatorType, String locatorValue) {
		return getElement(getLocator(locatorType, locatorValue)).getText();

	}

	public String doGetDomProperty(By locator, String propName) {
		nullcheck(propName);
		return getElement(locator).getDomProperty(propName);
	}

	public String doGetDomAttribute(By locator, String attrName) {
		nullcheck(attrName);
		return getElement(locator).getDomAttribute(attrName);
	}

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public boolean isElementDisplayed(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("element is found on page");
			return true;
		} else {
			System.out.println("element is not found on page");
			return false;
		}

	}

	public boolean isElementDisplayed(By locator, int elementCount) {
		if (getElements(locator).size() == elementCount) {
			System.out.println("element is found on page " + elementCount + "times");
			return true;
		} else {
			System.out.println("element is not found on page");
			return false;
		}

	}

	@Step("element:{0} is displaying")

	public boolean doIsElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();

		} catch (NoSuchElementException e) {
			System.out.println("element is not displayed");
			return false;
		}
	}

	// ---------------------Select dropdown utility----------------
	public void doSelectByIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
	}

	public void doSelectByVisibleText(By locator, String visibleText) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(visibleText);
	}

	public void doSelectByValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByVisibleText(value);
	}

	public void doSelectDropDownByContainsText(By locator, String partialText) {
		Select select = new Select(getElement(locator));
		select.selectByContainsVisibleText(partialText);
	}

	public int getOptionCount(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> countryOptionList = select.getOptions();
		System.out.println("Option size is:" + countryOptionList.size());
		return countryOptionList.size();
	}

	public void printDropDownText(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> OptionList = select.getOptions();
		for (WebElement e : OptionList) {
			String text = e.getText();
			System.out.println(text);

		}
	}

	public List<String> getDropDownOptionsTextList(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		List<String> optionsValueList = new ArrayList<String>();// []
		for (WebElement e : optionsList) {
			String text = e.getText();
			optionsValueList.add(text);
		}
		return optionsValueList;
	}

	public void selectValueFromDropDown(By locator, String value) {
		Select select = new Select(getElement(locator));
		List<WebElement> optionsList = select.getOptions();
		boolean flag = false;

		for (WebElement e : optionsList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(value)) {
				e.click();
				flag = true;
				break;
			}
		}

		if (flag) {
			System.out.println(value + " is available and selected");
		} else {
			System.out.println(value + " is not available");
		}
	}

	public void doSearch(By searchField, By suggestions, String searchKey, String actualValue)
			throws InterruptedException {

		doSendkeys(searchField, searchKey);
		Thread.sleep(2000);

		List<WebElement> suggList = getElements(suggestions);
		System.out.println(suggList.size());
		boolean flag = false;

		for (WebElement e : suggList) {
			String text = e.getText();
			System.out.println(text);
			if (text.contains(actualValue)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			System.out.println(actualValue + " is available and clicked");
		} else {
			System.out.println(actualValue + " is not available");
		}

	}

	/**
	 * this method is handling single/multiple and all choices selection. Please
	 * pass "all" to select all the choices. selectChoice(choiceDropDown, choices,
	 * "All");
	 * 
	 * @param choiceDropDown
	 * @param choices
	 * @param choiceValue
	 * @throws InterruptedException
	 */
	public void selectChoice(By choiceDropDown, By choices, String... choiceValue) throws InterruptedException {
		doClick(choiceDropDown);
		Thread.sleep(3000);
		List<WebElement> choicesList = getElements(choices);

		System.out.println(choicesList.size());

		if (choiceValue[0].equalsIgnoreCase("all")) {
			// select all the choice one by one:
			for (WebElement e : choicesList) {
				e.click();
			}
		}

		else {
			for (WebElement e : choicesList) {
				String text = e.getText();
				System.out.println(text);

				for (String ch : choiceValue) {
					if (text.equals(ch)) {
						e.click();
					}
				}

			}
		}
	}

	// *---------------action utils---------------

	public void handleTwoLevelMenuSubMenuHandling(By parentMenuLocator, By childMenuLocator) {
		Actions act = new Actions(driver);
		act.moveToElement(getElement(parentMenuLocator)).build().perform();
		getElement(childMenuLocator).click();

	}

	public void level4MenuSubMenuHandling(By level1Menu, By level2Menu, By level3Menu, By level4Menu)
			throws InterruptedException {
		Actions act = new Actions(driver);
		driver.findElement(level1Menu).click();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level2Menu)).build().perform();
		Thread.sleep(2000);
		act.moveToElement(driver.findElement(level3Menu)).build().perform();
		Thread.sleep(2000);
		driver.findElement(level4Menu).click();

	}

	public void doActionsSendKeys(By locator, CharSequence... value) {
		Actions act = new Actions(driver);
		act.sendKeys(getElement(locator), value).build().perform();
	}

	public void doActionsClick(By locator) {
		Actions act = new Actions(driver);
		act.click(getElement(locator)).build().perform();
	}

	public void doSendKeysWithPause(By locator, String value, long pauseTime) {
		Actions act = new Actions(driver);
		char val[] = value.toCharArray();
		for (char ch : val) {
			act.sendKeys(getElement(locator), String.valueOf(ch)).pause(pauseTime).perform();
		}

	}

	// *************Wait util************

	public WebElement waitForWebElementPresence(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		highlightelement(element);
		return element;

	}

	@Step("element:{0} is visibile within timout {1} ")

	public WebElement waitForWebElementVisible(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		highlightelement(element);
		return element;

	}

	// This method is check that if it is visible and enable,so hat you can click on
	// it
	// Recommend method when you want to click on any web element
	public void clickElementWhenReady(By locator, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

	}

	public String waitforTitleContains(String fractionTitle, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleContains(fractionTitle))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not fount after: " + timeout);

		}
		return null;
	}

	@Step("wait For Title Is :{0} within timout {1} ")
	public String waitforTitleIs(String fractionTitle, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.titleIs(fractionTitle))) {
				return driver.getTitle();
			}
		} catch (TimeoutException e) {
			System.out.println("title is not fount after: " + timeout);

		}
		return null;
	}

	@Step("wait For Url Is :{0} within timout {1} ")

	public String waitforURLcontains(String frationURL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlContains(frationURL))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not fount after: " + timeout + " Seconds ");

		}
		return null;
	}

	public String waitforURLtoBe(String frationURL, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			if (wait.until(ExpectedConditions.urlToBe(frationURL))) {
				return driver.getCurrentUrl();
			}
		} catch (TimeoutException e) {
			System.out.println("URL is not fount after: " + timeout + " Seconds ");

		}
		return null;
	}

//**************alerwait*******************
	public Alert waitForalert(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());

	}

	public String getAlertText(long timeout) {
		return waitForalert(timeout).getText();
	}

	public void acceptAlert(long timeout) {
		waitForalert(timeout).accept();
	}

	public void dismissAlert(long timeout) {
		waitForalert(timeout).dismiss();
	}

	public void alertSendKeys(String text, long timeout) {
		waitForalert(timeout).sendKeys(text);
	}

//**------------Frame wait-------------------
	public void WaitForFrameByLoactorandswitchToit(By frameLoactor, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLoactor));
	}

	public void WaitForFrameByIndexandswitchToit(int frameIndex, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}

	public void WaitForFrameByIDorNameandswitchToit(String frameIdOrName, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIdOrName));
	}

	public void WaitForFrameByFrameElementandswitchToit(WebElement frameElement, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	// ***********wait for windows*************
	public boolean waitforWindows(int numberofWindow, long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(numberofWindow));
		} catch (TimeoutException e) {
			System.out.println("number of windownot matched");
			return false;
		}

	}

	public List<WebElement> waitforElementsPresennce(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return Collections.emptyList();
		}
	}

	public List<WebElement> waitforElementsPresennce(By locator, long timeout, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

	}

	public List<WebElement> waitforElementsVisble(By locator, long timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			return Collections.emptyList();
		}

	}

	public List<WebElement> waitforElementsVisble(By locator, long timeout, int pollingTime) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout), Duration.ofSeconds(pollingTime));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

	}

	// ***********Wait forpage loading*************
	public boolean isPageLoaded(long timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String isPageLoaded = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"))
				.toString();
		return Boolean.parseBoolean(isPageLoaded);
	}

	// ***********fluentWait************
	public WebElement waitforElementVisibleusingFluentFeatures(By locator, long timeout, int pollingTime) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofSeconds(pollingTime)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("====element not found==");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

}
