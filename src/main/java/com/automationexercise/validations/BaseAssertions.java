package com.automationexercise.validations;

import com.automationexercise.utils.AllureUtils;
import com.automationexercise.utils.ElementActions;
import com.automationexercise.utils.LogUtils;
import com.automationexercise.utils.Waits;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Objects;

public abstract class BaseAssertions {
    protected ElementActions elementActions;
    protected Waits wait;
    protected WebDriver driver;

    public BaseAssertions(ElementActions elementActions, Waits wait, WebDriver driver) {
        this.elementActions = elementActions;
        this.wait = wait;
        this.driver = driver;
    }

    protected abstract void assertTrue(boolean condition, String message);

    protected abstract void assertFalse(boolean condition, String message);

    protected abstract void assertEquals(String actual, String expected, String message);

    @Step("Verify element is displayed")
    public void verifyDropdownSelectedByText(By by, String text) {
        Select select = new Select(elementActions.findElement(by));
        String selectedOption = select.getFirstSelectedOption().getText();
        LogUtils.info("Verify Option Selected by text: " + selectedOption);
        assertTrue(selectedOption.equals(text), "The option is not selected");
    }

    @Step("Verify element is selected")
    public void validateElementChecked(By by) {
        boolean flag = elementActions.findElement(by).isSelected();
        assertTrue(flag, "The element is not checked");
    }

    @Step("Verify condition is true: {condition}")
    public void validateTrue(boolean condition) {
        assertTrue(condition, "The condition is not true");
    }

    @Step("Verify condition is false: {condition}")
    public void validateFalse(boolean condition) {
        assertFalse(condition, "The condition is not false");
    }

    @Step("Verify text is equal: {actualText} == {expectedText}")
    public void validateEquals(String expectedText, String actualText) {
        assertEquals(actualText, expectedText, "The text is not as expected");
    }

    @Step("Verify text is not equal: {actualText} != {expectedText}")
    public void validateNotEquals(String expectedText, String actualText) {
        assertFalse(actualText.equals(expectedText), "The text is as expected");
    }

    public void validateElementVisible(By by) {
        String elementName = elementActions.findElement(by).getAccessibleName();
        Allure.step("Verify element " + elementName + " is visible ", () -> {
            AllureUtils.addStepParameters(new String[][]{
                    {"element", elementName}
            });
            wait.waitForElementVisible(by);
            assertTrue(elementActions.findElement(by).isDisplayed(), "The element is not visible");
        });
    }

    @Step("Verify page title: {pageTitle}")
    public void validatePageTitle(String pageTitle) {
        boolean forPageTitle = wait.waitForPageTitle(pageTitle);
        assertTrue(forPageTitle, "The title is not as expected");
    }

    @Step("Verify page contains text: {text}")
    public void validatePageContainsText(String text) {
        LogUtils.info("Verify page contains text: " + text);
        boolean contains = Objects.requireNonNull(driver.getPageSource()).contains(text);
        assertTrue(contains, "The text is not present in the page source");
    }

    @Step("Verify page URL: {expectedURL}")
    public void validatePageURL(String expectedURL) {
        boolean pageURL = wait.waitForPageURL(expectedURL);
        assertTrue(pageURL, "The URL is not as expected");
    }

    @Step("Verify alert is present")
    public void validateAlertPresent() {
        boolean alertPresent = wait.waitForAlertPresent();
        assertTrue(alertPresent, "Alert is not present");
    }
}
