package edu.plural.learn.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class IntermediateAndFinalOperations {

    public static void main(String[] args) {

        final List<String> list = Arrays.asList("one", "two", "three", "four", "five", "six");
        final List<String> result = new ArrayList<>();

        Stream<String> stream = list.stream();

        final Predicate<String> p1 = Predicate.isEqual("two");
        final Predicate<String> p2 = Predicate.isEqual("three");
        final Predicate<String> p3 = s -> s.length() < 3;

        final Consumer<String> c1 = System.out::println;
        final Consumer<String> c2 = result::add;

        stream = stream
                .peek(c1)
                .filter(p1.or(p2))
                .peek(c2);

        System.out.println("Done Intermediate Operations");
        System.out.println("Result size: " + result.size());

        stream.filter(p3).forEach(c2.andThen(c1));

        System.out.println("Done Terminal Operations");
        System.out.println(result);
        System.out.println("Result size: " + result.size());
    }
}
