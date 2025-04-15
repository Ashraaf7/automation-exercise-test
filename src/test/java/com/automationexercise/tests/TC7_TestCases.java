package com.automationexercise.tests;

import com.automationexercise.pages.NavigationBarPage;
import org.testng.annotations.Test;

public class TC7_TestCases extends BaseTest {

    @Test
    public void testCasesTC() {
        new NavigationBarPage(driver).clickTestCasesButton()
                .verifyTestCases();
    }


}
