package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage extends NavigationBarPage {
    private final By enterAccountInformation = By.cssSelector(".login-form > h2");
    private final By titleMale = By.id("id_gender1");
    private final By titleFemale = By.id("id_gender2");
    private final By password = By.id("password");
    private final By day = By.id("days");
    private final By month = By.id("months");
    private final By year = By.id("years");
    private final By newsletter = By.id("newsletter");
    private final By specialOffers = By.id("optin");
    private final By firstName = By.id("first_name");
    private final By lastName = By.id("last_name");
    private final By company = By.id("company");
    private final By address1 = By.id("address1");
    private final By address2 = By.id("address2");
    private final By country = By.id("country");
    private final By state = By.id("state");
    private final By city = By.id("city");
    private final By zipcode = By.id("zipcode");
    private final By mobileNumber = By.id("mobile_number");
    private final By createAccountButton = By.xpath("//button[@data-qa='create-account']");
    private final By accountCreated = By.cssSelector("[data-qa=\"account-created\"]");
    private final By continueButton = By.cssSelector("[data-qa=\"continue-button\"]");

    public SignupPage(GUIDriver driver) {
        super(driver);
    }

    @Step("Fill Registration Form")
    public SignupPage fillRegisterationForm(String title,
                                            String passwordText,
                                            String dayText,
                                            String monthText,
                                            String yearText,
                                            String firstNameText,
                                            String lastNameText,
                                            String companyText,
                                            String address1Text,
                                            String address2Text,
                                            String countryText,
                                            String stateText,
                                            String cityText,
                                            String zipcodeText,
                                            String mobileNumberText) {
        switch (title) {
            case "Mr":
                driver.element().click(titleMale);
                break;
            case "Mrs":
                driver.element().click(titleFemale);
                break;
            default:
                LogUtils.warn("Invalid title provided: " + title);
        }
        driver.element().type(password, passwordText);
        driver.element().selectFromDropDown(day, dayText);
        driver.element().selectFromDropDown(month, monthText);
        driver.element().selectFromDropDown(year, yearText);
        driver.element().click(newsletter);
        driver.element().click(specialOffers);
        driver.element().type(firstName, firstNameText);
        driver.element().type(lastName, lastNameText);
        driver.element().type(company, companyText);
        driver.element().type(address1, address1Text);
        driver.element().type(address2, address2Text);
        driver.element().selectFromDropDown(country, countryText);
        driver.element().type(state, stateText);
        driver.element().type(city, cityText);
        driver.element().type(zipcode, zipcodeText);
        driver.element().type(mobileNumber, mobileNumberText);
        return new SignupPage(driver);
    }

    @Step("Click Create Account Button")
    public SignupPage clickCreateAccountButton() {
        driver.element().click(createAccountButton);
        return this;
    }

    @Step("Verify Account Created")
    public SignupPage verifyAccountCreated() {
        driver.validate().validateElementVisible(accountCreated);
        return this;
    }

    @Step("Click Continue Button")
    public LandingPage clickContinueButton() {
        driver.element().click(continueButton);
        return new LandingPage(driver);
    }

    @Step("Verify Enter Account Information Visible")
    public SignupPage verifyEnterAccountInformationVisible() {
        driver.validate().validateElementVisible(enterAccountInformation);
        return this;
    }


}
