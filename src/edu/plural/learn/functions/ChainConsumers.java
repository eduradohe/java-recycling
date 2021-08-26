package edu.plural.learn.functions;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonsFile;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainConsumers {

    private static final PersonsFile PERSON_LIST = PersonsFile.getInstance();

    private static void chainPersons() {

        groupByAge();
        flatMapWithCollectors();

        final List<Person> persons = PERSON_LIST.getPersons();
        final List<Person> emptyPersons = Collections.emptyList();
        final List<Person> onePersonList = PERSON_LIST.getOnePersonCopiedList();
        final List<Person> anotherOnePersonList = PERSON_LIST.getOnePersonCopiedList(PersonsFile.PERSON_RAFAL);
        final List<Person> anotherLastPersonOnList = PERSON_LIST.getOnePersonCopiedList(88);

        System.out.println("Sum of everyone's ages: " + new AgeAggregator(persons).sum());
        System.out.println("Sum of empty Stream: " + new AgeAggregator(emptyPersons).sum());
        System.out.println("Sum of 1 element Stream: " + new AgeAggregator(onePersonList).sum());
        System.out.println("Greater Age: " + new AgeAggregator(persons).max());
        System.out.println("Max Age: " + new AgeAggregator(emptyPersons).max());
        System.out.println("Max Age: " + new AgeAggregator(anotherOnePersonList).max());
        System.out.println("Max Age: " + new AgeAggregator(anotherLastPersonOnList).max());
        System.out.println("Lesser age: " + new AgeAggregator(persons).min());
        System.out.println("Min Age between 10 and 30: " + new AgeAggregator(persons).min(10, 30));
        System.out.println("Min Age below 10: " + new AgeAggregator(persons).minBelow(10));
        System.out.println("Min Age above 30: " + new AgeAggregator(persons).minAbove(30));
        System.out.println("Max Age between 10 and 30: " + new AgeAggregator(persons).max(10, 30));
        System.out.println("Max Age above 33: " + new AgeAggregator(persons).maxAbove(33));
        System.out.println("Max Age below 30: " + new AgeAggregator(persons).maxBelow(30));
    }

    private static void groupByAge() {
        final List<Person> persons = PERSON_LIST.getPersons();

        final Map<Integer, Long> ageGroupBy = persons.stream()
                .filter(p -> p.getAge() > 10 && p.getAge() < 26)
                .collect(
                        Collectors.groupingBy(
                                Person::getAge,
                                Collectors.counting()
                        )
                );
        ageGroupBy.entrySet().stream().map(m -> "Number of persons at age " + m.getKey() + ": " + m.getValue()).sorted().forEach(System.out::println);
    }

    private static void flatMapWithCollectors(){

        final List<Person> persons = PERSON_LIST.getPersons();

        final List<String> personsBetween20And30 = persons.stream()
                .filter(p -> (p.getAge() > 20 && p.getAge() < 30 || p.getAge() == 34))
                .map(Person::toString)
                .collect(Collectors.toList());

        final Predicate<Person> greaterThan = p -> p.getAge() > 29;
        final Predicate<Person> lesserThan = p -> p.getAge() < 35;

        final List<String> eduKaro = persons.stream()
                .filter(greaterThan.and(lesserThan))
                .map(Person::getName)
                .collect(Collectors.toList());

        final Function<List<String>, Stream<String>> flatMapper = l -> l.stream();

        final List<List<String>> allPersonListedBefore = new ArrayList();
        allPersonListedBefore.add(personsBetween20And30);
        allPersonListedBefore.add(eduKaro);

        final String all = allPersonListedBefore.stream().flatMap(flatMapper).collect(Collectors.joining(", "));
        System.out.println(all);
    }

    private static void chainSimpleStrings() {

        final List<String> strings = Arrays.asList("one", "two", "three", "four", "five");
        final List<String> result = new ArrayList<>();

        final Consumer<String> c1 = System.out::println;
        final Consumer<String> c2 = result::add;

        strings.forEach(c1.andThen(c2));

        final BiPredicate<List, List> bp1 = (s, r) -> s.containsAll(r);
        final Predicate<List> p1 = Objects::nonNull;
        final Predicate<List> p2 = l -> !l.isEmpty();
        final Predicate<List> p3 = p1.and(p2);

        if ( p3.test(result) && bp1.test(strings, result) ) {
            System.out.println("Result has the same elements as Strings list!");
            System.out.println("Result list size: " + result.size());
        }
    }

    public static void main(String[] args) {
        chainSimpleStrings();
        chainPersons();
    }

    /**
     * Auxiliary class for looking up for min, max and sum of ages given a list of Persons
     */
    private static class AgeAggregator {

        private Stream<Integer> agesStream;

        /**
         * Instantiates an AgeAggregator object by mapping a Persons list into an Age stream
         * @param persons List of persons to be mapped into an Age stream
         */
        private AgeAggregator(final List<Person> persons) {
            this.agesStream = persons.stream().map(Person::getAge);
        }

        /**
         * Processes the greater age from within the age stream
         * @return The greater age from the stream
         */
        private Integer min() {
            return this.min(null, null);
        }

        /**
         * Calculates the lesser age from the stream above the passed parameters
         *
         * @param minAge minimum age allowed
         * @return The lesser age above <code>minAge</code> parameters
         */
        private Integer minAbove(final Integer minAge) {
            return this.min(minAge, null);
        }

        /**
         * Calculates the lesser age from the stream below the passed parameters
         *
         * @param maxAge maximum age allowed
         * @return The lesser age above <code>maxAge</code> parameters
         */
        private Integer minBelow(final Integer maxAge) {
            return this.min(null, maxAge);
        }

        /**
         * Calculates the lesser age from the stream within a range passed as parameters
         *
         * @param minAge minimum age allowed
         * @param maxAge maximum age allowed
         * @return The lesser age between <code>minAge</code> and <code>maxAge</code> parameters
         */
        private Integer min(final Integer minAge, final Integer maxAge) {

            final Predicate<Integer> floor = a -> minAge != null ? a > minAge : Boolean.TRUE;
            final Predicate<Integer> ceiling = a -> maxAge != null ? a < maxAge : Boolean.TRUE;

            return this.agesStream.filter(floor.and(ceiling)).min(Comparator.naturalOrder()).orElse(0);
        }

        /**
         * Processes the maximum age from within the age stream
         * @return The greater age from the stream
         */
        private Integer max() {
            return this.max(null, null);
        }

        /**
         * Calculates the greater age from the stream above the passed parameters
         *
         * @param minAge minimum age allowed
         * @return The greater age above <code>minAge</code> parameters
         */
        private Integer maxAbove(final Integer minAge) {
            return this.max(minAge, null);
        }

        /**
         * Calculates the greater age from the stream below the passed parameters
         *
         * @param maxAge minimum age allowed
         * @return The greater age below <code>maxAge</code> parameters
         */
        private Integer maxBelow(final Integer maxAge) {
            return this.max(null, maxAge);
        }

        /**
         * Calculates the greater age from the stream within a range passed as parameters
         *
         * @param minAge minimum age allowed
         * @param maxAge maximum age allowed
         * @return The greater age between <code>minAge</code> and <code>maxAge</code> parameters
         */
        private Integer max(final Integer minAge, final Integer maxAge) {

            final Predicate<Integer> floor = a -> minAge != null ? a > minAge : Boolean.TRUE;
            final Predicate<Integer> ceiling = a -> maxAge != null ? a < maxAge : Boolean.TRUE;

            return this.agesStream.filter(floor.and(ceiling)).max(Comparator.naturalOrder()).orElse(0);
        }

        /**
         * Processes the sum of all ages in the stream of ages
         *
         * @return The sum of all ages
         */
        private  Integer sum() {
            return this.agesStream.reduce(0, (age1, age2) -> age1 + age2);
        }
    }
}