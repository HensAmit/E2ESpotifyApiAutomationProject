package com.spotifyapi.utils;

import java.util.Properties;

public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader() {
        properties = PropertyUtils.propertyLoader("src\\test\\resources\\config.properties");
    }

    public static ConfigLoader getInstance() {
        if (configLoader == null) {
            return new ConfigLoader();
        }
        return configLoader;
    }

    public String getConfigData(String key) {
        String prop = properties.getProperty(key);
        if(prop != null) {
            return prop;
        } else {
            throw new RuntimeException(key + " is not present in config.properties");
        }
    }
}
