package com.qa.step3;

import java.io.IOException;
import java.util.List;

/**
 * Interface defining the contract for file processing operations.
 * 
 * This interface demonstrates the principle of abstraction in OOP,
 * allowing different file processors to be used interchangeably.
 */
public interface FileProcessor {
    
    /**
     * Process a file and return the URLs found within it
     * 
     * @param filePath the path to the file to process
     * @return list of URLs found in the file
     * @throws IOException if file processing fails
     */
    List<String> processFile(String filePath) throws IOException;
    
    /**
     * Get a human-readable description of this processor
     * 
     * @return description of what this processor handles
     */
    String getDescription();
}