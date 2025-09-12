package com.example.app.config;

import com.example.app.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Environment-specific configuration management
 * Supports dev, staging, and production environments
 */
public class EnvironmentConfig {
    private static String currentEnvironment = "dev";
    private static Properties envProperties;
    
    static {
        loadEnvironmentProperties();
    }
    
    /**
     * Sets the current environment and reloads properties
     * @param environment Environment name (dev, staging, prod)
     */
    public static void setEnvironment(String environment) {
        currentEnvironment = environment;
        loadEnvironmentProperties();
        Logger.getInstance().log("Environment switched to: " + environment);
    }
    
    /**
     * Gets the current environment
     * @return Current environment name
     */
    public static String getCurrentEnvironment() {
        return currentEnvironment;
    }
    
    /**
     * Loads environment-specific properties file
     */
    private static void loadEnvironmentProperties() {
        envProperties = new Properties();
        String propertiesFile = "environments/" + currentEnvironment + ".properties";
        
        try (InputStream is = EnvironmentConfig.class.getClassLoader()
                .getResourceAsStream(propertiesFile)) {
            
            if (is != null) {
                envProperties.load(is);
                Logger.getInstance().log("Loaded environment properties: " + propertiesFile);
            } else {
                Logger.getInstance().log("Warning: Environment properties file not found: " + propertiesFile);
                loadDefaultProperties();
            }
        } catch (IOException e) {
            Logger.getInstance().log("Warning: Could not load environment properties: " + e.getMessage());
            loadDefaultProperties();
        }
    }
    
    /**
     * Loads default properties as fallback
     */
    private static void loadDefaultProperties() {
        envProperties = new Properties();
        envProperties.setProperty("base.url", "https://");
        envProperties.setProperty("timeout.seconds", "10");
        envProperties.setProperty("screenshot.enabled", "true");
    }
    
    /**
     * Gets a property value for the current environment
     * @param key Property key
     * @return Property value or null if not found
     */
    public static String getProperty(String key) {
        return envProperties.getProperty(key);
    }
    
    /**
     * Gets a property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return envProperties.getProperty(key, defaultValue);
    }
    
    /**
     * Gets timeout value as integer
     * @return Timeout in seconds
     */
    public static int getTimeoutSeconds() {
        String timeout = getProperty("timeout.seconds", "10");
        try {
            return Integer.parseInt(timeout);
        } catch (NumberFormatException e) {
            return 10; // Default timeout
        }
    }
    
    /**
     * Checks if screenshots are enabled
     * @return true if screenshots should be taken
     */
    public static boolean isScreenshotEnabled() {
        String enabled = getProperty("screenshot.enabled", "true");
        return Boolean.parseBoolean(enabled);
    }
}