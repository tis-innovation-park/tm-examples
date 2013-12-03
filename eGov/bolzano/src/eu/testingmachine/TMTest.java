package eu.testingmachine;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import java.net.URL;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.android.AndroidDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class TMTest
{
  private static int driverAttempts;
  
  @DataProvider(name = "driver")
  public static Object[][] init()
  {
	WebDriver driver = null;
	driverAttempts = 0;
	  
    while(driver == null)
    {
      driver = setDriver();
    }
    
    return new Object[][]
    		{
    			new Object[] { driver }
    		};
  }

  public static WebDriver setDriver()
  {
	WebDriver driver = null;
	  
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

      case "RemoteWebDriver":
      {
        if(System.getProperty("tm.seleniumUrl") == null)
        {
          throw new RuntimeException("tm.seleniumUrl needs to be set for RemoteWebDriver.");
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
          
          Capabilities caps = DesiredCapabilities.firefox();
          
          if(System.getProperty("tm.browser") != null && System.getProperty("tm.browser").equals("chrome"))
          {
              caps = DesiredCapabilities.chrome();
          }
          
          driver = new RemoteWebDriver(url, caps);
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
    
	return driver;
  }
}
