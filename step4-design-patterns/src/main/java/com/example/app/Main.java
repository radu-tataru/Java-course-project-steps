package com.example.app;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private static final String DATA_DIR = "java_project/data/";

    public static void main(String[] args) {
        processFile("links.txt", "txt");
        processFile("links.csv", "csv");
        processFile("links.json", "json");
        
        // Show where our logs are saved using our Singleton
        Logger logger = Logger.getInstance();
        logger.showLogLocation();
        
        // Demonstrate that it's truly a Singleton
        demonstrateSingletonBehavior();
    }

    private static void processFile(String fileName, String fileExtension) {
        System.out.println("\n--- Processing " + fileName + " ---");
        try {
            LinkReader reader = LinkReaderFactory.getReader(fileExtension);
            List<String> links = reader.readLinks(DATA_DIR + fileName);

            for (String link : links) {
                System.out.println("Read Link: " + link);
                openLink(link);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error processing " + fileName + ": " + e.getMessage());
        }
    }

    private static void openLink(String url) {
        Logger logger = Logger.getInstance();
        
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Opened: " + url);
                
                // Log successful link opening
                logger.logLinkOpened(url);
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error opening link " + url + ": " + e.getMessage());
            
            // Log failed link opening
            logger.logLinkFailed(url);
        }
    }
    
    // Show that both variables point to the same object
    private static void demonstrateSingletonBehavior() {
        System.out.println("\n--- Singleton Demonstration ---");
        
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        System.out.println("logger1 == logger2: " + (logger1 == logger2));
        System.out.println("Both variables point to the same object!");
        
        System.out.println("This means all parts of our program write to the SAME log file!");
    }
}