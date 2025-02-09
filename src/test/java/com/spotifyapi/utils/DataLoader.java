package com.spotifyapi.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader dataLoader;

    private DataLoader() {
        properties = PropertyUtils.propertyLoader("src\\test\\resources\\data.properties");
    }

    public static DataLoader getInstance() {
        if(dataLoader == null)
            return new DataLoader();
        return dataLoader;
    }

    public String getPropertyData(String key) {
        String prop = properties.getProperty(key);
        if(prop != null) {
            return prop;
        } else {
            throw new RuntimeException(key + " is not present in data.properties");
        }
    }
}
