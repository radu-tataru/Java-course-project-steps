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
            
            // NEW: Enhanced JSON reading with title verification
            List<LinkData> enhancedLinks = readEnhancedJsonData();
            Logger.getInstance().log("Enhanced JSON links found: " + enhancedLinks.size());
            
            // NEW: Use Selenium instead of Desktop API
            SeleniumLinkOpener opener = new SeleniumLinkOpener();
            
            // Open regular links first (TXT, CSV, basic JSON)
            Logger.getInstance().log("\n=== Opening Basic Links ===");
            for (String link : allLinks) {
                opener.openLink(link);
                Thread.sleep(2000);
            }
            
            // Open enhanced JSON links with title verification
            Logger.getInstance().log("\n=== Opening Enhanced Links with Verification ===");
            for (LinkData linkData : enhancedLinks) {
                opener.openLinkWithVerification(linkData.getUrl(), linkData.getExpectedTitle());
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
        System.out.println("Check screenshots in: java_project/screenshots/");
    }
    
    private static List<LinkData> readEnhancedJsonData() throws IOException {
        JsonLinkDataReader jsonDataReader = new JsonLinkDataReader();
        return jsonDataReader.readLinkData(DATA_DIR + "links.json");
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