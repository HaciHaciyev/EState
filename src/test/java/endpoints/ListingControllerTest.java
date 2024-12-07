package endpoints;

import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

@QuarkusTest
class ListingControllerTest {

    @Test
    void createAndGetListing() {
        given()
                .contentType("application/json")
                .body("""
                {
                  "title": "Cozy Apartment in Downtown",
                  "description": "A beautiful 2-bedroom apartment located in the heart of the city.",
                  "price": 250000.00,
                  "latitude": 40.7128,
                  "longitude": -74.0060,
                  "ownerFirstName": "John",
                  "ownerLastName": "Doe",
                  "ownerPhoneNumber": "+994 55 214 23 54",
                  "ownerType": "OWNER",
                  "realEstateType": "APARTMENT",
                  "imageUrls": [
                    "http://example.com/image1.jpg",
                    "http://example.com/image2.jpg"
                  ],
                  "city": "BAKU",
                  "district": "NARIMANOV",
                  "area": 85.5,
                  "isCixariw": true,
                  "isRenovated": false,
                  "numberOfRooms": 3,
                  "totalFloors": 10,
                  "floor": 5
                }
                """)
                .when()
                .post("listings/save")
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        final String result = when()
                .get("listings/pageOfListings?pageNumber=1&pageSize=1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asPrettyString();

        Log.info(result);
    }
}
