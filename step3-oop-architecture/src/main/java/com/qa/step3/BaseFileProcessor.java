package com.qa.step3;

/**
 * Abstract base class for file processors.
 * 
 * This class demonstrates the use of abstract classes in OOP,
 * providing common functionality that can be shared by subclasses.
 */
public abstract class BaseFileProcessor implements FileProcessor {
    
    /**
     * Common URL validation logic shared by all processors
     */
    protected boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = url.trim();
        return trimmed.startsWith("http://") || trimmed.startsWith("https://");
    }
    
    /**
     * Common logging method for processing status
     */
    protected void logProcessing(String fileName, int urlCount) {
        System.out.println("  Processed " + fileName + " - found " + urlCount + " URLs");
    }
}