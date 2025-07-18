package com.automationexercise.utils.allurereport;

import com.automationexercise.utils.FilesUtils;
import com.automationexercise.utils.LogUtils;
import com.automationexercise.utils.ScreenRecorderUtils;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;

public class AllureAttachmentManager {

    public static void attachScreenshot(String name, String path) {
        try {
            Path screenshot = Path.of(path);
            if (Files.exists(screenshot)) {
                Allure.addAttachment(name, Files.newInputStream(screenshot));
            } else {
                LogUtils.error("Screenshot not found: " + path);
            }
        } catch (Exception e) {
            LogUtils.error("Error attaching screenshot", e.getMessage());
        }
    }

    public static void attachLogs() {
        try {
            //LogManager.shutdown();
            FilesUtils.getLogFileAfterTest(LogUtils.LOGS_PATH + File.separator + "logs.log");
            File logFile = new File(LogUtils.LOGS_PATH + File.separator + "logs.log");
            //((LoggerContext) LogManager.getContext(false)).reconfigure();
            if (logFile.exists()) {
                Allure.attachment("logs.log", Files.readString(logFile.toPath()));
            }
        } catch (Exception e) {
            LogUtils.error("Error attaching logs", e.getMessage());
        }
    }

    public static void attachRecords() {
        if (getConfigValue("recordTests").equalsIgnoreCase("true")) {
            try {
                File record = FilesUtils.getLatestFile(ScreenRecorderUtils.RECORDINGS_PATH);
                if (record != null && record.getName().endsWith(".mp4")) {
                    Allure.addAttachment("Test Execution Video", "video/mp4", Files.newInputStream(record.toPath()), ".mp4");
                }
            } catch (Exception e) {
                LogUtils.error("Error attaching records", e.getMessage());
            }
        }
    }
}