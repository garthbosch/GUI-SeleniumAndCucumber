package commonFunctions;

import com.relevantcodes.extentreports.ExtentReports;
import org.apache.log4j.Logger;
import org.testng.asserts.SoftAssert;
import utils.Logging;
import utils.SeleniumWebDriverUtils;
import utils.TestReports;
import utils.ValidationLogsAndCreateScreenshots;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CommonBase {

    protected ValidationLogsAndCreateScreenshots validateAndScreenshots;
    protected SeleniumWebDriverUtils driver;
    protected TestReports testReports = new TestReports();
    protected ConfigurationManager configMan;
    protected SoftAssert s_assert = new SoftAssert();

    //    config settings
    protected String trainTestsUploads;
    protected String reportLocation;
    protected String imageLocation;
    protected String browserDrivers;
    protected String testEnvironment;
    protected String baseUrl;
    protected String applicationType;
    protected String browserType;
    protected Integer waitTimeOut;
    protected boolean useRemoteWebDriver;
    protected String remoteWebDriverUrl;
    protected String remoteExecutionUsername;
    protected String remoteExecutionAccessKey;
    protected Double clientHighDefaultAmount;
    protected Double clientLowDefaultAmount;

    //    various
    protected static Logger log = Logging.getLogger(true);
    protected int ssCount;

    //    test data from xml
    protected String testcase;
    protected String username;
    protected String password;

    public void setUp() throws Exception {
        try {
            configMan = new ConfigurationManager();
            trainTestsUploads = configMan.getTrainTestsUploads();
            reportLocation = configMan.getReportLocation() + testcase + "/Execution_" + (new SimpleDateFormat
                    ("yyyyMMddhhmmss")
                    .format(new Date()));
            imageLocation = configMan.getImageLocation();
            browserDrivers = configMan.getBrowserDrivers();
            testEnvironment = configMan.getTestEnvironment();
            if (testEnvironment.equalsIgnoreCase("TEST")) {
                baseUrl = configMan.getTestURL();
            } else {
                baseUrl = configMan.getUatURL();
            }
            applicationType = configMan.getApplicationType();
            browserType = configMan.getBrowserType();
            waitTimeOut = configMan.getWaitTimeOut();
            useRemoteWebDriver = configMan.isUseRemoteWebDriver();
            remoteWebDriverUrl = configMan.getRemoteWebDriverUrl();
            remoteExecutionUsername = configMan.getRemoteExecutionUsername();
            remoteExecutionAccessKey = configMan.getRemoteExecutionAccessKey();
            clientHighDefaultAmount = configMan.getClientHighDefaultAmount();
            clientLowDefaultAmount = configMan.getClientLowDefaultAmount();
            username = configMan.getUsername();
            password = configMan.getPassword();
            testReports.setExtent(new ExtentReports(reportLocation + "/Automation.html", true));
            driver = new SeleniumWebDriverUtils(browserType, baseUrl, waitTimeOut, applicationType,
                    useRemoteWebDriver, remoteWebDriverUrl, browserDrivers);
//            driver.startDriver();
            log.info("SETUP DETAILS:");
            log.info("TEST ENVIRONMENT === " + testEnvironment);
            log.info("URL ================ " + baseUrl);
            log.info("BROWSER TYPE ======= " + browserType);
            log.info("REMOTE EXECUTION === " + useRemoteWebDriver);
            log.info("REPORT LOCATION ==== " + new File(reportLocation).getAbsolutePath());
            log.info("Setup completed successfully");
        } catch (Exception e) {
            log.error("Something when wrong while running setup - " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected void initialiseActions() {
        validateAndScreenshots = new ValidationLogsAndCreateScreenshots(driver, ssCount, reportLocation,
                imageLocation);
    }

    protected String getTitle(String idNo) {
        log.info("Gender digits: " + idNo.substring(6, 10));
        int idGenderDigits = Integer.parseInt(idNo.substring(6, 10));
        return (idGenderDigits > 4999 ? "Mr" : "Mrs");
    }

    public void tearDown() throws Exception {
        driver.shutdown();
        testReports.getExtent().flush();
        s_assert.assertAll();
    }
}
