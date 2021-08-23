package edu.plural.learn.optionals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionalExample {

    private static void maxOnOptionalEmptyList(final List result) {

        result.add("Expects Optional.empty:");
        
        final List<Integer> list = Arrays.asList();
        result.add(list.stream().max(Integer::max));
    }

    private static void maxOnOptionalNegativeElementsList(final List result) {

        result.add("Expects Optional[-10]:");
        
        final List<Integer> list = Arrays.asList(-10);
        result.add(list.stream().max(Integer::max));
    }

    private static void maxOnOptionalPositiveElementsList(final List result) {

        result.add("Expects Optional[10]:");
        
        final List<Integer> list = Arrays.asList(10);
        result.add(list.stream().max(Integer::max));
    }

    private static void maxOnNegativeAndPositiveElementsList(final List result) {

        result.add("Expects 20:");
        
        final List<Integer> list = Arrays.asList(-10, 20);
        result.add(list.stream().reduce(0, Integer::max));
    }

    private static void maxOnNegativeElementsList(final List result) {

        result.add("Expects 0:");
        
        final List<Integer> list = Arrays.asList(-10, -20);
        result.add(list.stream().reduce(0, Integer::max));
    }

    private static void maxOnTwoElementsList(final List result) {

        result.add("Expects 20:");
        
        final List<Integer> list = Arrays.asList(10, 20);
        result.add(list.stream().reduce(0, Integer::max));
    }

    private static void maxOnOneElementList(final List result) {

        result.add("Expects 10:");
        
        final List<Integer> list = Arrays.asList(10);
        result.add(list.stream().reduce(0, Integer::max));
    }

    private static void maxOnEmptyList(final List result) {

        result.add("Expects 0:");
        
        final List<Integer> list = Arrays.asList();
        result.add(list.stream().reduce(0, Integer::max));
    }

    public static void main(String[] args) {

        final List result = new ArrayList<>();
        
        maxOnEmptyList(result);
        maxOnOneElementList(result);
        maxOnTwoElementsList(result);
        maxOnNegativeElementsList(result);
        maxOnNegativeAndPositiveElementsList(result);
        maxOnOptionalPositiveElementsList(result);
        maxOnOptionalNegativeElementsList(result);
        maxOnOptionalEmptyList(result);

        result.forEach(System.out::println);
    }
}
