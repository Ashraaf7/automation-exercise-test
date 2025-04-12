package com.automationexercise.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;


public class Waits {
    private final int timeOut = Integer.parseInt(getConfigValue("WAIT_DEFAULT"));
    WebDriver driver;
    WebDriverWait wait;

    public Waits(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
    }

    // Wait for element to be present
    public void waitForElementPresent(By locator) {
        wait.until(driver -> driver.findElement(locator)); // No changes needed here
    }

    // Wait for element to be visible (re-find the element each time to avoid staleness)
    public void waitForElementVisible(By locator) {
        wait.until(driver -> driver.findElement(locator).isDisplayed());
    }

    // Wait for element to be clickable (re-find each time to avoid staleness)
    public void waitForElementClickable(By locator) {
        wait.until(driver -> (driver.findElement(locator).isDisplayed() && driver.findElement(locator).isEnabled()));
    }


    public boolean waitForPageTitle(String title) {
        try {
            return wait.until(ExpectedConditions.titleIs(title));
        } catch (Exception e) {
            LogUtils.warn(" Timeout for the page, current title is: " + driver.getTitle());
            return false;
        }

    }

    public boolean waitForAlertPresent() {
        try {
            return wait.until(ExpectedConditions.alertIsPresent()) != null;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }

    public boolean waitForPageURL(String url) {
        try {
            return wait.until(ExpectedConditions.urlToBe(url));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return false;
        }
    }


    public boolean waitForElementHasAttribute(By by, String attributeName) {
        try {
            return wait.until(ExpectedConditions.attributeToBeNotEmpty(driver.findElement(by), attributeName));
        } catch (Throwable error) {
            LogUtils.error("Timeout for element " + by.toString() + " to exist attribute: " + attributeName);
        }
        return false;
    }

    /**
     * Wait for a page to load with the default time from the config
     */
    public void waitForPageLoaded() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> {
            assert driver != null;
            return Objects.requireNonNull(((JavascriptExecutor) driver).executeScript("return document.readyState")).toString().equals("complete");
        };

        //Get JS is Ready
        boolean jsReady = Objects.requireNonNull(js.executeScript("return document.readyState")).toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            LogUtils.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
            }
        }
    }

    /**
     * Wait for JQuery to finish loading with default time from config
     */
    public void waitForJQueryLoad() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        };

        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            LogUtils.info("JQuery in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jQueryLoad);
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
            }
        }
    }

    /**
     * Wait for Angular to finish loading with default time from config
     */
    public void waitForAngularLoad() {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        final String angularReadyScript = "return angular.element(document).injector().get('$http').pendingRequests.length === 0";

        //Wait for ANGULAR to load
        ExpectedCondition<Boolean> angularLoad = driver -> {
            assert driver != null;
            return Boolean.valueOf(Objects.requireNonNull(((JavascriptExecutor) driver).executeScript(angularReadyScript)).toString());
        };

        //Get Angular is Ready
        boolean angularReady = Boolean.parseBoolean(Objects.requireNonNull(js.executeScript(angularReadyScript)).toString());

        //Wait ANGULAR until it is Ready!
        if (!angularReady) {
            LogUtils.warn("Angular is NOT Ready!");
            //Wait for Angular to load
            try {
                wait.until(angularLoad);
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
            }
        }

    }

    //general wait
    public FluentWait<WebDriver> Synchronize() {
        //code
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .pollingEvery(Duration.ofMillis(100));
    }

    public void implicitlyWait() {
        driver.manage().timeouts().
                implicitlyWait(Duration.ofSeconds(timeOut));
    }
}
