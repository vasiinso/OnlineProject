package com.vmetry.testcases;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.uncommons.reportng.HTMLReporter;
import org.uncommons.reportng.JUnitXMLReporter;

import com.vmetry.testBase.TestBase;
import com.vmety.testUtil.TestUtilityClass;

public class LoginPageTest extends TestBase {

	@BeforeClass
	public void loginBfrcls() throws IOException {

		if (initialize == false) {
			initialization();
		}
		lg=LoggerFactory.getLogger(LoginPageTest.class);

	}

@Test(dataProvider = "data")
public void testlogin(String TestCaseID, String UAN, String Password, String Type) 
		throws InterruptedException, IOException {
		lg.info("*************Test Started*********************");
		String expectederrorlbl = "Sign in login error - Invalid Credentials";
		getElementByXpath(loc.getProperty("lg_uan_txtbox")).sendKeys(UAN);
		getElementByXpath(loc.getProperty("lg_password_txtbox")).sendKeys(Password);
		getElementByXpath(loc.getProperty("lg_signin_clkbutton")).click();
		Thread.sleep(5000);

if (Type.equalsIgnoreCase("valid")) {
	String expectedname = "Welcome Vasanth Shanmugam" + "\n" + "UAN   100411794217";
	System.out.println("exp:"+expectedname);
	System.out.println(getElementByXpath(loc.getProperty("hp_welcomename")).getText());

	if (((getElementByXpath(loc.getProperty("hp_welcomename")).getText()).equals(expectedname))) {

		System.out.println("Write pass");
		map.put(TestCaseID, "Passed");
		lg.info("Test Passed");
		getElementByXpath(loc.getProperty("hp_logout_button")).click();
		Thread.sleep(3000);
		TestUtilityClass.alertchkandaccept();
		Thread.sleep(3000);
	} else {

		map.put(TestCaseID, "Failed");
		System.out.println("Write Fail");
		lg.info("Test Failed");
		getElementByXpath(loc.getProperty("hp_logout_button")).click();
		Thread.sleep(3000);
		TestUtilityClass.alertchkandaccept();
		Thread.sleep(3000);
		
		//Hard Assert
	//	Assert.assertEquals(actual, expected);
		SoftAssert sf=new SoftAssert();
		sf.assertTrue(false);
		sf.assertAll();
	}
} 
else if (Type.equalsIgnoreCase("invalid")) {

	
	if(TestUtilityClass.isAlertPresent() == true) {
		System.out.println("Alert checking loop");
		String alrtmsg = "Please enter Username.";
		System.out.println(alrtmsg);
		if (TestUtilityClass.closeAlertAndGetItsText().equalsIgnoreCase(alrtmsg)) {
			map.put(TestCaseID, "Passed");
			lg.info("Test Passed");
		} else {
			map.put(TestCaseID, "Failed");
			lg.info("Test Failed");
			SoftAssert sf=new SoftAssert();
			sf.assertTrue(false);
			sf.assertAll();
		}
	} 		
	else {
		
		System.out.println("error label is satisfied with actual");
	
		if (getElementByXpath(loc.getProperty("lg_errorlabel")).getText().equalsIgnoreCase(expectederrorlbl)) {

			map.put(TestCaseID, "passed");
			lg.info("Test passed");
		} else {
			map.put(TestCaseID, "Failed");
			lg.info("Test Failed");
			SoftAssert sf=new SoftAssert();
			sf.assertTrue(false);
			sf.assertAll();
		}

	}
}
Thread.sleep(5000);

}

	@DataProvider(name="data")
	public static Object[][] getdata() throws IOException {
		if (TestUtilityClass.runModeVerify("LoginPage") == true) {
			Object[][] data = TestUtilityClass.getExcelData("Login");

			return data;
		} else {
			return null;
		}

	}

	@AfterClass
	public void afrcls() throws IOException {
		lg.info("Writting in excel");
		TestUtilityClass.writeExcelFile("Login");
		wd.close();

	}

}
