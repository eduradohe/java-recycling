package edu.plural.learn.functions;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ChainConsumers {
    public static void main(String[] args) {

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
}