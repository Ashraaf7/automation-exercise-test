package com.automationexercise.utils;

import com.google.common.collect.ImmutableMap;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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


    private static String getAllureExecutable() {
        // Dynamically set the Allure binary path if it's not set yet
        ALLURE_BINARY_PATH = ALLURE_EXTRACTION_LOCATION + "allure-" + ALLURE_VERSION + File.separator + "bin" + File.separator + "allure";
        if (OS.getCurrentOS() == OS.WINDOWS) {
            return ALLURE_BINARY_PATH + ".bat";
        }
        return ALLURE_BINARY_PATH;
    }

    private static void generateReport(String outputFolder, boolean isSingleFile) {
        String allureExecutable = getAllureExecutable();

        List<String> command = new ArrayList<>();
        command.add(allureExecutable);
        command.add("generate");
        command.add(ALLURE_RESULTS_FOLDER_PATH);
        command.add("-o");
        command.add(outputFolder);
        command.add("--clean");
        if (isSingleFile) {
            command.add("--single-file");
        }

        TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));

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
            String extractionDir = ALLURE_EXTRACTION_LOCATION + "allure-" + ALLURE_VERSION;

            if (new File(extractionDir).exists()) {
                LogUtils.info("Allure binaries already exist.");
                return;
            }

            extractAllureBinaries(allureZipPath);
            LogUtils.info("Allure binaries downloaded and extracted.");

            if (!ConfigUtils.getConfigValue("os.name").toLowerCase().contains("win")) {
                setUnixExecutePermissions();
            }
        } catch (Exception e) {
            LogUtils.error("Error during Allure binaries extraction: " + e.getMessage());
        }
    }

    private static void extractAllureBinaries(String allureZipPath) {
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
        } catch (IOException e) {
            LogUtils.error("Failed to extract Allure binaries: " + e.getMessage());
        }
    }

    private static void setUnixExecutePermissions() {
        if (ALLURE_BINARY_PATH == null || ALLURE_BINARY_PATH.isEmpty()) {
            ALLURE_BINARY_PATH = getAllureExecutable();
        }
        TerminalUtils.executeTerminalCommand("chmod", "u+x", ALLURE_BINARY_PATH);
    }

    public static String urlConnection(String url) {
        try {
            String resolvedUrl = Jsoup.connect(url)
                    .followRedirects(true)
                    .execute()
                    .url()
                    .toString();
            LogUtils.info("Resolved URL: " + resolvedUrl);
            return resolvedUrl;
        } catch (IOException e) {
            LogUtils.error("Failed to resolve URL: " + url + " - " + e.getMessage());
            return null;
        }
    }

    private static String getAllureZipPath() {
        try {
            ALLURE_VERSION = Objects.requireNonNull(urlConnection("https://github.com/allure-framework/allure2/releases/latest")).split("/tag/")[1];
            LogUtils.info("Allure Version: " + ALLURE_VERSION);

            String allureZipUrl = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/" + ALLURE_VERSION + "/allure-commandline-" + ALLURE_VERSION + ".zip";
            String allureZipFile = "src/main/resources/allure/allure-" + ALLURE_VERSION + ".zip";

            if (!new File(allureZipFile).exists()) {
                downloadAllureZip(allureZipUrl, allureZipFile);
            }

            return allureZipFile;
        } catch (Exception e) {
            LogUtils.error("Error while fetching Allure ZIP path: " + e.getMessage());
            return null;
        }
    }

    private static void downloadAllureZip(String allureZipUrl, String allureZipFile) {
        try (BufferedInputStream in = new BufferedInputStream(new URL(allureZipUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(allureZipFile)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            LogUtils.info("Allure ZIP downloaded to: " + allureZipFile);
        } catch (IOException e) {
            LogUtils.error("Failed to download Allure ZIP from: " + allureZipUrl + " - " + e.getMessage());
        }
    }


    // Method to generate Allure Report programmatically
    public static void generateAllureReport() {
        generateReport(ALLURE_REPORT_PATH, true);
        LogUtils.info("Allure Report Generated Successfully on " + OS.getCurrentOS());
    }

    public static void generateFullAllureReport() {
        generateReport(FULL_ALLURE_REPORT_PATH, false);
        LogUtils.info("Full Allure Report Generated Successfully on " + OS.getCurrentOS());
    }

    public static String renameAllureReport() {
        String newFileName = "AllureReport" + TimeUtils.getTimestamp() + ".html";
        FilesUtils.renameFile(System.getProperty("user.dir") + File.separator + ALLURE_REPORT_PATH + File.separator + "index.html", newFileName);
        return newFileName;
    }

    public static void openAllureReport(String newFileName) {
        if (!ConfigUtils.getConfigValue("OpenAllureReportAfterExecution").equalsIgnoreCase("true")) {
            return;
        }

        String reportIndexPath = ALLURE_REPORT_PATH + File.separator + newFileName;

        switch (OS.getCurrentOS()) {
            case WINDOWS:
                TerminalUtils.executeTerminalCommand("cmd.exe", "/c", "start", reportIndexPath);
                break;
            case MAC:
            case LINUX:
                TerminalUtils.executeTerminalCommand("open", reportIndexPath);
                break;
            default:
                LogUtils.warn("Opening Allure Report is not supported on this OS.");
        }
    }

    public static void copyHistory() {
        try {
            File historyDir = new File(System.getProperty("user.dir") + File.separator + FULL_ALLURE_REPORT_PATH + File.separator + "history");
            File allureResultsDir = new File(System.getProperty("user.dir") + File.separator + ALLURE_RESULTS_FOLDER_PATH + File.separator + "history");
            if (historyDir.exists()) {
                FileUtils.copyDirectory(historyDir, allureResultsDir);
            }
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
            LogManager.shutdown(); // Flush and close appenders
            FilesUtils.getLogFileAfterTest(FilesUtils.getLatestFile(LogUtils.LOGS_PATH));
            File logFile = FilesUtils.getLatestFile(LogUtils.LOGS_PATH);
            ((LoggerContext) LogManager.getContext(false)).reconfigure();
            // Check if the log file exists and is not null
            if (logFile == null || !logFile.exists()) {
                LogUtils.error("Log file not found or does not exist.");
                return;
            }

            // Read the content of the log file and attach it to Allure
            String logContent = java.nio.file.Files.readString(Path.of(logFile.getPath()));
            Allure.addAttachment("logs.log", logContent);

        } catch (IOException e) {
            // Handle IO exceptions (e.g., file read issues)
            LogUtils.error("Error reading log file: " + e.getMessage());
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            LogUtils.error("Unexpected error: " + e.getMessage());
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

    public enum OS {
        WINDOWS, MAC, LINUX, OTHER;

        public static OS getCurrentOS() {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("win")) return WINDOWS;
            if (osName.contains("mac")) return MAC;
            if (osName.contains("nix") || osName.contains("nux")) return LINUX;
            return OTHER;
        }
    }

}

