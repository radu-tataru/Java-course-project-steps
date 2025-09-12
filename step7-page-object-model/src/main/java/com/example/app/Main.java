package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Main {
    
    private static final String DATA_DIR = "java_project/data/";
    
    public static void main(String[] args) {
        System.out.println("=== Step 7: Page Object Model & Advanced Testing ===");
        Logger.getInstance().log("Starting Page Object Model testing framework...");
        
        WebDriver driver = null;
        
        try {
            // Initialize WebDriver
            driver = initializeWebDriver();
            
            // Create Page Object Manager
            PageObjectManager pageManager = new PageObjectManager(driver);
            
            // Read test data
            List<LinkData> testLinks = readTestData();
            Logger.getInstance().log("Loaded " + testLinks.size() + " test cases");
            
            // Run Page Object Model tests
            TestSummary summary = runPageObjectTests(pageManager, testLinks);
            
            // Display comprehensive results
            displayTestResults(summary);
            
        } catch (Exception e) {
            Logger.getInstance().log("Application error: " + e.getMessage());
            System.err.println("Error running Page Object Model tests: " + e.getMessage());
        } finally {
            // Clean up WebDriver resources
            if (driver != null) {
                driver.quit();
                Logger.getInstance().log("WebDriver closed successfully");
            }
        }
        
        Logger.getInstance().showLogLocation();
        Logger.getInstance().log("Page Object Model testing completed!");
        
        System.out.println("\n🎉 Page Object Model testing framework completed!");
        System.out.println("Check the log file for details: java_project/data/activity.log");
        System.out.println("Check screenshots in: java_project/screenshots/");
    }
    
    /**
     * Initializes WebDriver with optimized settings
     */
    private static WebDriver initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        WebDriver driver = new ChromeDriver(options);
        Logger.getInstance().log("WebDriver initialized successfully");
        
        return driver;
    }
    
    /**
     * Reads test data from JSON file
     */
    private static List<LinkData> readTestData() throws IOException {
        JsonLinkDataReader jsonReader = new JsonLinkDataReader();
        return jsonReader.readLinkData(DATA_DIR + "links.json");
    }
    
    /**
     * Runs Page Object Model tests for all links
     */
    private static TestSummary runPageObjectTests(PageObjectManager pageManager, List<LinkData> testLinks) {
        TestSummary summary = new TestSummary();
        
        Logger.getInstance().log("\n=== Starting Page Object Model Tests ===");
        
        for (LinkData linkData : testLinks) {
            try {
                // Navigate to page using Page Object Manager
                Logger.getInstance().log("\n--- Testing: " + linkData.getName() + " ---");
                BasePage page = pageManager.navigateToPage(linkData.getUrl());
                
                // Perform page-specific tests based on page type
                TestResult result = performPageSpecificTests(page, linkData);
                summary.addTestResult(result);
                
                // Wait between tests to avoid overwhelming servers
                Thread.sleep(3000);
                
            } catch (Exception e) {
                Logger.getInstance().log("❌ Test failed for " + linkData.getName() + ": " + e.getMessage());
                TestResult failedResult = new TestResult(linkData.getName() + " Test");
                failedResult.addTest("Page Load Test", false, "Exception: " + e.getMessage());
                summary.addTestResult(failedResult);
            }
        }
        
        return summary;
    }
    
    /**
     * Performs specific tests based on page type
     */
    private static TestResult performPageSpecificTests(BasePage page, LinkData linkData) {
        if (page instanceof GitHubHomePage) {
            return ((GitHubHomePage) page).verifyPageElements();
        } else if (page instanceof JUnitHomePage) {
            return ((JUnitHomePage) page).verifyPageElements();
        } else if (page instanceof MavenHomePage) {
            return ((MavenHomePage) page).verifyPageElements();
        } else if (page instanceof SeleniumHomePage) {
            return ((SeleniumHomePage) page).verifyPageElements();
        } else {
            // Generic page test
            TestResult result = new TestResult(linkData.getName() + " Generic Test");
            
            String title = page.getPageTitle();
            String expectedTitle = linkData.getExpectedTitle();
            
            if (expectedTitle != null) {
                boolean titleMatches = title.toLowerCase().contains(expectedTitle.toLowerCase());
                result.addTest("Title Verification", titleMatches, 
                              "Expected: '" + expectedTitle + "', Actual: '" + title + "'");
            } else {
                result.addTest("Title Check", !title.isEmpty(), 
                              "Title: " + title);
            }
            
            return result;
        }
    }
    
    /**
     * Displays comprehensive test results
     */
    private static void displayTestResults(TestSummary summary) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           PAGE OBJECT MODEL TEST RESULTS");
        System.out.println("=".repeat(60));
        
        for (TestResult result : summary.getTestResults()) {
            System.out.println(result.getDetailedResults());
        }
        
        System.out.println("=".repeat(60));
        System.out.println("OVERALL SUMMARY:");
        System.out.printf("Total Test Suites: %d%n", summary.getTotalSuites());
        System.out.printf("Total Tests: %d%n", summary.getTotalTests());
        System.out.printf("Passed: %d (%.1f%%)%n", summary.getTotalPassed(), summary.getPassPercentage());
        System.out.printf("Failed: %d (%.1f%%)%n", summary.getTotalFailed(), 100 - summary.getPassPercentage());
        System.out.println("=".repeat(60));
        
        // Log summary to file
        Logger.getInstance().log("Test execution completed: " + summary.getTotalPassed() + "/" + summary.getTotalTests() + " tests passed");
    }
}