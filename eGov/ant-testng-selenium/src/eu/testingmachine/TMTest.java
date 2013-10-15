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
public void verifyAllServices() {

    driver.get(baseUrl);
    String expectedTitle = "Dienste A-Z | Südtiroler Bürgernetz";
    String actualTitle = driver.getTitle();
    Assert.assertEquals(actualTitle, expectedTitle);
    
}
  
  
  
  @Test
  public void verifyAbendSchule() {
        
      driver.get("http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_tgid=1000540");
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

      String expectedTitle = "Abendschulen | Dienste A-Z | Südtiroler Bürgernetz";
      String actualTitle = driver.getTitle();
      Assert.assertEquals(actualTitle, expectedTitle);
      driver.navigate().back();
        
      /*// Abendschule fuer Erwachsene
	  driver.get("http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1003544");
	  driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	  String heading = driver.findElement(By.cssSelector("div.main>h1")).getText();
	  Assert.assertTrue(heading.contains("Abendschule für Erwachsene - Mittelschule (Italienisches Schulamt)"));
		  
      */
        
  
        
  }
  
  @Test
  public void verifyAbfertigung() {
        
      driver.get("http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_tgid=10969");
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        
      String expectedTitle = "Abfertigung | Dienste A-Z | Südtiroler Bürgernetz";
      String actualTitle = driver.getTitle();
      Assert.assertEquals(actualTitle, expectedTitle);
        
  }
  
  @Test
  public void verifyAbgaben() {
        
      driver.get("http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_tgid=1004820");
      driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        
      String expectedTitle = "Abgaben | Dienste A-Z | Südtiroler Bürgernetz";
      String actualTitle = driver.getTitle();
      Assert.assertEquals(actualTitle, expectedTitle);
  }
} 
