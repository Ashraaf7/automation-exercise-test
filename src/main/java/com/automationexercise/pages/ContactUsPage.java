package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.ByUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ContactUsPage {
    private final GUIDriver driver;
    /// Page elements
    private final By gitInTouch = By.xpath("//h2[.='Get In Touch']");
    private final By nameField = ByUtils.dataQa("name");
    private final By emailField = ByUtils.dataQa("email");
    private final By subjectField = ByUtils.dataQa("subject");
    private final By messageField = ByUtils.dataQa("message");
    private final By fileUpload = By.name("upload_file");
    private final By submitButton = ByUtils.dataQa("submit-button");
    private final By successMessage = By.cssSelector(".contact-form > div:nth-of-type(2)");
    private final By homeButton = By.cssSelector("#form-section >a");

    public ContactUsPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Page actions
    //filling the contact us form
    @Step("Fill Contact Us Form")
    public ContactUsPage fillContactUsForm(String name, String email, String subject, String message, String filePath) {
        driver.element().type(nameField, name);
        driver.element().type(emailField, email);
        driver.element().type(subjectField, subject);
        driver.element().type(messageField, message);
        driver.element().uploadFile(fileUpload, filePath);
        return this;
    }

    //click on submit button
    @Step("Click Submit Button")
    public ContactUsPage clickSubmitButton() {
        driver.element().click(submitButton);
        return this;
    }

    //click on ok button from alert
    @Step("Click OK Button")
    public ContactUsPage clickOkButton() {
        driver.alert().acceptAlert();
        return this;
    }

    //click on home button
    @Step("Click Home Button")
    public LandingPage clickHomeButton() {
        driver.element().click(homeButton);
        return new LandingPage(driver);
    }

    // Page validations
    @Step("Verify Get In Touch is displayed")
    public ContactUsPage verifyGetInTouch() {
        driver.validate().validateElementVisible(gitInTouch);
        return this;
    }

    @Step("Verify success message is displayed")
    public ContactUsPage verifySuccessMessage() {
        driver.validate().validateElementVisible(successMessage);
        return this;
    }
}
