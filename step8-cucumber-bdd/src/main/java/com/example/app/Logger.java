package com.example.app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance = null;
    
    private String logFile = "java_project/data/activity.log";
    
    private Logger() {
        // Enhanced for Page Object Model integration
        writeToFile("SYSTEM", "Logger started", "Page Object Model testing framework beginning");
    }
    
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    // General logging method for flexible usage
    public void log(String message) {
        writeToFile("INFO", "", message);
    }
    
    // Existing methods for compatibility
    public void logLinkOpened(String link) {
        writeToFile("LINK_OPENED", link, "Successfully opened in browser");
    }
    
    public void logLinkFailed(String link) {
        writeToFile("LINK_FAILED", link, "Failed to open in browser");
    }
    
    // NEW: Page Object Model specific logging methods
    public void logPageTest(String pageName, String testResult) {
        writeToFile("PAGE_TEST", pageName, testResult);
    }
    
    public void logElementInteraction(String elementName, String action) {
        writeToFile("ELEMENT_ACTION", elementName, action);
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