package com.automationexercise.utils;

import com.automationexercise.utils.allurereport.AllureStepParameterHelper;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.Select;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;



public class ElementActions {

    private final WebDriver driver;
    Waits waits;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        waits = new Waits(driver);
    }

    //TODO: Function for selecting from drop down
    public void selectFromDropDown(By locator, String option) {
        new Select(findElement(locator)).selectByVisibleText(option);
    }

    //TODO:  Scroll to specific position
    public void scrollToPosition(int x, int y) {
        getJsExecutor().executeScript("window.scrollTo(" + x + ", " + y + ");");
        LogUtils.info("Scroll to position X = " + x + " ; Y = " + y);
    }

    /**
     * Uploading files with a click shows a form to select local files on your computer
     *
     * @param uploadButton is an element of type By
     * @param filePath     the absolute path to the file on your computer
     */
    public void uploadFileWithLocalForm(By uploadButton, String filePath) {
        click(uploadButton);
        // Create Robot class
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            LogUtils.error(e.getMessage());
        }
        // Copy File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
        // Press Control+V to paste
        assert robot != null;
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        // Release the Control V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        robot.delay(2000);
        // Press Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    //TODO: Clicking on element after checking clickability
    public void click(By locator) {
        String elementName = findElement(locator).getAccessibleName();
        AllureStepParameterHelper.addStepParameters(new String[][]{
                {"element", elementName}
        });
        scrollToElementAtTop(locator);
        waits.waitForElementClickable(locator);
        findElement(locator).click();
        LogUtils.info("Click on element", elementName);
    }


    /**
     * Upload files by dragging the link directly into the input box
     *
     * @param by       passes an element of object type By
     * @param filePath absolute path to the file
     */
    public void uploadFile(By by, String filePath) {
        String elementName = findElement(by).getAccessibleName();
        String fileAbsluted = System.getProperty("user.dir") + File.separator + filePath;
        Allure.step("Upload file in " + elementName, () -> {
            AllureStepParameterHelper.addStepParameters(new String[][]{
                    {"element", elementName},
                    {"File Path", filePath}
            });
            waits.waitForElementVisible(by);
            findElement(by).sendKeys(fileAbsluted);
            LogUtils.info("Upload File successfully:", fileAbsluted, "in", elementName);
        });


    }

    //TODO: Send data to element after checking visibility
    public void type(By locator, String data) {
        String elementName = findElement(locator).getAccessibleName();
        Allure.step("Type " + data + " in " + elementName, () -> {
            AllureStepParameterHelper.addStepParameters(new String[][]{
                    {"element", elementName}
                    , {"data", data}
            });
            scrollToElementAtTop(locator);
            waits.waitForElementVisible(locator);
            findElement(locator).sendKeys(data);
            LogUtils.info("Type ", data, "in", findElement(locator).getAccessibleName());
        });

    }

    //TODO: get text from element after checking visibility
    @Step("Get text")
    public String getText(By locator) {
        String elementName = findElement(locator).getAccessibleName();
        scrollToElementAtTop(locator);
        waits.waitForElementVisible(locator);
        String text = findElement(locator).getText();
        LogUtils.info("Get text from element", elementName, ":", text);
        return text;
    }

    //Convert Locator to Web Element
    public WebElement findElement(By locator) {
        waits.waitForElementPresent(locator);
        return driver.findElement(locator);
    }

    /**
     * Print the current page in the browser.
     * Note: Only works in headless mode
     *
     * @param endPage is the total number of pages to print. Adjective 1.
     * @return is content of page form PDF file
     */
    public String printPage(int endPage) {
        PrintOptions printOptions = new PrintOptions();
        //From page 1 to end page
        printOptions.setPageRanges("1-" + endPage);

        Pdf pdf = ((PrintsPage) driver).print(printOptions);
        return pdf.getContent();
    }

    /**
     * Find multiple elements with the locator By object
     *
     * @param by is an element of type By
     * @return Returns a List of WebElement objects
     */
    public List<WebElement> findElements(By by) {
        waits.waitForElementPresent(by);
        return driver.findElements(by);
    }

    /**
     * Get the JavascriptExecutor object created
     *
     * @return JavascriptExecutor
     */
    public JavascriptExecutor getJsExecutor() {
        return (JavascriptExecutor) driver;
    }

    /**
     * Simulate users hovering a mouse over the given element.
     *
     * @param by Represent a web element as the By object
     */
    public void hoverOnElement(By by) {
        try {
            scrollToElementAtTop(by);
            Actions action = new Actions(driver);
            waits.waitForElementVisible(by);
            action.moveToElement(findElement(by)).perform();
            LogUtils.info("Hover on element " + by);
        } catch (Exception e) {
            LogUtils.error("Hover on element failed because of: " + e.getMessage());
        }
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param locator Represent a web element as the By object
     */
    @Step("Scroll")
    public void scrollToElementAtBottom(By locator) {
        waits.waitForElementPresent(locator);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(false);", findElement(locator));
    }

    //TODO: Highlight element
    public WebElement highLightElement(By locator) {

        // draw a border around the found element
        if (driver instanceof JavascriptExecutor) {
            String elementName = findElement(locator).getAccessibleName();
            getJsExecutor().executeScript("arguments[0].style.border='3px solid red'", findElement(locator));
            LogUtils.info("Highlight on element", elementName);
        }
        return findElement(locator);
    }

    /**
     * Drag and drop an element onto another element.
     *
     * @param fromElement represent the drag-able element
     * @param toElement   represent the drop-able element
     */
    @Step("Drag and Drop")
    public void dragAndDrop(By fromElement, By toElement) {
        try {
            scrollToElementAtTop(fromElement);
            Actions action = new Actions(driver);
            waits.waitForElementClickable(fromElement);
            waits.waitForElementClickable(toElement);
            action.dragAndDrop(findElement(fromElement), findElement(toElement)).perform();
            LogUtils.info("Drag and Drop from ", findElement(fromElement).getAccessibleName(), "to", findElement(toElement).getAccessibleName());
        } catch (Exception e) {
            LogUtils.error("Drag and Drop failed because of: " + e.getMessage());
        }
    }

    //TODO: Function for getting selected option from drop down
    @Step("Get selected option from drop down")
    public WebElement getSelectedOptionFromDropDown(By locator) {
        return new Select(findElement(locator)).getFirstSelectedOption();
    }

    /**
     * Scroll an element into the visible area of the browser window. (at TOP)
     *
     * @param locator Represent a web element as the By object
     */
    public void scrollToElementAtTop(By locator) {
        waits.waitForElementPresent(locator);
        getJsExecutor().executeScript("arguments[0].scrollIntoView(true);", findElement(locator));
    }

    //TODO:  Scroll to specific element
    @Step("Scroll")
    public void scrollToElement(By locator) {
        waits.waitForElementPresent(locator);
        getJsExecutor().executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});", findElement(locator));
    }
}
