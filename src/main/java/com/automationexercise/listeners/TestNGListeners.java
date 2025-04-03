package com.automationexercise.listeners;

import com.automationexercise.utils.*;
import com.automationexercise.validations.SoftAssertions;
import org.testng.*;

import java.io.File;

import static com.automationexercise.utils.AllureUtils.copyHistory;
import static com.automationexercise.utils.PropertiesUtils.loadProperties;


public class TestNGListeners implements IExecutionListener, IInvokedMethodListener, ITestListener, ISuiteListener {
    File screenshots = new File("test-outputs/screenshots");
    File recordings = new File("test-outputs/recordings");
    File allure_results = new File("test-outputs/allure-results");
    File reports = new File("test-outputs/reports");
    File logs = new File("test-outputs/Logs");

    @Override
    public void onExecutionStart() {
        LogUtils.info("Test Execution started");
        loadProperties();
        copyHistory();
        if (ConfigUtils.getConfigValue("CleanAllureReport").equalsIgnoreCase("true")) {
            if (ConfigUtils.getConfigValue("cleanHistory").equalsIgnoreCase("true")) {
                FilesUtils.deleteFiles(new File(AllureUtils.ALLURE_RESULTS_FOLDER_PATH));
            }
            FilesUtils.deleteSpecificFiles(AllureUtils.ALLURE_RESULTS_FOLDER_PATH, "history");
        }
        FilesUtils.cleanDirectory(screenshots);
        FilesUtils.cleanDirectory(reports);
        FilesUtils.cleanDirectory(logs);
        FilesUtils.cleanDirectory(recordings);
        FilesUtils.createDirs(LogUtils.LOGS_PATH);
        FilesUtils.createDirs(ScreenshotUtils.SCREENSHOTS_PATH);
        FilesUtils.createDirs(ScreenRecorderUtils.RECORDINGS_PATH);
        FilesUtils.createDirs(AllureUtils.ALLURE_REPORT_PATH);
        AllureUtils.setAllureEnvironment();
    }

    @Override
    public void onExecutionFinish() {
        LogUtils.info("Test Execution Finished");
        AllureUtils.generateAllureReport();
        AllureUtils.generateFullAllureReport();
        String newFileName = AllureUtils.renameAllureReport();
        AllureUtils.openAllureReport(newFileName);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            //ScreenRecorderUtils.startRecording();
            LogUtils.info("Test Case " + testResult.getName() + " started");
        }
    }


    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult result) {
        if (method.isTestMethod()) {
            //ScreenRecorderUtils.stopRecording(result.getName());
            SoftAssertions.assertAll(result);
            // For Test Methods: Log and Take Screenshot
            switch (result.getStatus()) {
                case ITestResult.SUCCESS -> ScreenshotUtils.takeScreenShot("passed-" + result.getName());
                case ITestResult.FAILURE -> ScreenshotUtils.takeScreenShot("failed-" + result.getName());
                case ITestResult.SKIP -> ScreenshotUtils.takeScreenShot("skipped-" + result.getName());
            }
            AllureUtils.attachLogsToAllure();
            AllureUtils.attachRecordsToAllure();
        } else if (method.isConfigurationMethod()) {
            // For Configuration Methods: Log Only
            switch (result.getStatus()) {
                case ITestResult.FAILURE -> LogUtils.info("Configuration Method " + result.getName() + " failed");
                case ITestResult.SUCCESS -> LogUtils.info("Configuration Method " + result.getName() + " passed");
                case ITestResult.SKIP -> LogUtils.info("Configuration Method " + result.getName() + " skipped");
            }
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case", result.getName(), "passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.info("Test case", result.getName(), "failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.info("Test case", result.getName(), "skipped");
    }

    public void onFinish(ISuite suite) {

    }

}
