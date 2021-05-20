package edu.plural.learn.functions;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonList;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainConsumers {

    private static final PersonList PERSON_LIST = PersonList.getInstance();

    private static Integer max(final Stream<Integer> agesStream) {
        return agesStream.max(Comparator.naturalOrder()).orElse(0);
    }

    private static Integer sum(final Stream<Integer> agesStream) {
        return agesStream.reduce(0, (age1, age2) -> age1 + age2);
    }

    private static void chainPersons() {
        final List<String> result = PERSON_LIST.getPersons().stream()
                .filter(p -> (p.getAge() > 20 && p.getAge() < 30))
                .map(p -> "[" + p.getName() + ", " + p.getAge().toString() + "]")
                .collect(Collectors.toList());

        result.forEach(System.out::println);

        final List<Person> persons = PERSON_LIST.getPersons();
        final List<Person> onePersonList = PERSON_LIST.getOnePersonCopiedList();
        final List<Person> anotherOnePersonList = PERSON_LIST.getOnePersonCopiedList(PersonList.PERSON_RAFAELA);
        final List<Person> anotherLastPersonOnList = PERSON_LIST.getOnePersonCopiedList(88);

        System.out.println("Sum of everyone's ages: " + sum(persons.stream().map(Person::getAge)));
        System.out.println("Sum of empty Stream: " + sum(Stream.empty()));
        System.out.println("Sum of 1 element Stream: " + sum(onePersonList.stream().map(Person::getAge)));
        System.out.println("Max Age: " + max(persons.stream().map(Person::getAge)));
        System.out.println("Max Age: " + max(Stream.empty()));
        System.out.println("Max Age: " + max(anotherOnePersonList.stream().map(Person::getAge)));
        System.out.println("Max Age: " + max(anotherLastPersonOnList.stream().map(Person::getAge)));
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
}