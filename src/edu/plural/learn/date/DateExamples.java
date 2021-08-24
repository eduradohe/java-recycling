package edu.plural.learn.date;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DateExamples {
    public static void main(String[] args) {
        final LocalDate now = LocalDate.now();
        final LocalDate birthDate = LocalDate.of(1564, Month.APRIL, 23);

        final long years = birthDate.until(now, ChronoUnit.YEARS);

        final Instant instant = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        System.out.printf("Shakespeare birth date is %1$tm %1$te,%1$tY\n", instant.toEpochMilli());
        System.out.printf("It's been %1$d years since Shakespeare has been born", years);
    }
}
