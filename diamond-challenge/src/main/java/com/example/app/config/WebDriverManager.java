package com.example.app.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Singleton WebDriver Manager for the Diamond Challenge Framework.
 * Manages WebDriver lifecycle and ensures only one instance exists.
 */
public class WebDriverManager {
    private static final Logger logger = LogManager.getLogger(WebDriverManager.class);
    private static WebDriver driver;
    private static final int IMPLICIT_WAIT_SECONDS = 10;

    // Private constructor prevents instantiation
    private WebDriverManager() {
    }

    /**
     * Gets or creates the WebDriver instance (Singleton pattern).
     *
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            logger.info("Initializing WebDriver for the first time");
            io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));

            logger.info("WebDriver initialized successfully");
        }
        return driver;
    }

    /**
     * Quits the WebDriver and sets instance to null.
     */
    public static void quitDriver() {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
            driver = null;
        }
    }

    /**
     * Gets the current driver instance without creating a new one.
     *
     * @return Current WebDriver instance or null if not initialized
     */
    public static WebDriver getCurrentDriver() {
        return driver;
    }
}
