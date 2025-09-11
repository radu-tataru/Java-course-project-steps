package com.qa.step3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes plain text files containing URLs (one per line).
 * 
 * This class implements the FileProcessor interface, demonstrating
 * the principle of implementing interfaces in OOP.
 */
public class TxtFileProcessor implements FileProcessor {
    
    @Override
    public List<String> processFile(String filePath) throws IOException {
        List<String> urls = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty() && isValidUrl(trimmed)) {
                    urls.add(trimmed);
                }
            }
        }
        
        return urls;
    }
    
    @Override
    public String getDescription() {
        return "Plain text file processor (*.txt) - reads URLs line by line";
    }
    
    /**
     * Basic URL validation
     */
    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }
}