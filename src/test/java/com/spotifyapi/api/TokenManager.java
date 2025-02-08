package com.spotifyapi.api;

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
        formParams.put("grant_type", "refresh_token");
        formParams.put("refresh_token", "AQBDEkn5lxxcPQV5oJbG8TozN-qPQjxvTit9E5PDFKnfeDhl9Gb0_ONozCy9Dir4hRhSZYWLFiI9OiOpsAoT61ZPL17LIloJOR8LMezrXkKx2m4bBu0kojKD89njpWDYaBg");
        formParams.put("client_id", "83394bee777c45bdaead478454dce1ee");
        formParams.put("client_secret", "3ca1ba57bd31462e9f9b2a0b1ca9abf2");

        Response response = RestResourceApi.postAccount(formParams);

        if (response.statusCode() != 200) {
            throw new RuntimeException("ABORT! Renew Token Failed.");
        }
        return response;
    }
}
