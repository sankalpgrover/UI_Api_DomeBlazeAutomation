package com.adidas.selenium.pageobjects;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.FindBy;

/**
 * @author : SankalpGrover
 * @since : 11/1/2020, Sun
 **/

public class DemoBlazeHomePage extends BasePage {
    private WebDriver driver;

    public DemoBlazeHomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(how = How.ID, using = "itemc")
    public List<WebElement> Categories;

    @FindBy(how = How.XPATH, using = "//*[@id='tbodyid']//div/a")
    public WebElement addToCartButton;

    @FindBy(how = How.XPATH, using = "//*[@id='navbarExample']/ul//li/a[contains(text(),'Cart')]")
     public WebElement  cartIcon;

    @FindBy(how = How.XPATH, using = "(//*[@id='tbodyid']//tr/td[contains(text(),'Dell i7 8gb')])/parent::tr/td/a")
    public WebElement  deleteLaptopLink;

    @FindBy(how = How.ID, using = "name")
    public WebElement  name;

    @FindBy(how = How.ID, using = "country")
    public WebElement  country;

    @FindBy(how = How.ID, using = "city")
    public WebElement  city;

    @FindBy(how = How.ID, using = "card")
    public WebElement  creditCard;

    @FindBy(how = How.ID, using = "month")
    public WebElement  month;

    @FindBy(how = How.ID, using = "year")
    public WebElement  year;

    @FindBy(how = How.XPATH, using = "//*[@id='orderModal']//div/button[@onclick='purchaseOrder()']")
    public WebElement  purchaseButton;

    @FindBy(how = How.XPATH, using = "//*[@id='page-wrapper']//button[contains(text(),'Place Order')]")
    public WebElement  plaeOrderButton;

    @FindBy(how = How.XPATH, using = "/html/body//div/p[@class='lead text-muted ']")
    public WebElement transactionDetails;

    @FindBy(how = How.XPATH, using = "/html/body//div/button[contains(text(),'OK')]")
    public WebElement okButton;

    public WebElement getOkButton() {
        return okButton;
    }

    public WebElement getTransactionDetails() {
        return transactionDetails;
    }

    public WebElement getPlaeOrderButton() {
        return plaeOrderButton;
    }

    public WebElement getName() {
        return name;
    }

    public WebElement getCountry() {
        return country;
    }

    public WebElement getCity() {
        return city;
    }

    public WebElement getCreditCard() {
        return creditCard;
    }

    public WebElement getMonth() {
        return month;
    }

    public WebElement getYear() {
        return year;
    }

    public WebElement getPurchaseButton() {
        return purchaseButton;
    }

    public WebElement getDeleteLaptopLink() {
        return deleteLaptopLink;
    }

    public WebElement getCartIcon() {
        return cartIcon;
    }
    public WebElement locateDevice(String deviceId) {
        WebElement device=driver.findElement(By.xpath("//*[@id='tbodyid']//div/h4/a[@href='prod.html?idp_="+deviceId+"']"));
        return device;
    }

    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    public List<WebElement> getCategories() {
        return Categories;
    }

    public By get(String name) throws SecurityException, NoSuchFieldException {
        return getElemetLocator(this, "NewBug");
    }

}
