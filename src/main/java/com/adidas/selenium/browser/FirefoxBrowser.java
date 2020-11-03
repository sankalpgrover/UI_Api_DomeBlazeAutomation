package com.adidas.selenium.browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/


public class FirefoxBrowser {

    public static Capabilities getFirefoxCapabilities() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(String.valueOf(BrowserType.Firefox));
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates(true);
        profile.setAssumeUntrustedCertificateIssuer(true);
        cap.setCapability(FirefoxDriver.PROFILE, profile);
        return cap;
    }

    public static WebDriver getFirefoxDriver(Capabilities cap) {
        WebDriver driver = new FirefoxDriver(cap);
        return driver;
    }
}
