package com.automationexercise.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static org.apache.commons.io.FileUtils.copyFile;

public class FilesUtils {
    private FilesUtils() {
        super();
    }

    /**
     * Replaces all occurrences of `oldText` with `newText` in the given `input` string.
     *
     * @param input   The original string.
     * @param oldText The text to be replaced.
     * @param newText The text to replace `oldText` with.
     * @return The modified string with replacements.
     */
    public static String replaceText(String input, String oldText, String newText) {
        String newInput = "";
        try {
            newInput = input.replace(oldText, newText);
        } catch (NullPointerException e) {
            LogUtils.error("Input, oldText, and newText cannot be null.");
        }
        return newInput;
    }

    public static File getLatestFile(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0)
            return null;
        //Files already sorted because we named it using timestamp, so I comment this line.
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
        return files[0];
    }

    public static void deleteFiles(File dirPath) {
        if (dirPath == null || !dirPath.exists()) {
            LogUtils.warn("Directory does not exist: " + dirPath);
            return;
        }

        File[] filesList = dirPath.listFiles();
        if (filesList == null) {
            LogUtils.warn("Failed to list files in: " + dirPath);
            return;
        }

        for (File file : filesList) {
            if (file.getName().equals(".gitkeep")) {
                continue;  // Skip .gitkeep file
            }

            if (file.isDirectory()) {
                deleteFiles(file);  // Recursive deletion for directories
            }

            if (!file.delete()) {
                LogUtils.warn("Failed to delete file: " + file.getAbsolutePath());
            }
        }
    }

    public static void renameFile(String filePath, String newFileName) {
        try {
            var targetFile = new File(filePath);
            String targetDirectory = targetFile.getParentFile().getAbsolutePath();
            File newFile = new File(targetDirectory + File.separator + newFileName);
            if (!targetFile.getPath().equals(newFile.getPath())) {
                copyFile(targetFile, newFile);
                FileUtils.deleteQuietly(targetFile);
                LogUtils.info("Target File Path: \"" + filePath + "\", file was renamed to \"" + newFileName + "\".");
            } else {
                LogUtils.info(("Target File Path: \"" + filePath + "\", already has the desired name \"" + newFileName + "\"."));
            }
        } catch (IOException e) {
            LogUtils.error(e.getMessage());
        }
    }

    //creating dirs if not exist
    public static void createDirs(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
                LogUtils.info("Directory created: " + path);
            }
        } catch (Exception e) {
            LogUtils.error("Failed to create directory: " + e.getMessage());
        }

    }

    public static void cleanDirectory(File file) {
        try {
            FileUtils.deleteQuietly(file);
        } catch (Exception exception) {
            LogUtils.error(exception.getMessage());

        }
    }

    public static void deleteSpecificFiles(String dirPath, String... ExcludedFileNames) {
        Path directory = Paths.get(dirPath);
        Set<String> excludedFiles = Set.of(ExcludedFileNames);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                if (Files.isRegularFile(file) && !excludedFiles.contains(file.getFileName().toString())) {
                    Files.delete(file);
                    LogUtils.info("File deleted: " + file);
                }
            }
        } catch (IOException e) {
            LogUtils.error("Failed to delete files: " + e.getMessage());
        }
    }

    /**
     * Copies a file from sourceFilePath to destinationFilePath on the local storage
     *
     * @param sourceFilePath      the full (absolute) path of the source file that
     *                            will be copied
     * @param destinationFilePath the full (absolute) path of the desired location
     *                            and file name for the newly created copied file
     */
    public void copyFiles(String sourceFilePath, String destinationFilePath) {
        File sourceFile = new File(sourceFilePath);
        File destinationFile = new File(destinationFilePath);
        try {
            copyFile(sourceFile, destinationFile);
            LogUtils.info("Source File: \"" + sourceFilePath + "\" | Destination File: \"", destinationFilePath + "\"");

        } catch (IOException e) {
            LogUtils.error("Failed to copy file: " + e.getMessage());
        }
    }

    public Collection<File> getFileList(String targetDirectory) {
        StringBuilder files = new StringBuilder();
        Collection<File> filesList = new ArrayList<>();
        try {
            filesList = FileUtils.listFiles(new File(targetDirectory), TrueFileFilter.TRUE,
                    TrueFileFilter.TRUE);
            filesList.forEach(file -> files.append(file.getAbsolutePath()).append(System.lineSeparator()));
        } catch (IllegalArgumentException rootCauseException) {
            LogUtils.error("Failed to list absolute file paths in this directory: \"" + targetDirectory + "\"" + rootCauseException);
        }
        LogUtils.info("Target Directory: \"" + targetDirectory + "\"" + files.toString().trim());
        return filesList;
    }

    public void writeToFile(String fileName, List<String> text) {
        byte[] textToBytes = String.join(System.lineSeparator(), text).getBytes();
        String absoluteFilePath = (new File(fileName)).getAbsolutePath();
        try {
            Path targetFilePath = Paths.get(absoluteFilePath);
            Path parentDir = targetFilePath.getParent();
            if (!parentDir.toFile().exists()) {
                Files.createDirectories(parentDir);
            }
            Files.write(targetFilePath, textToBytes);
            LogUtils.info("Target File Path: \"" + targetFilePath + "\"" + Arrays.toString(textToBytes));
        } catch (InvalidPathException | IOException rootCauseException) {
            LogUtils.error("Folder Name: \"" + fileName + "\"." + rootCauseException);
        }
    }
}
