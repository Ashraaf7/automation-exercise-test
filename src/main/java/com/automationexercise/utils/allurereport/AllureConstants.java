package com.automationexercise.utils.allurereport;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;

public class AllureConstants {
    public static final Path USER_DIR = Paths.get(getConfigValue("user.dir"), File.separator);
    public static final Path USER_HOME = Paths.get(getConfigValue("user.home"), File.separator);

    public static final Path RESULTS_FOLDER = Paths.get(String.valueOf(USER_DIR),"test-outputs", "allure-results",File.separator);
    public static final Path REPORT_PATH = Paths.get(String.valueOf(USER_DIR),"test-outputs", "reports",File.separator);
    public static final Path FULL_REPORT_PATH = Paths.get(String.valueOf(USER_DIR),"test-outputs", "full-report",File.separator);

    public static final Path HISTORY_FOLDER= Paths.get(FULL_REPORT_PATH.toString(), "history", File.separator);
    public static final Path RESULTS_HISTORY_FOLDER= Paths.get(RESULTS_FOLDER.toString(), "history", File.separator);

    public static final String INDEX_HTML = "index.html";
    public static final String REPORT_PREFIX = "AllureReport_";
    public static final String REPORT_EXTENSION = ".html";


    public static final String VIDEO_ATTACHMENT_NAME = "Test Execution Video";
    public static final String VIDEO_MIME_TYPE = "video/mp4";
    public static final String VIDEO_EXTENSION = ".mp4";

    public static final String ALLURE_ZIP_BASE_URL = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";
    public static final String ALLURE_LOCAL_ZIP_DIR = "src/main/resources/allure";

    public static final Path EXTRACTION_DIR = Paths.get(String.valueOf(USER_HOME),".m2/repository/allure",File.separator);
}
