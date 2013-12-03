Ant + TestNG + Selenium Example
===============================

This is an example on how to use [the Ant build tool](http://ant.apache.org/) to perform [TestNG](http://testng.org/) test cases. All you need is a JDK (Java Development Kit) and Ant.

As this project just serves as a working example on how to set things up, this project just makes a general test (checks that a web service responds appropriately, with a 200 OK response). You can run it like so:

    ant -Dtm.url=https://testingmachine.eu/

TMTest.java contains the actual test. The methods annotated with <code>@Test</code> perform the actual testing, and end with an assertion. Adding a test case is a simple as adding a Test-annotated method.

The Ant command will compile the classes and run TestNG, which in turn will generate an HTML summary of the tests in a "testng_output" subdirectory. (If you want to change this directory, all you have to do is to set a <code>-Dtm.testngOutput</code> parameter.

You can customize the test suite, such as adding a test class to it, by modifying the testng.xml file.

Good luck with your testing!
