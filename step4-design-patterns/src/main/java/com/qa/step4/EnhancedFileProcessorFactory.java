package com.qa.step4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced Factory with integrated processors and logging
 * 
 * This factory demonstrates advanced Factory pattern implementation
 * with built-in logging and singleton pattern integration.
 */
public class EnhancedFileProcessorFactory {
    
    private static final Logger logger = Logger.getInstance();
    
    /**
     * Create and execute processor for a file
     */
    public static List<String> processFile(String filePath) throws IOException {
        logger.info("Processing file: " + filePath);
        
        String extension = getFileExtension(filePath).toLowerCase();
        FileProcessor processor;
        
        switch (extension) {
            case ".txt":
                processor = new TxtProcessor();
                break;
            case ".csv":
                processor = new CsvProcessor();
                break;
            case ".json":
                processor = new JsonProcessor();
                break;
            default:
                throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
        
        logger.info("Using processor: " + processor.getDescription());
        
        List<String> results = processor.processFile(filePath);
        logger.info("Found " + results.size() + " URLs in " + filePath);
        
        return results;
    }
    
    private static String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        return lastDotIndex > 0 ? filePath.substring(lastDotIndex) : "";
    }
    
    /**
     * Inner class for TXT processing
     */
    private static class TxtProcessor implements FileProcessor {
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
            return "Enhanced TXT processor with validation";
        }
    }
    
    /**
     * Inner class for CSV processing
     */
    private static class CsvProcessor implements FileProcessor {
        @Override
        public List<String> processFile(String filePath) throws IOException {
            List<String> urls = new ArrayList<>();
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean firstLine = true;
                
                while ((line = reader.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    
                    String[] columns = line.split(",");
                    if (columns.length >= 2) {
                        String url = columns[1].trim();
                        if (isValidUrl(url)) {
                            urls.add(url);
                        }
                    }
                }
            }
            
            return urls;
        }
        
        @Override
        public String getDescription() {
            return "Enhanced CSV processor with validation";
        }
    }
    
    /**
     * Inner class for JSON processing
     */
    private static class JsonProcessor implements FileProcessor {
        @Override
        public List<String> processFile(String filePath) throws IOException {
            List<String> urls = new ArrayList<>();
            
            try {
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                JSONObject jsonObject = new JSONObject(content);
                JSONArray linksArray = jsonObject.getJSONArray("links");
                
                for (int i = 0; i < linksArray.length(); i++) {
                    JSONObject linkObj = linksArray.getJSONObject(i);
                    if (linkObj.has("url")) {
                        String url = linkObj.getString("url");
                        if (isValidUrl(url)) {
                            urls.add(url.trim());
                        }
                    }
                }
            } catch (Exception e) {
                throw new IOException("Error parsing JSON: " + e.getMessage(), e);
            }
            
            return urls;
        }
        
        @Override
        public String getDescription() {
            return "Enhanced JSON processor with validation";
        }
    }
    
    /**
     * URL validation shared by all processors
     */
    private static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        String trimmed = url.trim();
        return trimmed.startsWith("http://") || trimmed.startsWith("https://");
    }
}