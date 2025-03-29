package com.automationexercise.tests;

import com.automationexercise.pages.HomePage;
import com.automationexercise.utils.TimeUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class TC1_RegisterUser extends BaseTest {
    String name, email, titleMale, titleFemale, password, day, month, year, firstName, lastName, companyName,
            address1, address2, country, state, city, zipcode, mobileNumber;

    @BeforeMethod
    public void preparingData() {
        name = testData.getJsonData("registeration.name") + TimeUtils.getTimestamp();
        email = testData.getJsonData("registeration.email") + TimeUtils.getTimestamp() + "@gmail.com";
        titleMale = testData.getJsonData("registeration.titleMale");
        titleFemale = testData.getJsonData("registeration.titleFemale");
        password = testData.getJsonData("registeration.password");
        day = testData.getJsonData("registeration.day");
        month = testData.getJsonData("registeration.month");
        year = testData.getJsonData("registeration.year");
        firstName = testData.getJsonData("registeration.firstName") + TimeUtils.getTimestamp();
        lastName = testData.getJsonData("registeration.lastName") + TimeUtils.getTimestamp();
        companyName = testData.getJsonData("registeration.companyName");
        address1 = testData.getJsonData("registeration.address1");
        address2 = testData.getJsonData("registeration.address2");
        country = testData.getJsonData("registeration.country");
        state = testData.getJsonData("registeration.state");
        city = testData.getJsonData("registeration.city");
        zipcode = testData.getJsonData("registeration.zipCode");
        mobileNumber = testData.getJsonData("registeration.mobileNumber");


    }

    @Test
    public void RegisterUser() {
        new HomePage(driver).isHomePageDisplayed()
                .clickSignupLoginButton()
                .verifyNewUserSignupVisible()
                .enterRegisterName(name)
                .enterRegisterEmail(email)
                .clickSignupButton()
                .verifyEnterAccountInformationVisible()
                .fillRegisterationForm(titleMale, password, day, month, year,
                        firstName, lastName, companyName, address1, address2, country, state, city,
                        zipcode, mobileNumber)
                .clickCreateAccountButton()
                .verifyAccountCreated()
                .clickContinueButton()
                .verifyUserName(name)
                .clickDeleteAccountButton()
                .verifyAccountDeleted()
                .clickContinueButton();
    }


}
