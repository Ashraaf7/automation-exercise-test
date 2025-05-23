package com.automationexercise.tests;

import com.automationexercise.pages.NavigationBarPage;
import org.testng.annotations.Test;

public class TC3_InvalidLogin extends BaseTest {
    @Test
    public void invalidLogin() {
        new NavigationBarPage(driver)
                .clickSignupLoginButton()
                .verifyLoginToAccountVisible()
                .enterLoginEmail(testData.getJsonData("login.inValid.email"))
                .enterLoginPassword(testData.getJsonData("login.inValid.password"))
                .clickLoginButton()
                .verifyInvalidLoginMessage();
    }
}
