package com.automationexercise.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class AlertUtils {
    private final WebDriver driver;
    private final Waits waits;

    public AlertUtils(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    /**
     * Click Dismiss on Alert
     */
    @Step("Click Dismiss on Alert")
    public void dismissAlert() {
        waits.waitForAlertPresent();
        driver.switchTo().alert().dismiss();
        LogUtils.info("Click Dismiss on Alert.");
    }

    /**
     * Get text on Alert
     */
    @Step("Get text on Alert")
    public String getTextAlert() {
        waits.waitForAlertPresent();
        LogUtils.info("Get text ion alert: " + driver.switchTo().alert().getText());
        return driver.switchTo().alert().getText();
    }

    /**
     * Set text on Alert
     */
    public void setTextAlert(String text) {
        waits.waitForAlertPresent();
        driver.switchTo().alert().sendKeys(text);
        LogUtils.info("Set " + text + " on Alert.");
    }

    /**
     * Click Accept on Alert
     */
    @Step("Click Accept on Alert")
    public void acceptAlert() {
        waits.waitForAlertPresent();
        driver.switchTo().alert().accept();
        LogUtils.info("Click Accept on Alert.");
    }
}
