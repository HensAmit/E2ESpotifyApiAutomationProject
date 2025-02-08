package com.spotifyapi.tests;

import com.spotifyapi.api.applicationApi.PlaylistApi;
import com.spotifyapi.pojo.Error;
import com.spotifyapi.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {

    @Test
    public void createPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 3")
                .setDescription("My 3rd playlist")
                .setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.contentType(), containsString("application/json"));

        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void getPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2 updated")
                .setDescription("My 2nd playlist updated")
                .setPublic(true);

        Response response = PlaylistApi.get("3RE2lEzzHhPqdyG56HPRva");
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.contentType(), containsString("application/json"));

        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void updatePlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist 2 altered")
                .setDescription("My 2nd playlist altered")
                .setPublic(false);

        Response response = PlaylistApi.update(requestPlaylist, "3RE2lEzzHhPqdyG56HPRva");
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = new Playlist()
                .setName("")
                .setDescription("Playlist Nil")
                .setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));
        assertThat(response.contentType(), containsString("application/json"));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithExpiredToken() {
        String invalidToken = "Bearer 12345";

        Playlist requestPlaylist = new Playlist()
                .setName("Test Playlist invalid token")
                .setDescription("Invalid token playlist")
                .setPublic(false);

        Response response = PlaylistApi.post(requestPlaylist, invalidToken);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.contentType(), containsString("application/json"));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
