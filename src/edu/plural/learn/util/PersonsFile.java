package edu.plural.learn.util;

import edu.plural.learn.model.Person;
import edu.plural.learn.model.PersonGender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        this.persons = this.readFile();
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
     * Builds a Function mapper to map from a String containing "<Name>,<DD> <MM> <YYY>" to a Person
     * @return The mapper containing the instructions on how to map from the string to the desired object
     */
    private Function<String, Person> getFileToPersonMapper() {
        return row -> {

            final String[] fields = row.split(",");

            final String name = fields[0];
            final String birthday = fields[1];
            final PersonGender gender = PersonGender.of(Character.valueOf(fields[2].charAt(0)));
            final String nickname = fields.length > 3 ? fields[3] : null;

            final String[] dateFields = birthday.split(" ");

            final Integer day = Integer.valueOf(dateFields[0]);
            final Month month = Month.of(Integer.valueOf(dateFields[1]));
            final Integer year = Integer.valueOf(dateFields[2]);

            final Person person = GenericBuilder
                    .of(Person::new)
                    .with(Person::setName, name)
                    .with(Person::setBirthday, LocalDate.of(year, month,day))
                    .with(Person::setNickname, nickname)
                    .with(Person::setGender, gender)
                    .build();

            return person;
        };
    }

    /**
     * Provides a reader for the CSV file containing the list of Person
     * @return The CSV file reader
     */
    private BufferedReader getReader() {
        final InputStream fileInputStream = PersonsFile.class.getResourceAsStream(FILE_PATH);
        final InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        return new BufferedReader(inputStreamReader);
    }

    /**
     * Reads the list of persons from the CSV file into a list
     * @return The list formed from reading the CSV file
     */
    private List<Person> readFile() {

        final Function<String, Person> fileToPersonMapper = this.getFileToPersonMapper();
        final List<Person> persons = new ArrayList<>();

        try ( final BufferedReader reader = this.getReader() ) {
            persons.addAll(reader
                    .lines()
                    .map(fileToPersonMapper)
                    .collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return persons;
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
}
