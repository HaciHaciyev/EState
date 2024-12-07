package core.project.estate.domain.value_objects;

import core.project.estate.domain.enums.City;
import core.project.estate.domain.enums.District;
import jakarta.annotation.Nullable;

import java.util.Objects;

public record SearchFilters(
        int pageNumber,
        int pageSize,
        @Nullable City city,
        @Nullable District district,
        @Nullable Integer minPrice,
        @Nullable Integer maxPrice,
        @Nullable Integer minArea,
        @Nullable Integer maxArea,
        @Nullable Integer minLandArea,
        @Nullable Integer maxLandArea,
        @Nullable Integer numberOfRooms,
        @Nullable Integer numberOfFloors,
        @Nullable Integer floor,
        @Nullable Boolean isCixariw) {

    public SearchFilters {
        if (pageNumber < 0 || pageSize < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0 and and page size must be greater than 1.");
        }

        validateNonNegative("minPrice", minPrice);
        validateNonNegative("maxPrice", maxPrice);
        validateRange("Price range", minPrice, maxPrice);

        validateNonNegative("minArea", minArea);
        validateNonNegative("maxArea", maxArea);
        validateRange("Area range", minArea, maxArea);

        validateNonNegative("minLandArea", minLandArea);
        validateNonNegative("maxLandArea", maxLandArea);
        validateRange("Land area range", minLandArea, maxLandArea);

        validateNonNegative("numberOfRooms", numberOfRooms);
        validateNonNegative("numberOfFloors", numberOfFloors);
        validateNonNegative("floor", floor);
    }

    private void validateNonNegative(String fieldName, Integer value) {
        if (Objects.nonNull(value) && value < 0) {
            throw new IllegalArgumentException(String.format("%s cannot be negative.", fieldName));
        }
    }

    private void validateRange(String rangeName, Integer minValue, Integer maxValue) {
        if (Objects.nonNull(minValue) && Objects.nonNull(maxValue) && minValue > maxValue) {
            throw new IllegalArgumentException(String.format("%s: minValue cannot be greater than maxValue.", rangeName));
        }
    }
}
