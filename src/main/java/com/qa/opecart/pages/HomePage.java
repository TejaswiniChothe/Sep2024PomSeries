package com.qa.opecart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opecart.constants.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class HomePage{

	private WebDriver driver;
	private ElementUtil eleUtil;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		eleUtil=new ElementUtil(driver);
	}

	// 1. private By locators:
	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content > h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");



//2. Public page actions
public String getHomePageTitle() {
	String title=eleUtil.waitforTitleIs(AppConstant.HOME_PAGE_TITLE,AppConstant.DEFAULT_TIME_OUT);
	ChainTestListener.log("home page title==>" + title);
	return title;
}

public String getHomePageURL() {
	String url=eleUtil.waitforURLcontains(AppConstant.HOME_PAGE_URL_FRACTION,AppConstant.DEFAULT_TIME_OUT);
	ChainTestListener.log("home page url==>" + url);
	return url;
}

public boolean isLogoutLinkExist() {
	return eleUtil.doIsElementDisplayed(logoutLink);
}

public void logout() {
	if(isLogoutLinkExist()) {
		driver.findElement(logoutLink).click();
	}
	
}

public List<String> getHeadersList() {
	List<WebElement> headersList = eleUtil.waitforElementsPresennce(headers, AppConstant.SHORT_TIME_OUT);
	List<String> headersValList = new ArrayList<String>();
	for(WebElement e : headersList) {
		String text = e.getText();
		headersValList.add(text);
	}
	return headersValList;
}

public SearchResultsPage doSearch(String searchKey) {
	System.out.println("search key: "+ searchKey);
	WebElement searchEle = eleUtil.waitForWebElementVisible(search, AppConstant.DEFAULT_TIME_OUT);
	eleUtil.doSendKeys(searchEle, searchKey);
	eleUtil.doClick(searchIcon);
	return new SearchResultsPage(driver);
}


}