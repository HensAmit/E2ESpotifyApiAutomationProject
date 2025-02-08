package com.spotifyapi.api.applicationApi;

import com.spotifyapi.api.RestResourceApi;
import com.spotifyapi.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistApi {
    static String accessToken = "Bearer BQDVtusv5VOPd9PcB1FjUsrEuHmDhk3IXeDb9bvMKCfRW3IqWbZz3Eu-ClR5c10f1Th3AcEgf01NdbmBnPl-fVhd-rJ34DDp8C7-P2s9Vq2cyDfmd3W6paiToTSYF4dXHKQIjXAwmV0VpPqUuEBaMha7TSoU_h2hn7LNy164LFhybvgbe1dZQ2WL3zXSZ4POaCA-MIbMFB7fsw1CXTaZf0z88mjgRMhDOUc_2Jd6HGybBreLDEOy5-_qCpOw2KXhNxKmEFJQlw2m0UNIRefCOo6L2u9uXtp59oHbPlUCbqSbZpcTBsfn";

    public static Response post(Playlist playlist) {
        return RestResourceApi.post(playlist, accessToken, "users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists");
    }

    public static Response post(Playlist playlist, String token) {
        return RestResourceApi.post(playlist, token, "users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists");
    }

    public static Response get(String playlistId) {
        return RestResourceApi.get(accessToken, "playlists/" + playlistId);
    }

    public static Response update(Playlist playlist, String playlistId) {
        return RestResourceApi.update(playlist, accessToken, "playlists/" + playlistId);
    }
}
