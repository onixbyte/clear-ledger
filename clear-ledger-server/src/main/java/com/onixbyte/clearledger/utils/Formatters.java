package com.onixbyte.clearledger.utils;

import java.time.format.DateTimeFormatter;

public final class Formatters {

    private Formatters() {
    }

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public static final DateTimeFormatter SHORTENED_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyMMdd");

}
