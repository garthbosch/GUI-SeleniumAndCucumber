package utils;

import com.relevantcodes.extentreports.LogStatus;
import commonFunctions.CommonBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.io.File;

public class ValidationLogsAndCreateScreenshots extends CommonBase {

    public ValidationLogsAndCreateScreenshots(SeleniumWebDriverUtils driver, int ssCount, String
            reportLocation, String imageLocation) {
        this.driver = driver;
        this.ssCount = ssCount;
        this.reportLocation = reportLocation;
        this.imageLocation = imageLocation;
    }

    public String createScreenshot(WebDriver driver, String screenshotName) throws Exception {
        ssCount = ssCount + 1;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            // copy file object to designated location
            FileUtils.copyFile(scrFile, new File(reportLocation + imageLocation + ssCount + "_" + removeStringSpecialCharsAndSpaces(screenshotName) + ".png"));
        } catch (Exception e) {
            log.error("Error while generating screenshot:\n" + e.getMessage());
            e.printStackTrace();
        }
        return reportLocation + imageLocation + ssCount + "_" + screenshotName + ".png";
    }

    public void log_validation(TestReports testReports, String testStepName, String expected, String actual, String
            screenshotName) throws Exception {
        actual = actual.trim();
        expected = expected.trim();

        if (expected.contains(actual)) {
            log.info("***VALIDATION SUCCESS*** " + testStepName + " || Expected = " + expected.trim() + " AND Actual = " + actual.trim());
            testReports.getExtentTest().log(LogStatus.PASS, testStepName, "Expected = " + expected + " AND Actual = " + actual);
            testReports.getExtentTest().log(LogStatus.INFO, testStepName, createScreenshot(driver.driver, screenshotName + "_success"));
        } else {
            log.error("***VALIDATION FAILURE*** " + testStepName + " || Expected = " + expected.trim() + " AND Actual = " + actual.trim());
            testReports.getExtentTest().log(LogStatus.FAIL, testStepName, "Expected = " + expected + " AND Actual = " + actual);
            testReports.getExtentTest().log(LogStatus.WARNING, testStepName, createScreenshot(driver.driver, screenshotName +
                    "_failure"));
            Assert.fail("***VALIDATION FAILURE*** " + testStepName + " || Expected = " + expected.trim() + " AND Actual = " + actual.trim());
            throw new Exception("***VALIDATION FAILURE*** " + testStepName + " || Expected = " + expected.trim() + " AND Actual = " + actual.trim());
        }
    }

    /*
     * This method removes all the spaces and special characters in the given string
     * @param st The string
     */
    public static String removeStringSpecialCharsAndSpaces(String st) {
        return st.replaceAll("[^a-zA-Z0-9-_]", "");
    }
}
