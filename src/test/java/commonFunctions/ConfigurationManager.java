package commonFunctions;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Garth.bosch
 * Date: 2018/02/05
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public final class ConfigurationManager {

    private static String trainTestsUploads;
    private static String reportLocation;
    private static String imageLocation;
    private static String browserDrivers;
    private static String testEnvironment;
    private static String uatURL;
    private static String testURL;
    private static String testDBdriverName;
    private static String testDBconnectionString;
    private static String testDBusername;
    private static String testDBpassword;
    private static String uatDBdriverName;
    private static String uatDBconnectionString;
    private static String uatDBusername;
    private static String uatDBpassword;
    private static Integer waitTimeOut;
    private static String applicationType;
    private static String browserType;
    private static boolean useRemoteWebDriver;
    private static String remoteWebDriverUrl;
    private static String remoteExecutionUsername;
    private static String remoteExecutionAccessKey;
    private static Double clientHighDefaultAmount;
    private static Double clientLowDefaultAmount;
    private static String username;
    private static String password;

    public Properties properties;
    private String configFilePath = "configurations/config.properties";

    public ConfigurationManager() {
        try {
            loadConfigs();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void loadConfigs() {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(configFilePath));
            trainTestsUploads = properties.getProperty("trainTestsUploads");
            reportLocation = properties.getProperty("reportLocation");
            imageLocation = properties.getProperty("imageLocation");
            browserDrivers = properties.getProperty("browserDrivers");
            testEnvironment = properties.getProperty("testEnvironment");
            uatURL = properties.getProperty("uatURL");
            testURL = properties.getProperty("testURL");
            testDBdriverName = properties.getProperty("testDBdriverName");
            testDBconnectionString = properties.getProperty("testDBconnectionString");
            testDBusername = properties.getProperty("testDBusername");
            testDBpassword = properties.getProperty("testDBpassword");
            uatDBdriverName = properties.getProperty("uatDBdriverName");
            uatDBconnectionString = properties.getProperty("uatDBconnectionString");
            uatDBusername = properties.getProperty("uatDBusername");
            uatDBpassword = properties.getProperty("uatDBpassword");
            applicationType = properties.getProperty("applicationType");
            waitTimeOut = Integer.valueOf(properties.getProperty("waitTimeOut"));
            browserType = properties.getProperty("browserType");
            if (properties.getProperty("useRemoteWebDriver").equalsIgnoreCase("true")
                    || properties.getProperty("useRemoteWebDriver").equalsIgnoreCase("yes")
                    || properties.getProperty("useRemoteWebDriver").equals("")
                    || properties.getProperty("useRemoteWebDriver").isEmpty()) {
                useRemoteWebDriver = true;
            } else {
                useRemoteWebDriver = false;
            }
            remoteWebDriverUrl = properties.getProperty("remoteWebDriverUrl");
            remoteExecutionUsername = properties.getProperty("remoteExecutionUsername");
            remoteExecutionAccessKey = properties.getProperty("remoteExecutionAccessKey");
            clientHighDefaultAmount = Double.valueOf(properties.getProperty("clientHighDefaultAmount"));
            clientLowDefaultAmount = Double.valueOf(properties.getProperty("clientLowDefaultAmount"));
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    public static String getReportLocation() {
        return reportLocation;
    }

    public static String getImageLocation() {
        return imageLocation;
    }

    public static String getUatURL() {
        return uatURL;
    }

    public static String getTestURL() {
        return testURL;
    }

    public static String getTestDBdriverName() {
        return testDBdriverName;
    }

    public static String getTestDBconnectionString() {
        return testDBconnectionString;
    }

    public static String getTestDBusername() {
        return testDBusername;
    }

    public static String getTestDBpassword() {
        return testDBpassword;
    }

    public static String getUatDBdriverName() {
        return uatDBdriverName;
    }

    public static String getUatDBconnectionString() {
        return uatDBconnectionString;
    }

    public static String getUatDBusername() {
        return uatDBusername;
    }

    public static String getUatDBpassword() {
        return uatDBpassword;
    }

    public static String getBrowserDrivers() {
        return browserDrivers;
    }

    public static String getApplicationType() {
        return applicationType;
    }

    public static Integer getWaitTimeOut() {
        return waitTimeOut;
    }

    public static String getBrowserType() {
        return browserType;
    }

    public static boolean isUseRemoteWebDriver() {
        return useRemoteWebDriver;
    }

    public static String getRemoteWebDriverUrl() {
        return remoteWebDriverUrl;
    }

    public static String getRemoteExecutionUsername() {
        return remoteExecutionUsername;
    }

    public static String getRemoteExecutionAccessKey() {
        return remoteExecutionAccessKey;
    }

    public static String getTestEnvironment() {
        return testEnvironment;
    }

    public static String getTrainTestsUploads() {
        return trainTestsUploads;
    }

    public static Double getClientHighDefaultAmount() {
        return clientHighDefaultAmount;
    }

    public static Double getClientLowDefaultAmount() {
        return clientLowDefaultAmount;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
