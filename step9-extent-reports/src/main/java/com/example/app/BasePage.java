package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.time.Duration;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Base Page Object class that provides common functionality for all page objects
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private final String SCREENSHOTS_DIR = "java_project/screenshots/";
    private static int screenshotCounter = 1;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        createScreenshotsDirectory();
    }
    
    /**
     * Creates screenshots directory if it doesn't exist
     */
    private void createScreenshotsDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
        } catch (IOException e) {
            Logger.getInstance().log("Warning: Could not create screenshots directory: " + e.getMessage());
        }
    }
    
    /**
     * Takes a screenshot and saves it with a descriptive name
     * @param testName Name of the test or action being performed
     */
    protected void takeScreenshot(String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String filename = String.format("screenshot_%03d_%s.png", screenshotCounter++, 
                                           testName.replaceAll("[^a-zA-Z0-9]", "_"));
            String filePath = SCREENSHOTS_DIR + filename;
            
            Files.copy(sourceFile.toPath(), Paths.get(filePath));
            Logger.getInstance().log("📸 Screenshot saved: " + filename);
            
        } catch (Exception e) {
            Logger.getInstance().log("Warning: Could not take screenshot: " + e.getMessage());
        }
    }
    
    /**
     * Checks if an element is displayed without throwing exceptions
     * @param element WebElement to check
     * @return true if element is displayed, false otherwise
     */
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Gets the current page title
     * @return Page title
     */
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Gets the current page URL
     * @return Current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Waits for page to load and verifies basic page elements
     * Should be overridden by specific page classes
     */
    public void verifyPageLoaded() {
        // Default implementation - can be overridden
        Logger.getInstance().log("Page loaded: " + getPageTitle());
    }
}