package core.project.estate.application.dto;

import core.project.estate.domain.enums.City;
import core.project.estate.domain.enums.District;
import core.project.estate.domain.enums.ListingOwnership;
import core.project.estate.domain.enums.RealStateType;

import java.util.Set;

public record EStateForm(String title,
                         String description,
                         double price,
                         double latitude,
                         double longitude,
                         String ownerFirstName,
                         String ownerLastName,
                         String ownerPhoneNumber,
                         ListingOwnership ownerType,
                         RealStateType realEstateType,
                         Set<String> imageUrls,
                         City city,
                         District district,
                         double area,
                         boolean isCixariw,
                         boolean isRenovated,
                         byte numberOfRooms,
                         byte totalFloors,
                         byte floor) {
}
