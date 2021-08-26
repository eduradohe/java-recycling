package edu.plural.learn.maps;

import edu.plural.learn.model.Person;
import edu.plural.learn.util.PersonsFile;

import java.util.*;
import java.util.stream.Collectors;

public class MergingMaps {
    public static void main(String[] args) {

        final List<Person> persons = PersonsFile.getInstance().getPersons();

        persons.forEach(System.out::println);

        final List<Person> subList1 = persons.subList(0, 5);
        final List<Person> subList2 = persons.subList(5, persons.size());

        final Map<Integer, List<Person>> map1 = mapByAge(subList1);
        System.out.println("Map1");
        map1.forEach((age, list) -> System.out.println(age + " -> " + list));

        final Map<Integer, List<Person>> map2 = mapByAge(subList2);
        System.out.println("Map2");
        map2.forEach((age, list) -> System.out.println(age + " -> " + list));

        map2.entrySet().stream().forEach(
                entry -> map1.merge(
                        entry.getKey(),
                        entry.getValue(),
                        (l1, l2) -> {
                            l1.addAll(l2);
                            return l1;
                        }
                )
        );

        System.out.println("Map1 after merge");
        map1.forEach((age, list) -> System.out.println(age + " -> " + list));

        final Map<Integer, Map<String, List<Person>>> biMap = new HashMap<>();

        persons.forEach(
                person ->
                        biMap.computeIfAbsent(
                                person.getAge(),
                                HashMap::new
                        ).merge(
                                person.getGender().getCode().toString(),
                                new ArrayList<>(Arrays.asList(person)),
                                (l1,l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                }
                        )
        );

        System.out.println("Bi-Map");
        biMap.forEach((age, m) -> System.out.println(age + " -> " + m));
    }

    private static Map<Integer, List<Person>> mapByAge(final List<Person> persons) {

        final Map<Integer, List<Person>> mapByAge = persons
                .stream()
                .collect(
                        Collectors.groupingBy(Person::getAge)
                );

        return mapByAge;
    }
}
