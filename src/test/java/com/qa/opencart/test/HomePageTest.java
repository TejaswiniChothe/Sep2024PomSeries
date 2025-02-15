package com.qa.opencart.test;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opecart.constants.AppConstant;
import com.qa.opecart.constants.AppError;
import com.qa.opencart.base.Base;

public class HomePageTest extends Base{

	@BeforeClass
	public void homePageSetup() {
		homePage = loginPage.doLogin(prop.getProperty("username"),prop.getProperty("password"));
	}

	@Test
	public void homePageTitleTest() {
		ChainTestListener.log("Verifying Home Page URL");

		Assert.assertEquals(homePage.getHomePageTitle(), AppConstant.HOME_PAGE_TITLE,AppError.TITLE_NOT_FOUND_ERROR);
	}

	@Test
	public void homePageURLTest() {
		ChainTestListener.log("Verifying Home Page URL");
		Assert.assertTrue(homePage.getHomePageURL().contains(AppConstant.HOME_PAGE_URL_FRACTION), AppError.URL_NOT_FOUND_ERROR);
	}

	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(homePage.isLogoutLinkExist(),AppError.Element_NOT_FOUND_ERROR );
	}

	@Test
	public void headersTest() {
		List<String> actualHeaders = homePage.getHeadersList();
		System.out.println("home page headers: ==>" + actualHeaders);
		
	}
	
	@DataProvider
	public Object[][] getSearchData()
	{
		return new Object[][] {
			{"macbook", 3},
			{"imac", 1},
			{"samsung", 2},
			{"canon", 1},
			{"airtel", 0}
		};
	}

	@Test(dataProvider="getSearchData")
	public void searchTest(String searchKey,int resultCount) {
		searchResultsPage = homePage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getProductResultsCount(), resultCount);
	}

}
