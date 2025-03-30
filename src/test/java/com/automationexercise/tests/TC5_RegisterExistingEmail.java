package com.automationexercise.tests;

import com.automationexercise.pages.SignupLoginPage;
import org.testng.annotations.Test;

public class TC5_RegisterExistingEmail extends BaseTest {
    @Test
    public void registerExistingEmail() {
        new SignupLoginPage(driver)
                .clickSignupLoginButton()
                .verifyNewUserSignupVisible()
                .enterRegisterName(testData.getJsonData("login.valid.name"))
                .enterRegisterEmail(testData.getJsonData("login.valid.email"))
                .clickSignupButton();
        new SignupLoginPage(driver)
                .verifyInvalidRegisterMessage();
    }
}
