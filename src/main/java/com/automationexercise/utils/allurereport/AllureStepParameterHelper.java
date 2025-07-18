package com.automationexercise.utils.allurereport;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;

import java.util.Arrays;
import java.util.List;

public class AllureStepParameterHelper {

    public static void addStepParameters(String[][] nameValuePairs) {
        List<Parameter> parameters = Arrays.stream(nameValuePairs)
                .map(pair -> new Parameter().setName(pair[0]).setValue(pair[1]))
                .toList();
        Allure.getLifecycle().updateStep(step -> step.setParameters(parameters));
    }
}