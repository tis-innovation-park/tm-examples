package eu.testingmachine;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TMTest {

  //@Test(description="Launches an EGov webpage")

  public String baseUrl = "http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1029144"; 
  
  public WebDriver driver;

  @BeforeTest
  public void init()
  {
    while(driver == null)
    {
      setDriver();
    }
    
    getBaseURL();
  }

  public void setDriver()
  {
    try
    {
      driver = new AndroidDriver();
    }
    catch(UnreachableBrowserException e)
    {
      System.out.println("Could not set driver...");
    }
  }

  public void getBaseURL()
  {
    try
    {
      driver.get(baseUrl);
    }
    catch(UnreachableBrowserException e)
    {
      System.out.println("driver.get() threw an exception.");
      e.printStackTrace();
    }
  }

  @Test
  public void verifyPageTitle() {

    // Make sure the page loads on AVD before checking...

    //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    String expectedTitle = "Schulkalender (Deutsches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";
    String actualTitle = driver.getTitle();
    Assert.assertEquals(actualTitle, expectedTitle);
  }
  
  @Test
  public void verifyPageText() {
    String expectedText = "Schulkalender (Deutsches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";

    // driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    // Not too sure exactly what to check for yet, need to take a closer look at API
    String bodyText = driver.findElement(By.tagName("h2")).getText();
    // Assert.assertEquals(actualText, expectedText);
    Assert.assertEquals(bodyText, expectedText);
  }
  
  @AfterTest
  public void sendSession() {
    driver.close();
    //  driver.quit();
  }
} 
