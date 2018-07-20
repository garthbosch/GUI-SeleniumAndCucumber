package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static utils.Logging.getLogger;

//import org.openqa.selenium.*;

/**
 * Created with IntelliJ IDEA.
 * User: Garth Bosch
 * Date: 2018/04/24
 * Time: 14:00
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumWebDriverUtils {

    public WebDriver driver;
    public String browserType;
    public String baseUrl;
    public Integer waitTimeOut;
    public String applicationType;
    public Boolean isDriverRunning;
    private boolean useRemoteWebDriver;
    private String remoteWebDriverUrl;
    private String browserDrivers;
    public File fileChromeDriver;
    public File fileIEDriver;
    public File fileFirefoxDriver;
    public File fileEdgeDriver;
    protected static Logger log = getLogger(true);
    private String operatingSystemName;

    public SeleniumWebDriverUtils(String browserType, String baseUrl, Integer waitTimeOut, String applicationType,
                                  boolean useRemoteWebDriver, String remoteWebDriverUrl, String browserDrivers) {
        this.browserType = browserType;
        this.baseUrl = baseUrl;
        this.waitTimeOut = waitTimeOut;
        this.applicationType = applicationType;
        isDriverRunning = false;
        this.useRemoteWebDriver = useRemoteWebDriver;
        this.remoteWebDriverUrl = remoteWebDriverUrl;
        this.browserDrivers = browserDrivers;
        setDriverProperties();
    }

    private void setDriverProperties() {
        operatingSystemName = System.getProperty("os.name");
        log.info("Operating system running the test is : " + operatingSystemName);
        if (operatingSystemName.contains("Windows")) {
            fileChromeDriver = new File(browserDrivers + "/windows/chromedriver_win32/chromedriver.exe");
            log.info("Where the file is: " + fileChromeDriver.getAbsolutePath());
            System.setProperty("webdriver.chrome.driver", fileChromeDriver.getAbsolutePath());
        }
        if (operatingSystemName.contains("Linux")) {
            fileChromeDriver = new File(browserDrivers + "/linux/chromedriver_linux64/chromedriver");
            System.setProperty("webdriver.chrome.driver", fileChromeDriver.getAbsolutePath());
        }
        if (operatingSystemName.contains("Mac")) {
            fileChromeDriver = new File(browserDrivers + "/mac/chromedriver_mac64/chromedriver");
            System.setProperty("webdriver.chrome.driver", fileChromeDriver.getAbsolutePath());
        }
    }

    public void startDriver() throws Exception {
        try {
            if (applicationType.toUpperCase().equals("DESKTOP")) {
                switch (browserType.toLowerCase()) {
                    case "chrome":
                        closeBrowserInstances();
                        if (useRemoteWebDriver) {
                            log.info("Remote WebDriver will be started at: " + remoteWebDriverUrl);
                            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl.trim()), setChromeOptions());
                        } else {
                            driver = new ChromeDriver(setChromeCapabilities());
                        }
                        isDriverRunning = true;
                        setURL();
                        break;

//                    case "firefox":
//                        closeBrowserInstances();
//                        if (useRemoteWebDriver) {
//                            log.info("Remote WebDriver will be started at: " + remoteWebDriverUrl);
////                            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), setChromeOptions());
//                        } else {
//                            driver = new FirefoxDriver(setCapabilities());
//                        }
//                        isDriverRunning = true;
//                        setURL();
//                        break;
//
                    case "ie":
                    case "internet explorer":
                    case "iexplore":
                        closeBrowserInstances();
                        if (useRemoteWebDriver) {
                            log.info("Remote WebDriver will be started at: " + remoteWebDriverUrl);
//                            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), setChromeOptions());
                        } else {
                            driver = new InternetExplorerDriver(setIECapabilities());
                        }
                        isDriverRunning = true;
                        setURL();
                        break;

//                    case "microsoftedge":
//                    case "edge":
//                    case "microsoft edge":
//                        if (useRemoteWebDriver) {
//                            log.info("Remote WebDriver will be started at: " + remoteWebDriverUrl);
////                            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), setEdgeOptions());
//                        } else {
//                            driver = new EdgeDriver(setCapabilities());
//                        }
//                        isDriverRunning = true;
//                        setURL();
//                        break;

                    default:
                        if (useRemoteWebDriver) {
                            log.info("Remote WebDriver will be started at: " + remoteWebDriverUrl);
//                            driver = new RemoteWebDriver(new URL(remoteWebDriverUrl), setChromeOptions());
                        } else {
                            driver = new ChromeDriver(setChromeCapabilities());
                        }
                        isDriverRunning = true;
                        setURL();
                        break;
                }
                log.info("Done selecting Browser");
            } else {
//                switch (browserType.toLowerCase()) {
//                    case BrowserType.ANDROID:
//                        log.info("Mobile Emulator will be started at: " + remoteWebDriverUrl);
//                        driver = new AndroidDriver(new URL(remoteWebDriverUrl), setAndroidOptions());
//                        isDriverRunning = true;
//                        setURL();
//                        break;
//                }
            }
            log.info("Selenium driver started");
        } catch (Exception e) {
            log.error("Something went wrong while starting up selenium driver - " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Capabilities setChromeOptions() throws Exception {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-plugins");
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setCapability("browser", "Chrome");
        capability.setCapability("browser_version", "66.0");
        capability.setCapability("os", "Windows");
        capability.setCapability("os_version", "10");
        capability.setCapability("browserstack.selenium_version", "3.11.0");
        capability.setCapability("resolution", "1024x768");
        capability.setCapability("browserstack.debug", "true");
        capability.setCapability(ChromeOptions.CAPABILITY, options);
        return capability;
    }

    private DesiredCapabilities setChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();
//        String downloadFilepath = System.getProperty("user.dir") + "\\" + whizzEduRoot + "\\download";
//        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//        chromePrefs.put("download.prompt_for_download", false);
//        chromePrefs.put("download.directory_upgrade", true);
//        chromePrefs.put("download.default_directory", downloadFilepath);
//        chromePrefs.put("profile.default_content_settings.popups", 0);
//        chromePrefs.put("profile.exit_type", "Normal");
//        options.setExperimentalOption("prefs", chromePrefs);
//        /* added this code to remove the message chrome was throwing "Chrome is being controlled by automated test software" */
//        options.addArguments("disable-infobars");
//        /* to disable browser extension popup */
//        options.addArguments("--disable-extensions");
////            options.addArguments("--profile-directory=Default");
//        options.addArguments("--user-data-dir=somedirectory");
////            options.addArguments("--start-maximized");
//
//        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        /* added this code to remove the error message chrome was throwing "You are using an unsupported command-line flag:
         * --ignore-certificate-errors. Stability and security will suffer" */
        options.addArguments("test-type");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("disable-infobars");

        DesiredCapabilities dc = null;
        dc = DesiredCapabilities.chrome();
        dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
        dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        dc.setJavascriptEnabled(true);
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        return dc;
    }

    //    private ChromeOptions setChromeOptions() {
