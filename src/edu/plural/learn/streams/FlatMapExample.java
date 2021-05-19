package edu.plural.learn.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapExample {

    public static void main(String[] args) {

        final List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5, 6);
        final List<Integer> list2 = Arrays.asList(2, 4, 6);
        final List<Integer> list3 = Arrays.asList(3, 5, 7);

        final List<List<Integer>> listsList = Arrays.asList(list1, list2, list3);

        printLists(listsList);
        printFlattenedLists(listsList);
    }

    private static void printLists(final List<List<Integer>> listsList) {

        final Function<List<?>, String> description = l -> "Items: " + l.toString() + ", Size: " + l.size();

        listsList.stream()
                .map(description)
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }

    private static void printFlattenedLists(final List<List<Integer>> listsList) {

        final Function<List<Integer>, Stream<Integer>> flatMapper = l -> l.stream();

        listsList.stream()
                .map(flatMapper)
                .forEach(System.out::println);

        System.out.println("Each element in a flattened disposition:");

        listsList.stream()
                .flatMap(flatMapper)
                .forEach(System.out::println);
    }
}
