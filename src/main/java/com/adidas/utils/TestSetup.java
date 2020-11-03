package com.adidas.utils;

import org.testng.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileInputStream;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.adidas.selenium.browser.BrowserType;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/

public class TestSetup {

    String suiteName = "";
    String extentReportName;
    public String testStatus = "INFO";
    public static ExtentReports extent;
    public static ExtentTest extentTest;
    public static ExtentSparkReporter extentReportFile;

    public TestSetup() throws IOException {
            extentReportName = "extentReportFile.html";
            extentReportFile = new ExtentSparkReporter(System.getProperty("user.dir") + "/"+extentReportName);
            extentReportFile.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
            extent = new ExtentReports();
            extent.attachReporter(extentReportFile);

    }


    public Properties loadConfigProperties() throws IOException {
        Properties configProps = new Properties();
            InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/config.properties");
            configProps.load(inputStream);
        return configProps;
    }

    public Properties loadMessageProperties() throws IOException {
        Properties msgProps = new Properties();
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/message.properties");
        msgProps.load(inputStream);
        return msgProps;
    }

    public void setTestStatus(String status, String msg) {
        try {
            String tempTestStatus = testStatus;
            testStatus = status;
            switch (status) {
                case "PASS":
                    extentTest.log(Status.PASS, loadMessageProperties().getProperty("testStatus_PASS") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
                case "FAIL":
                    extentTest.log(Status.FAIL, loadMessageProperties().getProperty("testStatus_FAIL") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
                case "WARNING":
                    extentTest.log(Status.WARNING, loadMessageProperties().getProperty("testStatus_WARNING") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
                case "INFO":
                    extentTest.log(Status.INFO, loadMessageProperties().getProperty("testStatus_INFO") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
                case "SKIP":
                    extentTest.log(Status.SKIP, loadMessageProperties().getProperty("testStatus_SKIP") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
                default:
                    extentTest.log(Status.FAIL, loadMessageProperties().getProperty("testStatus_FAIL") + " " + msg);
                    testStatus = (tempTestStatus.equals("FAIL") ? testStatus = tempTestStatus : testStatus);
                    break;
            }
        } catch (Exception e) {
            Assert.fail("Logging Failed: " + e.getLocalizedMessage());
        }
    }


    public void assertEquals(String fieldName,String expectedString, String actualString) {
        try {
            if (expectedString == null || actualString == null || expectedString.equals("") || actualString.equals("")) {
                setTestStatus("INFO",fieldName+": Expected: "+expectedString);
                setTestStatus("INFO",fieldName+": Actual: "+actualString);
                setTestStatus("FAIL", "Value is either BLANK or NULL");
            }else if(expectedString.equalsIgnoreCase(actualString)) {
                setTestStatus("INFO",fieldName+": Expected: "+expectedString);
                setTestStatus("INFO",fieldName+": Actual: "+actualString);
                setTestStatus("PASS", "");
            }
        } catch (AssertionError e) {
            setTestStatus("FAIL", e.getLocalizedMessage());
        }
    }


    public int getImplicitWait() throws IOException {
        setTestStatus("INFO", "ImplicitWait: " + loadConfigProperties().getProperty("ImplicitWait"));
        return Integer.parseInt(loadConfigProperties().getProperty("ImplicitWait"));
    }

    public int getExplicitWait() throws IOException {
        setTestStatus("INFO", "ExplicitWait: " + loadConfigProperties().getProperty("ExplicitWait"));
        return Integer.parseInt(loadConfigProperties().getProperty("ExplicitWait"));
    }

    public BrowserType getBrowser() throws IOException {
        setTestStatus("INFO", "Browser in use: " + loadConfigProperties().getProperty("Browser"));
        return BrowserType.valueOf(loadConfigProperties().getProperty("Browser"));
    }

    public int getPageLoadTimeOut() throws IOException {
        setTestStatus("INFO", "PageLoadTimeOut: " + loadConfigProperties().getProperty("PageLoadTimeOut"));
        return Integer.parseInt(loadConfigProperties().getProperty("PageLoadTimeOut"));
    }

}
