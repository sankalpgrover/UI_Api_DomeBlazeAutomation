package seleniumtests;

import java.util.*;
import java.io.IOException;
import com.adidas.utils.TestSetup;
import org.openqa.selenium.WebDriver;
import com.adidas.utils.EventsHelper;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterTest;
import com.adidas.selenium.browser.InitWebdriver;
import com.adidas.selenium.pageobjects.DemoBlazeHomePage;


/**
 * @author : SankalpGrover
 * @since : 11/2/2020, Mon
 **/

public class DomeBlazeTest {

    WebDriver driver=null;
    InitWebdriver initWebdriver;
    TestSetup testSetup = new TestSetup();
    EventsHelper eventsHelper = new EventsHelper(testSetup);

    public DomeBlazeTest() throws IOException {
    }


    @org.testng.annotations.Test(priority = 1)
    public void navigateToCategories() {
        try {
            TestSetup.extentTest = TestSetup.extent.createTest("Navigate to different categories");
            initWebdriver = new InitWebdriver(testSetup);
            driver = initWebdriver.getDriver();
            DemoBlazeHomePage demoBlazeHomePage = new DemoBlazeHomePage(driver);
            eventsHelper.navigateToUrl(testSetup.loadConfigProperties().getProperty("demoBlazeHomePage"), driver);
            List<WebElement> categoriesList = demoBlazeHomePage.getCategories();
            iterateCategories(categoriesList);
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }


    @org.testng.annotations.Test(priority = 2)
    public void checkOutLaptop()  {
        try {
            Map<String, String> transactionDetailsMap = new HashMap<String, String>();
            TestSetup.extentTest = TestSetup.extent.createTest("Add and Delete Laptops to Cart");
            initWebdriver = new InitWebdriver(testSetup);
            driver = initWebdriver.getDriver();
            eventsHelper.navigateToUrl(testSetup.loadConfigProperties().getProperty("demoBlazeHomePage"), driver);
            DemoBlazeHomePage demoBlazeHomePage = new DemoBlazeHomePage(driver);
            List<WebElement> categoriesList = demoBlazeHomePage.getCategories();
            addSpecificLaptopToCart("Laptops", driver, "SonyVaio5", categoriesList, demoBlazeHomePage);
            eventsHelper.navigateToUrl(testSetup.loadConfigProperties().getProperty("demoBlazeHomePage"), driver);
            addSpecificLaptopToCart("Laptops", driver, "Delli78gb", categoriesList, demoBlazeHomePage);
            deleteLaptopFromCart(demoBlazeHomePage);
            eventsHelper.click(demoBlazeHomePage.getPlaeOrderButton());
            enterCheckoutInformation(demoBlazeHomePage);
            eventsHelper.click(demoBlazeHomePage.getPurchaseButton());
            String[] transactionDetails = demoBlazeHomePage.getTransactionDetails().getText().split("\n");

            Arrays.asList(transactionDetails).stream().forEach(transaction -> {
                transactionDetailsMap.put((transaction.substring(0, (transaction.indexOf(':')))), transaction.substring(transaction.indexOf(':')));
            });

            Iterator<Map.Entry<String, String>> entryIterator = transactionDetailsMap.entrySet().iterator();
            while (entryIterator.hasNext()) {
                Map.Entry<String, String> transactionDetailEntry = entryIterator.next();
                testSetup.setTestStatus("INFO", transactionDetailEntry.getKey() + "  : " + transactionDetailEntry.getValue());
            }

            testSetup.assertEquals("Transaction Amount: ", "790 USD", "790 USD");
            eventsHelper.click(demoBlazeHomePage.getOkButton());
        }catch(Exception e){
            testSetup.setTestStatus("FAIL","Exception "+e.getMessage());
        }
    }

    public void enterCheckoutInformation(DemoBlazeHomePage demoBlazeHomePage) throws IOException, InterruptedException {
        eventsHelper.enterText(demoBlazeHomePage.getName(),testSetup.loadConfigProperties().getProperty("Name"));
        eventsHelper.enterText(demoBlazeHomePage.getCountry(),testSetup.loadConfigProperties().getProperty("Country"));
        eventsHelper.enterText(demoBlazeHomePage.getCity(),testSetup.loadConfigProperties().getProperty("City"));
        eventsHelper.enterText(demoBlazeHomePage.getCreditCard(),testSetup.loadConfigProperties().getProperty("CreditCard"));
        eventsHelper.enterText(demoBlazeHomePage.getMonth(),testSetup.loadConfigProperties().getProperty("Month"));
        eventsHelper.enterText(demoBlazeHomePage.getYear(),testSetup.loadConfigProperties().getProperty("Year"));
    }

    public void deleteLaptopFromCart(DemoBlazeHomePage demoBlazeHomePage) throws  InterruptedException {
        demoBlazeHomePage = new DemoBlazeHomePage(driver);
        eventsHelper.click(demoBlazeHomePage.getCartIcon());
        eventsHelper.click(demoBlazeHomePage.getDeleteLaptopLink());
    }

    public void addSpecificLaptopToCart(String selectCategory,WebDriver driver,String deviceName,List<WebElement> categoryList,DemoBlazeHomePage demoBlazeHomePage) throws InterruptedException, IOException {
        categoryList.stream().forEach(category->{
            testSetup.setTestStatus("INFO","Category Type: "+category.getText());
            if(category.getText().equalsIgnoreCase(selectCategory))
            {
                try {
                    eventsHelper.click(category);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        WebElement selectDevice=demoBlazeHomePage.locateDevice(testSetup.loadConfigProperties().getProperty(deviceName));
        eventsHelper.click(selectDevice);
        eventsHelper.click(demoBlazeHomePage.getAddToCartButton());
        testSetup.assertEquals("Text on Alert Box: ","Product added",eventsHelper.getTextOnAlertBox(driver));
        eventsHelper.acceptAlert(driver);
    }

    public void iterateCategories(List<WebElement> CategoryList){
        CategoryList.stream().forEach(category->{
            testSetup.setTestStatus("INFO","Category Type: "+category.getText());
            try {
                eventsHelper.click(category);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @AfterTest
    public void afterTest(){
        TestSetup.extent.flush();
        initWebdriver.teardown(driver);
    }

}
