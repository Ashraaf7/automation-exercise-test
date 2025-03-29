package com.automationexercise.utils;


import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import java.util.Set;

public class BrowserActions {
    private final WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Maximize window
     */
    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    /**
     * Minimize window
     */
    public void minimizeWindow() {
        driver.manage().window().minimize();
    }

    // Method to zoom out using JavaScript

    /**
     * Zoom out the web page
     *
     * @param zoomFactor the zoom factor e.g. 60% (sent without %)
     */
    public void zoomOut(int zoomFactor) {
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom = '" + zoomFactor + "%'");
    }

    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    public void restoreSession(Set<Cookie> cookies) {
        for (Cookie cookie : cookies)
            driver.manage().addCookie(cookie);
    }

    /**
     * Get current web page's title
     *
     * @return the current URL as String
     */
    public String getPageTitle() {
        String title = driver.getTitle();
        LogUtils.info("Get Page Title: " + title);
        return title;
    }

    /**
     * Get current URL from current driver
     *
     * @return the current URL as String
     */
    public String getCurrentUrl() {
        String currentUrl = driver.getCurrentUrl();
        LogUtils.info("Get Current URL: " + currentUrl);
        return currentUrl;
    }

    /**
     * Open new Tab
     */
    public void openNewTab() {
        // Opens a new tab and switches to new tab
        driver.switchTo().newWindow(WindowType.TAB);
        LogUtils.info("Open new Tab");
    }

    /**
     * Open new Window
     */
    public void openNewWindow() {
        // Opens a new window and switches to new window
        driver.switchTo().newWindow(WindowType.WINDOW);
        LogUtils.info("Open new Window");
    }

    /**
     * Navigate to the specified URL.
     *
     * @param URL the specified url
     */
    public void navigate(String URL) {
        driver.get(URL);
        LogUtils.info("Open website with URL: " + URL);
    }

    /**
     * Reload the current web page.
     */
    public void reloadPage() {
        driver.navigate().refresh();
        LogUtils.info("Reloaded page " + driver.getCurrentUrl());
    }

    /**
     * Close current Window
     */
    public void closeCurrentWindow() {
        LogUtils.info("Close current Window." + getCurrentUrl());
        driver.close();
        LogUtils.info("Close current Window");
    }
}
