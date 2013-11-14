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

    @Test
    public void testFindElements()throws Exception{
	 
	loadServicesPage();
	clickAndLoadAbendschulen();
	
	
    }
    
    @Test
    public void verifyTitleAbendSchulen() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	String expectedTitle = "Abendschulen | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	
    } 
    
    @Test
    public void verifyLinkAbendschulen() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	WebElement element = ((FindsByLinkText)driver).findElementByLinkText("Abendschule für Erwachsene - Mittelschule (Italienisches Schulamt)");
	
    }
    
    @Test
    public void verifyTextAbendSchulen() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	Assert.assertEquals("Nella nostra provincia l'Istituto Comprensivo Bolzano III di via Napoli 1 - Bolzano è il punto di riferimento per questo tipo di servizio.", driver.findElement(By.xpath("//div[@id='main']/p[3]")).getText());
    }

    // Verify all links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1003544
    
    @Test
    public void verifyLinkScolastica() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	driver.findElement(By.linkText("sito dell'Intendenza scolastica italiana")).click();
	String expectedTitle = "Corsi per adulti | Intendenza scolastica italiana | Provincia autonoma di Bolzano - Alto Adige";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	
    } 
    
    @Test
    public void verifyLinkAbendschuleWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	driver.findElement(By.linkText("Webseite der für diesen Dienst zuständigen Institution")).click();
	String expectedTitle = "Corsi per adulti | Intendenza scolastica italiana | Provincia autonoma di Bolzano - Alto Adige";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
}

    //Verify all contact links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1003544

    @Test
    public void checkAbdendSchulenContactEmail() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	Assert.assertEquals("is.organici@scuola.alto-adige.it", driver.findElement(By.linkText("is.organici@scuola.alto-adige.it")).getText());
    }

    @Test
    public void checkAbendSchulenContactPEC() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	Assert.assertEquals("is.organici@pec.prov.bz.it", driver.findElement(By.linkText("is.organici@pec.prov.bz.it")).getText());

    }

    @Test
    public void checkAbendSchulenContactWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendschulenErwachsen();
	Assert.assertEquals("http://www.provinz.bz.it/italienisches-schulamt/verwaltung/83.asp", driver.findElement(By.linkText("http://www.provinz.bz.it/italienisches-schulamt/verwaltung/83.asp")).getText());


    }
    
    //Start testing Abendoberschule http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1005822
    
    @Test
    public void verifyLinkAbendoberschule() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	String expectedTitle = "Deutschsprachige Abendoberschule | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	
    }
    
    @Test
    public void verifyTextAbendoberschule() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	Assert.assertEquals("Die Abendkurse erstrecken sich über drei Jahre. Das erste Kursjahr bereitet auf die Eignungsprüfung der ersten und zweiten Klasse, das zweite Kursjahr auf die Eignungsprüfung der dritten und vierten Klasse und das dritte Kursjahr die Privatistinnen und Privatisten auf die Matura vor.", driver.findElement(By.xpath("//div[@id='main']/p[]")).getText());
    }
    
    // Verify all links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1005822
    
    @Test
    public void verifyLinkAbendoberschuleWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	driver.findElement(By.linkText("Webseite der für diesen Dienst zuständigen Institution")).click();
	String expectedTitle = "Autonome Provinz Bozen - Südtirol | Deutsches Schulamt | Abendschule";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }
    
    @Test
    public void verifyLinkAbendoberschuleVerwaltung() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	driver.findElement(By.linkText("http://www.provinz.bz.it/schulamt/verwaltung/111.asp?intOrga_id=8")).click();
	String expectedTitle = "Autonome Provinz Bozen - Südtirol | Deutsches Schulamt | Mitarbeiter/innen";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }

    //Verify all contact links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1005822

    @Test
    public void checkAbdendoberschuleContactEmail() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	Assert.assertEquals("sa.schulverwaltung@schule.suedtirol.it", driver.findElement(By.linkText("sa.schulverwaltung@schule.suedtirol.it")).getText());
    }

    @Test
    public void checkAbendoberchuleContactPEC() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	Assert.assertEquals("schulverwaltung.ammscolastica@pec.prov.bz.it", driver.findElement(By.linkText("schulverwaltung.ammscolastica@pec.prov.bz.it")).getText());

    }

    @Test
    public void checkAbendoberchuleContactWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadAbendoberschule();
	Assert.assertEquals("http://www.provinz.bz.it/schulamt/verwaltung/111.asp?intOrga_id=8", driver.findElement(By.xpath("//a[contains(.,'http://www.provinz.bz.it/schulamt/verwaltung/111.asp?intOrga_id=8')]")).getText());

    }

    //Start testing Schulkalender (Deutsches Schulamt)  http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1029144
    
    @Test
    public void verifyLinkSchulKalenderDE() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	String expectedTitle = "Schulkalender (Deutsches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	
    }
    
    @Test
    public void verifyTextSchulkalenderDE() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	String expectedTitle = "Schulkalender (Deutsches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	Assert.assertEquals("An den Südtiroler Grund-, Mittel- und Oberschulen mit deutscher Unterrichtssprache findet der Unterricht an fünf Tagen pro Woche statt. Einige wenige Oberschulen haben die Sechs-Tage-Woche. Eine Schule kann die Unterrichtsstunden auch auf sechs Wochentage verteilen, wenn Sie gemäß Landesgesetz Nr. 13 vom 13. Juli 2012 die Ermächtigung dafür erhält.", driver.findElement(By.xpath("//div[@id='main']/p[1]")).getText());

    }
    
    // Verify all links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1029144
    
    @Test
    public void verifyLinkDeutschenSchulamtes() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	driver.findElement(By.linkText("Webseite der für diesen Dienst zuständigen Institution")).click();
	String expectedTitle = "Autonome Provinz Bozen - Südtirol | Deutsches Schulamt";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }
    
    @Test
    public void verifyLinkSchulkalenderWebsiteDE() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	driver.findElement(By.linkText("Webseite der für diesen Dienst zuständigen Institution")).click();
	String expectedTitle = "Autonome Provinz Bozen - Südtirol | Deutsches Schulamt";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }
    
    
    //Verify all contact links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1029144
    
    @Test
    public void checkSchulkalenderDEContactEmail() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	Assert.assertEquals("deutsches.schulamt@schule.suedtirol.it", driver.findElement(By.linkText("deutsches.schulamt@schule.suedtirol.it")).getText());
    }
    
    @Test
    public void checkSchulkalenderDEContactPEC() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	Assert.assertEquals("schulamt.intendenzascolastica@pec.prov.bz.it", driver.findElement(By.linkText("schulamt.intendenzascolastica@pec.prov.bz.it")).getText());
	
    }
    
    @Test
    public void checkSchuleKalenderDEContactWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderDE();
	Assert.assertEquals("http://www.provinz.bz.it/schulamt/", driver.findElement(By.xpath("//a[contains(.,'http://www.provinz.bz.it/schulamt/')]")).getText());

    }

    //Start testing Schulkalender (Italienisches Schulamt)  http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1002760
    
    @Test
    public void verifyLinkSchulKalenderIT() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	String expectedTitle = "Schulkalender (Italienisches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	
    }
    
    @Test
    public void verifyTextSchulkalenderIT() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	String expectedTitle = "Schulkalender (Italienisches Schulamt) | Dienste A-Z | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	Assert.assertEquals("Dieser Dienst wird vom Italienischen Schulamt angeboten. Die nachfolgenden Informationen liegen nur in italienischer Sprache vor.", driver.findElement(By.xpath("//div[@id='main']/p[2]")).getText());

    }
    
    // Verify all links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1002760
    
    @Test
    public void verifyLinkImageFormulare() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	driver.findElement(By.xpath (".//*[@id='main']/p[1]/a/img")).click();
	Assert.assertEquals("Beschluss vom 23. Januar 2012, Nr. 75 (abgeändert mit Beschluss Nr. 210 vom 13.02.2012) - Schulkalender ›", driver.findElement(By.xpath(".//*[@id='servicelink']/table/tbody/tr/td[2]/ul/li/a")).getText());

    }
    
    @Test
    public void verifyLinkGiuntaProvinciale() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	driver.findElement(By.linkText("n. 75/2012")).click();
	String expectedTitle = "Beschluss vom 23. Januar 2012, Nr. 75 (abgeändert mit Beschluss Nr. 210 vom 13.02.2012) - Kindergarten- und Schulkalender | Formulare nach Kategorien | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }
    
    @Test
    public void verifyLinkGesetzbestimmungen() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	driver.findElement(By.linkText("Beschluss vom 23. Januar 2012, Nr. 75 (abgeändert mit Beschluss Nr. 210 vom 13.02.2012)")).click();
	String expectedTitle = "Beschluss vom 23. Januar 2012, Nr. 75 (abgeändert mit Beschluss Nr. 210 vom 13.02.2012) - Kindergarten- und Schulkalender | Formulare nach Kategorien | Südtiroler Bürgernetz";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
	Assert.assertEquals("Beschluss vom 23. Januar 2012, Nr. 75 (abgeändert mit Beschluss Nr. 210 vom 13.02.2012) - Kindergarten- und Schulkalender", driver.findElement(By.xpath(".//*[@id='main']/h1")).getText());

    }
    
    @Test
    public void verifyLinkWebsiteInstitution() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	driver.findElement(By.linkText("Webseite der für diesen Dienst zuständigen Institution")).click();
	String expectedTitle = "Calendario scolastico | Intendenza scolastica italiana | Provincia autonoma di Bolzano - Alto Adige";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle);
    }
    
    //Verify all contact links on http://www.provinz.bz.it/de/dienste/dienste-az.asp?bnsvaz_svid=1002760
    
    @Test
    public void checkSchulkalenderITContactEmail() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	Assert.assertEquals("is.ord-scol@scuola.alto-adige.it", driver.findElement(By.linkText("is.ord-scol@scuola.alto-adige.it")).getText());
    }
    
    @Test
    public void checkSchulkalenderITContactPEC() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	Assert.assertEquals("is.ord-scol@pec.prov.bz.it", driver.findElement(By.linkText("is.ord-scol@pec.prov.bz.it")).getText());
	
    }
    
    @Test
    public void checkSchuleKalenderITContactWebsite() {
	loadServicesPage();
	clickAndLoadAbendschulen();
	clickAndLoadSchulkalenderIT();
	Assert.assertEquals("http://www.provinz.bz.it/italienisches-schulamt/verwaltung/83.asp", driver.findElement(By.xpath("//a[contains(.,'http://www.provinz.bz.it/italienisches-schulamt/')]")).getText());

    }
    
        
    //TODO
    /* Find a smart way to check if download link provides the correct status code.
     * 
     */


    private void loadServicesPage() {
	driver.get("http://www.provinz.bz.it/de/dienste/dienste-az.asp");
    }
    
    private void clickAndLoadAbendschulen() {
	driver.findElement(By.linkText("Abendschulen")).click();
    }
    
    private void clickAndLoadAbendschulenErwachsen() {
	driver.findElement(By.linkText("Abendschule für Erwachsene - Mittelschule (Italienisches Schulamt)")).click();
    }
    
    private void clickAndLoadAbendoberschule() {
	driver.findElement(By.linkText("Deutschsprachige Abendoberschule")).click();
    }

    private void clickAndLoadSchulkalenderDE() {
	driver.findElement(By.linkText("Schulkalender (Deutsches Schulamt)")).click();
    }
    
    private void clickAndLoadSchulkalenderIT() {
	driver.findElement(By.linkText("Schulkalender (Italienisches Schulamt)")).click();
    }
}
