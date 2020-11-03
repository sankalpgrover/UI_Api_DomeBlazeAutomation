package com.adidas.utils;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.*;
import java.util.function.Function;
import java.util.concurrent.TimeUnit;
import com.adidas.selenium.browser.InitWebdriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * @author : SankalpGrover
 * @since : 11/2/2020, Mon
 **/


public class WaitHelper {
    TestSetup testSetup;

    public WaitHelper(TestSetup testSetup){
        this.testSetup=testSetup;
    }

    public WaitHelper() {

    }

    private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec, Class...exceptiontoIgnore) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.pollingEvery(Duration.ofMillis(pollingEveryInMiliSec));
        if(exceptiontoIgnore != null){
            for (Class exp : exceptiontoIgnore) {
                wait.ignoring(exp);
            }
        }
        return wait;
    }

    public  void setImplicitWait(long timeout,TimeUnit unit) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getDriver();
        driver.manage().timeouts().implicitlyWait(timeout, unit == null ? TimeUnit.SECONDS : unit);
    }

    public  void waitForElement(By locator, int timeOutInSeconds, int pollingEveryInMiliSec, Class...exceptiontoIgnore ) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        setImplicitWait(1, TimeUnit.SECONDS);
        WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec, exceptiontoIgnore);
        //wait.until(ExpectedConditions.visibilityOf(GenericHelper.getElement(locator)));
        setImplicitWait(testSetup.getImplicitWait(), TimeUnit.SECONDS);
    }

    public  void hardWait(int timeOutInSec) throws InterruptedException {
        Thread.sleep(timeOutInSec);
    }

    public  void waitForElementVisible(By locator,int timeOutInSeconds,int pollingEveryInMiliSec,Class...exceptiontoIgnore ) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        setImplicitWait(1, TimeUnit.SECONDS);
        WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec, exceptiontoIgnore);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        setImplicitWait(testSetup.getImplicitWait(), TimeUnit.SECONDS);
    }

    public  WebElement handleStaleElement(By locator,int retryCount,int delayInSeconds) throws InterruptedException, IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        WebDriver driver = initWebdriver.getDriver();
        WebElement element = null;

        while (retryCount >= 0) {
            try {
                element = driver.findElement(locator);
                return element;
            } catch (StaleElementReferenceException e) {
                hardWait(delayInSeconds);
                retryCount--;
            }
        }
        throw new StaleElementReferenceException("Element cannot be recovered");
    }

    public  void elementExits(By locator,int timeOutInSeconds,int pollingEveryInMiliSec) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        setImplicitWait(1, TimeUnit.SECONDS);
        WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec, NoSuchElementException.class,
                InvalidElementStateException.class);
        wait.until(elementLocatedBy(locator));
        setImplicitWait(testSetup.getImplicitWait(), TimeUnit.SECONDS);
    }

    public  void elementExistAndVisible(By locator,int timeOutInSeconds,int pollingEveryInMiliSec) throws IOException {
        InitWebdriver initWebdriver = new InitWebdriver();
        setImplicitWait(1, TimeUnit.SECONDS);
        WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec, NoSuchElementException.class,
                InvalidElementStateException.class);
        wait.until(elementLocatedBy(locator));
        //JavaScriptHelper.scrollIntoView(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        setImplicitWait(testSetup.getImplicitWait(), TimeUnit.SECONDS);

    }

    private  Function<WebDriver, Boolean> elementLocatedBy(final By locator){
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.findElements(locator).size() >= 1;
            }
        };
    }
}
