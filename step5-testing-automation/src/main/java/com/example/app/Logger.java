package com.example.app;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance = null;
    
    private String logFile = "java_project/data/activity.log";
    
    private Logger() {
        writeToFile("SYSTEM", "Logger started", "Application beginning");
    }
    
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    public void logLinkOpened(String link) {
        writeToFile("LINK_OPENED", link, "Successfully opened in browser");
    }
    
    public void logLinkFailed(String link) {
        writeToFile("LINK_FAILED", link, "Failed to open in browser");
    }
    
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
    
    public void showLogLocation() {
        System.out.println("\n--- Logger Information (Singleton) ---");
        System.out.println("Log file location: " + logFile);
        System.out.println("Check this file to see all logged actions!");
        System.out.println("------------------------------------");
    }
}