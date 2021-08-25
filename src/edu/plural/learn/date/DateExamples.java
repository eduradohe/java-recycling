package edu.plural.learn.date;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonsFile;
import edu.plural.learn.util.StringUtils;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Consumer;

public class DateExamples {

    private static void shakespeareExample() {
        final LocalDate now = LocalDate.now();
        final LocalDate birthDate = LocalDate.of(1564, Month.APRIL, 23);

        final long years = birthDate.until(now, ChronoUnit.YEARS);

        final Calendar cal = GregorianCalendar.from(birthDate.atStartOfDay(ZoneId.systemDefault()));

        System.out.printf("Shakespeare birth date is %1$tm %1$te,%1$tY\n", cal);
        System.out.printf("It's been %1$d years since Shakespeare has been born\n\n", years);
    }

    private static void tellAgePersonsFile() {

        final PersonsFile personsFile = PersonsFile.getInstance();
        final List<Person> persons = personsFile.getPersons();

        persons.stream().forEach(personAgeConsumer());
    }

    private static String getTimeUnit( final Long time, final ChronoUnit chronoUnit ) {
        final boolean removePlural = (time <= 1);
        String timeUnit = chronoUnit.name().toLowerCase();
        if (removePlural) timeUnit = timeUnit.replace("s", "");

        return timeUnit;
    }

    private static String getFormattedTime(final LocalDate localDate, final ChronoUnit chronoUnit, final Long time) {

        final StringBuilder formattedTimeBuilder = new StringBuilder();
        final String timeUnit = getTimeUnit(time, chronoUnit);

        formattedTimeBuilder.append(time);
        formattedTimeBuilder.append(" ");
        formattedTimeBuilder.append(timeUnit);

        if ( ChronoUnit.YEARS.equals(chronoUnit) ) {
            return formattedTimeBuilder.toString();
        }

        final Long timeInUnit = localDate.until(LocalDate.now(), chronoUnit);
        final String subTimeUnit = getTimeUnit(timeInUnit, chronoUnit);

        formattedTimeBuilder.append(" [");
        formattedTimeBuilder.append(timeInUnit);
        formattedTimeBuilder.append(" ");
        formattedTimeBuilder.append(subTimeUnit);
        formattedTimeBuilder.append("]");

        return formattedTimeBuilder.toString();
    }

    private static String getChronoUnit(final LocalDate localDate, final ChronoUnit chronoUnit) {
        final Period period = localDate.until(LocalDate.now());
        final Long time = period.get(chronoUnit);
        return getFormattedTime(localDate, chronoUnit, time);
    }

    private static Consumer<Person> personAgeConsumer() {
        return p -> {
            final LocalDate now = LocalDate.now();

            final String nicknameToAdd = StringUtils.trimmedIsEmpty(p.getNickname()) ?
                    ""
                    :
                    " (" + p.getNickname() + ")";

            System.out.println(p.getName() + nicknameToAdd + ", " + p.getGender().getDescription() + ", was born " +
                    getChronoUnit(p.getBirthday(), ChronoUnit.YEARS) + ", " +
                    getChronoUnit(p.getBirthday(), ChronoUnit.MONTHS) + ", and " +
                    getChronoUnit(p.getBirthday(), ChronoUnit.DAYS) + " ago" );
        };
    }

    public static void main(String[] args) {
        shakespeareExample();
        tellAgePersonsFile();
    }
}
