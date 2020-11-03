package com.adidas.selenium.browser;

import java.io.IOException;
import com.adidas.utils.TestSetup;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

/**
 * @author : SankalpGrover
 * @since : 11/2/2020, Mon
 **/


public class InitWebdriver {

    private WebDriver driver = null;
    TestSetup testSetup;

    public InitWebdriver(TestSetup testSetup) throws IOException {
        this.testSetup=testSetup;
        setUp();
    }


    public void setUp() throws IOException {
        BrowserType type = BrowserType.valueOf(testSetup.loadConfigProperties().getProperty("Browser"));
        switch (type) {
            case Firefox:
                driver = FirefoxBrowser.getFirefoxDriver(FirefoxBrowser.getFirefoxCapabilities());
                break;

            case Chrome:
                driver = ChromeBrowser.getChromeDriver(ChromeBrowser.getChromeCapabilities());
                break;
            default:
                driver = ChromeBrowser.getChromeDriver(ChromeBrowser.getChromeCapabilities());
        }
        driver.manage().timeouts().pageLoadTimeout(testSetup.getPageLoadTimeOut(), TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(testSetup.getImplicitWait(), TimeUnit.SECONDS);
        testSetup.setTestStatus("INFO","Maximize Browser Window");
        driver.manage().window().maximize();
    }

    public InitWebdriver() throws IOException {
        setUp();
    }

    public void teardown(WebDriver driver){
        if (driver != null) {
            testSetup.setTestStatus("INFO","Quitting Browser");
            driver.quit();
        }
    }

    public  WebDriver getDriver() {
        return driver;
    }


}

