package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.ConfigUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage extends NavigationBarPage {

    private final By nameField = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By registerEmailField = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By signupButton = By.cssSelector("[data-qa=\"signup-button\"]");
    private final By loginEmailField = By.cssSelector("[data-qa=\"login-email\"]");
    private final By loginPasswordField = By.cssSelector("[data-qa=\"login-password\"]");
    private final By loginButton = By.cssSelector("[data-qa=\"login-button\"]");
    private final By newUserSignup = By.cssSelector(".signup-form > h2");
    private final By loginToAccountLabel = By.cssSelector(".login-form > h2");
    private final By loginErrorMsg = By.cssSelector("[action=\"/login\"] > p");
    private final By registerErrorMsg = By.cssSelector("[action=\"/signup\"] > p");

    public SignupLoginPage(GUIDriver driver) {
        super(driver);
    }

    //Navigation method
    @Step("Navigate to Register/Login Page")
    public SignupLoginPage navigateToRegisterPage() {
        driver.browser().navigate(ConfigUtils.getConfigValue("REGISTER_LOGIN_URL"));
        return this;
    }

    //Register methods
    @Step("Enter email {name} in register field")
    public SignupLoginPage enterRegisterName(String name) {
        driver.element().type(nameField, name);
        return this;
    }

    @Step("Enter email {email} in register field")
    public SignupLoginPage enterRegisterEmail(String email) {
        driver.element().type(registerEmailField, email);
        return this;
    }

    @Step("Click signup button")
    public SignupPage clickSignupButton() {
        driver.element().click(signupButton);
        return new SignupPage(driver);
    }

    //Login methods
    @Step("Enter email {email} in login field")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.element().type(loginEmailField, email);
        return this;
    }

    @Step("Enter password {password} in login field")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.element().type(loginPasswordField, password);
        return this;
    }

    @Step("Click login button")
    public SignupLoginPage clickLoginButton() {
        driver.element().click(loginButton);
        return this;
    }

    //validation methods
    @Step("verify that New User Signup! is visible")
    public SignupLoginPage verifyNewUserSignupVisible() {
        driver.validate().validateElementVisible(newUserSignup);
        return this;
    }

    @Step("verify that Login to your Account is visible")
    public SignupLoginPage verifyLoginToAccountVisible() {
        driver.validate().validateElementVisible(loginToAccountLabel);
        return this;
    }

    @Step("verify that Login Error message is visible")
    public SignupLoginPage verifyInvalidLoginMessage() {
        driver.validate().validateElementVisible(loginErrorMsg);
        return this;
    }

    @Step("verify that Register Error message is visible")
    public SignupLoginPage verifyInvalidRegisterMessage() {
        driver.validate().validateElementVisible(registerErrorMsg);
        return this;
    }
}
