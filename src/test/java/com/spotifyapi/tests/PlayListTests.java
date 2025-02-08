package com.spotifyapi.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PlayListTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String accessToken = "Bearer BQACgHOTNyiTStenODglTOH9ovmp6Rkl7_rDBZt8DoRAnyPVp44FythsOXaCps0Vd0tjwoINS7cwhWz2UVlW5zh5U-HK7IV21lkKVnrWeJC8rDmFU0IoookMPp75yTZRllMrM47Yxw_CkUN6Xmbn2VUPyu3F-QFt7-1FhuRj0h1mW1MwB17uEH_LAwhStWAp1nvfkg-JKK7kbK_uBRHFRh5PKZflbiLWyr3XHCKW1ebOdNBVa7X7GWysxZflF2YJLGVNfyPIa2vT4MBzJwPKQMTWaTxDSEwJMGgTGAYIA1h7u-Dd-5Ps";

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void createPlaylist() {
        String payLoad = "{\n" +
                "  \"name\": \"Test Playlist 1\",\n" +
                "  \"description\": \"My 1st playlist\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payLoad)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Test Playlist 1"),
                        "description", equalTo("My 1st playlist"),
                        "public", equalTo(false));
    }

    @Test
    public void getPlaylist() {
        given(requestSpecification)
                .pathParam("playlistId", "7mMh0qREnrVdt6JzwP5VUI")
                .when()
                .get("playlists/{playlistId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", equalTo("Test Playlist 1"),
                        "description", equalTo("My 1st playlist"),
                        "public", equalTo(true));
    }

    @Test
    public void updatePlaylist() {
        String payLoad = "{\n" +
                "  \"name\": \"Test Playlist 1 updated\",\n" +
                "  \"description\": \"My 1st playlist updated\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .pathParam("playlistId", "7mMh0qREnrVdt6JzwP5VUI")
                .body(payLoad)
                .when()
                .put("playlists/{playlistId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        String payLoad = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"Playlist Nil\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payLoad)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .body("error.status", equalTo(400),
                        "error.message", equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken() {
        String payLoad = "{\n" +
                "  \"name\": \"Test Playlist invalid token\",\n" +
                "  \"description\": \"Invalid token playlist\",\n" +
                "  \"public\": false\n" +
                "}";
        given().baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer 12345")
                .contentType(ContentType.JSON)
                .log().all()
                .body(payLoad)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
