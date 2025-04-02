package com.automationexercise.tests;

import com.automationexercise.pages.NavigationBarPage;
import org.testng.annotations.Test;

public class TC6_ContactUs extends BaseTest {
    @Test
    public void contactUsTc() {
        new NavigationBarPage(driver).clickContactUsButton()
                .verifyGetInTouch()
                .fillContactUsForm(testData.getJsonData("contactUs.name"),
                        testData.getJsonData("contactUs.email"),
                        testData.getJsonData("contactUs.subject"),
                        testData.getJsonData("contactUs.message"),
                        testData.getJsonData("contactUs.filePath"))
                .clickSubmitButton()
                .clickOkButton()
                .verifySuccessMessage()
                .clickHomeButton()
                .isHomePageDisplayed();
    }

}
