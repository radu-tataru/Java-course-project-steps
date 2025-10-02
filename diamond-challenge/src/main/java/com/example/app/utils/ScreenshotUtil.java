package com.example.app.utils;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for capturing screenshots during test execution.
 */
public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "reports/screenshots/";

    static {
        // Create screenshots directory if it doesn't exist
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * Captures a screenshot and saves it to the reports/screenshots directory.
     *
     * @param driver WebDriver instance
     * @param scenarioName Name of the scenario/test
     * @return Absolute path to the screenshot file
     */
    public static String captureScreenshot(WebDriver driver, String scenarioName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
        String filePath = SCREENSHOT_DIR + fileName;

        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            FileUtils.copyFile(sourceFile, destinationFile);

            logger.info("Screenshot captured: {}", filePath);
            return destinationFile.getAbsolutePath();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Gets the relative path for embedding in HTML reports.
     *
     * @param absolutePath Absolute path to the screenshot
     * @return Relative path suitable for HTML reports
     */
    public static String getRelativePath(String absolutePath) {
        if (absolutePath == null) {
            return null;
        }
        return absolutePath.substring(absolutePath.indexOf("reports/"));
    }
}
