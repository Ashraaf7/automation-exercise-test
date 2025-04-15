package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.NavigationBarPage;
import com.automationexercise.utils.JsonUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {

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
}