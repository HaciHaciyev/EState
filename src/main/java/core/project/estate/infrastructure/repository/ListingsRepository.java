package core.project.estate.infrastructure.repository;

import core.project.estate.domain.entities.EStateListing;
import core.project.estate.domain.enums.City;
import core.project.estate.domain.enums.District;
import core.project.estate.domain.enums.ListingOwnership;
import core.project.estate.domain.enums.RealStateType;
import core.project.estate.domain.value_objects.*;
import core.project.estate.infrastructure.util.containers.Pair;
import core.project.estate.infrastructure.util.containers.Result;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Transactional
@ApplicationScoped
public class ListingsRepository {

    private final JDBCWrap jdbc;

    private static final String SAVE_LISTING = """
            INSERT INTO EState (
                id, title, description, price, latitude, longitude,
                owner_full_name_first, owner_full_name_last, owners_phone_number, owner_type,
                state_type, city, district, area, is_cixariw, is_renovated,
                number_of_rooms, total_floors, floor_of_listing, images
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

    ListingsRepository(JDBCWrap jdbc) {
        this.jdbc = jdbc;
    }

    public void save(EStateListing listing) {
        final byte arrayIndex = 20;
        final String arrayDefinition = "TEXT";
        jdbc.writeArrayOf(SAVE_LISTING, arrayDefinition, arrayIndex, listing.getImages(),
                listing.getId().toString(), listing.getTitle().title(), listing.getDescription().description(), listing.getPrice().price(),
                listing.getCoordinate().latitude(), listing.getCoordinate().longitude(),
                listing.getOwnerFullName().firstName(), listing.getOwnerFullName().lastName(),
                listing.getOwnersPhoneNumber().phoneNumber(), listing.getOwner().toString(),
                listing.getStateType().toString(), listing.getCity().toString(), listing.getDistrict().toString(),
                listing.getArea().valueInSquareMeters(), listing.isCixariw(), listing.isRenovated(),
                listing.getNumberOfRooms(), listing.getFloor().totalNumberOfFloors(), listing.getFloor().floorOfTheListening()
        );
    }

    public Result<List<EStateListing>, Throwable> listOfListings(SearchFilters searchFilters) {
        Pair<String, List<Object>> pair = buildFilteredQuery(searchFilters);
        String sql = pair.getFirst();
        List<Object> params = pair.getSecond();

        return jdbc.readListOf(sql, this::eStateListingMapper, params.toArray());
    }

    public static Pair<String, List<Object>> buildFilteredQuery(SearchFilters searchFilters) {
        StringBuilder query = new StringBuilder("SELECT * FROM ESTATE WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (searchFilters.city() != null) {
            query.append(" AND city = ?");
            parameters.add(searchFilters.city());
        }
        if (searchFilters.district() != null) {
            query.append(" AND district = ?");
            parameters.add(searchFilters.district());
        }
        if (searchFilters.minPrice() != null) {
            query.append(" AND price >= ?");
            parameters.add(searchFilters.minPrice());
        }
        if (searchFilters.maxPrice() != null) {
            query.append(" AND price <= ?");
            parameters.add(searchFilters.maxPrice());
        }
        if (searchFilters.minArea() != null) {
            query.append(" AND area >= ?");
            parameters.add(searchFilters.minArea());
        }
        if (searchFilters.maxArea() != null) {
            query.append(" AND area <= ?");
            parameters.add(searchFilters.maxArea());
        }
        if (searchFilters.minLandArea() != null) {
            query.append(" AND land_area >= ?");
            parameters.add(searchFilters.minLandArea());
        }
        if (searchFilters.maxLandArea() != null) {
            query.append(" AND land_area <= ?");
            parameters.add(searchFilters.maxLandArea());
        }
        if (searchFilters.numberOfRooms() != null) {
            query.append(" AND number_of_rooms = ?");
            parameters.add(searchFilters.numberOfRooms());
        }
        if (searchFilters.numberOfFloors() != null) {
            query.append(" AND total_floors = ?");
            parameters.add(searchFilters.numberOfFloors());
        }
        if (searchFilters.floor() != null) {
            query.append(" AND floor_of_listing = ?");
            parameters.add(searchFilters.floor());
        }
        if (searchFilters.isCixariw() != null) {
            query.append(" AND is_cixariw = ?");
            parameters.add(searchFilters.isCixariw());
        }

        query.append(" LIMIT ? OFFSET ?");
        parameters.add(searchFilters.pageSize());
        parameters.add((searchFilters.pageNumber() - 1) * searchFilters.pageSize());

        Log.infof("Generated SQL: {%s}, with parameters: {%s}", query.toString(), parameters);
        return Pair.of(query.toString(), parameters);
    }

    private EStateListing eStateListingMapper(final ResultSet rs) throws SQLException {
        String[] imageURLs = (String[]) rs.getArray("images").getArray();
        Set<ImageURL> setOfImageURLs = new HashSet<>();
        for (String imageURL : imageURLs) {
            setOfImageURLs.add(new ImageURL(imageURL));
        }

        return new EStateListing(
                UUID.fromString(rs.getString("id")),
                new Title(rs.getString("title")),
                new Description(rs.getString("description")),
                new Price(rs.getDouble("price")),
                new Coordinate(rs.getDouble("latitude"), rs.getDouble("longitude")),
                new FullName(rs.getString("owner_full_name_first"), rs.getString("owner_full_name_last")),
                new Phone(rs.getString("owners_phone_number")),
                ListingOwnership.valueOf(rs.getString("owner_type")),
                RealStateType.valueOf(rs.getString("state_type")),
                setOfImageURLs,
                City.valueOf(rs.getString("city")),
                District.valueOf(rs.getString("district")),
                new Area(rs.getDouble("area")),
                rs.getBoolean("is_cixariw"),
                rs.getBoolean("is_renovated"),
                rs.getInt("number_of_rooms"),
                new Floors((byte) rs.getInt("total_floors"), (byte) rs.getInt("floor_of_listing"))
        );
    }
}
