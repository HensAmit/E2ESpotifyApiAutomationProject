package com.spotifyapi.tests;

import com.spotifyapi.pojo.Error;
import com.spotifyapi.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotifyapi.api.SpecBuilder.getRequestSpecification;
import static com.spotifyapi.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {

    @Test
    public void createPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2")
                .setDescription("My 1st playlist")
                .setPublic(false);

        Playlist responsePlaylist = given(getRequestSpecification())
                .body(requestPlaylist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(getResponseSpecification())
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

        Playlist responsePlaylist = given(getRequestSpecification())
                .pathParam("playlistId", "3RE2lEzzHhPqdyG56HPRva")
                .when()
                .get("playlists/{playlistId}")
                .then()
                .spec(getResponseSpecification())
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

        given(getRequestSpecification())
                .pathParam("playlistId", "3RE2lEzzHhPqdyG56HPRva")
                .body(requestPlaylist)
                .when()
                .put("playlists/{playlistId}")
                .then()
                .spec(getResponseSpecification())
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("Playlist Nil")
                .setPublic(false);

        Error error = given(getRequestSpecification())
                .body(requestPlaylist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(getResponseSpecification())
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
                .spec(getResponseSpecification())
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
