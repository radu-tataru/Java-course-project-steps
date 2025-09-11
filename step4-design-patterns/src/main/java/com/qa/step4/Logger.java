package com.qa.step4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple Logger using Singleton pattern
 * 
 * This class demonstrates another implementation of Singleton pattern
 * using traditional approach with thread safety.
 */
public class Logger {
    
    private static volatile Logger instance;
    private static final Object lock = new Object();
    
    private final DateTimeFormatter timeFormatter;
    
    private Logger() {
        timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * Get the singleton instance (thread-safe)
     */
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }
    
    /**
     * Log info message
     */
    public void info(String message) {
        log("INFO", message);
    }
    
    /**
     * Log warning message
     */
    public void warning(String message) {
        log("WARN", message);
    }
    
    /**
     * Log error message
     */
    public void error(String message) {
        log("ERROR", message);
    }
    
    /**
     * Log error with exception
     */
    public void error(String message, Throwable throwable) {
        log("ERROR", message + " - " + throwable.getMessage());
    }
    
    /**
     * Internal logging method
     */
    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(timeFormatter);
        String logMessage = String.format("[%s] %s: %s", timestamp, level, message);
        System.out.println(logMessage);
    }
}