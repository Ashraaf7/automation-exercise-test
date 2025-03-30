package com.automationexercise.tests;

import com.automationexercise.pages.SignupLoginPage;
import org.testng.annotations.Test;

public class TC4_Logout extends BaseTest {

    @Test
    public void logout() {
        new SignupLoginPage(driver)
                .clickSignupLoginButton()
                .verifyLoginToAccountVisible()
                .enterLoginEmail(testData.getJsonData("login.valid.email"))
                .enterLoginPassword(testData.getJsonData("login.valid.password"))
                .clickLoginButton()
                .verifyUserName(testData.getJsonData("login.valid.name"))
                .clickLogoutButton()
                .verifyLoginToAccountVisible();
    }
}
