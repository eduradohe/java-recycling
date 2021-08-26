package edu.plural.learn.model;

import java.util.Arrays;

/**
 * Person gender enumerator
 */
public enum PersonGender {
    FEMININE('F', "Feminine"),
    MASCULINE('M', "Masculine"),
    UNDEFINED('U', "Undefined");

    private Character code;
    private String description;

    PersonGender(final Character code, final String description) {
        this.code = code;
        this.description = description;
    }

    public Character getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Look up for a Person Gender of specified code
     *
     * @param code The code to look up
     * @return The correspondent Person Gender
     */
    public static PersonGender of(final Character code) {
        return Arrays.stream(PersonGender.values())
                .filter(pg -> pg.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * Look up for a Person Gender of specified description
     *
     * @param description The description to look up
     * @return The correspondent Person Gender
     */
    public static PersonGender of(final String description) {
        return Arrays.stream(PersonGender.values())
                .filter(pg -> pg.getDescription().equals(description))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "PersonGender{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
