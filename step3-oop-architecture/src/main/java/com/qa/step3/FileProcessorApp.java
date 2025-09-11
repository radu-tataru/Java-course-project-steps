package com.qa.step3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 3: Object-Oriented Programming Architecture
 * 
 * This application demonstrates OOP principles by refactoring the previous
 * monolithic code into a well-structured, extensible architecture.
 * 
 * Learning Objectives:
 * - Interface design and implementation
 * - Abstract classes and inheritance
 * - Factory design pattern
 * - Separation of concerns
 * - Polymorphism in action
 * - Code reusability and maintainability
 */
public class FileProcessorApp {
    
    private static final String DATA_DIR = "step3-oop-architecture/data/";
    
    public static void main(String[] args) {
        System.out.println("=== Step 3: Object-Oriented Programming Architecture ===");
        System.out.println("Demonstrating interfaces, inheritance, and Factory pattern\n");
        
        // Define the files to process
        String[] filePaths = {
            DATA_DIR + "links.txt",
            DATA_DIR + "links.csv", 
            DATA_DIR + "links.json"
        };
        
        List<String> allUrls = new ArrayList<>();
        
        // Process each file using the appropriate processor
        for (String filePath : filePaths) {
            try {
                processFile(filePath, allUrls);
            } catch (Exception e) {
                System.err.println("Error processing " + filePath + ": " + e.getMessage());
            }
        }
        
        // Launch all URLs in browser
        if (!allUrls.isEmpty()) {
            System.out.println("\n4. Launching all URLs in browser:");
            BrowserLauncher.openUrls(allUrls);
        }
        
        System.out.println("\n=== Step 3 Complete! ===");
        System.out.println("Key OOP concepts demonstrated:");
        System.out.println("  • Interface abstraction (FileProcessor)");
        System.out.println("  • Inheritance (BaseFileProcessor)");
        System.out.println("  • Factory pattern (FileProcessorFactory)");
        System.out.println("  • Polymorphism (different processors, same interface)");
        System.out.println("  • Encapsulation (BrowserLauncher utility class)");
        System.out.println("\nNext: Step 4 - Design Patterns");
    }
    
    /**
     * Process a single file using the Factory pattern to get the right processor
     */
    private static void processFile(String filePath, List<String> allUrls) throws IOException {
        System.out.println("Processing: " + filePath);
        
        // Use Factory pattern to get the appropriate processor
        FileProcessor processor = FileProcessorFactory.createProcessor(filePath);
        System.out.println("  Using: " + processor.getDescription());
        
        // Process the file (polymorphism in action)
        List<String> urls = processor.processFile(filePath);
        System.out.println("  Found " + urls.size() + " URLs");
        
        // Add to our collection
        allUrls.addAll(urls);
        
        // Display URLs
        for (int i = 0; i < urls.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + urls.get(i));
        }
        
        System.out.println();
    }
}