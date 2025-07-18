package com.automationexercise.utils.allurereport;

import com.automationexercise.utils.LogUtils;
import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.nio.file.Paths;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {

    public static void setEnvironmentVariables() {
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("OS", getConfigValue("os.name"))
                        .put("Java version:", getConfigValue("java.runtime.version"))
                        .put("Browser", getConfigValue("browserType"))
                        .put("Execution Type", getConfigValue("executionType"))
                        .put("URL", getConfigValue("baseUrlWeb"))
                        .build(), String.valueOf(AllureConstants.RESULTS_FOLDER) + File.separator
        );
        LogUtils.info("Allure environment variables set.");
        AllureBinaryManager.downloadAndExtract();
    }
}
