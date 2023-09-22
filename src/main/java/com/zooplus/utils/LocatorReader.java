package com.zooplus.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LocatorReader {

    private Properties properties;

    public LocatorReader(String fileName) {
        this.properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/" + fileName + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLocator(String key) {
        try {
            return properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}