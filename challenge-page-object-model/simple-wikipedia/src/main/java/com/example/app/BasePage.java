package com.example.app;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

/**
 * Base Page class containing common functionality for all page objects
 * Simple Challenge Solution
 */
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final String SCREENSHOT_DIR = "screenshots";

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Click on an element after waiting for it to be clickable
     */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Type text into an element after waiting for it to be visible
     */
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from an element after waiting for it to be visible
     */
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Check if an element is visible
     */
    protected boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Wait for an element to be visible
     */
    protected void waitForElement(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Take screenshot and save to file
     */
    public void takeScreenshot(String filename) {
        try {
            new File(SCREENSHOT_DIR).mkdirs();
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(SCREENSHOT_DIR, filename + ".png");
            Files.copy(screenshot.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("📸 Screenshot saved: " + destFile.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("❌ Failed to take screenshot: " + e.getMessage());
        }
    }

    /**
     * Get current page title
     */
    public abstract String getPageTitle();

    /**
     * Check if page is loaded successfully
     */
    public abstract boolean isPageLoaded();
}
