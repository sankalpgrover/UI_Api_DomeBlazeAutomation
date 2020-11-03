package com.adidas.utils;

import org.openqa.selenium.*;

/**
 * @author : SankalpGrover
 * @since : 11/2/2020, Mon
 **/


public class EventsHelper {
    TestSetup testSetup;
    WaitHelper waitHelper = new WaitHelper();

    public EventsHelper(TestSetup testSetup){
        this.testSetup=testSetup;
    }

    public EventsHelper(){

    }

    public void click(WebElement webElement) throws InterruptedException {
        try {
            webElement.click();
            waitHelper.hardWait(2500);
        } catch (NoSuchElementException nse) {
            testSetup.setTestStatus("FAIL", "Element Not Found: " + nse.getMessage());
        }
    }

    public void navigateToUrl(String url, WebDriver driver) {
        try {
            driver.get(url);
            waitHelper.hardWait(2500);
        } catch (Exception e) {
            testSetup.setTestStatus("FAIL", "Can not navigate to the specified url: " + e.getMessage());
        }
    }

    public void acceptAlert(WebDriver driver){
        try {
            driver.switchTo().alert().accept();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL", "Could not handle alert: " + e.getMessage());
        }
    }

    public String getTextOnAlertBox(WebDriver driver){
        String text="";
        try {
            text=driver.switchTo().alert().getText();
        }catch(Exception e){
            testSetup.setTestStatus("FAIL", "Could not get text from alert box: " + e.getMessage());
        }
        return  text;
    }


    public void enterText(WebElement webElement,String value) throws InterruptedException {
        try {
            webElement.clear();
            webElement.sendKeys(value);
            waitHelper.hardWait(2500);
        } catch (NoSuchElementException nse) {
            testSetup.setTestStatus("FAIL", "Webelement not found: " + nse.getMessage());
        }
    }
}
