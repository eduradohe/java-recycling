package edu.plural.learn.util;

import edu.plural.learn.model.Person;

import java.util.*;

/**
 * Encapsulates default person data mass and provides methods for accessing it as an immutable list, in which
 * this method will provide copies of the elements on original list to be used and changed safely as the
 * original list will be kept and available for generating new copies at will
 */
public class PersonList {

    public static final String PERSON_EDUARDO = "Eduardo";
    public static final String PERSON_KAROLINA = "Karolina";
    public static final String PERSON_RAFAL = "Rafal";
    public static final String PERSON_RAFAELA = "Rafaela";
    public static final String PERSON_THIAGO = "Thiago";

    private static PersonList instance;

    private final List<Person> persons;

    private PersonList() {
        super();
        this.persons = Arrays.asList(
                GenericBuilder.of(Person::new).with(Person::setName, PERSON_EDUARDO).with(Person::setAge, 33).build(),
                GenericBuilder.of(Person::new).with(Person::setName, PERSON_KAROLINA).with(Person::setAge, 30).build(),
                GenericBuilder.of(Person::new).with(Person::setName, PERSON_RAFAL).with(Person::setAge, 8).build(),
                GenericBuilder.of(Person::new).with(Person::setName, PERSON_RAFAELA).with(Person::setAge, 18).build(),
                GenericBuilder.of(Person::new).with(Person::setName, PERSON_THIAGO).with(Person::setAge, 25).build()
        );
    }

    /**
     * Returns the instance of singleton PersonList
     *
     * @return the instance of singleton PersonList
     */
    public static PersonList getInstance() {
        if (instance == null) {
            synchronized(PersonList.class) {
                if (instance == null) {
                    instance = new PersonList();
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
