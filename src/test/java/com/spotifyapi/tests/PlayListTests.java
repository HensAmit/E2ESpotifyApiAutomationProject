package com.spotifyapi.tests;

import com.spotifyapi.api.applicationApi.PlaylistApi;
import com.spotifyapi.pojo.Error;
import com.spotifyapi.pojo.Playlist;
import com.spotifyapi.utils.DataLoader;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PlayListTests {

    @Step("Building Playlist object")
    public Playlist playlistBuilder(String name, String description, boolean _public) {
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Description("Creating a new playlist for the user")
    @Test(description = "Create a Playlist")
    public void createPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Test Playlist 3", "My 3rd playlist", false);

        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        assertThat(response.contentType(), containsString("application/json"));

        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Test
    public void getPlaylist() {
        Playlist requestPlaylist = playlistBuilder("Test Playlist 2 altered", "My 2nd playlist altered", true);

        Response response = PlaylistApi.get(DataLoader.getInstance().getPropertyData("playlist_id"));
        assertThat(response.statusCode(), equalTo(200));
        assertThat(response.contentType(), containsString("application/json"));

        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Test
    public void updatePlaylist() {
        Playlist requestPlaylist = playlistBuilder("Test Playlist 2 altered", "My 2nd playlist altered", false);

        Response response = PlaylistApi.update(requestPlaylist, DataLoader.getInstance().getPropertyData("playlist_id"));
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreatePlaylistWithoutName() {
        Playlist requestPlaylist = playlistBuilder("", "Playlist Nil", false);

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

        Playlist requestPlaylist = playlistBuilder("Test Playlist invalid token", "Invalid token playlist", false);

        Response response = PlaylistApi.post(requestPlaylist, invalidToken);
        assertThat(response.statusCode(), equalTo(401));
        assertThat(response.contentType(), containsString("application/json"));

        Error error = response.as(Error.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
    }
}
