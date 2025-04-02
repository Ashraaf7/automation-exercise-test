package com.automationexercise.utils;

import org.openqa.selenium.By;

public class ByUtils {
    public static By testId(String id) {
        return By.cssSelector("[test-id='" + id + "']");
    }

    public static By dataQa(String qa) {
        return By.cssSelector("[data-qa='" + qa + "']");
    }
}
