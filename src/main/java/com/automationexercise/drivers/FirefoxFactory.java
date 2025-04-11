package com.automationexercise.drivers;

import com.automationexercise.utils.LogUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class FirefoxFactory extends AbstractDriver implements WebDriverOptionsAbstract<FirefoxOptions> {
    @Override
    public FirefoxOptions getOptions() {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--start-maximized");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--disable-infobars");
        firefoxOptions.addArguments("--disable-notifications");
        firefoxOptions.addArguments("--remote-allow-origins=*");
        if (executionType.equalsIgnoreCase("LocalHeadless") || executionType.equalsIgnoreCase("Remote")) {
            firefoxOptions.addArguments("--headless=new");
        }
        firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        firefoxOptions.setAcceptInsecureCerts(true);
        return firefoxOptions;
    }

    @Override
    public WebDriver startDriver() {
        if (executionType.equalsIgnoreCase("Local") || executionType.equalsIgnoreCase("LocalHeadless"))
            return new FirefoxDriver(getOptions());
        else if (executionType.equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + remoteExecutionHost + ":" + remoteExecutionPort + "/wd/hub").toURL(),
                        getOptions());
            } catch (Exception e) {
                LogUtils.error(e.getMessage());
                return null;
            }
        } else
            throw new IllegalArgumentException("Invalid execution type: " + executionType);
    }
}
