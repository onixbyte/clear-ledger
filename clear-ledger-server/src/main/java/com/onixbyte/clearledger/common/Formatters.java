package com.onixbyte.clearledger.common;

import java.time.format.DateTimeFormatter;

/**
 * This class providing predefined formatters for dates and times.
 *
 * @author zihluwang
 */
public final class Formatters {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Formatters() {
    }

    /**
     * A formatter for date and time in the format "yyyy-MM-dd HH:mm:ss".
     * <p>
     * This formatter produces strings representing both date and time, such as
     * "2025-03-26 14:30:45". It is suitable for logging, detailed timestamps, or any scenario
     * requiring a complete date-time representation.
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * A formatter for dates in the format "yyyy-MM-dd".
     * <p>
     * This formatter generates date-only strings, such as "2025-03-26". It is ideal for
     * scenarios where only the date is required, such as filing systems or date-based queries.
     */
    public static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * A formatter for times in the format "HH:mm:ss".
     * <p>
     * This formatter outputs time-only strings, such as "14:30:45", using a 24-hour clock.
     * It is useful for time-specific displays or logging events within a day.
     */
    public static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * A formatter for shortened dates in the format "yyMMdd".
     * <p>
     * This formatter produces compact date strings, such as "250326" for 26 March 2025. It is
     * intended for use in contexts where brevity is essential, such as file naming or
     * concise identifiers.
     */
    public static final DateTimeFormatter SHORTENED_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyMMdd");

}
