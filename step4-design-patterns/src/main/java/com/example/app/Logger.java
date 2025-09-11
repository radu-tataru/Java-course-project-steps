package com.example.app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    // The single instance of this class
    private static Logger instance = null;
    
    // File where we write our logs
    private String logFile = "java_project/data/activity.log";
    
    // Private constructor prevents other classes from creating instances
    private Logger() {
        // Create initial log entry
        writeToFile("SYSTEM", "Logger started", "Application beginning");
    }
    
    // This method returns the single instance
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    // Log when we open a link
    public void logLinkOpened(String link) {
        writeToFile("LINK_OPENED", link, "Successfully opened in browser");
    }
    
    // Log when we fail to open a link
    public void logLinkFailed(String link) {
        writeToFile("LINK_FAILED", link, "Failed to open in browser");
    }
    
    // Write a log entry to the file
    private void writeToFile(String action, String link, String description) {
        try (FileWriter writer = new FileWriter(logFile, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = timestamp + " | " + action + " | " + link + " | " + description + "\n";
            writer.write(logEntry);
            System.out.println("Logged: " + action + " for " + link);
        } catch (IOException e) {
            System.err.println("Could not write to log file: " + e.getMessage());
        }
    }
    
    // Show where our log file is located
    public void showLogLocation() {
        System.out.println("\n--- Logger Information (Singleton) ---");
        System.out.println("Log file location: " + logFile);
        System.out.println("Check this file to see all logged actions!");
        System.out.println("------------------------------------");
    }
}