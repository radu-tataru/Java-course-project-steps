package com.example.app.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton Logger utility for application-wide logging
 */
public class Logger {
    private static Logger instance = null;
    
    private String logFile = "java_project/data/activity.log";
    
    private Logger() {
        writeToFile("SYSTEM", "Logger started", "Data-driven testing framework initialized");
    }
    
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void log(String message) {
        writeToFile("INFO", "", message);
    }
    
    public void logPageTest(String pageName, String testResult) {
        writeToFile("PAGE_TEST", pageName, testResult);
    }
    
    public void logElementInteraction(String elementName, String action) {
        writeToFile("ELEMENT_ACTION", elementName, action);
    }
    
    public void logDataProviderAction(String dataSource, String action) {
        writeToFile("DATA_PROVIDER", dataSource, action);
    }
    
    public void logParameterizedTest(String testName, String parameters) {
        writeToFile("PARAM_TEST", testName, parameters);
    }
    
    private void writeToFile(String action, String target, String description) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = timestamp + " | " + action + " | " + target + " | " + description + "\n";
            writer.write(logEntry);
            System.out.println("Logged: " + description + (target.isEmpty() ? "" : " for " + target));
        } catch (IOException e) {
            System.err.println("Could not write to log file: " + e.getMessage());
        }
    }
    
    public void showLogLocation() {
        System.out.println("\n--- Logger Information (Singleton) ---");
        System.out.println("Log file location: " + logFile);
        System.out.println("Check this file to see all logged actions!");
        System.out.println("------------------------------------");
    }
}