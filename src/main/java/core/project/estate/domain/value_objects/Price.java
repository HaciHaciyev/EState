package core.project.estate.domain.value_objects;

public record Price(double price) {

    public Price {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
    }
}
