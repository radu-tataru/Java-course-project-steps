package com.example.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
    private static final String DATA_DIR = "java_project/data/";
    
    public static void main(String[] args) {
        System.out.println("=== Step 6: Selenium Integration ===");
        Logger.getInstance().log("Starting Selenium-powered file reader application...");
        
        // Create readers using the factory pattern - same as before!
        try {
            List<String> allLinks = readAllLinks();
            Logger.getInstance().log("Total links found: " + allLinks.size());
            
            // NEW: Use Selenium instead of Desktop API
            SeleniumLinkOpener opener = new SeleniumLinkOpener();
            
            // Open each link with Selenium WebDriver
            for (String link : allLinks) {
                opener.openLink(link);
                
                // Wait a bit between opening links to see the results
                Thread.sleep(2000);
            }
            
            // Clean up Selenium resources
            opener.cleanup();
            
        } catch (Exception e) {
            Logger.getInstance().log("Application error: " + e.getMessage());
            System.err.println("Error processing links: " + e.getMessage());
        }
        
        Logger.getInstance().showLogLocation();
        Logger.getInstance().log("Application completed successfully!");
        
        System.out.println("\n🎉 All links have been processed with Selenium WebDriver!");
        System.out.println("Check the log file for details: java_project/data/activity.log");
    }
    
    private static List<String> readAllLinks() throws IOException {
        List<String> allLinks = new ArrayList<>();
        
        // Read from TXT file
        LinkReader txtReader = LinkReaderFactory.getReader("txt");
        allLinks.addAll(txtReader.readLinks(DATA_DIR + "links.txt"));
        
        // Read from CSV file
        LinkReader csvReader = LinkReaderFactory.getReader("csv");
        allLinks.addAll(csvReader.readLinks(DATA_DIR + "links.csv"));
        
        // Read from JSON file  
        LinkReader jsonReader = LinkReaderFactory.getReader("json");
        allLinks.addAll(jsonReader.readLinks(DATA_DIR + "links.json"));
        
        return allLinks;
    }
}