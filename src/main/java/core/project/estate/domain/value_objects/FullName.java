package core.project.estate.domain.value_objects;

import java.util.Objects;

public record FullName(String firstName, String lastName) {

    public FullName {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("FullName must contain at least one character.");
        }

        if (firstName.length() < 2 || firstName.length() > 52) {
            throw new IllegalArgumentException("FullName must contain at least two characters and no more than 52 characters.");
        }

        if (lastName.length() < 2 || lastName.length() > 52) {
            throw new IllegalArgumentException("FullName must contain at least two characters and no more than 52 characters.");
        }
    }
}
