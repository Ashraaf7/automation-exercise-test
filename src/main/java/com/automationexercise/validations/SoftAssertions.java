package com.automationexercise.validations;

import com.automationexercise.utils.ElementActions;
import com.automationexercise.utils.LogUtils;
import com.automationexercise.utils.Waits;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class SoftAssertions extends BaseAssertions {
    private static SoftAssert softAssert = new SoftAssert();

    public SoftAssertions(ElementActions elementActions, Waits wait, WebDriver driver) {
        super(elementActions, wait, driver);
    }

    @Step("Assert all soft assertions")
    public static void assertAll(ITestResult result) {
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            // Handle the assertion failure
            LogUtils.error("Assertion failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            resetSoftAssert();
        }
    }

    private static void resetSoftAssert() {
        softAssert = new SoftAssert();
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        softAssert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        softAssert.assertEquals(actual, expected, message);
    }

}
