package com.automation.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class to manage configuration properties.
 * Reads from config.properties and supports system property overrides.
 */
public final class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private final Properties properties;

    private ConfigManager() {
        properties = new Properties();
        loadProperties();
    }

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("config.properties not found in classpath");
                throw new RuntimeException("config.properties not found");
            }
            properties.load(input);
            logger.info("Configuration loaded successfully");
        } catch (IOException e) {
            logger.error("Error loading config.properties", e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Get a property value. System properties take precedence over file properties.
     */
    public String getProperty(String key) {
        String systemProp = System.getProperty(key);
        if (systemProp != null) {
            return systemProp;
        }
        return properties.getProperty(key);
    }

    /**
     * Get a property with a default fallback value.
     */
    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }

    public String getBaseUrl() {
        return getProperty("base.url");
    }

    public String getBrowser() {
        return getProperty("browser", "chromium");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }

    public double getSlowMo() {
        return Double.parseDouble(getProperty("slow.mo", "0"));
    }

    public int getViewportWidth() {
        return Integer.parseInt(getProperty("viewport.width", "1920"));
    }

    public int getViewportHeight() {
        return Integer.parseInt(getProperty("viewport.height", "1080"));
    }

    public double getDefaultTimeout() {
        return Double.parseDouble(getProperty("default.timeout", "30000"));
    }

    public double getNavigationTimeout() {
        return Double.parseDouble(getProperty("navigation.timeout", "30000"));
    }

    public String getValidUsername() {
        return getProperty("valid.username");
    }

    public String getValidPassword() {
        return getProperty("valid.password");
    }

    public String getLockedUsername() {
        return getProperty("locked.username");
    }
}
