package edu.plural.learn.date;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonsFile;
import edu.plural.learn.util.StringUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

public class DateExamples {

    private static void shakespeareExample() {
        final LocalDate now = LocalDate.now();
        final LocalDate birthDate = LocalDate.of(1564, Month.APRIL, 23);

        final long years = birthDate.until(now, ChronoUnit.YEARS);

        final Instant instant = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

        System.out.printf("Shakespeare birth date is %1$tm %1$te,%1$tY\n", instant.toEpochMilli());
        System.out.printf("It's been %1$d years since Shakespeare has been born\n", years);
    }

    private static void tellAgePersonsFile() {

        final PersonsFile personsFile = PersonsFile.getInstance();
        final List<Person> persons = personsFile.getPersons();

        persons.stream().forEach(personAgeConsumer());
    }

    private static String getChronoUnit(final Period period, final ChronoUnit chronoUnit) {

       final Long time = period.get(chronoUnit);

       final boolean removePlural = (time <= 1);

       String timeUnit = chronoUnit.name().toLowerCase();
       if (removePlural) timeUnit = timeUnit.replace("s", "");

       return time + " " + timeUnit;
    }

    private static Consumer<Person> personAgeConsumer() {
        return p -> {
            final LocalDate now = LocalDate.now();
            final Period period = p.getBirthday().until(now);

            final String nicknameToAdd = StringUtils.trimmedIsEmpty(p.getNickname()) ?
                    ""
                    :
                    " (" + p.getNickname() + ")";

            System.out.println(p.getName() + nicknameToAdd + " was born " +
                    getChronoUnit(period, ChronoUnit.YEARS) + ", " +
                    getChronoUnit(period, ChronoUnit.MONTHS) + ", and " +
                    getChronoUnit(period, ChronoUnit.DAYS) + " ago" );
        };
    }

    public static void main(String[] args) {
        shakespeareExample();
        tellAgePersonsFile();
    }
}
