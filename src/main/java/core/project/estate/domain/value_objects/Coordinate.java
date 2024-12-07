package core.project.estate.domain.value_objects;

public record Coordinate(double latitude, double longitude) {

    public Coordinate {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");
        }
    }
}
