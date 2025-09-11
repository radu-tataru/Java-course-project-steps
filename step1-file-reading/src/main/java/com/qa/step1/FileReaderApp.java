package com.qa.step1;

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
 * Step 1: File Reading Operations
 * 
 * This application demonstrates basic file I/O operations for QA Engineers.
 * It reads URLs from different file formats: TXT, CSV, and JSON.
 * 
 * Learning Objectives:
 * - Basic file reading with BufferedReader
 * - CSV parsing with String.split()
 * - JSON parsing with org.json library
 * - Exception handling for file operations
 */
public class FileReaderApp {
    
    private static final String DATA_DIR = "step1-file-reading/data/";
    
    public static void main(String[] args) {
        System.out.println("=== Step 1: File Reading Operations ===");
        System.out.println("Learning basic file I/O with different formats\n");
        
        try {
            // Read TXT file
            System.out.println("1. Reading TXT file:");
            List<String> txtLinks = readTxtFile(DATA_DIR + "links.txt");
            displayLinks(txtLinks, "TXT");
            
            System.out.println("\n2. Reading CSV file:");
            List<String> csvLinks = readCsvFile(DATA_DIR + "links.csv");
            displayLinks(csvLinks, "CSV");
            
            System.out.println("\n3. Reading JSON file:");
            List<String> jsonLinks = readJsonFile(DATA_DIR + "links.json");
            displayLinks(jsonLinks, "JSON");
            
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
        
        System.out.println("\n=== Step 1 Complete! ===");
        System.out.println("Next: Step 2 - Desktop API Integration");
    }
    
    /**
     * Read links from a plain text file (one URL per line)
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
     * Read links from a CSV file (name,url format)
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
     * Read links from a JSON file
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
    
    /**
     * Display links in a formatted way
     */
    private static void displayLinks(List<String> links, String fileType) {
        System.out.println("Found " + links.size() + " links in " + fileType + " file:");
        for (int i = 0; i < links.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + links.get(i));
        }
    }
}