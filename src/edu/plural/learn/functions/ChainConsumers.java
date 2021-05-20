package edu.plural.learn.functions;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.GenericBuilder;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChainConsumers {

    private final static List<Person> PERSONS = Arrays.asList(
            GenericBuilder.of(Person::new).with(Person::setName, "Eduardo").with(Person::setAge, 33).build(),
            GenericBuilder.of(Person::new).with(Person::setName, "Karolina").with(Person::setAge, 30).build(),
            GenericBuilder.of(Person::new).with(Person::setName, "Rafal").with(Person::setAge, 8).build(),
            GenericBuilder.of(Person::new).with(Person::setName, "Rafaela").with(Person::setAge, 18).build(),
            GenericBuilder.of(Person::new).with(Person::setName, "Thiago").with(Person::setAge, 25).build()
    );

    private static List<Person> getOnePersonCopiedList() {
        return Arrays.asList(GenericBuilder.of(Person::new).with(Person::setPerson, PERSONS.get(0)).build());
    }

    private static Integer sum(final Stream<Integer> agesStream) {
        return agesStream.reduce(0, (age1, age2) -> age1 + age2);
    }

    private static void chainPersons() {
        final List<String> result = PERSONS.stream()
                .filter(p -> (p.getAge() > 20 && p.getAge() < 30))
                .map(p -> "[" + p.getName() + ", " + p.getAge().toString() + "]")
                .collect(Collectors.toList());

        result.forEach(System.out::println);

        System.out.println("Sum of everyone's ages: " + sum(PERSONS.stream().map(Person::getAge)));
        System.out.println("Sum of empty Stream: " + sum(Stream.empty()));
        System.out.println("Sum of 1 element Stream: " + sum(getOnePersonCopiedList().stream().map(Person::getAge)));
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