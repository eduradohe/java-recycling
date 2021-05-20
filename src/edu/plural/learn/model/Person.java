package edu.plural.learn.model;

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
