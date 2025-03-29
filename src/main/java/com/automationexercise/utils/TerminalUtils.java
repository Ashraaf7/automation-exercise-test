package com.automationexercise.utils;

public class TerminalUtils {
    private TerminalUtils() {
        super();
    }

    public static void executeTerminalCommand(String... command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO(); // Redirect input/output to the current process
            Process process = processBuilder.start();
            process.waitFor(); // Wait for the command to complete
            LogUtils.info("Command executed successfully: " + String.join(" ", command));
        } catch (Exception e) {
            LogUtils.error("Failed to execute command: " + e.getMessage());
        }
    }
}
