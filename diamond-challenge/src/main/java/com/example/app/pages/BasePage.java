package com.example.app.pages;

import com.example.app.utils.ScreenshotUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page Object containing common methods for all page objects.
 * Implements Singleton pattern for WebDriver management.
 */
public abstract class BasePage {
    protected static final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT = 10;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    // ============= WAIT METHODS =============

    protected WebElement waitForElement(By locator) {
        logger.debug("Waiting for element: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        logger.debug("Waiting for element to be clickable: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForInvisibility(By locator) {
        logger.debug("Waiting for element to be invisible: {}", locator);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    // ============= INTERACTION METHODS =============

    protected void click(By locator) {
        logger.info("Clicking element: {}", locator);
        waitForClickable(locator).click();
    }

    protected void type(By locator, String text) {
        logger.info("Typing '{}' into element: {}", text, locator);
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void selectByValue(By locator, String value) {
        logger.info("Selecting value '{}' from dropdown: {}", value, locator);
        WebElement element = waitForElement(locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    protected void selectByVisibleText(By locator, String text) {
        logger.info("Selecting text '{}' from dropdown: {}", text, locator);
        WebElement element = waitForElement(locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    // ============= RETRIEVAL METHODS =============

    protected String getText(By locator) {
        String text = waitForElement(locator).getText();
        logger.debug("Retrieved text '{}' from element: {}", text, locator);
        return text;
    }

    protected String getAttribute(By locator, String attribute) {
        String value = waitForElement(locator).getAttribute(attribute);
        logger.debug("Retrieved attribute '{}' = '{}' from element: {}", attribute, value, locator);
        return value;
    }

    protected boolean isDisplayed(By locator) {
        try {
            boolean displayed = waitForElement(locator).isDisplayed();
            logger.debug("Element {} displayed: {}", locator, displayed);
            return displayed;
        } catch (TimeoutException | NoSuchElementException e) {
            logger.debug("Element {} not displayed", locator);
            return false;
        }
    }

    protected int getElementCount(By locator) {
        int count = driver.findElements(locator).size();
        logger.debug("Found {} elements matching: {}", count, locator);
        return count;
    }

    // ============= SCREENSHOT METHODS =============

    protected String captureScreenshot(String scenarioName) {
        logger.info("Capturing screenshot for: {}", scenarioName);
        return ScreenshotUtil.captureScreenshot(driver, scenarioName);
    }

    // ============= NAVIGATION METHODS =============

    protected void navigateTo(String url) {
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        logger.debug("Current URL: {}", url);
        return url;
    }

    public String getPageTitle() {
        String title = driver.getTitle();
        logger.debug("Page title: {}", title);
        return title;
    }
}
