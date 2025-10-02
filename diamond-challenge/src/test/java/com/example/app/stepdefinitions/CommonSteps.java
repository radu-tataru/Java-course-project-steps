package com.example.app.stepdefinitions;

import com.example.app.config.WebDriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * Common step definitions for Cucumber hooks (Before/After).
 */
public class CommonSteps {
    private static final Logger logger = LogManager.getLogger(CommonSteps.class);
    private WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");

        driver = WebDriverManager.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("========================================");
        logger.info("Scenario '{}' finished with status: {}", scenario.getName(), scenario.getStatus());
        logger.info("========================================");

        if (scenario.isFailed()) {
            logger.error("Scenario FAILED: {}", scenario.getName());
        }

        WebDriverManager.quitDriver();
    }
}
