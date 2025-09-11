package com.qa.step4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton Configuration Manager
 * 
 * This class demonstrates the Singleton design pattern - ensuring only
 * one instance of configuration exists throughout the application lifecycle.
 * 
 * Thread-safe implementation using enum singleton pattern.
 */
public enum ConfigurationManager {
    INSTANCE;
    
    private final Properties properties;
    private boolean initialized = false;
    
    ConfigurationManager() {
        properties = new Properties();
    }
    
    /**
     * Initialize configuration from properties file
     */
    public synchronized void initialize() throws IOException {
        if (initialized) {
            return; // Already initialized
        }
        
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("application.properties")) {
            
            if (input != null) {
                properties.load(input);
            } else {
                // Load default configuration if file not found
                loadDefaultConfiguration();
            }
            
            initialized = true;
            System.out.println("Configuration Manager initialized successfully");
        }
    }
    
    /**
     * Get configuration property value
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    /**
     * Get configuration property with default value
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * Get integer property
     */
    public int getIntProperty(String key, int defaultValue) {
        try {
            String value = properties.getProperty(key);
            return value != null ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer value for " + key + ", using default: " + defaultValue);
            return defaultValue;
        }
    }
    
    /**
     * Get boolean property
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Boolean.parseBoolean(value) : defaultValue;
    }
    
    /**
     * Load default configuration when properties file is not found
     */
    private void loadDefaultConfiguration() {
        properties.setProperty("app.name", "QA Automation Course Project");
        properties.setProperty("app.version", "1.0.0");
        properties.setProperty("browser.delay.ms", "1000");
        properties.setProperty("file.processing.enabled", "true");
        properties.setProperty("logging.level", "INFO");
        
        System.out.println("Loaded default configuration");
    }
    
    /**
     * Display all configuration properties (for debugging)
     */
    public void displayConfiguration() {
        System.out.println("=== Configuration Settings ===");
        properties.forEach((key, value) -> 
            System.out.println("  " + key + " = " + value));
        System.out.println("==============================");
    }
}