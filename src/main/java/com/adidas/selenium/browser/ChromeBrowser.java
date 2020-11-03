package com.adidas.selenium.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/


public class ChromeBrowser {

    public static Capabilities getChromeCapabilities() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setBrowserName(String.valueOf(BrowserType.Chrome));
        cap.setJavascriptEnabled(true);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        return cap;
    }

    public static WebDriver getChromeDriver(Capabilities cap) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver(cap);
        return driver;
    }
}
