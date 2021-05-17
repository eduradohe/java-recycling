package edu.plural.learn.lambdas;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComparatorLambda {

    private static Comparator<String> createAnonymousComparator() {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };
    }

    private static Comparator<String> createLambdaComparator() {
        return (s1, s2) -> Integer.compare(s2.length(), s1.length());
    }

    private static List<String> createStringList() {
        return Arrays.asList("***", "**", "****", "*");
    }

    private static void printList( final List<String> strings ) {
        strings.forEach(System.out::println);
    }

    public static void main(String[] args) {
        final Comparator<String> ascComparator = createAnonymousComparator();
        final Comparator<String> dscComparator = createLambdaComparator();

        final List<String> list1 = createStringList();
        final List<String> list2 = createStringList();

        Collections.sort(list1, ascComparator);
        Collections.sort(list2, dscComparator);

        printList(list1);
        printList(list2);
    }
}
