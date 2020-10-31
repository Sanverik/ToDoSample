package com.softserve.itacademy.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatterUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateFormatterUtil() {

    }

    public static String format(LocalDateTime localDateTime) {

        return localDateTime.format(FORMATTER);
    }
}
