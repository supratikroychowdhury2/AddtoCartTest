package com.qa.tests;

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
	
	@BeforeTest
	public void beforeTest() {
		
		//initializing drivers and webURL
		System.setProperty("webdriver.chrome.driver", "C:\\Java Projects\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		driver.manage().window().maximize();
	}
	
	
	@Test
	public void test() throws InterruptedException {
		
		//Adding Items to Cart
		WebElement brocoliQuantity=driver.findElement(By.xpath("//h4[contains(text(),'Brocolli')]/following-sibling::div/input"));
		brocoliQuantity.clear();
		brocoliQuantity.sendKeys("2");
		WebElement brocoliAddtoCart=driver.findElement(By.xpath("//h4[contains(text(),'Brocolli')]/following-sibling::div/button"));
		brocoliAddtoCart.click();
		
		Thread.sleep(5000);
		
		WebElement brinjalQuantity=driver.findElement(By.xpath("//h4[contains(text(),'Brinjal')]/following-sibling::div/input"));
		WebElement brinjalAddtoCart=driver.findElement(By.xpath("//h4[contains(text(),'Brinjal')]/following-sibling::div/button"));
		brinjalAddtoCart.click();
		
		//moving to cart
		WebElement cartIcon=driver.findElement(By.xpath("//img[@alt='Cart']"));
		cartIcon.click();
		
		//moving to checkout page
		WebElement checkoutButton=driver.findElement(By.xpath("//button[contains(text(),'PROCEED TO CHECKOUT')]"));
		checkoutButton.click();
		
		Thread.sleep(2000);
		
		//validating the quantity of elements
		WebElement qty1=driver.findElement(By.xpath("//p[contains(text(),'Brocolli')]/../../td[3]/p"));
		WebElement qty2=driver.findElement(By.xpath("//p[contains(text(),'Brinjal')]/../../td[3]/p"));
		Assert.assertEquals(qty1.getText(), "2");
		Assert.assertEquals(qty2.getText(), "1");

		
		//plcae order
		WebElement placeOrder=driver.findElement(By.xpath("//button[contains(text(),'Place Order')]"));
		placeOrder.click();
		
		Select s=new Select(driver.findElement(By.xpath("//select")));
		s.selectByValue("India");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//input")).click();
		
		Thread.sleep(5000);

		//validating confirmation message
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
