package core.project.estate.domain.value_objects;

public record Area(double valueInSquareMeters) {

    public Area {
        if (valueInSquareMeters <= 0) {
            throw new IllegalArgumentException("Area must be positive.");
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f m²", valueInSquareMeters);
    }
}
