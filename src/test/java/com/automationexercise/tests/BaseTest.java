package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.ConfigUtils;
import com.automationexercise.utils.JsonUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;


public class BaseTest {

    JsonUtils testData;
    GUIDriver driver;

    @BeforeClass
    public void beforeClass() {
        //  testData= new JsonUtils("test");
    }

    @BeforeMethod
    public void setup() {
        driver = new GUIDriver();
        driver.browser().navigate(ConfigUtils.getConfigValue("baseUrlWeb"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        driver.quit();
    }
}