//        ChromeOptions options = new ChromeOptions();
//        DesiredCapabilities dc = null;
//        String downloadFilepath = System.getProperty("user.dir") + "\\" + whizzEduRoot + "\\download";
//        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
//        chromePrefs.put("download.prompt_for_download", false);
//        chromePrefs.put("download.directory_upgrade", true);
//        chromePrefs.put("download.default_directory", downloadFilepath);
//        chromePrefs.put("profile.default_content_settings.popups", 0);
//        chromePrefs.put("profile.exit_type", "Normal");
//        options.setExperimentalOption("prefs", chromePrefs);
//        /* added this code to remove the message chrome was throwing "Chrome is being controlled by automated test software" */
//        options.addArguments("disable-infobars");
//        /* to disable browser extension popup */
//        options.addArguments("--disable-extensions");
//        /* added this code to remove the error message chrome was throwing "You are using an unsupported command-line flag:
//         * --ignore-certificate-errors. Stability and security will suffer" */
//        options.addArguments("test-type");
//        options.addArguments("--ignore-certificate-errors");
////            options.addArguments("--profile-directory=Default");
//        options.addArguments("--user-data-dir=somedirectory");
////            options.addArguments("--start-maximized");
//
//        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
//
//        dc = DesiredCapabilities.chrome();
//        dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
//        dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
//        dc.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
//        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        dc.setJavascriptEnabled(true);
//        dc.setCapability(ChromeOptions.CAPABILITY, options);
//        dc.setCapability(CapabilityType.PROXY, seleniumProxy);
//        options.merge(dc);
//        return options;
//    }
//
//    private FirefoxOptions setFirefoxOptions() {
//        FirefoxOptions options = new FirefoxOptions();
//        DesiredCapabilities dc = null;
//        dc = DesiredCapabilities.firefox();
//        dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
//        dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
//        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        dc.setCapability("marionette", true);
//        options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//        dc.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
//        dc.setJavascriptEnabled(true);
//        FirefoxProfile fp = new FirefoxProfile();
//        fp.setAcceptUntrustedCertificates(true);
//        fp.setPreference("xpinstall.signatures.required", false);
//        options.setProfile(fp);
//        options.merge(dc);
//        return options;
//    }
//
    private DesiredCapabilities setIECapabilities() {
        DesiredCapabilities dc = null;
        dc = DesiredCapabilities.internetExplorer();
        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
        dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
        dc.setJavascriptEnabled(true);
        return dc;
    }
