Ant + TestNG + Selenium Example
===============================

This is an example on how to use [the Ant build tool](http://ant.apache.org/) to perform [TestNG](http://testng.org/) test cases using [Selenium](http://www.seleniumhq.org/). All you need is a JDK (Java Development Kit) and Ant.

Build and run the project simply by placing yourself in this directory (the directory containing build.xml) and type:

    ant -Dtm.seleniumDriver=<Driver>

<Driver> should be replaced with either <code>AndroidDriver</code>, <code>FirefoxDriver</code>, or <code>ChromeDriver</code>. When ChromeDriver is used, you must set the path to the driver file:

    ant -Dtm.seleniumDriver=ChromeDriver -Dwebdriver.chrome.driver=<Path>

When AndroidDriver is used, you can set the optional tm.seleniumUrl parameter in this way:

    ant -Dtm.seleniumDriver=AndroidDriver -Dtm.seleniumUrl=<URL>

The Ant command will compile the classes and run TestNG, which in turn will generate an HTML summary of the tests in a "testng_output" subdirectory.

You can customize the test suite, such as adding a test class to it, by modifying the testng.xml file.

TMTest.java contains the actual tests. The <code>init</code> method, which is annotated with <code>@BeforeClass</code>, will set up the <code>driver</code> object for us, and it does so before the actual testing start.

The methods annotated with <code>@Test</code> perform the actual testing, and end with an assertion. Adding a test case is a simple as adding a Test-annotated method.

Finally, the driver is closed with a method annotated with <code>@AfterClass</code>.

Good luck with your Selenium testing!
