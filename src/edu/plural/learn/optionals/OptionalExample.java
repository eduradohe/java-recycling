package edu.plural.learn.optionals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalExample {

    private static Optional<Integer> maxOnOptionalEmptyList() {
        final List<Integer> list = Arrays.asList();
        return list.stream().max(Integer::max);
    }

    private static Optional<Integer> maxOnOptionalNegativeElementsList() {
        final List<Integer> list = Arrays.asList(-10);
        return list.stream().max(Integer::max);
    }

    private static Optional<Integer> maxOnOptionalPositiveElementsList() {
        final List<Integer> list = Arrays.asList(10);
        return list.stream().max(Integer::max);
    }

    private static Integer maxOnNegativeAndPositiveElementsList() {
        final List<Integer> list = Arrays.asList(-10, 20);
        return list.stream().reduce(0, Integer::max);
    }

    private static Integer maxOnNegativeElementsList() {
        final List<Integer> list = Arrays.asList(-10, -20);
        return list.stream().reduce(0, Integer::max);
    }

    private static Integer maxOnTwoElementsList() {
        final List<Integer> list = Arrays.asList(10, 20);
        return list.stream().reduce(0, Integer::max);
    }

    private static Integer maxOnOneElementList() {
        final List<Integer> list = Arrays.asList(10);
        return list.stream().reduce(0, Integer::max);
    }

    private static Integer maxOnEmptyList() {
        final List<Integer> list = Arrays.asList();
        return list.stream().reduce(0, Integer::max);
    }

    public static void main(String[] args) {

        final List objects = new ArrayList();

        objects.add("Expects 0:");
        objects.add(maxOnEmptyList());
        objects.add("Expects 10:");
        objects.add(maxOnOneElementList());
        objects.add("Expects 20:");
        objects.add(maxOnTwoElementsList());
        objects.add("Expects 0:");
        objects.add(maxOnNegativeElementsList());
        objects.add("Expects 20:");
        objects.add(maxOnNegativeAndPositiveElementsList());
        objects.add("Expects Optional[10]:");
        objects.add(maxOnOptionalPositiveElementsList());
        objects.add("Expects Optional[-10]:");
        objects.add(maxOnOptionalNegativeElementsList());
        objects.add("Expects Optional.empty:");
        objects.add(maxOnOptionalEmptyList());

        objects.forEach(System.out::println);
    }
}
