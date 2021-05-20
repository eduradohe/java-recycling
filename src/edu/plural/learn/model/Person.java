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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (getName() != null ? !getName().equals(person.getName()) : person.getName() != null){
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
                '}';
    }
}
