package com.spotifyapi.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotifyapi.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResourceApi {
    public static Response post(Object payload, String accessToken, String path) {
        return given(getRequestSpecification())
                .header("Authorization", accessToken)
                .body(payload)
                .when()
                .post(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response get(String accessToken, String path) {
        return given(getRequestSpecification())
                .header("Authorization", accessToken)
                .when()
                .get(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response update(Object payload, String accessToken, String path) {
        return given(getRequestSpecification())
                .header("Authorization", accessToken)
                .body(payload)
                .when()
                .put(path)
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }

    public static Response postAccount(HashMap<String, String> formParams) {
        return given(getAccountRequestSpecification())
                .formParams(formParams)
                .when()
                .post("api/token")
                .then()
                .spec(getResponseSpecification())
                .extract()
                .response();
    }
}
