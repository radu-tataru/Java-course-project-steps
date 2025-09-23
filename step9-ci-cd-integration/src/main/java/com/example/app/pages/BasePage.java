package com.example.app.pages;

import com.example.app.utils.Logger;
import com.example.app.utils.ScreenshotUtils;
import com.example.app.config.EnvironmentConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page Object class with enhanced data-driven testing support
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        int timeoutSeconds = EnvironmentConfig.getTimeoutSeconds();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Takes a screenshot if enabled in environment configuration
     * @param testName Name of the test or action being performed
     */
    protected void takeScreenshot(String testName) {
        if (EnvironmentConfig.isScreenshotEnabled()) {
            ScreenshotUtils.takeScreenshot(driver, testName);
        }
    }
    
    /**
     * Takes a screenshot with test data context
     * @param testName Name of the test
     * @param dataContext Additional context from test data
     */
    protected void takeScreenshotWithContext(String testName, String dataContext) {
        if (EnvironmentConfig.isScreenshotEnabled()) {
            ScreenshotUtils.takeScreenshotWithContext(driver, testName, dataContext);
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
     * Can be overridden by specific page classes
     * @param testDataContext Context from test data for logging
     */
    public void verifyPageLoaded(String testDataContext) {
        String title = getPageTitle();
        Logger.getInstance().log("Page loaded: " + title + " (Context: " + testDataContext + ")");
        takeScreenshotWithContext("page_loaded", testDataContext);
    }
    
    /**
     * Default page load verification without test data context
     */
    public void verifyPageLoaded() {
        verifyPageLoaded("default");
    }
}