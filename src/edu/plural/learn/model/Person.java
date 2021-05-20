package edu.plural.learn.model;

import edu.plural.learn.util.GenericBuilder;

public class Person {

    private String name;
    private Integer age;

    public Person() {
        super();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }

    /**
     * For copy purpose
     * @param person Person to be copied
     */
    public void setPerson(final Person person) {
        this.setName(person.getName());
        this.setAge(person.getAge());
    }

    /**
     * Copies this object into a new instance
     * @return this copied into a new instance
     */
    public Person copy() {
        return GenericBuilder.of(Person::new).with(Person::setPerson, this).build();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
