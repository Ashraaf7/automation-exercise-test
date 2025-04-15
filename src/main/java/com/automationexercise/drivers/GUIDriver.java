package com.automationexercise.drivers;

import com.automationexercise.utils.*;
import com.automationexercise.validations.Assertions;
import com.automationexercise.validations.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.testng.Assert.fail;

public class GUIDriver {
    //code
    private final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    String browserName = ConfigUtils.getConfigValue("browserType");

    WebDriver driver;

    public GUIDriver() {
        driver = getDriver(browserName).startDriver();
        setDriver(driver);
    }

    public static GUIDriver extractDriver(ITestResult result) {
        Object testInstance = result.getInstance();
        Class<?> clazz = testInstance.getClass();

        while (clazz != null) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);

                    Object value;
                    if (Modifier.isStatic(field.getModifiers())) {
                        value = field.get(null);
                    } else {
                        value = field.get(testInstance);
                    }

                    // ThreadLocal<GUIDriver>
                    if (value instanceof ThreadLocal<?> threadLocal) {
                        Object driverObj = threadLocal.get();
                        if (driverObj instanceof GUIDriver driver) {
                            return driver;
                        }
                    }

                    // Direct GUIDriver
                    if (value instanceof GUIDriver driver) {
                        return driver;
                    }

                } catch (IllegalAccessException e) {
                    LogUtils.error("Unable to access field '", field.getName(), "'", e.getMessage());
                }
            }

            // Go up to check parent class
            clazz = clazz.getSuperclass();
        }

        LogUtils.warn("GUIDriver instance not found in test class: ", testInstance.getClass().getSimpleName());
        return null;
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
