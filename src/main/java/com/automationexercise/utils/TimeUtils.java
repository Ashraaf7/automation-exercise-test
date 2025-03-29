package com.automationexercise.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    private TimeUtils() {
        super();
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_hh-mm-ssa").format(new Date());
    }

    public static String getSimpleTimestamp() {
        return new SimpleDateFormat("hh-mm-ssa").format(new Date());
    }


}
