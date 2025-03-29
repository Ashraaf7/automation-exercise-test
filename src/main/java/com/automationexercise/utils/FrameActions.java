package com.automationexercise.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FrameActions {
    WebDriver driver;
    Waits waits;

    public FrameActions(WebDriver driver) {
        this.driver = driver;
        waits = new Waits(driver);
    }

    /**
     * Switch to Default Content
     */
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        LogUtils.info("Switch to Default Content");
    }

    /**
     * Switch to iframe by Element is this iframe tag
     *
     * @param by iframe tag
     */
    public void switchToFrameByElement(By by) {
        waits.Synchronize()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
        LogUtils.info("Switch to Frame by Element. " + by);
    }

    public void switchToFrameByIndex(int index) {
        waits.Synchronize()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
        LogUtils.info("Switch to Frame by Index. " + index);
    }

    /**
     * Switch to iframe by ID or Name of iframe tag
     *
     * @param IdOrName ID or Name of iframe tag
     */
    public void switchToFrameByIdOrName(String IdOrName) {
        waits.Synchronize()
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(IdOrName));
        LogUtils.info("Switch to Frame by ID or Name. " + IdOrName);
    }
}
