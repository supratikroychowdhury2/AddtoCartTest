package com.qa.tests;

import java.awt.ItemSelectable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestOne {
	
	WebDriver driver;
	PageObjects p=new PageObjects();
	HashMap<String, String> map=new HashMap<String, String>();
	
	
	@BeforeTest
	public void beforeTest() throws IOException {
		
		map.put("Brocolli", "2");
		map.put("Brinjal", "1");
		//initializing drivers and webURL
		FileInputStream fis=new FileInputStream("C:\\Users\\SupratikRoychowdhury\\eclipse-workspace\\AddtoCartTest\\src\\test\\resources\\com\\qa\\resources\\GlobalParameters.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String url=prop.getProperty("url");
		String chromepath=prop.getProperty("chromepath");
		String path=System.getProperty("user.dir");
		System.out.println(path+chromepath);
		System.setProperty("webdriver.chrome.driver",path+chromepath);
		driver=new ChromeDriver();
		driver.get(url);
		driver.manage().window().maximize();
		
		
		
	}
	
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test() throws InterruptedException {
		
		
		for(Map.Entry m: map.entrySet()) {
		//Adding Items to Cart------------------------------
		WebElement itemQuantity=driver.findElement(By.xpath("//h4[contains(text(),'"+m.getKey()+"')]/following-sibling::div/input"));
		
		itemQuantity.clear();
		itemQuantity.sendKeys(m.getValue().toString());
		WebElement AddtoCart=driver.findElement(By.xpath("//h4[contains(text(),'"+m.getKey()+"')]/following-sibling::div/button"));
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		AddtoCart.click();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
			
		//moving to cart--------------------------------------
		WebElement cartIcon=driver.findElement(By.xpath("//img[@alt='Cart']"));
		cartIcon.click();
		
		//moving to checkout page-------------------------------
		WebElement checkoutButton=driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]"));
		checkoutButton.click();
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//validating the quantity of elements---------------------
		for(Map.Entry p: map.entrySet()) {
		WebElement qty=driver.findElement(By.xpath("//p[contains(text(),'"+p.getKey()+"')]/../../td[3]/p"));
		Assert.assertEquals(qty.getText(), p.getValue());
		}
		
		//plcae order------------------------------------------------
		WebElement placeOrder=driver.findElement(By.xpath("//button[contains(text(),'Place Order')]"));
		placeOrder.click();
		
		Select s=new Select(driver.findElement(By.xpath("//select")));
		s.selectByValue("India");
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//input")).click();
		
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

		//validating confirmation message----------------------------
		driver.findElement(By.xpath("//button[contains(text(),'Proceed')]")).click();
		Thread.sleep(2000);
		String verifyMsg="Thank you, your order has been placed successfully\n"
				+ "You'll be redirected to Home page shortly!!";
		String actualMsg= driver.findElement(By.xpath("//div[@class='wrapperTwo']")).getText();
		
		Assert.assertEquals(actualMsg.trim(), verifyMsg.trim());
		
		
	}
	
	@AfterTest
	public void afterTest() {
		driver.close();
	}
}
