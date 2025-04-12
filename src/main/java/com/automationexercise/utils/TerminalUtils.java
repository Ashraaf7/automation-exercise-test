package com.automationexercise.utils;

import java.io.IOException;
import java.util.Arrays;

public class TerminalUtils {

    public static void executeTerminalCommand(String... commandParts) {
        try {
            Process process = Runtime.getRuntime().exec(commandParts);
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                LogUtils.error("Command failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            LogUtils.error("Failed to execute terminal command: " + Arrays.toString(commandParts), e.getMessage());
        }
    }
}
