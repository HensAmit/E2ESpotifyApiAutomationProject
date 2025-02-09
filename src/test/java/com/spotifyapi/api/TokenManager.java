package com.spotifyapi.api;

import com.spotifyapi.utils.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;

    public static String getToken() {
        try {
            if (accessToken == null || Instant.now().isAfter(expiryTime)) {
                System.out.println("Renewing Token..");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is good to use.");
            }
        } catch (Exception e) {
            throw new RuntimeException("ABORT! Failed to get Token.");
        }
        return "Bearer " + accessToken;
    }

    private static Response renewToken() {
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getConfigData("grant_type"));
        formParams.put("refresh_token", ConfigLoader.getInstance().getConfigData("refresh_token"));
        formParams.put("client_id", ConfigLoader.getInstance().getConfigData("client_id"));
        formParams.put("client_secret", ConfigLoader.getInstance().getConfigData("client_secret"));

        Response response = RestResourceApi.postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT! Renew Token Failed.");
        }
        return response;
    }
}
