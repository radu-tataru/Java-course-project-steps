package com.example.app;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String DATA_DIR = "java_project/data/";

    public static void main(String[] args) {
        System.out.println("=== Step 5: Testing and Build Automation ===");
        
        // Get the singleton logger instance
        Logger logger = Logger.getInstance();
        logger.showLogLocation();
        
        // Test reading from all file types
        testFileReading();
        
        // Test link opening
        testLinkOpening();
        
        System.out.println("\n=== All operations completed ===");
        System.out.println("Run: mvn test");
        System.out.println("To execute the unit tests!");
    }

    private static void testFileReading() {
        System.out.println("\n--- Testing File Reading ---");
        
        String[] testFiles = {"links.txt", "links.csv", "links.json"};
        
        for (String fileName : testFiles) {
            String filePath = DATA_DIR + fileName;
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
            
            try {
                LinkReader reader = LinkReaderFactory.getReader(extension);
                List<String> links = reader.readLinks(filePath);
                System.out.println("Successfully read " + links.size() + " links from " + fileName);
            } catch (IOException e) {
                System.err.println("Error reading " + fileName + ": " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("Unsupported file type: " + e.getMessage());
            }
        }
    }

    private static void testLinkOpening() {
        System.out.println("\n--- Testing Link Opening ---");
        
        LinkOpener linkOpener = new LinkOpener();
        Logger logger = Logger.getInstance();
        
        // Test with a safe URL (won't actually open during tests)
        String testUrl = "https://junit.org";
        
        if (linkOpener.isValidUrl(testUrl)) {
            System.out.println("URL validation successful: " + testUrl);
            // Log the attempted link opening
            logger.logLinkOpened(testUrl);
        } else {
            System.out.println("Invalid URL: " + testUrl);
            logger.logLinkFailed(testUrl);
        }
    }
}