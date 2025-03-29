package com.automationexercise.drivers;


import com.automationexercise.utils.ConfigUtils;

public interface WebDriverOptionsAbstract<T> {
    String executionType = ConfigUtils.getConfigValue("executionType");
    String remoteExecutionHost = ConfigUtils.getConfigValue("remoteExecutionHost");
    String remoteExecutionPort = ConfigUtils.getConfigValue("remoteExecutionPort");

    T getOptions();
}
