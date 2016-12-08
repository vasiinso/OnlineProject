package com.vmetry.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;

public class TestBase {
	
	public static File f=null;
	public static FileInputStream configfis=null;
	public static FileInputStream locatorfis=null;
	public static Properties cnfg=null;
	public static Properties loc=null;
	public static WebDriver wd=null;
	public static boolean initialize=false;
	public static FluentWait<WebDriver> wait=null;
	public static FileInputStream xlfis=null;
	public static boolean runverify=false;
	public static Date dt=null;
	public static SimpleDateFormat dateFormat=null;
	public static Map<String,String> map=null;
	public static Logger lg=null;
	
	
	public static void initialization() throws IOException{
			
		f=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\vmetry\\Config\\Config.properties");
		configfis=new FileInputStream(f);
		cnfg=new Properties();
		cnfg.load(configfis);
		
	
		f=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\com\\vmetry\\Config\\Locators.properties");
		locatorfis=new FileInputStream(f);
		loc=new Properties();
		loc.load(locatorfis);
		
		if(cnfg.getProperty("Browser").equals("FF")){
			
			wd=new FirefoxDriver();
		}else if (cnfg.get("Browser").equals("IE")){
			
			wd=new InternetExplorerDriver();
		}else if(cnfg.get("Browser").equals("chrome")){
			
			System.setProperty("webdriver.chrome.driver", "E:\\chromedriver\\chromedriver.exe");
			wd=new ChromeDriver();
			wd.manage().window().maximize();
		}
		
		wd.get(cnfg.getProperty("TestingURL"));
		wait=new FluentWait<WebDriver>(wd);
		wait.withTimeout(120, TimeUnit.SECONDS);
		dt = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		map =new HashMap<String,String>();
		
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\lib\\Log4j.properties");
		
		initialize=true;
		
		
	}
	
	
	public static WebElement getElementByXpath(String xpath){
		
	try{
	wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
	wait.pollingEvery(2, TimeUnit.SECONDS);
	return wd.findElement(By.xpath(xpath));
	}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception - unable to find the element or Elmt not loaded");
			return null;
	}
	
		
		
		
		
		
	}
		

}
