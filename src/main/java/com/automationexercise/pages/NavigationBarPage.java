package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;

public class NavigationBarPage {
    protected final GUIDriver driver;
    private final By homeButton = By.xpath("//a[.=' Home']");
    private final By productsButton = By.xpath("//a[.=' Products']");
    private final By cartButton = By.xpath("//a[.=' Cart']");
    private final By logoutButton = By.xpath("//a[.=' Logout']");
    private final By signupLoginButton = By.xpath("//a[.=' Signup / Login']");
    private final By testCasesButton = By.xpath("//a[.=' Test Cases']");
    private final By deleteAccountButton = By.xpath("//a[.=' Delete Account']");
    private final By apiButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorials = By.xpath("//a[.=' Video Tutorials']");
    private final By contactUsButton = By.xpath("//a[.=' Contact us']");
    private final By homePageQuote = By.cssSelector("#slider-carousel h2");
    private final By userNameLabel = By.tagName("b");


    public NavigationBarPage(GUIDriver driver) {
        this.driver = driver;
    }

    /**
     * Navigate to Home Page
     */
    @Step("Navigate to Home Page")
    public NavigationBarPage navigateToHomePage() {
        driver.browser().navigate(getConfigValue("baseUrlWeb"));
        return this;
    }

    /**
     * Click on Home Button
     */
    @Step("Click on Home Button")
    public NavigationBarPage clickHomeButton() {
        driver.element().click(homeButton);
        return this;
    }

    /**
     * Click on Products Button
     */
    @Step("Click on Products Button")
    public NavigationBarPage clickProductsButton() {
        driver.element().click(productsButton);
        return this;
    }

    /**
     * Click on Cart Button
     */
    @Step("Click on Cart Button")
    public NavigationBarPage clickCartButton() {
        driver.element().click(cartButton);
        return this;
    }

    /**
     * Click on Signup/Login Button
     */
    @Step("Click on Signup/Login Button")
    public SignupLoginPage clickSignupLoginButton() {
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    /**
     * Click on Test Cases Button
     */
    @Step("Click on Test Cases Button")
    public NavigationBarPage clickTestCasesButton() {
        driver.element().click(testCasesButton);
        return this;
    }

    /**
     * Click on Delete Account Button
     */
    @Step("Click on Delete Account Button")
    public LandingPage clickDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new LandingPage(driver);
    }

    /**
     * Click on API Testing Button
     */
    @Step("Click on API Testing Button")
    public NavigationBarPage clickApiButton() {
        driver.element().click(apiButton);
        return this;
    }

    /**
     * Click on Video Tutorials Button
     */
    @Step("Click on Video Tutorials Button")
    public NavigationBarPage clickVideoTutorialsButton() {
        driver.element().click(videoTutorials);
        return this;
    }

    /**
     * Click on Contact Us Button
     */
    @Step("Click on Contact Us Button")
    public ContactUsPage clickContactUsButton() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    /**
     * Click on Logout Button
     */
    @Step("Click on Logout Button")
    public SignupLoginPage clickLogoutButton() {
        driver.element().click(logoutButton);
        return new SignupLoginPage(driver);
    }

    /**
     * Verify Home Page is displayed
     */
    @Step("Verify Home Page is displayed")
    public NavigationBarPage isHomePageDisplayed() {
        driver.validate().validateElementVisible(homePageQuote);
        return this;
    }

    /**
     * Verify user name
     *
     * @param userName user name
     * @return LandingPage
     */
    @Step("Verify user name {userName}")
    public LandingPage verifyUserName(String userName) {
        String uActual = driver.element().getText(userNameLabel);
        driver.validate().validateEquals(userName, uActual);
        return new LandingPage(driver);
    }

}
