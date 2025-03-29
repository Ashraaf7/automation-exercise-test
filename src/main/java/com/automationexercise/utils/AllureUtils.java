package com.automationexercise.utils;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureUtils {
    public static final String ALLURE_REPORT_PATH = "test-outputs/reports";
    public static final String FULL_ALLURE_REPORT_PATH = "test-outputs/full-report";
    public static final String ALLURE_RESULTS_FOLDER_PATH = "test-outputs/allure-results";
    private static final String USER_HOME = ConfigUtils.getConfigValue("user.home");
    private static final String ALLURE_EXTRACTION_LOCATION = USER_HOME + File.separator + ".m2" + File.separator + "repository" + File.separator + "allure" + File.separator;
    private static String ALLURE_VERSION = "";
    private static String ALLURE_BINARY_PATH = "";

    private AllureUtils() {
        super();
    }

    public static void setAllureEnvironment() {
        LogUtils.info("Initializing Allure Reporting Environment...");
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", ConfigUtils.getConfigValue("os.name"))
                        .put("Java  version:", ConfigUtils.getConfigValue("java.runtime.version"))
                        .put("Browser", ConfigUtils.getConfigValue("browserType"))
                        .put("Execution Type", ConfigUtils.getConfigValue("executionType"))
                        .put("URL", ConfigUtils.getConfigValue("baseUrlWeb"))
                        .build(), ConfigUtils.getConfigValue("user.dir")
                        + "\\" + ALLURE_RESULTS_FOLDER_PATH + "\\");
        downloadAndExtractAllureBinaries();
    }

    private static void downloadAndExtractAllureBinaries() {
        try {
            String allureZipPath = getAllureZipPath();
            ALLURE_BINARY_PATH = ALLURE_EXTRACTION_LOCATION + "allure-" + ALLURE_VERSION + File.separator + "bin" + File.separator + "allure";
            if (new File((ALLURE_EXTRACTION_LOCATION + "allure-" + ALLURE_VERSION)).exists()) {
                LogUtils.info("Allure binaries already exist.");
                return;
            } else {
                // Extract Allure binaries
                try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(allureZipPath))) {
                    ZipEntry entry;
                    while ((entry = zipInputStream.getNextEntry()) != null) {
                        File file = new File(ALLURE_EXTRACTION_LOCATION + entry.getName());
                        if (entry.isDirectory()) {
                            file.mkdirs();
                        } else {
                            file.getParentFile().mkdirs();
                            try (FileOutputStream fos = new FileOutputStream(file)) {
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = zipInputStream.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }
                            }
                        }
                    }
                }
                LogUtils.info("Allure binaries downloaded and extracted.");
            }

            // Set execute permissions for Unix-based systems
            if (!ConfigUtils.getConfigValue("os.name").toLowerCase().contains("win")) {
                Runtime.getRuntime().exec("chmod u+x " + ALLURE_BINARY_PATH);
            }

        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static String urlConnection(String url) {
        try {
            String URL = Jsoup.connect(url)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toString();
            LogUtils.info("URL: " + URL);
            return URL;
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return null;
        }
    }

    private static String getAllureZipPath() {

        ALLURE_VERSION = Objects.requireNonNull(urlConnection("https://github.com/allure-framework/allure2/releases/latest")).split("/tag/")[1];
        LogUtils.info("Allure Version: " + ALLURE_VERSION);
        String allureZipUrl = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/" + ALLURE_VERSION + "/allure-commandline-" + ALLURE_VERSION + ".zip";
        String allure = "src/main/resources/allure/allure-" + ALLURE_VERSION + ".zip";
        if (!new File(ALLURE_BINARY_PATH).exists()) {
            // Download Allure binaries
            try (BufferedInputStream in = new BufferedInputStream(new URL(allureZipUrl).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(allure)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (Exception e) {
                LogUtils.error(e.getMessage());

            }
        }
        return allure;
    }

    // Method to generate Allure Report programmatically
    public static void generateAllureReport() {
        if (ConfigUtils.getConfigValue("os.name").toLowerCase().contains("win")) {
            String WIN_ALLURE_BINARY_PATH = ALLURE_BINARY_PATH + ".bat";
            TerminalUtils.executeTerminalCommand(
                    WIN_ALLURE_BINARY_PATH, "generate", ALLURE_RESULTS_FOLDER_PATH, "-o", ALLURE_REPORT_PATH, "--single-file", "--clean");
            LogUtils.info("Allure Report Generated Successfully on windows.");
        } else {
            TerminalUtils.executeTerminalCommand(
                    ALLURE_BINARY_PATH, "generate", ALLURE_RESULTS_FOLDER_PATH, "-o", ALLURE_REPORT_PATH, "--single-file", "--clean");
            LogUtils.info("Allure Report Generated Successfully on ", ConfigUtils.getConfigValue("os.name"));
        }
    }

    public static void generateFullAllureReport() {
        if (ConfigUtils.getConfigValue("os.name").toLowerCase().contains("win")) {
            String WIN_ALLURE_BINARY_PATH = ALLURE_BINARY_PATH + ".bat";
            TerminalUtils.executeTerminalCommand(
                    WIN_ALLURE_BINARY_PATH, "generate", ALLURE_RESULTS_FOLDER_PATH, "-o", FULL_ALLURE_REPORT_PATH, "--clean");
            LogUtils.info("Allure Report Generated Successfully on windows.");
        } else {
            TerminalUtils.executeTerminalCommand(
                    ALLURE_BINARY_PATH, "generate", ALLURE_RESULTS_FOLDER_PATH, "-o", FULL_ALLURE_REPORT_PATH, "--clean");
            LogUtils.info("Allure Report Generated Successfully on ", ConfigUtils.getConfigValue("os.name"));
        }
    }

    public static String renameAllureReport() {
        String newFileName = "AllureReport" + TimeUtils.getTimestamp() + ".html";
        FilesUtils.renameFile(System.getProperty("user.dir") + File.separator + ALLURE_REPORT_PATH + File.separator + "index.html", newFileName);
        return newFileName;
    }


    public static void openAllureReport(String newFileName) {

        String reportIndexPath = ALLURE_REPORT_PATH + File.separator + newFileName;
        if (ConfigUtils.getConfigValue("OpenAllureReportAfterExecution").equalsIgnoreCase("true")) {
            if (ConfigUtils.getConfigValue("os.name").toLowerCase().contains("win")) {
                TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportIndexPath);
            } else {
                TerminalUtils.executeTerminalCommand("open", reportIndexPath);
            }
        }
    }

    public static void copyHistory() {
        try {
            File historyDir = new File(System.getProperty("user.dir") + File.separator + FULL_ALLURE_REPORT_PATH + File.separator + "history");
            File allureResultsDir = new File(System.getProperty("user.dir") + File.separator + ALLURE_RESULTS_FOLDER_PATH + File.separator + "history");
            if (historyDir.exists()) {
                FileUtils.copyDirectory(historyDir, allureResultsDir);
                LogUtils.info("History copied successfully");
            }
            FileUtils.deleteDirectory(new File(FULL_ALLURE_REPORT_PATH));
            LogUtils.info("History deleted successfully");
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    //TODO: Attach the screenshot to Allure
    public static void attachScreenshotToAllure(String screenshotName, String screenshotPath) {
        try {
            Allure.addAttachment(screenshotName, java.nio.file.Files.newInputStream(Path.of(screenshotPath)));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }

    }

    //TODO: Attach the Logs to Allure
    public static void attachLogsToAllure() {
        try {
            File logFile = FilesUtils.getLatestFile(LogUtils.LOGS_PATH);
            assert logFile != null;
            Allure.addAttachment("logs.log", java.nio.file.Files.readString(Path.of(logFile.getPath())));
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    //TODO: Attach the Records to Allure
    public static void attachRecordsToAllure() {
        try {
            File screenRecord = FilesUtils.getLatestFile(ScreenRecorderUtils.RECORDINGS_PATH);
            if (screenRecord != null && screenRecord.getName().contains(".mp4"))
                Allure.addAttachment("Test Execution Video", "video/mp4", java.nio.file.Files.newInputStream(screenRecord.toPath()), ".mp4");
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

}

