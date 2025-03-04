package core.project.estate.domain.value_objects;

import java.util.Objects;

public record Description(String description) {

    public Description {
        Objects.requireNonNull(description);
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description should`t be blank.");
        }
        if (description.length() < 5 || description.length() > 255) {
            throw new IllegalArgumentException("Description should be greater than 5 and shorter than 255 characters.");
        }
    }
}