package com.example.app.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Utility class for screenshot management
 */
public class ScreenshotUtils {
    private static final String SCREENSHOTS_DIR = "java_project/screenshots/";
    private static int screenshotCounter = 1;
    
    static {
        createScreenshotsDirectory();
    }
    
    /**
     * Creates screenshots directory if it doesn't exist
     */
    private static void createScreenshotsDirectory() {
        try {
            Files.createDirectories(Paths.get(SCREENSHOTS_DIR));
        } catch (IOException e) {
            Logger.getInstance().log("Warning: Could not create screenshots directory: " + e.getMessage());
        }
    }
    
    /**
     * Takes a screenshot and saves it with a descriptive name
     * @param driver WebDriver instance
     * @param testName Name of the test or action being performed
     * @return File path of the saved screenshot
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            
            String filename = String.format("screenshot_%03d_%s.png", screenshotCounter++, 
                                           testName.replaceAll("[^a-zA-Z0-9]", "_"));
            String filePath = SCREENSHOTS_DIR + filename;
            
            Files.copy(sourceFile.toPath(), Paths.get(filePath));
            Logger.getInstance().log("📸 Screenshot saved: " + filename);
            
            return filePath;
            
        } catch (Exception e) {
            Logger.getInstance().log("Warning: Could not take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Takes a screenshot with test data context
     * @param driver WebDriver instance
     * @param testName Name of the test
     * @param dataContext Additional context from test data
     * @return File path of the saved screenshot
     */
    public static String takeScreenshotWithContext(WebDriver driver, String testName, String dataContext) {
        String contextualTestName = testName + "_" + dataContext;
        return takeScreenshot(driver, contextualTestName);
    }
}