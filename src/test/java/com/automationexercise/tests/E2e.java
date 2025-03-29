package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.ConfigUtils;
import com.automationexercise.utils.JsonUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class E2e {

    /// ///////////// Configurations \\\\\\\\\\\\\\\\\
    JsonUtils testData;
    GUIDriver driver;

    @Test
    public void loginTC() {
    }


    @BeforeClass
    public void setup() {
        testData = new JsonUtils("test-data");
        driver = new GUIDriver();
        driver.browser().navigate(ConfigUtils.getConfigValue("baseUrlWeb"));
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }

}
