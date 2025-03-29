package com.automationexercise.utils;

public class ConfigUtils {

    public final static String CONFIGS_PATH = "src/main/resources/";

    private ConfigUtils() {
        super();
    }

    //TODO: get properties from any .properties file
    public static String getConfigValue(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
            return "";
        }
    }
}
