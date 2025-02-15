package com.qa.opencart.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opecart.constants.AppConstant;
import com.qa.opecart.constants.AppError;
import com.qa.opencart.base.Base;
import com.qa.opencart.util.ExcelUtil;

public class RegsisterPageTest extends Base {

	@BeforeClass
	public void setUpforRegPage()
	{
		registartionPage.redirectToRegistartionPage();
	}

	@Test
	public void registerPageTitleTest() {
		String actTitle = registartionPage.getRegistrationPageTitle();
		Assert.assertEquals(actTitle, AppConstant.REGISTRATION_PAGE_TITLE, AppError.TITLE_NOT_FOUND_ERROR);
	}

	@Test
	public void registerPageURLTest() {
		String actURL = registartionPage.getRegistrationUrl();
		Assert.assertTrue(actURL.contains(AppConstant.REGISTRATION_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);
	}

	@DataProvider
	public Object[][] getRegsiterUserSheetData() {
		Object regUserData[][] = ExcelUtil.getTestData(AppConstant.REGSITER_SHEET_NAME);
		return regUserData;
	}


	@Test(dataProvider = "getRegsiterUserSheetData")
	public void RegistartionTest(String firstname, String lastname, String emailid, String telephone, String passoword,
			String confirmPassword) {
		registartionPage.doRegistration(firstname, lastname, emailid, telephone, passoword, confirmPassword);
		Assert.assertEquals(registartionPage.getRegistrationPageTitle(), AppConstant.REGISTRATION_PAGE_TITLE,AppError.TITLE_NOT_FOUND_ERROR);


	}
}
