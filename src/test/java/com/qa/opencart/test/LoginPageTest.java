package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opecart.constants.AppConstant;
import com.qa.opecart.constants.AppError;
import com.qa.opencart.base.Base;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

@Epic("Epic 100: design login page for open cart")
@Story("US 101: design the various features of open cart login page")
@Feature("Feature 50: Login Page Feature")
@Owner("Naveen Automation Labs")

public class LoginPageTest extends Base {
	@Description("checking login page title....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageTitleTest() {
		ChainTestListener.log("Verifying Loging Page Title");
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstant.LOGIN_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);

	}

	@Description("checking login page url....")
	@Severity(SeverityLevel.MINOR)
	@Test
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageUrl();
		Assert.assertTrue(actURL.contains(AppConstant.LOGIN_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);
	}

	@Description("checking user is able to login with right credentials....")
	@Severity(SeverityLevel.BLOCKER)
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist(), AppError.Element_NOT_FOUND_ERROR);
	}

	@Description("checking user is able to login with right credentials....")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		homePage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(homePage.getHomePageTitle(), AppConstant.HOME_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}

	@DataProvider
	public Object[][] getFooterData() {
		return new Object[][] { { "About Us" }, { "Contact Us" }, { "Specials" }, { "Order History" } };
	}

	@Description("checking page footers....")
	@Severity(SeverityLevel.NORMAL)
	@Test(dataProvider = "getFooterData")
	public void footerTest(String footerLink) {
		Assert.assertTrue(commonPage.checkFooterLink(footerLink));
	}
	@Description("checking page logo....")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void logoTest() {
		Assert.assertTrue(commonPage.isLogoDisplayed(), AppError.LOGO_NOT_FOUND_ERROR);
	}
}
