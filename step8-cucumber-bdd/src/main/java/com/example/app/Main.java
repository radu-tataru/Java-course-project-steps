package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

/**
 * Main class demonstrating BDD-style testing without Cucumber runner
 * Shows how Page Objects can be used in both traditional and BDD approaches
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Step 8: Cucumber BDD Integration ===");
        System.out.println("This step demonstrates how to transform Page Object tests into business-readable scenarios.");
        System.out.println();

        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            // Initialize components
            JsonLinkDataReader dataReader = new JsonLinkDataReader();
            PageObjectManager pageManager = new PageObjectManager(driver);
            TestSummary testSummary = new TestSummary();
            Logger logger = Logger.getInstance();

            // Load test data
            List<LinkData> websites = dataReader.readLinkData("java_project/data/links.json");
            System.out.println("Loaded " + websites.size() + " websites for BDD testing");
            System.out.println();

            // Execute BDD-style scenarios manually (demonstrating the concepts)
            System.out.println("=== Scenario: Verify developer website accessibility ===");

            for (LinkData website : websites) {
                System.out.println("\nGiven I navigate to the \"" + website.getName() + "\" website");
                driver.get(website.getUrl());
                logger.logLinkOpened(website.getUrl());

                System.out.println("When the page loads completely");
                Thread.sleep(2000); // Simple wait

                System.out.println("Then the page title should contain \"" + website.getExpectedTitle() + "\"");
                String actualTitle = driver.getTitle();
                boolean titleMatches = actualTitle.contains(website.getExpectedTitle());

                System.out.println("And all critical page elements should be visible");
                BasePage currentPage = pageManager.navigateToPage(website.getUrl());
                TestResult result = currentPage.verifyPageElements();
                testSummary.addTestResult(result);

                System.out.println("And I should be able to take a screenshot");
                String screenshotPath = currentPage.takeScreenshot();

                // Report results in BDD style
                if (titleMatches && result.allTestsPassed()) {
                    System.out.println("✅ PASSED: " + website.getName() + " verification successful");
                } else {
                    System.out.println("❌ FAILED: " + website.getName() + " verification failed");
                    if (!titleMatches) {
                        System.out.println("   Title mismatch: expected '" + website.getExpectedTitle() +
                                         "', got '" + actualTitle + "'");
                    }
                    if (!result.allTestsPassed()) {
                        System.out.println("   Element verification: " + result.getFailureMessage());
                    }
                }
            }

            System.out.println();
            System.out.println("=== BDD Test Execution Complete ===");
            System.out.println(testSummary.generateSummary());
            System.out.println();

            System.out.println("=== Key BDD Concepts Demonstrated ===");
            System.out.println("✓ Business-readable test scenarios");
            System.out.println("✓ Given-When-Then structure");
            System.out.println("✓ Reuse of existing Page Objects");
            System.out.println("✓ Clear separation of test logic and business language");
            System.out.println();

            System.out.println("To run actual Cucumber tests:");
            System.out.println("mvn test");
            System.out.println();

        } catch (Exception e) {
            System.err.println("Error during BDD demonstration: " + e.getMessage());
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
