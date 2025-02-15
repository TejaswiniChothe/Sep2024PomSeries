package com.qa.opecart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opecart.constants.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class CommonPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	public CommonPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	private By logo = By.className("img-responsive");
	private By footer = By.xpath("//footer//a");

	public boolean isLogoDisplayed() {
		return eleUtil.doIsElementDisplayed(logo);
	}

	public List<String> getFootersList() {
		List<WebElement> footerList = eleUtil.waitforElementsPresennce(footer, AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Total number of footers: " + footerList.size());
		List<String> footers = new ArrayList<String>();
		for (WebElement e : footerList) {
			String text = e.getText();
			footers.add(text);
		}
		return footers;
	}

	public boolean checkFooterLink(String footerLink) {
		return getFootersList().contains(footerLink);
	}
}
