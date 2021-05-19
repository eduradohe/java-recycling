package edu.plural.learn.streams;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class PredicateStream {

    public static void main(String[] args) {

        final Stream<String> stream = Stream.of("one", "two", "three", "four", "five", "six");
        final Predicate<String> p1 = s -> s.length() > 3;
        final Predicate<String> p2 = Predicate.isEqual("six");
        final Predicate<String> p3 = Predicate.isEqual("one");

        stream.filter(p1.or(p3.or(p2))).forEach(System.out::println);
    }
}
