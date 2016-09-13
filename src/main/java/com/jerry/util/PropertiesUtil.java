package com.jerry.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * PropertiesUtil
 *
 * @author jerrychien
 * @create 2016-09-13 22:57
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private Properties properties = new Properties();

    private String filename;

    public PropertiesUtil(String filename) {
        this.filename = filename;
        reload();
    }

    public void reload() {
        InputStream inputStream = PropertiesUtil.class.getResourceAsStream(filename);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("read properties error", e);
        }
    }

    public static PropertiesUtil newInstance(String filename) {
        return new PropertiesUtil(filename);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
