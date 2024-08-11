package io.github.facuu16.flib.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class Formatter {

    private final String[] ROMAN_SYMBOLS = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M" };
    private final int[] ROMAN_VALUES = { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000 };

    public final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");

    public String date(@NonNull Date date) {
        return DATE_FORMAT.format(date);
    }

    public String duration(long millis) {
        final long seconds = millis / 1000;
        final long minutes = seconds / 60;
        final long hours = minutes / 60;

        return (hours > 0) ? String.format("%02dh %02dm %02ds", hours, minutes % 60, seconds % 60)
                : (minutes > 0) ? String.format("%02dm %02ds", minutes, seconds % 60)
                : String.format("%02ds", seconds);
    }

    public String suffix(double number) {
        if (number < 1000)
            return String.valueOf(number);

        final int exponent = (int) Math.floor(Math.log10(Math.abs(number)) / 3);
        final char suffix = "kMBTPE".charAt(exponent - 1);

        return String.format("%.1f%c", number / Math.pow(10, exponent * 3), suffix);
    }

    public String commas(double number, int limit) {
        return String.format(limit == 0 ? "%,.0f" : "%,.1f", number);
    }

    public String toRomanNumbers(int number) {
        if (number <= 0)
            return String.valueOf(number);

        final StringBuilder result = new StringBuilder();

        int i = 12;

        while (number > 0) {
            number %= ROMAN_VALUES[i];

            for (int j = 0; j < (number / ROMAN_VALUES[i]); j++)
                result.append(ROMAN_SYMBOLS[i]);

            i--;
        }

        return result.toString();
    }

}