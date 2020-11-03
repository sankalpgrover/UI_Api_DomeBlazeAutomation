package com.adidas.selenium.pageobjects;

import java.time.Duration;
import org.openqa.selenium.*;
import java.util.function.Function;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {

    private WebDriver driver;

    private By getFindByAnnotation(FindBy annotation){
        switch (annotation.how()) {
            case CLASS_NAME:
                return new By.ByClassName(annotation.using());
            case CSS:
                return new By.ByCssSelector(annotation.using());
            case ID:
                return new By.ById(annotation.using());
            case LINK_TEXT:
                return new By.ByLinkText(annotation.using());
            case NAME:
                return new By.ByName(annotation.using());
            case PARTIAL_LINK_TEXT:
                return new By.ByPartialLinkText(annotation.using());
            case XPATH:
                return new By.ByXPath(annotation.using());
            default :
                throw new IllegalArgumentException("Locator not Found : " + annotation.how() + " : " + annotation.using());
        }
    }

    protected By getElemetLocator(Object obj,String element) throws SecurityException,NoSuchFieldException {
        Class childClass = obj.getClass();
        By locator = null;
        try {
            locator = getFindByAnnotation(childClass.
                    getDeclaredField(element).
                    getAnnotation(FindBy.class));
        } catch (SecurityException | NoSuchFieldException e) {
            throw e;
        }
        return locator;
    }

    public void waitForElement(WebElement element, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.ignoring(NoSuchElementException.class);
        wait.ignoring(ElementNotVisibleException.class);
        wait.ignoring(StaleElementReferenceException.class);
        wait.ignoring(ElementNotVisibleException.class);
        wait.pollingEvery(Duration.ofMillis(25));
        wait.until(elementLocated(element));
    }

    private Function<WebDriver, Boolean> elementLocated(final WebElement element) {
        return new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return element.isDisplayed();
            }
        };
    }

    public BasePage(WebDriver driver) {
        if(driver == null)
            throw new IllegalArgumentException("Driver object is null");
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

}
