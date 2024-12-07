package core.project.estate.application.controller;

import core.project.estate.application.dto.EStateForm;
import core.project.estate.domain.enums.City;
import core.project.estate.domain.enums.District;
import core.project.estate.domain.value_objects.SearchFilters;
import core.project.estate.application.service.ListingService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

@Path("/listings")
public class ListingsController {

    private final ListingService listingService;

    ListingsController(ListingService listingService) {
        this.listingService = listingService;
    }

    @POST
    @Path("/save")
    public Response save(EStateForm eStateForm) {
        if (Objects.isNull(eStateForm)) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("EStateForm can`t be null.").build());
        }

        listingService.save(eStateForm);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/pageOfListings")
    public Response pageOfListings(
            @QueryParam("pageNumber") int pageNumber,
            @QueryParam("pageSize") int pageSize,
            @QueryParam("city") String city,
            @QueryParam("district") String district,
            @QueryParam("minPrice") Integer minPrice,
            @QueryParam("maxPrice") Integer maxPrice,
            @QueryParam("minArea") Integer minArea,
            @QueryParam("maxArea") Integer maxArea,
            @QueryParam("minLandArea") Integer minLandArea,
            @QueryParam("maxLandArea") Integer maxLandArea,
            @QueryParam("numberOfRooms") Integer numberOfRooms,
            @QueryParam("numberOfFloors") Integer numberOfFloors,
            @QueryParam("floor") Integer floor,
            @QueryParam("isCixariw") Boolean isCixariw
    ) {
        try {
            SearchFilters searchFilters = new SearchFilters(
                    pageNumber,
                    pageSize,
                    Objects.nonNull(city) ? City.valueOf(city) : null,
                    Objects.nonNull(district) ? District.valueOf(district) : null,
                    minPrice,
                    maxPrice,
                    minArea,
                    maxArea,
                    minLandArea,
                    maxLandArea,
                    numberOfRooms,
                    numberOfFloors,
                    floor,
                    isCixariw
            );

            return Response.ok(listingService.listOfListings(searchFilters)).build();
        } catch (IllegalArgumentException e) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
        }
    }

}
