package com.automationexercise.utils.allurereport;

import com.automationexercise.utils.LogUtils;
import com.automationexercise.utils.OSUtils;
import com.automationexercise.utils.TerminalUtils;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.automationexercise.utils.ConfigUtils.getConfigValue;

public class AllureBinaryManager {

    private static class LazyHolder {
        static final String VERSION = resolveVersion();

        private static String resolveVersion() {
            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true).execute().url().toString();
                LogUtils.info("Resolved Allure version: " + url.split("/tag/")[1]);
                return url.split("/tag/")[1];
            } catch (IOException e) {
                throw new IllegalStateException("Unable to resolve Allure version", e);
            }
        }
    }

    public static void downloadAndExtract() {
        try {
            String version = LazyHolder.VERSION;
            Path extractionDir = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version);
            if (Files.exists(extractionDir)) {
                LogUtils.info("Allure binaries already exist.");
                return;
            }
            // Give execute permissions to the binary if not on Windows
            if (!OSUtils.getCurrentOS().equals(OSUtils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", getExecutable().toString());
            }
            Path zipPath = downloadZip(version);
            extractZip(zipPath);

            LogUtils.info("Allure binaries downloaded and extracted.");
        } catch (Exception e) {
            LogUtils.error("Error downloading or extracting binaries", e.getMessage());
        }
    }

    public static Path getExecutable() {
        String version = LazyHolder.VERSION;
        Path binaryPath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version, "bin", "allure");
        return OSUtils.getCurrentOS() == OSUtils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }

    private static Path downloadZip(String version) throws IOException {
        String url = "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/" + version + "/allure-commandline-" + version + ".zip";
        Path zipFile = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");
        if (!Files.exists(zipFile)) {
            try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                 OutputStream out = Files.newOutputStream(zipFile)) {
                in.transferTo(out);
            } catch (URISyntaxException e) {
                 LogUtils.error("Invalid URL for Allure download: " + url, e.getMessage());
            }
        }
        return zipFile;
    }

    private static void extractZip(Path zipPath) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstants.EXTRACTION_DIR.toString(), entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        }
    }


}