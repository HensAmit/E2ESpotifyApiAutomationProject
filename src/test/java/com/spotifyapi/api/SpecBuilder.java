package com.spotifyapi.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
    static String accessToken = "Bearer BQCd_lAI9wSLqSQlYTPcPJyILjF4Ex8Wx3PrFXC9GPxXQ8QBPIhCWmbaGw5QXugD5mwmtN8I4dBxTq12MyumLikugQQsmARzIu7ri7dygzrzCM9PpPTf5xye-J4SgbpSjvAbqfo7n6j2NVwD3Igr7RM5jODmF5hB3pBepcFWA47T8IWZiHmSoK6RqHS7UYvnsGgxD_8gszc7bnkqq3xYGak7oUTbK4RQnQoNlOXmNR-4zrY9hwNPkEKjT_XE2G9gQp58XFW5DT1_WJ60MBjQ-R_gxGIlvIL9czZhAWHA-1uubQe7WaXK";

    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL).build();
    }

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
