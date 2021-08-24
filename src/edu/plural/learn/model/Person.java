package edu.plural.learn.model;

import edu.plural.learn.util.GenericBuilder;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Person {

    private String name;
    private LocalDate birthday;
    private Integer age;
    private String nickname;

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

    public Integer getAge() {
        return age;
    }

    public void setAge( Integer age) {
        if (this.age == null) {
            this.age = age;
        }
    }

    public void setBirthday( LocalDate birthday ) {
        this.birthday = birthday;
        this.age = Long.valueOf(this.birthday.until(LocalDate.now()).get(ChronoUnit.YEARS)).intValue();
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
        this.setAge(person.getAge());
        this.setBirthday(person.getBirthday());
        this.setNickname(person.getNickname());
    }

    /**
     * Copies this object into a new instance
     * @return this copied into a new instance
     */
    public Person copy() {
        return GenericBuilder.of(Person::new).with(Person::setPerson, this).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null) {
            return false;
        }

        return (getAge() == null || person.getAge() == null) ? true : getAge().equals(person.getAge());
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getAge() != null ? getAge().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" +
                    birthday.get(ChronoField.DAY_OF_MONTH) + "-" +
                    birthday.get(ChronoField.MONTH_OF_YEAR) + "-" +
                    birthday.get(ChronoField.YEAR) +
                ", nickname=" + nickname +
                '}';
    }
}
