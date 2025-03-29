package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class LandingPage extends HomePage {
    private final By userNameLabel = By.tagName("b");
    private final By accountDeletedText = By.cssSelector("[data-qa=\"account-deleted\"]");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");

    public LandingPage(GUIDriver driver) {
        super(driver);
    }

    @Step("Click Continue Button")
    public LandingPage clickContinueButton() {
        driver.element().click(continueButton);
        return new LandingPage(driver);
    }

    @Step("Verify Account Deleted")
    public LandingPage verifyAccountDeleted() {
        driver.validate().validateElementVisible(accountDeletedText);
        return this;
    }

    /**
     * Verify user name
     *
     * @param userName user name
     * @return LandingPage
     */
    @Step("Verify user name {userName}")
    public LandingPage verifyUserName(String userName) {
        String uActual = driver.element().getText(userNameLabel);
        driver.validate().validateEquals(userName, uActual);
        return this;
    }
}
