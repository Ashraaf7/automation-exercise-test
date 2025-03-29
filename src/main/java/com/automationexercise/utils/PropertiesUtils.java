package com.automationexercise.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class PropertiesUtils {
    private static final String propertiesPath = "src/main/resources/";

    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFilesList;
            propertiesFilesList = FileUtils.listFiles(new File(propertiesPath), new String[]{"properties"}, true);
            propertiesFilesList.forEach(propertyFile -> {
                try {
                    properties.load(new FileInputStream(propertyFile));
                } catch (IOException ioe) {
                    LogUtils.error(ioe.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            LogUtils.info("Loading Properties File Data");
            return properties;
        } catch (Exception e) {
            LogUtils.error("Failed to Load Properties File Data because: " + e.getMessage());
            return null;
        }
    }


    public static void AddProperty(String key, String value) throws IOException {
        Properties properties = loadProperties();
        FileOutputStream output = new FileOutputStream(propertiesPath);
        assert properties != null;
        properties.setProperty(key, value);
        properties.store(output, null);
    }

}
