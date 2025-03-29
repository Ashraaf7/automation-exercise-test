package com.automationexercise.utils;

import org.testng.ITestResult;
import org.testng.asserts.SoftAssert;

public class SoftAssertionUtils {

    /**
     * The SoftAssert object is created
     */
    private static SoftAssert softAssert = new SoftAssert();

    /**
     * Soft Assert of TestNG
     */
    public static SoftAssert softAssert() {
        return softAssert;
    }

    /**
     * Soft Assert All of TestNG
     */
    public static void softAssertAll(ITestResult result) {
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            // Log the error message
            LogUtils.error("Soft Assertion Failed: " + e.getMessage());
            result.setStatus(ITestResult.FAILURE);
            result.setThrowable(e);
        } finally {
            // Reset soft assertions after asserting all
            resetSoftAssert();
        }

    }

    /**
     * Reset soft assertions to start a new set of assertions.
     */
    private static void resetSoftAssert() {
        softAssert = new SoftAssert();
    }
}
