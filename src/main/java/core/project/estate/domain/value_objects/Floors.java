package core.project.estate.domain.value_objects;

public record Floors (byte totalNumberOfFloors, byte floorOfTheListening) {

    public Floors {
        if (totalNumberOfFloors <= 0 || floorOfTheListening > totalNumberOfFloors) {
            throw new IllegalArgumentException("Floors must be between 0 and %s.".formatted(totalNumberOfFloors));
        }
    }
}
