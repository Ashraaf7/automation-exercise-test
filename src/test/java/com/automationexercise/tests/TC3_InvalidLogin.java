package com.automationexercise.tests;

import com.automationexercise.pages.NavigationBarPage;
import org.testng.annotations.Test;

public class TC3_InvalidLogin extends BaseTest {
    @Test
    public void invalidLogin() {
        new NavigationBarPage(driver)
                .clickSignupLoginButton()
                .verifyLoginToAccountVisible()
                .enterLoginEmail(testData.getJsonData("login.invalidEmail"))
                .enterLoginPassword(testData.getJsonData("login.invalidPassword"))
                .clickLoginButton()
                .verifyInvalidLoginMessage();
    }
}
