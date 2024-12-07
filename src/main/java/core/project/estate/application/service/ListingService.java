package core.project.estate.application.service;

import core.project.estate.application.dto.EStateForm;
import core.project.estate.domain.entities.EStateListing;
import core.project.estate.domain.value_objects.*;
import core.project.estate.infrastructure.repository.ListingsRepository;
import core.project.estate.infrastructure.util.containers.Result;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class ListingService {

    private final ListingsRepository listingsRepository;

    ListingService(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    public void save(EStateForm eStateForm) {
        Set<ImageURL> listOfImageURLs = new HashSet<>();
        eStateForm.imageUrls().forEach(url -> listOfImageURLs.add(new ImageURL(url)));

        Result<EStateListing, Throwable> listing = Result.ofThrowable(() -> new EStateListing(
                UUID.randomUUID(),
                new Title(eStateForm.title()),
                new Description(eStateForm.description()),
                new Price(eStateForm.price()),
                new Coordinate(eStateForm.latitude(), eStateForm.longitude()),
                new FullName(eStateForm.ownerFirstName(), eStateForm.ownerLastName()),
                new Phone(eStateForm.ownerPhoneNumber()),
                eStateForm.ownerType(),
                eStateForm.realEstateType(),
                listOfImageURLs,
                eStateForm.city(),
                eStateForm.district(),
                new Area(eStateForm.area()),
                eStateForm.isCixariw(),
                eStateForm.isRenovated(),
                eStateForm.numberOfRooms(),
                new Floors(eStateForm.totalFloors(), eStateForm.floor())
        ));
        if (!listing.success()) {
            Log.errorf("Can`t create new EStateListing cause of: %s", listing.throwable());
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Invalid real estate listing.").build());
        }

        listingsRepository.save(listing.orElseThrow());
    }

    public List<EStateListing> listOfListings(SearchFilters searchFilters) {
        Result<List<EStateListing>, Throwable> pageOfListings = listingsRepository.listOfListings(searchFilters);
        if (!pageOfListings.success()) {
            Log.errorf("Can`t get a page of listings cause error: %s", pageOfListings.throwable());
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Invalid search filters.").build());
        }

        return pageOfListings.orElseThrow();
    }
}
