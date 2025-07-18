package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.pages.NavigationBarPage;
import com.automationexercise.utils.JsonUtils;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest implements WebDriverProvider {

    public JsonUtils testData;
    public GUIDriver driver;


    @BeforeClass
    public void beforeClass() {
        testData = new JsonUtils("test-data");
        driver = new GUIDriver();
        new NavigationBarPage(driver)
                .navigateToHomePage()
                .isHomePageDisplayed();
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }

    @Override
    public WebDriver getWebDriver() {
       return driver.get();
    }
}