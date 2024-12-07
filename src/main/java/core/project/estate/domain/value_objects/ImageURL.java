package core.project.estate.domain.value_objects;

import java.util.Objects;

public record ImageURL(String url) {

    private static final int MAX_URL_LENGTH = 2000;

    public ImageURL {
        Objects.requireNonNull(url);

        if (url.length() > MAX_URL_LENGTH) {
            throw new IllegalArgumentException("Image URL is too long (max %d characters).".formatted(MAX_URL_LENGTH));
        }
    }
}
