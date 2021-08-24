package edu.plural.learn.util;

import edu.plural.learn.model.Person;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Encapsulates default person data mass and provides methods for accessing it as an immutable list, in which
 * this method will provide copies of the elements on original list to be used and changed safely as the
 * original list will be kept and available for generating new copies at will
 */
public class PersonsFile {

    public static final String PERSON_RAFAL = "Rafal";

    private static final String FILE_PATH = "persons.csv";

    private static PersonsFile instance;

    private final List<Person> persons;

    private PersonsFile() {
        super();
        final Stream<String> stream = this.readFile();
        this.persons = stream.map(this.mapper()).collect(Collectors.toList());
    }

    private Function<String, Person> mapper() {
        return row -> {

            final String[] fields = row.split(",");
            final String name = fields[0];
            final String birthday = fields[1];

            final String[] dateFields = birthday.split(" ");

            return GenericBuilder
                    .of(Person::new)
                    .with(Person::setName, name)
                    .with(Person::setBirthday,
                            LocalDate.of(
                                    Integer.valueOf(dateFields[2]),
                                    Integer.valueOf(dateFields[1]),
                                    Integer.valueOf(dateFields[0])
                            )
                    )
                    .build();
        };
    }

    private Stream<String> readFile() {
        final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        PersonsFile.class.getResourceAsStream(FILE_PATH)
                )
        );

        return reader.lines();
    }

    /**
     * Returns the instance of singleton PersonList
     *
     * @return the instance of singleton PersonList
     */
    public static PersonsFile getInstance() {
        if (instance == null) {
            synchronized(PersonsFile.class) {
                if (instance == null) {
                    instance = new PersonsFile();
                }
            }
        }

        return instance;
    }

    /**
     * Encapsulates list with all persons ensuring original list will be kept unchanged if another method is changing it.
     *
     * @return Copy of default data mass of Persons
     */
    public List<Person> getPersons() {
        final List<Person> copiedList = new ArrayList<>();
        this.persons.stream().map(Person::copy).forEach(copiedList::add);

        return copiedList;
    }

    /**
     * Encapsulates persons list ensuring original list will be kept unchanged if another method is changing it.
     *
     * This method brings a list of 1 person present at index 0.
     *
     * @return Copy of 1st element on default data mass of Persons
     */
    public List<Person> getOnePersonCopiedList() {
        return this.getOnePersonCopiedList(0);
    }

    /**
     * Encapsulates person list ensuring original list will be kept unchanged if another method is changing it.
     *
     * This method brings a list of 1 person whose name equals the name passed as parameter.
     *
     * @param name Name of the Person to be searched
     * @return Copy of person list containing element whose name equals the name passed as parameter
     */
    public List<Person> getOnePersonCopiedList( final String name ) {

        final Person requiredPerson = GenericBuilder.of(Person::new).with(Person::setName, name).build();
        final Integer position = this.persons.indexOf(requiredPerson);

        return this.getOnePersonCopiedList(position);
    }

    /**
     * Makes sure the position is within the size of the list
     *
     * @param position Position of element to be taken from the list
     * @return The index related to position; 0; or the index of last element on the list
     */
    private Integer restrainIndex(final Integer position) {
        final List<Integer> minIndexes = Arrays.asList(Optional.ofNullable(position).orElse(0), 0);
        Integer index = minIndexes.stream().max(Comparator.naturalOrder()).orElse(0);
        final List<Integer> maxIndexes = Arrays.asList(index, Integer.valueOf(this.persons.size() - 1));
        return maxIndexes.stream().min(Comparator.naturalOrder()).orElse(0);
    }

    /**
     * Encapsulates persons list ensuring original list will be kept unchanged if another method is changing it.
     *
     * @param position Position identifier in the list
     * @return Copy of 1st element on default data mass of Persons
     */
    public List<Person> getOnePersonCopiedList( final Integer position ) {
        return Collections.singletonList(
                GenericBuilder
                        .of(Person::new)
                        .with(Person::setPerson, this.getPersons().get(restrainIndex(position)))
                        .build()
        );
    }
}