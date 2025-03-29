package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.HomePage;
import com.automationexercise.utils.JsonUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


public class BaseTest {

    protected JsonUtils testData;
    protected GUIDriver driver;

    @BeforeClass
    public void beforeClass() {
        testData = new JsonUtils("test-data");
        driver = new GUIDriver();
        new HomePage(driver).navigateToHomePage();
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }
}