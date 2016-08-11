package com.feed.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Scanner;

public class ConsoleUtil {

    private static final String MESSAGE_ERROR = "You entered incorrectly value";

    private static Scanner scanner = new Scanner(System.in);

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printError(String message) {
        System.err.println(message);
    }

    public static long enterSchedulePeriod(String message) {
        print(message);
        long period;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            period = checkPeriod(line);
            if (period > 0) {
                return period;
            } else {
                printError("Time period is not correct");
            }
        }
        return 1l;
    }

    public static String enterDirectory(String message) {
        print(message);
        String result = null;
        while (scanner.hasNext()) {
            result = scanner.nextLine();
            if (validateDirectory(result)) {
                return result;
            } else {
                printError("Directory is incorrect or doesn't exist");
            }
        }
        return result;
    }

    private static long checkPeriod(String periodValue) {
        long period = 0;
        if (StringUtils.isNumeric(periodValue)) {
            period = Long.valueOf(periodValue);
            if (period > 0) {
                return period;
            }
        }
        return period;
    }

    private static boolean validateDirectory(String path) {
        if (StringUtils.isNotBlank(path)) {
            File dir = new File(path);
            return dir.exists() && dir.isDirectory();
        }
        return false;
    }

}
