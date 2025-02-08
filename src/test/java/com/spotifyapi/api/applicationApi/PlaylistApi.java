package com.spotifyapi.api.applicationApi;

import com.spotifyapi.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotifyapi.api.SpecBuilder.getRequestSpecification;
import static com.spotifyapi.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class PlaylistApi {
    static String accessToken = "Bearer BQBk7WiBPqcJK4WVkt_O-UGPcPHXInozL1HJ7_KOU1bvz5sf0nMqS1Ce82vm8_Lry0N2n7rg-TAgPYysRNcBOnl80NakahWr0oud2bt6yaxBsKlyp3FqrXWiTq4WIhHpJedTNQVNMMXfykdE8yM2bNoyZ0Folfd03PYZQhrtZvoliSZRKHeOYcET7qfGrjx_NXdKXsgmrjhkAnJqM35e-Q271eNmH-VcEOjmy4Sqk3TU7LId-Ibsb2_n_FnxAEOsGnJGACq6hz_RLd6JUhlEOe1DxJecwoSwfqcv96qZ6lsNHwO-dzvK";

    public static Response post(Playlist playlist) {
        return given(getRequestSpecification())
                .header("Authorization", accessToken)
                .body(playlist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response post(Playlist playlist, String token) {
        return given(getRequestSpecification())
                .header("Authorization", token)
                .body(playlist)
                .when()
                .post("users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response get(String playlistId) {
        return given(getRequestSpecification())
                .pathParam("playlistId", playlistId)
                .header("Authorization", accessToken)
                .when()
                .get("playlists/{playlistId}")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response update(Playlist playlist, String playlistId) {
        return given(getRequestSpecification())
                .pathParam("playlistId", playlistId)
                .header("Authorization", accessToken)
                .body(playlist)
                .when()
                .put("playlists/{playlistId}")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
}
