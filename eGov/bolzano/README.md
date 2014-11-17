Bolzano eGov Example
====================

This is an extension of the simple Testing Machine example that can be found in the parent of this directory. It contains some Bolzano-specific tests, and is using [Selenium](http://www.seleniumhq.org/) (and [Selendroid](http://selendroid.io/)).

The <code>init</code> method (in the TMText.java file), which is annotated with <code>@BeforeClass</code>, will set up the <code>driver</code> object for us, and it does so before the actual testing start. The actual tests are contained in the SeleniumTests.java file.

Finally, the driver is closed with a method annotated with <code>@AfterClass</code>.

For AndroidDriver tests, please make sure to put the file "selendroid-standalone-0.12.0-with-dependencies.jar" in the local directory.

Build and run the project simply by placing yourself in this directory (the directory containing build.xml) and type:

    ant -Dtm.seleniumDriver=<Driver>

<Driver> should be replaced with either <code>AndroidDriver</code>, <code>FirefoxDriver</code>, or <code>ChromeDriver</code>. When ChromeDriver is used, you must set the path to the driver file:

    ant -Dtm.seleniumDriver=ChromeDriver -Dwebdriver.chrome.driver=<Path>

When AndroidDriver is used, you can set the optional tm.seleniumUrl parameter in this way:

    ant -Dtm.seleniumDriver=AndroidDriver -Dtm.seleniumUrl=<URL>

The Ant command will compile the classes and run TestNG, which in turn will generate an HTML summary of the tests in a "testng_output" subdirectory. (If you want to change this directory, all you have to do is to set the <code>-Dtm.testngOutput</code> parameter.
