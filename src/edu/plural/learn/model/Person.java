package edu.plural.learn.model;

import edu.plural.learn.util.GenericBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Person {

    private String name;
    private LocalDate birthday;
    private String nickname;
    private PersonGender gender;

    public Person() {
        super();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
    }

    public PersonGender getGender() {
        return gender;
    }

    public Integer getAge() {
        return this.calculateAge();
    }

    public void setBirthday( LocalDate birthday ) {
        this.birthday = birthday;
    }

    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * For copy purpose
     * @param person Person to be copied
     */
    public void setPerson(final Person person) {
        this.setName(person.getName());
        this.setBirthday(person.getBirthday());
        this.setNickname(person.getNickname());
        this.setGender(person.getGender());
    }

    /**
     * Copies this object into a new instance
     * @return this copied into a new instance
     */
    public Person copy() {
        return GenericBuilder
                .of(Person::new)
                .with(Person::setPerson, this)
                .build();
    }

    /**
     * Calculates the current age measure by the difference in years from birthday until now.
     * @return The difference in years from birthday until now.
     */
    private Integer calculateAge() {

        final LocalDate now = LocalDate.now();

        return Long.valueOf(
                this.birthday.until(now).get(ChronoUnit.YEARS)
        ).intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (!getName().equals(person.getName())) return false;
        if (!getBirthday().equals(person.getBirthday())) return false;
        if (getNickname() != null ? !getNickname().equals(person.getNickname()) : person.getNickname() != null)
            return false;
        return getGender() == person.getGender();
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getBirthday().hashCode();
        result = 31 * result + (getNickname() != null ? getNickname().hashCode() : 0);
        result = 31 * result + getGender().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + this.getAge() +
                ", birthday=" +
                    birthday.get(ChronoField.DAY_OF_MONTH) + "-" +
                    birthday.get(ChronoField.MONTH_OF_YEAR) + "-" +
                    birthday.get(ChronoField.YEAR) +
                ", nickname=" + nickname +
                ", gender=" + gender.getDescription() +
                '}';
    }
}
