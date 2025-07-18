package com.automationexercise.utils;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.utils.allurereport.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

import static com.automationexercise.utils.TimeUtils.getTimestamp;

public class ScreenshotUtils {
    public final static String SCREENSHOTS_PATH = "test-outputs/screenshots/";
    private static ElementActions elementActions;

    private ScreenshotUtils() {
        super();
    }


    //TODO: Take general Screenshot
    public static void takeScreenShot(GUIDriver driver, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed

            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);
            LogUtils.info("Capturing Screenshot Succeeded");
            // Attach the screenshot to Allure
            Thread.sleep(4000);
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getPath());

        } catch (Exception e) {
            LogUtils.error("Failed to Capture Screenshot ", e.getMessage());
        }
    }

    //TODO: Take general Screenshot
    public static void takeHighlightedScreenShot(GUIDriver driver, String screenshotName, By element) {
        try {
            //Highlight element
            elementActions = new ElementActions(driver.get());
            elementActions.highLightElement(element);
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);


            // Attach the screenshot to Allure
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getPath());
            LogUtils.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogUtils.error("Failed to Capture Screenshot " + e.getMessage());

        }
    }

    //TODO: Take Screenshot for specific element
    public static void takeScreenShotForElement(GUIDriver driver, By locator, String screenshotName) {
        try {
            // Capture screenshot using TakesScreenshot
            elementActions = new ElementActions(driver.get());
            File screenshotSrc = elementActions.findElement(locator)
                    .getScreenshotAs(OutputType.FILE);
            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // Attach the screenshot to Allure
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getPath());
            LogUtils.info("Capturing Screenshot Succeeded");

        } catch (Exception e) {
            LogUtils.error("Failed to Capture Screenshot " + e.getMessage());

        }
    }

    //TODO: take full screenshot without highlighting on element
    public static void takeFullScreenshot(GUIDriver driver) {
        try {
            Shutterbug.shootPage(driver.get(), Capture.FULL_SCROLL)
                    .save(SCREENSHOTS_PATH);
            LogUtils.info("Capturing Screenshot Succeeded");
        } catch (Exception e) {
            LogUtils.error("Failed to Capture Screenshot " + e.getMessage());

        }

    }

    //TODO: take full screenshot with highlighting on element
    public static void takeFullScreenshotWithHighlighting(GUIDriver driver, String screenshotName, By locator) {
        try {
            //Highlight element
            elementActions = new ElementActions(driver.get());
            elementActions.highLightElement(locator);
            // Capture screenshot using TakesScreenshot
            File screenshotSrc = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);

            // Save screenshot to a file if needed
            File screenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimestamp() + ".png");
            FileUtils.copyFile(screenshotSrc, screenshotFile);

            // Attach the screenshot to Allure
            AllureAttachmentManager.attachScreenshot(screenshotName, screenshotFile.getPath());
            LogUtils.info("Capturing Screenshot Succeeded");

        } catch (Exception e) {
            LogUtils.error("Failed to Capture Screenshot " + e.getMessage());

        }

    }
}
