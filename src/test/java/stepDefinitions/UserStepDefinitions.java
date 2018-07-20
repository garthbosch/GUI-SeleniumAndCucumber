package stepDefinitions;

import com.relevantcodes.extentreports.ExtentReports;
import commonFunctions.CommonBase;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStepDefinitions extends CommonBase {

    public UserStepDefinitions() throws Exception {
        testcase = "Selenium_Download_Validation";
        setUp();
        initialiseActions();
    }

    @Given("^I navigate to website Selenium")
    public void navigate_to_website() throws Exception {
        driver.startDriver();
        testReports.setExtent(new ExtentReports(reportLocation + "/Automation.html", true));
        testReports.setExtentTest(testReports.getExtent().startTest(testcase));
    }

    @When("^I click on linkText \"(.*?)\"$")
    public void click_link(String elementValue) {
        driver.click("linktext", elementValue);
    }

    @Then("^I should see the title \"(.*?)\" in (.+) \"(.*?)\"")
    public void i_should_see_the_title(String expectedValue, String elementType, String elementValue) throws Exception {
        String actualValue = driver.getElementText(elementType, elementValue);
        validateAndScreenshots.log_validation(testReports, "Validate Selenium Home Page", expectedValue, actualValue, "selenium_home_page");
    }

    @Then("^I should see \"(.*?)\" heading in (.+) \"(.*?)\"")
    public void i_should_see(String expectedValue, String elementType, String elementValue) throws Exception {
        String actualValue = driver.getElementText(elementType, elementValue);
        validateAndScreenshots.log_validation(testReports, "Validate Selenium Downloads Page", expectedValue, actualValue, "selenium_downloads_page");
        testReports.getExtent().endTest(testReports.getExtentTest());
    }

    @Then("Shutdown test")
    public void shutdown() throws Exception {
        tearDown();
    }
}
