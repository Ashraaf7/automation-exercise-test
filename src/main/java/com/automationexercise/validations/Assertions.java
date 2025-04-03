package com.automationexercise.validations;

import com.automationexercise.utils.ElementActions;
import com.automationexercise.utils.Waits;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Assertions extends BaseAssertions {

    public Assertions(ElementActions elementActions, Waits wait, WebDriver driver) {
        super(elementActions, wait, driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
}
