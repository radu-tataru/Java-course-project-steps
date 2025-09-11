package com.qa.step4;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 4: Advanced Design Patterns
 * 
 * This application demonstrates production-ready design patterns
 * commonly used in enterprise automation frameworks.
 * 
 * Learning Objectives:
 * - Singleton pattern implementation (enum and traditional)
 * - Enhanced Factory pattern with inner classes
 * - Logging integration
 * - Configuration management
 * - Production-ready error handling
 * - Clean code architecture
 */
public class AutomationApp {
    
    private static final String DATA_DIR = "step4-design-patterns/data/";
    private static final Logger logger = Logger.getInstance();
    private static final ConfigurationManager config = ConfigurationManager.INSTANCE;
    
    public static void main(String[] args) {
        try {
            // Initialize application
            initializeApplication();
            
            // Process files
            List<String> allUrls = processAllFiles();
            
            // Launch URLs if enabled
            if (config.getBooleanProperty("file.processing.enabled", true)) {
                launchUrls(allUrls);
            }
            
            logger.info("Application completed successfully");
            
        } catch (Exception e) {
            logger.error("Application failed", e);
        }
    }
    
    /**
     * Initialize application with configuration and logging
     */
    private static void initializeApplication() throws IOException {
        System.out.println("=== Step 4: Advanced Design Patterns ===");
        System.out.println("Production-ready automation framework architecture\n");
        
        // Initialize singleton configuration manager
        config.initialize();
        config.displayConfiguration();
        
        logger.info("Starting " + config.getProperty("app.name", "QA App") + 
                   " v" + config.getProperty("app.version", "1.0"));
    }
    
    /**
     * Process all files using enhanced factory
     */
    private static List<String> processAllFiles() {
        String[] filePaths = {
            DATA_DIR + "automation-tools.txt",
            DATA_DIR + "testing-frameworks.csv",
            DATA_DIR + "resources.json"
        };
        
        List<String> allUrls = new ArrayList<>();
        
        for (String filePath : filePaths) {
            try {
                List<String> urls = EnhancedFileProcessorFactory.processFile(filePath);
                allUrls.addAll(urls);
                
                System.out.println("✓ Processed " + filePath + " - found " + urls.size() + " URLs");
                
            } catch (Exception e) {
                logger.error("Failed to process " + filePath, e);
                System.out.println("✗ Failed to process " + filePath);
            }
        }
        
        logger.info("Total URLs collected: " + allUrls.size());
        return allUrls;
    }
    
    /**
     * Launch URLs using configuration-driven approach
     */
    private static void launchUrls(List<String> urls) {
        if (urls.isEmpty()) {
            logger.info("No URLs to launch");
            return;
        }
        
        logger.info("Launching " + urls.size() + " URLs in browser");
        
        int delay = config.getIntProperty("browser.delay.ms", 1000);
        int successCount = 0;
        
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            
            if (openUrl(url)) {
                successCount++;
                System.out.println("  " + (i + 1) + "/" + urls.size() + " ✓ " + url);
            } else {
                System.out.println("  " + (i + 1) + "/" + urls.size() + " ✗ " + url);
            }
            
            // Apply configured delay
            if (i < urls.size() - 1) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    logger.warning("Launch process interrupted");
                    break;
                }
            }
        }
        
        logger.info("Launch complete: " + successCount + "/" + urls.size() + " successful");
        
        System.out.println("\n=== Step 4 Complete! ===");
        System.out.println("Advanced design patterns demonstrated:");
        System.out.println("  • Singleton pattern (ConfigurationManager, Logger)");
        System.out.println("  • Enhanced Factory pattern with inner classes");
        System.out.println("  • Configuration-driven application behavior");
        System.out.println("  • Production-ready logging and error handling");
        System.out.println("  • Clean separation of concerns");
        System.out.println("\nCourse Complete! Ready for enterprise automation frameworks!");
    }
    
    /**
     * Open URL with enhanced error handling
     */
    private static boolean openUrl(String url) {
        try {
            if (!Desktop.isDesktopSupported()) {
                logger.error("Desktop API not supported");
                return false;
            }
            
            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                logger.error("Browser launching not supported");
                return false;
            }
            
            desktop.browse(new URI(url));
            return true;
            
        } catch (IOException | URISyntaxException e) {
            logger.error("Failed to open URL: " + url, e);
            return false;
        }
    }
}