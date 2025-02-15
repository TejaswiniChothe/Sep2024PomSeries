package com.qa.opecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opecart.constants.AppConstant;
import com.qa.opencart.util.ElementUtil;

public class RegistrationPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 1. By Locator: page objects: OR
	private By NewCustomerBtn=By.xpath("//a[text()='Continue']");
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailId = By.id("input-email");
	private By telePhone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By passwordConfirm = By.id("input-confirm");
	private By privacyPolicy = By.name("agree");
	private By continueBtn = By.xpath("//input[@type='submit']");
	private By accountCreationSuccess = By.xpath("//h1[text()='Your Account Has Been Created!']");
	private By logout = By.xpath("//a[text()='Logout' and @class='list-group-item']");
	private By logoutConformation = By.xpath("//h1[text()='Account Logout']");
	private By registerLiink = By.xpath("//a[text()='Register' and @class='list-group-item']");

	// 2. Public page actions - methods (features)
	
	public void redirectToRegistartionPage()
	{
		eleUtil.waitForWebElementVisible(NewCustomerBtn, AppConstant.DEFAULT_TIME_OUT).click();
	}
	public String getRegistrationPageTitle() {
		String title = eleUtil.waitforTitleIs(AppConstant.REGISTRATION_PAGE_TITLE, AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Title of page is : " + title);
		return title;
	}

	public String getRegistrationUrl() {
		String url = eleUtil.waitforURLcontains(AppConstant.REGISTRATION_PAGE_URL_FRACTION,
				AppConstant.DEFAULT_TIME_OUT);
		System.out.println("Title of page is : " + url);
		return url;
	}
	

	public void doRegistration(String firstname, String lastname, String emailid, String telephone, String passoword,
			String confirmPassword) {
		System.out.println("User details are: ==> " + firstname + " : " + lastname + " : " + emailId + " : " + telephone
				+ " : " + passoword + " : " + confirmPassword);
		eleUtil.waitForWebElementVisible(firstName, AppConstant.DEFAULT_TIME_OUT).sendKeys(firstname);
		eleUtil.doSendkeys(lastName, lastname);
		eleUtil.doSendkeys(emailId, emailid);
		eleUtil.doSendkeys(telePhone, telephone);
		eleUtil.doSendkeys(password, passoword);
		eleUtil.doSendkeys(passwordConfirm, confirmPassword);
		eleUtil.doClick(privacyPolicy);
		eleUtil.doClick(continueBtn);
		eleUtil.doClick(logout);
		eleUtil.doClick(registerLiink);

	}
	


}
