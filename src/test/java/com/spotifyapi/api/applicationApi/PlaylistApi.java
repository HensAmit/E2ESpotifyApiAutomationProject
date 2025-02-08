package com.spotifyapi.api.applicationApi;

import com.spotifyapi.api.RestResourceApi;
import com.spotifyapi.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotifyapi.api.TokenManager.getToken;

public class PlaylistApi {

    public static Response post(Playlist playlist) {
        return RestResourceApi.post(playlist, getToken(), "users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists");
    }

    public static Response post(Playlist playlist, String token) {
        return RestResourceApi.post(playlist, token, "users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists");
    }

    public static Response get(String playlistId) {
        return RestResourceApi.get(getToken(), "playlists/" + playlistId);
    }

    public static Response update(Playlist playlist, String playlistId) {
        return RestResourceApi.update(playlist, getToken(), "playlists/" + playlistId);
    }
}
