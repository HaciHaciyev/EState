Quick Setup Guide for Docker Compose

This guide will help you set up your application with Docker Compose, PostgreSQL, and optionally pgAdmin.
Step 1: Create Environment Files

Prepare the following environment files in the root of your project:
1. infrastructure.env

# PostgreSQL Configuration
POSTGRES_USER=exampleusername
POSTGRES_URL=jdbc:postgresql://localhost:5832/exampledbname
POSTGRES_PASSWORD=examplepassword
POSTGRES_DB=exampledbname

# PostgreSQL Data Directory
PGDATA=/data/postgres

2. pgadmin.env (Optional)

# pgAdmin Configuration
PGADMIN_DEFAULT_EMAIL=example@domain.com
PGADMIN_DEFAULT_PASSWORD=examplepassword

    Note: If you do not need pgAdmin, you can skip creating the pgadmin.env file and remove the corresponding service from your docker-compose.yaml.

Step 2: Build and Run the Application

    Install dependencies and build the application:

mvn install

Start the services using Docker Compose:

    docker-compose up -d --build

---

# Listings API Documentation

## Base Path: `/listings`

### 1. **Save a Listing**

#### Endpoint:
`POST /listings/save`

#### Description:
Creates a new real estate listing.

#### Request Body:

```json
{
  "title": "string",
  "description": "string",
  "price": 0.0,
  "latitude": 0.0,
  "longitude": 0.0,
  "ownerFirstName": "string",
  "ownerLastName": "string",
  "ownerPhoneNumber": "string",
  "ownerType": "ListingOwnership",
  "realEstateType": "RealStateType",
  "imageUrls": ["string"],
  "city": "City",
  "district": "District",
  "area": 0.0,
  "isCixariw": true,
  "isRenovated": true,
  "numberOfRooms": 0,
  "totalFloors": 0,
  "floor": 0
}
```

#### Response:
- **201 Created**: The listing has been successfully created.
- **400 Bad Request**: Invalid input or `EStateForm` is `null`.

#### Example Error Response:

```json
{
  "error": "EStateForm can’t be null."
}
```

---

### 2. **Get Paginated Listings**

#### Endpoint:
`GET /listings/pageOfListings`

#### Description:
Fetches a paginated list of real estate listings based on the provided filters.

#### Query Parameters:

| Parameter         | Type    | Description                                                                 | Required |
|--------------------|---------|-----------------------------------------------------------------------------|----------|
| `pageNumber`       | `int`   | The page number to retrieve.                                                | Yes      |
| `pageSize`         | `int`   | The number of listings per page.                                            | Yes      |
| `city`             | `string`| The city to filter listings (e.g., `City.NAME`).                            | No       |
| `district`         | `string`| The district to filter listings (e.g., `District.NAME`).                    | No       |
| `minPrice`         | `int`   | Minimum price filter.                                                      | No       |
| `maxPrice`         | `int`   | Maximum price filter.                                                      | No       |
| `minArea`          | `int`   | Minimum area filter.                                                       | No       |
| `maxArea`          | `int`   | Maximum area filter.                                                       | No       |
| `minLandArea`      | `int`   | Minimum land area filter.                                                  | No       |
| `maxLandArea`      | `int`   | Maximum land area filter.                                                  | No       |
| `numberOfRooms`    | `int`   | Filter by the number of rooms.                                             | No       |
| `numberOfFloors`   | `int`   | Filter by the number of floors in the building.                            | No       |
| `floor`            | `int`   | Filter by a specific floor number.                                         | No       |
| `isCixariw`        | `bool`  | Filter listings marked as "Cixariw".                                       | No       |

#### Response:
- **200 OK**: A paginated list of real estate listings.
- **400 Bad Request**: Invalid filter parameters.

#### Example Success Response:

```json
{
  "listings": [
    {
      "title": "Cozy Apartment",
      "price": 120000,
      "city": "City.NAME",
      "district": "District.NAME",
      "area": 75,
      "numberOfRooms": 3,
      "isCixariw": true
    },
    {
      "title": "Spacious Villa",
      "price": 500000,
      "city": "City.NAME",
      "district": "District.NAME",
      "area": 300,
      "numberOfRooms": 7,
      "isCixariw": false
    }
  ],
  "pagination": {
    "currentPage": 1,
    "pageSize": 10,
    "totalPages": 5,
    "totalListings": 50
  }
}
```

#### Example Error Response:

```json
{
  "error": "Invalid value for city: CITY_NAME"
}
```

---

### Notes:

1. **Enums**:
    - `ListingOwnership`: Enum representing ownership types.
    - `RealStateType`: Enum representing real estate types.
    - `City` and `District`: Enumerations of available cities and districts.

2. **Pagination**:
    - Ensure `pageNumber` and `pageSize` are positive integers.
    - Invalid or out-of-range parameters will result in an error response.

3. **Validation**:
    - Filter values such as `city`, `district`, and enums are case-sensitive.

This API documentation provides a comprehensive overview for developers interacting with the `ListingsController`.
