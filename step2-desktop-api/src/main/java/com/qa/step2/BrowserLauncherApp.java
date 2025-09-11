package com.qa.step2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 2: Desktop API Integration
 * 
 * This application builds on Step 1 by adding browser integration.
 * It reads URLs from files and automatically opens them in the default browser.
 * 
 * Learning Objectives:
 * - Using java.awt.Desktop for system integration
 * - Opening URLs programmatically in the default browser
 * - Platform compatibility checking
 * - Combining file I/O with system operations
 */
public class BrowserLauncherApp {
    
    private static final String DATA_DIR = "step2-desktop-api/data/";
    
    public static void main(String[] args) {
        System.out.println("=== Step 2: Desktop API Integration ===");
        System.out.println("Reading files and opening URLs in browser\n");
        
        try {
            // Read and open TXT file URLs
            System.out.println("1. Opening URLs from TXT file:");
            List<String> txtLinks = readTxtFile(DATA_DIR + "links.txt");
            openUrls(txtLinks);
            
            System.out.println("\n2. Opening URLs from CSV file:");
            List<String> csvLinks = readCsvFile(DATA_DIR + "links.csv");
            openUrls(csvLinks);
            
            System.out.println("\n3. Opening URLs from JSON file:");
            List<String> jsonLinks = readJsonFile(DATA_DIR + "links.json");
            openUrls(jsonLinks);
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        System.out.println("\n=== Step 2 Complete! ===");
        System.out.println("Next: Step 3 - Object-Oriented Architecture");
    }
    
    /**
     * Open a single URL in the default browser
     */
    public static void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("  ✓ Opened: " + url);
            } else {
                System.out.println("  ✗ Desktop browsing not supported: " + url);
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("  ✗ Error opening " + url + ": " + e.getMessage());
        }
    }
    
    /**
     * Open multiple URLs with a delay between each
     */
    public static void openUrls(List<String> urls) {
        if (urls.isEmpty()) {
            System.out.println("  No URLs to open");
            return;
        }
        
        System.out.println("  Opening " + urls.size() + " URLs...");
        for (String url : urls) {
            openLink(url);
            
            // Small delay to prevent overwhelming the system
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("  Interrupted");
                break;
            }
        }
    }
    
    /**
     * Read links from a plain text file (reused from Step 1)
     */
    public static List<String> readTxtFile(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    links.add(trimmed);
                }
            }
        }
        
        return links;
    }
    
    /**
     * Read links from a CSV file (reused from Step 1)
     */
    public static List<String> readCsvFile(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String url = parts[1].trim();
                    if (!url.isEmpty()) {
                        links.add(url);
                    }
                }
            }
        }
        
        return links;
    }
    
    /**
     * Read links from a JSON file (reused from Step 1)
     */
    public static List<String> readJsonFile(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray linksArray = jsonObject.getJSONArray("links");
            
            for (int i = 0; i < linksArray.length(); i++) {
                JSONObject linkObj = linksArray.getJSONObject(i);
                String url = linkObj.getString("url");
                if (url != null && !url.trim().isEmpty()) {
                    links.add(url.trim());
                }
            }
        } catch (Exception e) {
            throw new IOException("Error parsing JSON file: " + e.getMessage(), e);
        }
        
        return links;
    }
}