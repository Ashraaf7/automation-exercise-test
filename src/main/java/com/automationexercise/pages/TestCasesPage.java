package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class TestCasesPage {
    private final GUIDriver driver;
    private final By testCases = By.cssSelector("h2 > b");

    public TestCasesPage(GUIDriver driver) {
        this.driver = driver;
    }

    @Step("Verify Test Cases Page")
    public TestCasesPage verifyTestCases() {
        driver.validate().validateElementVisible(testCases);
        return this;
    }
}
