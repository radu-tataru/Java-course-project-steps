package com.example.app;

import com.example.app.config.EnvironmentConfig;
import com.example.app.data.models.WebsiteTestData;
import com.example.app.data.providers.TestDataProvider;
import com.example.app.pages.BasePage;
import com.example.app.pages.GitHubHomePage;
import com.example.app.pages.PageObjectManager;
import com.example.app.tests.TestResult;
import com.example.app.utils.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Parameterized tests for website verification using JUnit 5
 * Demonstrates data-driven testing with clean architecture
 */
public class ParameterizedWebsiteTests {
    
    private WebDriver driver;
    private PageObjectManager pageManager;
    
    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        // Add headless mode for production environment
        if ("prod".equals(EnvironmentConfig.getCurrentEnvironment())) {
            options.addArguments("--headless");
        }
        
        driver = new ChromeDriver(options);
        pageManager = new PageObjectManager(driver);
        
        Logger.getInstance().log("Test setup completed for environment: " + 
                                EnvironmentConfig.getCurrentEnvironment());
    }
    
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
            Logger.getInstance().log("Test cleanup completed");
        }
    }
    
    @ParameterizedTest
    @MethodSource("getAllWebsiteTestData")
    @DisplayName("Website verification with comprehensive test data")
    void testWebsiteWithAllData(WebsiteTestData testData) {
        // Set environment configuration from test data
        EnvironmentConfig.setEnvironment(testData.getEnvironment());
        
        Logger.getInstance().logParameterizedTest(testData.getTestName(), 
            "Environment: " + testData.getEnvironment() + ", Priority: " + testData.getPriority());
        
        // Navigate to page using Page Object Manager
        BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
        page.verifyPageLoaded(testData.getTestName());
        
        // Perform page-specific verification
        TestResult result = performPageVerification(page, testData);
        
        // Assert that all tests passed
        assertThat(result.allTestsPassed())
            .as("All page verification tests should pass for: " + testData.getTestName())
            .isTrue();
        
        Logger.getInstance().logParameterizedTest(testData.getTestName(), 
            "Test completed: " + result.getOverallResult());
    }
    
    @ParameterizedTest
    @MethodSource("getHighPriorityTestData")
    @DisplayName("High-priority smoke tests")
    void testHighPriorityScenarios(WebsiteTestData testData) {
        EnvironmentConfig.setEnvironment(testData.getEnvironment());
        
        Logger.getInstance().logParameterizedTest("SMOKE_" + testData.getTestName(), 
            "High-priority test execution");
        
        BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
        TestResult result = performPageVerification(page, testData);
        
        // Stricter assertions for high-priority tests
        assertThat(result.getPassedCount()).isGreaterThan(0);
        assertThat(result.getFailedCount()).isEqualTo(0);
        
        Logger.getInstance().logParameterizedTest("SMOKE_" + testData.getTestName(), 
            "Smoke test passed: " + result.getPassedCount() + " assertions verified");
    }
    
    @ParameterizedTest
    @MethodSource("getEnvironmentSpecificTestData")
    @DisplayName("Environment-specific test execution")
    void testEnvironmentSpecificScenarios(WebsiteTestData testData) {
        String originalEnv = EnvironmentConfig.getCurrentEnvironment();
        
        try {
            // Switch to test data's target environment
            EnvironmentConfig.setEnvironment(testData.getEnvironment());
            
            Logger.getInstance().logParameterizedTest("ENV_" + testData.getTestName(), 
                "Testing in " + testData.getEnvironment() + " environment");
            
            BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
            TestResult result = performPageVerification(page, testData);
            
            // Environment-aware assertions
            if ("prod".equals(testData.getEnvironment())) {
                // Production should have stricter requirements
                assertThat(result.allTestsPassed()).isTrue();
            } else {
                // Dev/staging can be more lenient
                assertThat(result.getPassedCount()).isGreaterThan(0);
            }
            
        } finally {
            // Restore original environment
            EnvironmentConfig.setEnvironment(originalEnv);
        }
    }
    
    /**
     * Performs page-specific verification based on page type
     */
    private TestResult performPageVerification(BasePage page, WebsiteTestData testData) {
        if (page instanceof GitHubHomePage) {
            GitHubHomePage githubPage = (GitHubHomePage) page;
            return githubPage.verifyPageElements(testData.getExpectedTitle(), 
                                               testData.getButtonText(), 
                                               testData.getTestName());
        } else {
            // Generic page verification for other page types
            TestResult result = new TestResult("Generic Page Verification", testData.getTestName());
            
            String actualTitle = page.getPageTitle();
            boolean titleMatches = testData.getExpectedTitle() == null || 
                                 actualTitle.toLowerCase().contains(testData.getExpectedTitle().toLowerCase());
            
            result.addTest("Page Title Check", titleMatches, 
                          "Expected: '" + testData.getExpectedTitle() + "', Actual: '" + actualTitle + "'");
            
            return result;
        }
    }
    
    // Data provider methods for JUnit 5 parameterized tests
    
    static Stream<WebsiteTestData> getAllWebsiteTestData() {
        TestDataProvider provider = new TestDataProvider();
        List<WebsiteTestData> allData = provider.getAllWebsiteTestData();
        Logger.getInstance().logDataProviderAction("JUnit5DataProvider", 
            "Providing " + allData.size() + " test cases for parameterized execution");
        return allData.stream();
    }
    
    static Stream<WebsiteTestData> getHighPriorityTestData() {
        TestDataProvider provider = new TestDataProvider();
        List<WebsiteTestData> highPriorityData = provider.getSmokeTestData();
        Logger.getInstance().logDataProviderAction("JUnit5DataProvider", 
            "Providing " + highPriorityData.size() + " high-priority test cases");
        return highPriorityData.stream();
    }
    
    static Stream<WebsiteTestData> getEnvironmentSpecificTestData() {
        TestDataProvider provider = new TestDataProvider();
        // Get data for current environment
        String currentEnv = EnvironmentConfig.getCurrentEnvironment();
        List<WebsiteTestData> envData = provider.getTestDataForEnvironment(currentEnv);
        Logger.getInstance().logDataProviderAction("JUnit5DataProvider", 
            "Providing " + envData.size() + " test cases for environment: " + currentEnv);
        return envData.stream();
    }
}