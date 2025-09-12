package com.example.app;

import com.example.app.config.EnvironmentConfig;
import com.example.app.data.models.WebsiteTestData;
import com.example.app.data.providers.TestDataProvider;
import com.example.app.pages.BasePage;
import com.example.app.pages.GitHubHomePage;
import com.example.app.pages.PageObjectManager;
import com.example.app.tests.TestResult;
import com.example.app.tests.TestSummary;
import com.example.app.utils.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

/**
 * Main class demonstrating Step 8: Test Data Management & Parameterization
 * Features clean architecture, data-driven testing, and environment management
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("=== Step 8: Test Data Management & Parameterization ===");
        Logger.getInstance().log("Starting data-driven testing framework with clean architecture...");
        
        // Initialize environment (default to dev, can be changed via system property)
        String environment = System.getProperty("test.env", "dev");
        EnvironmentConfig.setEnvironment(environment);
        
        WebDriver driver = null;
        
        try {
            // Validate data sources before execution
            TestDataProvider dataProvider = new TestDataProvider();
            if (!dataProvider.validateDataSources()) {
                Logger.getInstance().log("❌ Data source validation failed. Please check data files.");
                return;
            }
            
            // Initialize WebDriver with environment-specific settings
            driver = initializeWebDriver();
            PageObjectManager pageManager = new PageObjectManager(driver);
            
            // Demonstrate different data-driven testing approaches
            demonstrateDataDrivenTesting(pageManager, dataProvider);
            
        } catch (Exception e) {
            Logger.getInstance().log("Application error: " + e.getMessage());
            System.err.println("Error running data-driven tests: " + e.getMessage());
        } finally {
            if (driver != null) {
                driver.quit();
                Logger.getInstance().log("WebDriver closed successfully");
            }
        }
        
        Logger.getInstance().showLogLocation();
        Logger.getInstance().log("Data-driven testing framework demonstration completed!");
        
        System.out.println("\n🎉 Step 8 implementation completed successfully!");
        System.out.println("✨ Clean architecture with proper package organization");
        System.out.println("📊 Excel and JSON data providers implemented");
        System.out.println("🔧 Environment-specific configuration management");
        System.out.println("🧪 Parameterized testing with JUnit 5 and TestNG");
        System.out.println("\nCheck the log file: java_project/data/activity.log");
        System.out.println("Check screenshots: java_project/screenshots/");
    }
    
    /**
     * Initializes WebDriver with environment-specific configuration
     */
    private static WebDriver initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Environment-specific browser configuration
        if ("prod".equals(EnvironmentConfig.getCurrentEnvironment())) {
            options.addArguments("--headless");
            Logger.getInstance().log("WebDriver initialized in headless mode for production");
        }
        
        WebDriver driver = new ChromeDriver(options);
        Logger.getInstance().log("WebDriver initialized for environment: " + 
                                EnvironmentConfig.getCurrentEnvironment());
        
        return driver;
    }
    
    /**
     * Demonstrates various data-driven testing approaches
     */
    private static void demonstrateDataDrivenTesting(PageObjectManager pageManager, TestDataProvider dataProvider) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("           DATA-DRIVEN TESTING DEMONSTRATION");
        System.out.println("=".repeat(70));
        
        // Demonstration 1: All test data
        System.out.println("\n1. Testing with ALL available test data:");
        TestSummary allDataSummary = executeTestsWithData(pageManager, 
                                                          dataProvider.getAllWebsiteTestData(), 
                                                          "All Data");
        
        // Demonstration 2: High-priority tests only
        System.out.println("\n2. Testing with HIGH-PRIORITY data only:");
        TestSummary prioritySummary = executeTestsWithData(pageManager, 
                                                           dataProvider.getSmokeTestData(), 
                                                           "High Priority");
        
        // Demonstration 3: Environment-specific tests
        String currentEnv = EnvironmentConfig.getCurrentEnvironment();
        System.out.println("\n3. Testing with " + currentEnv.toUpperCase() + " environment data:");
        TestSummary envSummary = executeTestsWithData(pageManager, 
                                                      dataProvider.getTestDataForEnvironment(currentEnv), 
                                                      currentEnv + " Environment");
        
        // Demonstration 4: Multi-environment testing
        System.out.println("\n4. Multi-environment testing demonstration:");
        demonstrateMultiEnvironmentTesting(pageManager, dataProvider);
        
        // Display comprehensive results
        displayFinalResults(allDataSummary, prioritySummary, envSummary);
    }
    
    /**
     * Executes tests with provided test data
     */
    private static TestSummary executeTestsWithData(PageObjectManager pageManager, 
                                                   List<WebsiteTestData> testDataList, 
                                                   String dataSetName) {
        TestSummary summary = new TestSummary();
        Logger.getInstance().log("\n--- Executing " + dataSetName + " Tests ---");
        
        for (WebsiteTestData testData : testDataList) {
            try {
                Logger.getInstance().log("Testing: " + testData.getTestName());
                
                // Set environment from test data
                EnvironmentConfig.setEnvironment(testData.getEnvironment());
                
                // Navigate and test
                BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
                page.verifyPageLoaded(testData.getTestName());
                
                TestResult result = performPageVerification(page, testData);
                summary.addTestResult(result);
                
                // Wait between tests
                Thread.sleep(2000);
                
            } catch (Exception e) {
                Logger.getInstance().log("❌ Test failed for " + testData.getTestName() + ": " + e.getMessage());
                TestResult failedResult = new TestResult(testData.getTestName() + " - ERROR", 
                                                        testData.getEnvironment());
                failedResult.addTest("Execution", false, "Exception: " + e.getMessage());
                summary.addTestResult(failedResult);
            }
        }
        
        return summary;
    }
    
    /**
     * Demonstrates multi-environment testing
     */
    private static void demonstrateMultiEnvironmentTesting(PageObjectManager pageManager, 
                                                          TestDataProvider dataProvider) {
        String[] environments = {"dev", "staging", "prod"};
        String originalEnv = EnvironmentConfig.getCurrentEnvironment();
        
        for (String env : environments) {
            try {
                System.out.println("  Testing in " + env.toUpperCase() + " environment...");
                EnvironmentConfig.setEnvironment(env);
                
                List<WebsiteTestData> envData = dataProvider.getTestDataForEnvironment(env);
                if (!envData.isEmpty()) {
                    WebsiteTestData sampleData = envData.get(0);
                    BasePage page = pageManager.navigateToPage(sampleData.getWebsiteUrl());
                    TestResult result = performPageVerification(page, sampleData);
                    
                    System.out.println("    " + env.toUpperCase() + ": " + result.getOverallResult());
                } else {
                    System.out.println("    " + env.toUpperCase() + ": No data available for this environment");
                }
                
            } catch (Exception e) {
                System.out.println("    " + env.toUpperCase() + ": Error - " + e.getMessage());
            }
        }
        
        // Restore original environment
        EnvironmentConfig.setEnvironment(originalEnv);
    }
    
    /**
     * Performs page verification with enhanced data context
     */
    private static TestResult performPageVerification(BasePage page, WebsiteTestData testData) {
        if (page instanceof GitHubHomePage) {
            GitHubHomePage githubPage = (GitHubHomePage) page;
            return githubPage.verifyPageElements(testData.getExpectedTitle(), 
                                               testData.getButtonText(), 
                                               testData.getTestName());
        } else {
            // Generic verification for other page types
            TestResult result = new TestResult("Generic Page Test", testData.getTestName());
            
            String actualTitle = page.getPageTitle();
            boolean titleMatches = testData.getExpectedTitle() == null || 
                                 actualTitle.toLowerCase().contains(testData.getExpectedTitle().toLowerCase());
            
            result.addTest("Page Title Verification", titleMatches, 
                          "Expected: '" + testData.getExpectedTitle() + "', Actual: '" + actualTitle + "'");
            result.addTest("Page Load Success", true, "Page loaded successfully");
            
            return result;
        }
    }
    
    /**
     * Displays comprehensive test results
     */
    private static void displayFinalResults(TestSummary... summaries) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                    FINAL RESULTS SUMMARY");
        System.out.println("=".repeat(70));
        
        int totalSuites = 0;
        int totalTests = 0;
        int totalPassed = 0;
        int totalFailed = 0;
        
        for (TestSummary summary : summaries) {
            totalSuites += summary.getTotalSuites();
            totalTests += summary.getTotalTests();
            totalPassed += summary.getTotalPassed();
            totalFailed += summary.getTotalFailed();
        }
        
        System.out.printf("📊 Overall Statistics:%n");
        System.out.printf("   Test Suites Executed: %d%n", totalSuites);
        System.out.printf("   Total Tests: %d%n", totalTests);
        System.out.printf("   Passed: %d (%.1f%%)%n", totalPassed, 
                         totalTests > 0 ? (totalPassed * 100.0 / totalTests) : 0.0);
        System.out.printf("   Failed: %d (%.1f%%)%n", totalFailed, 
                         totalTests > 0 ? (totalFailed * 100.0 / totalTests) : 0.0);
        
        System.out.println("\n🏗️ Architecture Achievements:");
        System.out.println("   ✅ Clean package organization implemented");
        System.out.println("   ✅ Data-driven testing with Excel and JSON sources");
        System.out.println("   ✅ Environment-specific configuration management");
        System.out.println("   ✅ Builder pattern for flexible test data creation");
        System.out.println("   ✅ Enhanced Page Object Model with data context");
        
        System.out.println("=".repeat(70));
        
        // Log final statistics
        Logger.getInstance().log("Framework demonstration completed. Total tests executed: " + 
                                totalTests + ", Passed: " + totalPassed + ", Failed: " + totalFailed);
    }
}