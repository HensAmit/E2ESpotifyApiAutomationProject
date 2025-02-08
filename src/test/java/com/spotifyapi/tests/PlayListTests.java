package com.spotifyapi.tests;

import com.spotifyapi.pojo.Error;
import com.spotifyapi.pojo.Playlist;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    String accessToken = "Bearer BQCd_lAI9wSLqSQlYTPcPJyILjF4Ex8Wx3PrFXC9GPxXQ8QBPIhCWmbaGw5QXugD5mwmtN8I4dBxTq12MyumLikugQQsmARzIu7ri7dygzrzCM9PpPTf5xye-J4SgbpSjvAbqfo7n6j2NVwD3Igr7RM5jODmF5hB3pBepcFWA47T8IWZiHmSoK6RqHS7UYvnsGgxD_8gszc7bnkqq3xYGak7oUTbK4RQnQoNlOXmNR-4zrY9hwNPkEKjT_XE2G9gQp58XFW5DT1_WJ60MBjQ-R_gxGIlvIL9czZhAWHA-1uubQe7WaXK";

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
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2")
                .setDescription("My 1st playlist")
                .setPublic(false);

        Playlist responsePlaylist = given(requestSpecification)
                .body(requestPlaylist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void getPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2")
                .setDescription("My 1st playlist")
                .setPublic(true);

        Playlist responsePlaylist = given(requestSpecification)
                .pathParam("playlistId", "3RE2lEzzHhPqdyG56HPRva")
                .when()
                .get("playlists/{playlistId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void updatePlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2 updated")
                .setDescription("My 2nd playlist")
                .setPublic(false);

        given(requestSpecification)
                .pathParam("playlistId", "3RE2lEzzHhPqdyG56HPRva")
                .body(requestPlaylist)
                .when()
                .put("playlists/{playlistId}")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("Playlist Nil")
                .setPublic(false);

        Error error = given(requestSpecification)
                .body(requestPlaylist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist invalid token")
                .setDescription("Invalid token playlist")
                .setPublic(false);

        Error error = given().baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer 12345")
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestPlaylist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .contentType(ContentType.JSON)
                .extract()
                .response()
                .as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
