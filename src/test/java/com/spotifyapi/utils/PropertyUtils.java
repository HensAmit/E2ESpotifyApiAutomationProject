package com.spotifyapi.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyLoader(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file: " + filePath);
        }
        return properties;
    }
}
