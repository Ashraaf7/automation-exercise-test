package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AllProductsPage {
    private final GUIDriver driver;
    /// Page elements
    private final By allProducts = By.xpath("//h2[.='All Products']");
    private final By viewProduct = By.xpath("(//div[@class='choose'] //li)[1] /a");

    public AllProductsPage(GUIDriver driver) {
        this.driver = driver;
    }

    // Page actions
    //click on view product
    @Step("Click View Product")
    public ProductDetailsPage clickViewProduct() {
        driver.element().click(viewProduct);
        return new ProductDetailsPage(driver);
    }

    // Page validations
    @Step("Verify All Products is displayed")
    public AllProductsPage verifyAllProducts() {
        driver.validate().validateElementVisible(allProducts);
        return this;
    }


}
