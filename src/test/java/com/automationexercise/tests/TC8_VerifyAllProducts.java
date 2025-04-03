package com.automationexercise.tests;

import com.automationexercise.pages.NavigationBarPage;
import org.testng.annotations.Test;

public class TC8_VerifyAllProducts extends BaseTest {
    @Test
    public void verifyAllProducts() {
        // Test steps to verify all products
        new NavigationBarPage(driver)
                .clickProductsButton()
                .verifyAllProducts()
                .clickViewProduct()
                .verifyProductDetails();
    }
}
