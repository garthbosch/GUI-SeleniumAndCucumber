GUI-SeleniumAndCucumber
=======================

Demo Automated Test Framework using the following technologies:
* Selenium WebDriver
* JUnit
* Java
* Cucumber
* Extent Reports
* Maven

This framework is built on a behavior driven development (BDD) test approach. 
It has one test scenario testing the Selenium Website.

Documentation
-------------
* [Installation]

Download a Framework
--------------
* Maven - 

Framework Architecture
--------------
	Project-Name
		
		|_configurations
		|	|_config.properties
		|	|...
		|_drivers
		|	|_linux
		|	|	|...
		|	|_mac
		|	|	|...
		|	|_windows
		|	|	|...
		|_src/test/java
		|	|_commonFunctions
		|	|	|_CommonBase.java
		|	|	|ConfigurationManager.java
		|	|	|...
		|	|_setpDefinitions
		|	|	|_UserStepDefinitions.java
		|	|	|...
		|	|_testRunners
		|		|_RunCucumberTest.java
		|		|...
		|	|_utils
		|	|	|_Loggin.java
		|	|	|_SeleniumWebDriverUtils.java
		|	|	|_TestReports.java
		|	|	|_ValidationLogsAndCreateScreenshots.java
		|	|	|...
		|_src/test/resources
		|	|_features
		|	|	|_selenium_download.feature
		|	|	|...
		
Running test
--------------

Go to your project directory from terminal and hit following commands
* `mvn test -Dcucumber.options="classpath:features"`