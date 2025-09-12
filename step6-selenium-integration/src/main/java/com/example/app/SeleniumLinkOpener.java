package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Selenium-powered link opener that replaces the Desktop API approach.
 * Provides better control and cross-platform compatibility for web automation.
 */
public class SeleniumLinkOpener {
    private WebDriver driver;
    
    public SeleniumLinkOpener() {
        initializeWebDriver();
    }
    
    private void initializeWebDriver() {
        // Automatically download and setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        
        // Configure Chrome options for better automation
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Create WebDriver instance
        this.driver = new ChromeDriver(options);
        
        Logger.getInstance().log("Selenium WebDriver initialized successfully");
    }
    
    /**
     * Opens a link using Selenium WebDriver instead of Desktop API
     * @param url The URL to open
     */
    public void openLink(String url) {
        try {
            Logger.getInstance().log("Opening: " + url);
            driver.get(url);
            
            // Wait for page to load
            Thread.sleep(2000);
            
            // Get page title for basic verification
            String title = driver.getTitle();
            Logger.getInstance().log("✅ Opened: " + url + " (Title: " + title + ")");
            
        } catch (Exception e) {
            Logger.getInstance().log("❌ Error opening " + url + ": " + e.getMessage());
        }
    }
    
    /**
     * Clean up WebDriver resources
     */
    public void cleanup() {
        if (driver != null) {
            driver.quit();
            Logger.getInstance().log("Browser closed successfully");
        }
    }
}