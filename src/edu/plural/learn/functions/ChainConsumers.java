package edu.plural.learn.functions;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonList;
import edu.plural.learn.util.StringUtils;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainConsumers {

    private static final PersonList PERSON_LIST = PersonList.getInstance();

    private static void chainPersons() {
        final List<String> result = PERSON_LIST.getPersons().stream()
                .filter(p -> (p.getAge() > 20 && p.getAge() < 30))
                .map(p -> "[" + p.getName() + ", " + p.getAge().toString() + "]")
                .collect(Collectors.toList());

        result.forEach(System.out::println);

        final List<Person> persons = PERSON_LIST.getPersons();
        final List<Person> emptyPersons = Collections.emptyList();
        final List<Person> onePersonList = PERSON_LIST.getOnePersonCopiedList();
        final List<Person> anotherOnePersonList = PERSON_LIST.getOnePersonCopiedList(PersonList.PERSON_RAFAL);
        final List<Person> anotherLastPersonOnList = PERSON_LIST.getOnePersonCopiedList(88);

        System.out.println("Sum of everyone's ages: " + new AgeCalculator(persons).sum());
        System.out.println("Sum of empty Stream: " + new AgeCalculator(emptyPersons).sum());
        System.out.println("Sum of 1 element Stream: " + new AgeCalculator(onePersonList).sum());
        System.out.println("Greater Age: " + new AgeCalculator(persons).max());
        System.out.println("Max Age: " + new AgeCalculator(emptyPersons).max());
        System.out.println("Max Age: " + new AgeCalculator(anotherOnePersonList).max());
        System.out.println("Max Age: " + new AgeCalculator(anotherLastPersonOnList).max());
        System.out.println("Lesser age: " + new AgeCalculator(persons).min());
        System.out.println("Min Age between 10 and 30: " + new AgeCalculator(persons).min(10, 30));
        System.out.println("Min Age below 10: " + new AgeCalculator(persons).minBelow(10));
        System.out.println("Min Age above 30: " + new AgeCalculator(persons).minAbove(30));
        System.out.println("Max Age between 10 and 30: " + new AgeCalculator(persons).max(10, 30));
        System.out.println("Max Age above 33: " + new AgeCalculator(persons).maxAbove(33));
        System.out.println("Max Age below 30: " + new AgeCalculator(persons).maxBelow(30));
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
     * Auxiliary class for calculate min, max and sum of ages given a list of Persons
     */
    private static class AgeCalculator {

        private Stream<Integer> agesStream;

        /**
         * Instantiates an AgeCalculator object by mapping a Persons list into an Age stream
         * @param persons List of persons to be mapped into an Age stream
         */
        private AgeCalculator(final List<Person> persons) {
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

            try {
                return this.agesStream.filter(floor.and(ceiling)).min(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            } catch(IllegalArgumentException e) {
                System.out.print(e);
                System.out.println(": no age found within reach (" + StringUtils.toString(minAge) + " and " + StringUtils.toString(maxAge) + ")");
                return 0;
            }

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

            try {
                return this.agesStream.filter(floor.and(ceiling)).max(Comparator.naturalOrder()).orElseThrow(IllegalArgumentException::new);
            } catch(IllegalArgumentException e) {
                System.out.print(e);
                System.out.println(": no age found within reach (" + StringUtils.toString(minAge) + " and " + StringUtils.toString(maxAge) + ")");
                return 0;
            }
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