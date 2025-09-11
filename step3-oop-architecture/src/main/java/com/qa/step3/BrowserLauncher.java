package com.qa.step3;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Browser launcher utility class.
 * 
 * This class demonstrates utility class design patterns
 * and encapsulates browser launching functionality.
 */
public class BrowserLauncher {
    
    // Private constructor to prevent instantiation (utility class)
    private BrowserLauncher() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
    
    /**
     * Open a single URL in the default browser
     * 
     * @param url the URL to open
     * @return true if successfully opened, false otherwise
     */
    public static boolean openUrl(String url) {
        try {
            if (!Desktop.isDesktopSupported()) {
                System.err.println("Desktop API not supported on this system");
                return false;
            }
            
            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                System.err.println("Browser launching not supported on this system");
                return false;
            }
            
            desktop.browse(new URI(url));
            return true;
            
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error opening URL " + url + ": " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Open multiple URLs with a delay between each
     * 
     * @param urls list of URLs to open
     * @param delayMs delay in milliseconds between opens
     */
    public static void openUrls(List<String> urls, int delayMs) {
        if (urls == null || urls.isEmpty()) {
            System.out.println("No URLs to open");
            return;
        }
        
        System.out.println("Opening " + urls.size() + " URLs...");
        
        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            System.out.print("  " + (i + 1) + "/" + urls.size() + " ");
            
            if (openUrl(url)) {
                System.out.println("✓ Opened: " + url);
            } else {
                System.out.println("✗ Failed: " + url);
            }
            
            // Add delay between opens (except for the last one)
            if (i < urls.size() - 1) {
                try {
                    Thread.sleep(delayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Interrupted");
                    break;
                }
            }
        }
    }
    
    /**
     * Open URLs with default 1-second delay
     * 
     * @param urls list of URLs to open
     */
    public static void openUrls(List<String> urls) {
        openUrls(urls, 1000);
    }
}