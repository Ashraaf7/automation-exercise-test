package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {

    private final GUIDriver driver;


    // Page elements
    private final By productName = By.cssSelector(".product-information > h2");
    private final By productCategory = By.cssSelector(".product-information > p:nth-of-type(1)");
    private final By productPrice = By.cssSelector(".product-information >span >span");
    private final By productAvailability = By.cssSelector(".product-information > p:nth-of-type(2)");
    private final By productCondition = By.cssSelector(".product-information > p:nth-of-type(3)");
    private final By productBrand = By.cssSelector(".product-information > p:nth-of-type(4)");
    private final By addToCartButton = By.cssSelector("[type=\"button\"]");

    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
    }

    //Verify that product detail is visible: product name, category, price, availability, condition, brand
    @Step("Verify all Product Details is displayed")
    public ProductDetailsPage verifyProductDetails() {
        driver.softValidate().validateElementVisible(productName);
        driver.softValidate().validateElementVisible(productCategory);
        driver.softValidate().validateElementVisible(productPrice);
        driver.softValidate().validateElementVisible(productAvailability);
        driver.softValidate().validateElementVisible(productCondition);
        driver.softValidate().validateElementVisible(productBrand);
        return this;
    }

}
