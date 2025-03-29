package com.automationexercise.drivers;

import com.automationexercise.utils.LogUtils;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.URI;

public class SafariFactory extends AbstractDriver implements WebDriverOptionsAbstract<SafariOptions> {
    @Override
    public SafariOptions getOptions() {
        SafariOptions safariOptions = new SafariOptions();
        safariOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        safariOptions.setAutomaticInspection(false);
        return safariOptions;
    }

    @Override
    public WebDriver startDriver() {
        if (executionType.equalsIgnoreCase("Local") || executionType.equalsIgnoreCase("LocalHeadless"))
            return new SafariDriver(getOptions());
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
