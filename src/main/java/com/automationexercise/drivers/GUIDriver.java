package com.automationexercise.drivers;

import com.automationexercise.utils.*;
import com.automationexercise.validations.Assertions;
import com.automationexercise.validations.SoftAssertions;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.fail;

public class GUIDriver {
    //code
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    String browserName = ConfigUtils.getConfigValue("browserType");

    WebDriver driver;

    public GUIDriver() {
        driver = getDriver(browserName).startDriver();
        setDriver(driver);
    }

    public static WebDriver getInstance() {
        return driverThreadLocal.get();
    }

    public void quit() {
        if (get() != null) {
            get().quit();
        }
    }

    public WebDriver get() {
        if (driverThreadLocal.get() == null) {
            LogUtils.error("Driver is null");
            fail("Driver is null");
            return null;
        }
        return driverThreadLocal.get();
    }

    private AbstractDriver getDriver(String browserName) {
        //code
        return switch (browserName.toLowerCase()) {
            case "chrome" -> new ChromeFactory();
            case "firefox" -> new FirefoxFactory();
            case "edge" -> new EdgeFactory();
            default -> throw new IllegalArgumentException("Invalid browser name: " + browserName);
        };

    }

    private void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public ElementActions element() {
        return new ElementActions(get());
    }

    public BrowserActions browser() {
        return new BrowserActions(get());
    }

    public FrameActions frame() {
        return new FrameActions(get());
    }

    public Assertions validate() {
        return new Assertions(element(), new Waits(get()), get());
    }

    public SoftAssertions softValidate() {
        return new SoftAssertions(element(), new Waits(get()), get());
    }

    public AlertUtils alert() {
        return new AlertUtils(get());
    }
}
