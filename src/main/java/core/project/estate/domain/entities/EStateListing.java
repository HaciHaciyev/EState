package core.project.estate.domain.entities;

import core.project.estate.domain.enums.City;
import core.project.estate.domain.enums.District;
import core.project.estate.domain.value_objects.*;
import core.project.estate.domain.enums.ListingOwnership;
import core.project.estate.domain.enums.RealStateType;
import core.project.estate.domain.value_objects.Area;

import lombok.Getter;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
public class EStateListing {
    private final UUID id;
    private Title title;
    private Description description;
    private final Price price;
    private final Coordinate coordinate;
    private final FullName ownerFullName;
    private final Phone ownersPhoneNumber;
    private final ListingOwnership owner;
    private final RealStateType stateType;
    private final Set<ImageURL> images;
    private final City city;
    private final District district;
    private final Area area;
    private final boolean isCixariw;
    private final boolean isRenovated;
    private final int numberOfRooms;
    private final Floors floor;

    public EStateListing(UUID id, Title title, Description description, Price price, Coordinate coordinate,
                         FullName ownerFullName, Phone ownersPhoneNumber, ListingOwnership owner,
                         RealStateType stateType, Set<ImageURL> images, City city, District district, Area area,
                         boolean isCixariw, boolean isRenovated, int numberOfRooms, Floors floor) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(price);
        Objects.requireNonNull(coordinate);
        Objects.requireNonNull(ownerFullName);
        Objects.requireNonNull(ownersPhoneNumber);
        Objects.requireNonNull(owner);
        Objects.requireNonNull(stateType);
        Objects.requireNonNull(images);
        Objects.requireNonNull(city);
        Objects.requireNonNull(district);
        Objects.requireNonNull(area);
        Objects.requireNonNull(floor);

        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.coordinate = coordinate;
        this.ownerFullName = ownerFullName;
        this.ownersPhoneNumber = ownersPhoneNumber;
        this.owner = owner;
        this.stateType = stateType;
        this.images = images;
        this.city = city;
        this.district = district;
        this.area = area;
        this.isCixariw = isCixariw;
        this.isRenovated = isRenovated;
        this.numberOfRooms = numberOfRooms;
        this.floor = floor;
    }

    public void changeTitle(Title title) {
        Objects.requireNonNull(title);
        this.title = title;
    }

    public void changeDescription(Description description) {
        Objects.requireNonNull(description);
        this.description = description;
    }

    public String[] getImages() {
        return images.stream().map(ImageURL::toString).toArray(String[]::new);
    }
}
