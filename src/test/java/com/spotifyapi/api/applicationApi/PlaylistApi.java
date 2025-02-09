package com.spotifyapi.api.applicationApi;

import com.spotifyapi.api.RestResourceApi;
import com.spotifyapi.pojo.Playlist;
import com.spotifyapi.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotifyapi.api.TokenManager.getToken;
import static com.spotifyapi.constants.RouteConstants.*;

public class PlaylistApi {

    public static Response post(Playlist playlist) {
        return RestResourceApi.post(playlist, getToken(), USERS + "/" + ConfigLoader.getInstance().getConfigData("user_id") + PLAYLISTS);
    }

    public static Response post(Playlist playlist, String token) {
        return RestResourceApi.post(playlist, token, USERS + "/3133u3fxpnaisnp6inrt3t6fxrvm" + PLAYLISTS);
    }

    public static Response get(String playlistId) {
        return RestResourceApi.get(getToken(), PLAYLISTS + "/" + playlistId);
    }

    public static Response update(Playlist playlist, String playlistId) {
        return RestResourceApi.update(playlist, getToken(), PLAYLISTS + "/" + playlistId);
    }
}