//
//    private EdgeOptions setEdgeOptions() {
//        EdgeOptions options = new EdgeOptions();
//        DesiredCapabilities dc = null;
//        dc = DesiredCapabilities.edge();
//        dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        dc.setCapability(CapabilityType.ELEMENT_SCROLL_BEHAVIOR, true);
//        dc.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
//        dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
//        dc.setJavascriptEnabled(true);
//        options.merge(dc);
//        return options;
//    }
//
//    private ChromeOptions setAndroidOptions() {
//        ChromeOptions options = new ChromeOptions();
//        DesiredCapabilities dc = null;
//        dc = DesiredCapabilities.android();
//        dc.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
//        dc.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
//        dc.setCapability(MobileCapabilityType.VERSION, "8.1.0");
//        dc.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
//        dc.setCapability("platformName", "Android");
//        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION, "23");
//        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
//        options.merge(dc);
//        return options;
//    }

    private void setURL() throws Exception {
        if (baseUrl != null && !baseUrl.isEmpty()) {
            driver.manage().timeouts().implicitlyWait(waitTimeOut, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            if (applicationType.toUpperCase().equals("DESKTOP")) {
//                driver.manage().window().setSize(new Dimension(1060, 250));
                driver.manage().window().maximize();
            }
            driver.manage().deleteAllCookies();
            Robot r = new Robot();
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            driver.get(baseUrl);
        } else {
            throw new Exception("====================NO URL SPECIFIED======================");
        }
    }

    public void shutdown() {
        try {
            driver.quit();
            closeBrowserInstances();
            log.info("Driver shutting down");
        } catch (Exception ex) {
            log.error("Error found while shutting down driver - " + ex.getMessage());
        }
        isDriverRunning = false;
    }

    public void closeBrowserInstances() throws Exception {
        String serviceName = "";
        String driverName = "";
        if (browserType.equalsIgnoreCase("chrome")) {
            serviceName = "chrome.exe";
            driverName = "chromedriver.exe";
        }
        if (browserType.equalsIgnoreCase("ie") || browserType.equalsIgnoreCase("internet explorer")
                || browserType.equalsIgnoreCase("iexplore")) {
            serviceName = "iexplore.exe";
            driverName = "IEDriverServer.exe";
        }
        if (browserType.equalsIgnoreCase("firefox")) {
            serviceName = "firefox.exe";
            driverName = "geckodriver.exe";
        }
        String TASKLIST = "tasklist";
        String KILL = "taskkill /F /IM ";
        String line;
        Process p = Runtime.getRuntime().exec(TASKLIST);
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        log.info(serviceName + " process being closed");
        while ((line = reader.readLine()) != null) {
            if (line.contains(serviceName)) {
                Process killProcess = Runtime.getRuntime().exec(KILL + serviceName);
            }
            if (line.contains(driverName)) {
                Process killProcess = Runtime.getRuntime().exec(KILL + driverName);
            }
        }
    }

    public Boolean waitForVisibilityOfElement(String elementType, String element) {
        try {
            switch (elementType.toLowerCase()) {
                case "id":
                    WebElement webIdElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.id(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "name":
                    WebElement webNameElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.name(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "xpath":
                    WebElement webXpathElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "cssselector":
                    WebElement webCssSelectorElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "linktext":
                    WebElement webLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "partiallinktext":
                    WebElement webPartialLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return false;
            }
        } catch (Exception ex) {
            logWaitFailure(elementType, element, ex);
            return false;
        }
    }

    public Boolean waitForElementToBeClickable(String elementType, String element) {
        try {
            switch (elementType.toLowerCase()) {
                case "id":
                    WebElement webIdElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.id(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "name":
                    WebElement webNameElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.name(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "xpath":
                    WebElement webXpathElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.xpath(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "cssselector":
                    WebElement webCssSelectorElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.cssSelector(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "linktext":
                    WebElement webLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.linkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "partiallinktext":
                    WebElement webPartialLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.elementToBeClickable(By.partialLinkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return false;
            }
        } catch (Exception ex) {
            logWaitFailure(elementType, element, ex);
            return false;
        }
    }

    public Boolean waitForElementPresenceLocated(String elementType, String element) {
        try {
            switch (elementType.toLowerCase()) {
                case "id":
                    WebElement webIdElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "name":
                    WebElement webNameElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.name(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "xpath":
                    WebElement webXpathElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "cssselector":
                    WebElement webCssSelectorElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "linktext":
                    WebElement webLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.linkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                case "partialtinktext":
                    WebElement webPartialLinkTextElement = (new WebDriverWait(driver, waitTimeOut)).until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(element)));
                    logWaitSuccess(elementType, element);
                    return true;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return false;
            }
        } catch (Exception ex) {
            logWaitFailure(elementType, element, ex);
            return false;
        }
    }

    public boolean invisibilityOfElementLocated(String elementType, String element, int timeOutInSec) {
        try {
            switch (elementType.toLowerCase()) {
                case "id":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(element)));

                case "name":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.name(element)));

                case "xpath":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(element)));

                case "cssselector":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(element)));

                case "linktext":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(element)));

                case "partiallinktext":
                    return (new WebDriverWait(driver, timeOutInSec)).until(ExpectedConditions.invisibilityOfElementLocated(By.partialLinkText(element)));

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return false;
            }
        } catch (Exception ex) {
            logWaitFailure(elementType, element, ex);
            return false;
        }
    }

    private void logWaitSuccess(String elementType, String element) {
        log.info("The " + elementType + " element " + element + " found on the page");
    }

    private void logWaitFailure(String elementType, String element, Exception ex) {
        log.error("The " + elementType + " element " + element + " NOT found on the page - " + ex.getMessage());
        ex.printStackTrace();
    }

    public void enterText(String elementType, String element, String text) {
        if (waitForElementToBeClickable(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    driver.findElement(By.id(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                case "name":
                    driver.findElement(By.name(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                case "cssselector":
                    driver.findElement(By.cssSelector(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                case "xpath":
                    driver.findElement(By.xpath(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                case "linktext":
                    driver.findElement(By.linkText(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                case "partiallinktext":
                    driver.findElement(By.partialLinkText(element)).sendKeys(text);
                    logEnterTextSuccess(elementType, element, text);
                    break;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    break;
            }
        }
    }

    private void logEnterTextSuccess(String elementType, String element, String text) {
        log.info("Text " + text + " entered successfully in element " + element + " with attribute type " + elementType);
    }

    public void sendEnterKey(String elementType, String element) {
        if (waitForElementToBeClickable(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    driver.findElement(By.id(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                case "name":
                    driver.findElement(By.name(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                case "cssselector":
                    driver.findElement(By.cssSelector(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                case "xpath":
                    driver.findElement(By.xpath(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                case "linktext":
                    driver.findElement(By.linkText(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                case "partiallinktext":
                    driver.findElement(By.partialLinkText(element)).sendKeys(Keys.ENTER);
                    logSendEnterKeySuccess(elementType, element);
                    break;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    break;
            }
        }
    }

    private void logSendEnterKeySuccess(String elementType, String element) {
        log.info("Enter button executed successfully on " + element + " with attribute type " + elementType);
    }

    public void click(String elementType, String element) {
        if (waitForElementToBeClickable(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    driver.findElement(By.id(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                case "name":
                    driver.findElement(By.name(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                case "cssselector":
                    driver.findElement(By.cssSelector(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                case "xpath":
                    driver.findElement(By.xpath(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                case "linktext":
                    driver.findElement(By.linkText(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                case "partiallinktext":
                    driver.findElement(By.partialLinkText(element)).click();
                    logClickSuccess(elementType, element);
                    break;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    break;
            }
        }
    }

    private void logClickSuccess(String elementType, String element) {
        log.info("Successfully clicked on element " + element + " with attribute type " + elementType);
    }

    public WebElement findElement(String elementType, String element) {
        switch (elementType.toLowerCase()) {
            case "id":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.id(element)) : null;

            case "name":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.name(element)) : null;

            case "cssselector":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.cssSelector(element)) : null;

            case "xpath":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.xpath(element)) : null;

            case "linktext":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.linkText(element)) : null;

            case "partiallinktext":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElement(By.partialLinkText(element)) : null;

            default:
                log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                return null;
        }
    }

    public List<WebElement> findElements(String elementType, String element) {
        switch (elementType.toLowerCase()) {
            case "id":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.id(element)) : null;

            case "name":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.name(element)) : null;

            case "cssselector":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.cssSelector(element)) : null;

            case "xpath":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.xpath(element)) : null;

            case "linktext":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.linkText(element)) : null;

            case "partiallinktext":
                return (waitForVisibilityOfElement(elementType, element)) ? driver.findElements(By.partialLinkText(element)) : null;

            default:
                log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                return null;
        }
    }

    //    if the element does not exist then it will only return a false and not an exception
    public boolean isElementPresentInDOM(String elementType, String element) {
        try {
            switch (elementType.toLowerCase()) {
                case "id":
                    return driver.findElements(By.id(element)).size() > 0 ? true : false;

                case "name":
                    return driver.findElements(By.name(element)).size() > 0 ? true : false;

                case "cssselector":
                    return driver.findElements(By.cssSelector(element)).size() > 0 ? true : false;

                case "xpath":
                    return driver.findElements(By.xpath(element)).size() > 0 ? true : false;

                case "linktext":
                    return driver.findElements(By.linkText(element)).size() > 0 ? true : false;

                case "partiallinktext":
                    return driver.findElements(By.partialLinkText(element)).size() > 0 ? true : false;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return false;
            }
        } catch (Exception e) {
            log.error("Something went wrong while looking for element " + element + " with attribute type " + elementType + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public String getElementText(String elementType, String element) {
        switch (elementType.toLowerCase()) {
            case "id":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.id(element)).getText() : null;

            case "name":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.name(element)).getText() : null;

            case "cssselector":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.cssSelector(element)).getText() : null;

            case "xpath":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.xpath(element)).getText() : null;

            case "linktext":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.linkText(element)).getText() : null;

            case "partiallinktext":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.partialLinkText(element)).getText() : null;

            default:
                log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                return null;
        }
    }

    public String getFirstSelectedOptionText(String elementType, String element) {
        if (waitForElementToBeClickable(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    Select selectId = new Select(driver.findElement(By.id(element)));
                    return selectId.getFirstSelectedOption().getText();

                case "name":
                    Select selectName = new Select(driver.findElement(By.name(element)));
                    return selectName.getFirstSelectedOption().getText();

                case "cssselector":
                    Select selectCssSelector = new Select(driver.findElement(By.cssSelector(element)));
                    return selectCssSelector.getFirstSelectedOption().getText();

                case "xpath":
                    Select selectXpath = new Select(driver.findElement(By.xpath(element)));
                    return selectXpath.getFirstSelectedOption().getText();

                case "linktext":
                    Select selectLinkText = new Select(driver.findElement(By.linkText(element)));
                    return selectLinkText.getFirstSelectedOption().getText();

                case "partiallinktext":
                    Select selectPartialLinkText = new Select(driver.findElement(By.partialLinkText(element)));
                    return selectPartialLinkText.getFirstSelectedOption().getText();

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    return null;
            }
        }
        return null;
    }

    public String getElementAttributeValue(String elementType, String element, String attribute) {
        switch (elementType.toLowerCase()) {
            case "id":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.id(element)).getAttribute
                        (attribute) : null;

            case "name":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.name(element)).getAttribute
                        (attribute) : null;

            case "cssselector":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.cssSelector(element)).getAttribute
                        (attribute) : null;

            case "xpath":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.xpath(element)).getAttribute
                        (attribute) : null;

            case "linktext":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.linkText(element)).getAttribute
                        (attribute) : null;

            case "partiallinktext":
                return (waitForElementToBeClickable(elementType, element)) ? driver.findElement(By.partialLinkText(element)).getAttribute
                        (attribute) : null;

            default:
                log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                return null;
        }
    }

    public void pause(int t) throws InterruptedException {
        Thread.sleep(t);
    }

    public void selectByVisibleText(String elementType, String element, String text) {
        if (waitForElementPresenceLocated(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    Select selectId = new Select(driver.findElement(By.id(element)));
                    selectId.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                case "name":
                    Select selectName = new Select(driver.findElement(By.name(element)));
                    selectName.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                case "cssselector":
                    Select selectCssSelector = new Select(driver.findElement(By.cssSelector(element)));
                    selectCssSelector.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                case "xpath":
                    Select selectXpath = new Select(driver.findElement(By.xpath(element)));
                    selectXpath.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                case "linktext":
                    Select selectLinkText = new Select(driver.findElement(By.linkText(element)));
                    selectLinkText.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                case "partiallinktext":
                    Select selectPartialLinkText = new Select(driver.findElement(By.partialLinkText(element)));
                    selectPartialLinkText.selectByVisibleText(text);
                    logSelectSuccess(elementType, element, text);
                    break;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    break;
            }
        }
    }

    private void logSelectSuccess(String elementType, String element, String text) {
        log.info("Successfully selected " + text + " on dropdown element " + element + " with attribute type " + elementType);
    }

    public void selectByValue(String elementType, String element, String value) {
        if (waitForElementPresenceLocated(elementType, element)) {
            switch (elementType.toLowerCase()) {
                case "id":
                    Select selectId = new Select(driver.findElement(By.id(element)));
                    selectId.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                case "name":
                    Select selectName = new Select(driver.findElement(By.name(element)));
                    selectName.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                case "cssselector":
                    Select selectCssSelector = new Select(driver.findElement(By.cssSelector(element)));
                    selectCssSelector.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                case "xpath":
                    Select selectXpath = new Select(driver.findElement(By.xpath(element)));
                    selectXpath.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                case "linktext":
                    Select selectLinkText = new Select(driver.findElement(By.linkText(element)));
                    selectLinkText.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                case "partiallinkext":
                    Select selectPartialLinkText = new Select(driver.findElement(By.partialLinkText(element)));
                    selectPartialLinkText.selectByValue(value);
                    logSelectSuccess(elementType, element, value);
                    break;

                default:
                    log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                    break;
            }
        }
    }

    public void setImplicitWaitTimeout(int timeoutInSeconds) {
        try {
            driver.manage().timeouts().implicitlyWait(timeoutInSeconds, TimeUnit.SECONDS);
            log.info("Successfully set the driver implicit wait value to " + timeoutInSeconds + " seconds");
        } catch (Exception e) {
            log.error("Error occurred while trying to set the driver implicit wait value to " + timeoutInSeconds + " seconds - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void refreshPage() {
        try {
            driver.navigate().refresh();
            log.info("Page successfully refreshed");
        } catch (Exception e) {
            log.error("Something went wrong while refreshing the page - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToFrame(String frame, String elementType) {
        switch (elementType.toLowerCase()) {
            case "id":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            case "name":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            case "xpath":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            case "cssselector":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            case "linktext":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            case "partiallinktext":
                driver.switchTo().frame(findElement(elementType, frame));
                log.info("Successfully switched to frame - " + frame);
                break;

            default:
                log.error("No or incorrect element type was specified, please specify which element attribute you want to interact with");
                break;
        }
    }

    public void switchToParentFrame() {
        try {
            driver.switchTo().parentFrame();
            log.info("Successfully switched to parent frame");
        } catch (Exception e) {
            log.error("Unable to find frame - " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getPageSource() {
        return driver.getPageSource();
    }

    public String getAlertTextAndAccept() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            log.info("Alert data: " + alertText);
            alert.accept();
            return alertText;
        } catch (NoAlertPresentException e) {
            log.error("Something went wrong while getting the alert text - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String switchToNewWindow() {
        try {
            String firstWindow = driver.getWindowHandle();
            Set<String> windowHandles = driver.getWindowHandles();
            log.info("Number of windows: " + windowHandles.size());
            for (String windowHandle : windowHandles) {
                log.info("Handle: " + windowHandle);
                if (!windowHandle.equalsIgnoreCase(firstWindow)) {
                    driver.switchTo().window(windowHandle);
                    log.info("Switched to window - " + windowHandle);
                    return windowHandle;
                }
            }
        } catch (Exception e) {
            log.error("Something went wrong while switching to new window " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void switchToWindow(String windowHandle) {
        try {
            driver.switchTo().window(windowHandle);
            log.info("Switched to window - " + windowHandle);
        } catch (Exception e) {
            log.error("Something went wrong while switching to window " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getCurrentWindowHandle() {
        try {
            String windowHandle = driver.getWindowHandle();
            log.info("Current window handle - " + windowHandle);
            return windowHandle;
        } catch (Exception e) {
            log.error("Something went wrong while getting window " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void closeWindow(String windowHandle) {
        try {
            driver.switchTo().window(windowHandle).close();
            log.info("Closed window - " + windowHandle);
        } catch (Exception e) {
            log.error("Something went wrong while closing window " + e.getMessage());
            e.printStackTrace();
        }
    }
}