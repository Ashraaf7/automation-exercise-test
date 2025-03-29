package com.automationexercise.utils;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.Objects;

public class Validations {
    WebDriver driver;
    Waits wait;
    ElementActions elementActions;

    public Validations(WebDriver driver) {
        this.driver = driver;
        elementActions = new ElementActions(driver);
        wait = new Waits(driver);
    }

    /**
     * Verify if the options at the given text are selected.
     *
     * @param by   Represent a web element as the By object
     * @param text the specified options text
     */
    public void verifyDropdownSelectedByText(By by, String text) {
        Select select = new Select(elementActions.findElement(by));
        String selectedOption = select.getFirstSelectedOption().getText();
        LogUtils.info("Verify Option Selected by text: " + selectedOption);
        boolean isSelected = selectedOption.equals(text);
        Assert.assertTrue(isSelected, "The option is not selected");
    }

    /**
     * Verify if the given web element is checked.
     *
     * @param by Represent a web element as the By object
     */
    @Step("validate Element Checked: {by}")
    public void validateElementChecked(By by) {
        boolean flag = elementActions.findElement(by).isSelected();
        Assert.assertTrue(flag, "The element is not checked");
    }

    @Step("Validate True")
    public void validateTrue(boolean condition) {
        Assert.assertTrue(condition, "The condition is not true");
    }

    @Step("Validate False")
    public void validateFalse(boolean condition) {
        Assert.assertFalse(condition, "The condition is not false");
    }


    @Step("validate Equals {expectedText} and {actualText}")
    public void validateEquals(String expectedText, String actualText) {
        Assert.assertEquals(actualText, expectedText, "The text is not as expected, ");
    }

    @Step("validate Equals: {expectedText} and {actualText}")
    public void validateNotEquals(String expectedText, String actualText) {
        Assert.assertEquals(actualText, expectedText, "The text is as expected");
    }

    /**
     * validate if the given web element is visible.
     *
     * @param by Represent a web element as the By object
     */
    @Step("validate Element Visible: {by}")
    public void validateElementVisible(By by) {
        wait.waitForElementVisible(by);
        Assert.assertTrue(elementActions.findElement(by).isDisplayed(), "The element is not visible");
    }

    /**
     * validate the web page's title equals with the specified title
     *
     * @param pageTitle The title of the web page that needs verifying
     */
    @Step("validate Page Title: {pageTitle}")
    public void validatePageTitle(String pageTitle) {
        boolean forPageTitle = wait.waitForPageTitle(pageTitle);
        Assert.assertTrue(forPageTitle, "The title is not as expected");
    }

    /**
     * validate if the given text presents anywhere in the page source.
     */
    @Step("validate page contains text: {text}")
    public void validatePageContainsText(String text) {
        LogUtils.info("Verify page contains text: " + text);
        boolean contains = Objects.requireNonNull(driver.getPageSource()).contains(text);
        Assert.assertTrue(contains, "The text is not present in the page source");
    }

    /**
     * validate if the given text presents in the alert.
     */
    @Step("validate alert contains text: {text}")
    public void validatePageURL(String expectedURL) {
        boolean pageURL = wait.waitForPageURL(expectedURL);
        Assert.assertTrue(pageURL, "The URL is not as expected");
    }

    /**
     * validate if the given text presents in the alert.
     */
    @Step("validate alert contains text: {text}")
    public void validateAlertPresent(int timeOut) {
        boolean alertPresent = wait.waitForAlertPresent();
        Assert.assertTrue(alertPresent, "Alert is not present");
    }

}
