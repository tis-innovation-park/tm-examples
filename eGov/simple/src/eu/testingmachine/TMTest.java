package eu.testingmachine;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import java.net.URL;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.internal.FindsByClassName;
import org.openqa.selenium.internal.FindsByLinkText;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TMTest
{
  public String baseUrl = "http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1029144"; 
  
  public WebDriver driver;

  private int driverAttempts = 0;

  @BeforeClass
  public void init()
  {
    // TODO: Limit the amount of times we will retry.
    while(driver == null)
    {
      setDriver();
    }
    
    getBaseURL();
  }

  @AfterClass
  public void tearDown()
  {
    driver.quit();
  }

  public void setDriver()
  {
    driverAttempts++;

    try
    {
      switch(System.getProperty("tm.seleniumDriver"))
      {
        case "AndroidDriver":
        {
          if(System.getProperty("tm.seleniumUrl") == null)
          {
            driver = new AndroidDriver();
          }
          else
          {
            URL url;
            
            try
            {
              url = new URL(System.getProperty("tm.seleniumUrl"));
            }
            catch(MalformedURLException e)
            {
              e.printStackTrace();
              throw new RuntimeException("Malformed Selenium URL.");
            }
            
            driver = new AndroidDriver(url);
          }

          break;
        }

        case "ChromeDriver":
        {
          if(System.getProperty("webdriver.chrome.driver") == null)
          {
            throw new RuntimeException("webdriver.chrome.driver needs to be set.");
          }

          driver = new ChromeDriver();

          break;
        }

        case "FirefoxDriver":
        {
          driver = new FirefoxDriver();

          break;
        }

        default:
        {
          throw new RuntimeException("Unimplemented Selenium driver.");
        }
      }
    }
    catch(UnreachableBrowserException e)
    {
      System.out.println("Could not set driver...");

      if(driverAttempts == 10)
      {
        throw new RuntimeException("Could not get the Selenium driver.");
      }

      try
      {
        Thread.sleep(1000);
      }
      catch(InterruptedException e_)
      {
        e_.printStackTrace();
      }
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
}
