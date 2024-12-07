package core.project.estate.domain.value_objects;

import java.util.Objects;

public record Title(String title) {

    public Title {
        Objects.requireNonNull(title);
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be blank");
        }

        if (title.length() < 25 || title.length() > 255) {
            throw new IllegalArgumentException("Title length must be between 25 and 255 characters.");
        }
    }
}
