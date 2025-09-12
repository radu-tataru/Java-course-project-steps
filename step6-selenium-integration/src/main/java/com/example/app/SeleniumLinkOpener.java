package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Selenium-powered link opener that replaces the Desktop API approach.
 * Provides better control and cross-platform compatibility for web automation.
 */
public class SeleniumLinkOpener {
    private WebDriver driver;
    private final String SCREENSHOTS_DIR = "java_project/screenshots/";
    private int screenshotCounter = 1;
    
    public SeleniumLinkOpener() {
        initializeWebDriver();
        createScreenshotsDirectory();
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
    
    private void createScreenshotsDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
            Logger.getInstance().log("Screenshots directory created: " + SCREENSHOTS_DIR);
        } catch (IOException e) {
            Logger.getInstance().log("Warning: Could not create screenshots directory: " + e.getMessage());
        }
    }
    
    /**
     * Opens a link using Selenium WebDriver with enhanced verification and screenshots
     * @param url The URL to open
     */
    public void openLink(String url) {
        openLinkWithVerification(url, null);
    }
    
    /**
     * Opens a link with optional title verification
     * @param url The URL to open
     * @param expectedTitle Expected page title (optional)
     */
    public void openLinkWithVerification(String url, String expectedTitle) {
        try {
            Logger.getInstance().log("Opening: " + url);
            driver.get(url);
            
            // Wait for page to load
            Thread.sleep(2000);
            
            // Get page title for verification
            String actualTitle = driver.getTitle();
            
            // Verify title if expected title is provided
            boolean titleMatches = true;
            if (expectedTitle != null && !expectedTitle.trim().isEmpty()) {
                titleMatches = actualTitle.toLowerCase().contains(expectedTitle.toLowerCase());
                if (titleMatches) {
                    Logger.getInstance().log("✅ Title verification passed: " + actualTitle);
                } else {
                    Logger.getInstance().log("⚠️ Title mismatch - Expected: " + expectedTitle + ", Actual: " + actualTitle);
                }
            }
            
            // Take screenshot
            takeScreenshot(url, actualTitle);
            
            // Log success
            String status = titleMatches ? "✅" : "⚠️";
            Logger.getInstance().log(status + " Opened: " + url + " (Title: " + actualTitle + ")");
            
        } catch (Exception e) {
            Logger.getInstance().log("❌ Error opening " + url + ": " + e.getMessage());
        }
    }
    
    /**
     * Takes a screenshot of the current page
     * @param url The URL being captured
     * @param title The page title
     */
    private void takeScreenshot(String url, String title) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            // Create filename based on counter and site name
            String siteName = url.replaceAll("https?://", "")
                                .replaceAll("[^a-zA-Z0-9.-]", "_")
                                .substring(0, Math.min(20, url.length() - 8));
            String filename = String.format("screenshot_%03d_%s.png", screenshotCounter++, siteName);
            String filePath = SCREENSHOTS_DIR + filename;
            
            // Copy screenshot to destination
            Files.copy(sourceFile.toPath(), Paths.get(filePath));
            Logger.getInstance().log("📸 Screenshot saved: " + filename);
            
        } catch (Exception e) {
            Logger.getInstance().log("Warning: Could not take screenshot: " + e.getMessage());
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