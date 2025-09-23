package com.example.app;

import com.example.app.reporting.ExtentReportManager;
import com.example.app.reporting.TestResultAnalyzer;
import com.example.app.notifications.SlackNotifier;
import com.example.app.data.providers.ExcelDataProvider;
import com.example.app.data.models.WebsiteTestData;
import com.example.app.pages.PageObjectManager;
import com.example.app.pages.BasePage;
import com.example.app.config.EnvironmentConfig;
import com.example.app.utils.Logger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Step 9: CI/CD Integration & Automated Reporting
 * 
 * This application demonstrates:
 * - ExtentReports integration for comprehensive test reporting
 * - Slack notifications for CI/CD pipeline alerts
 * - Quality gates and automated test analysis
 * - Professional test execution with detailed analytics
 */
public class Main {
    private static final Logger logger = Logger.getInstance();
    private static WebDriver driver;
    private static List<TestResultAnalyzer.TestResult> testResults = new ArrayList<>();
    
    public static void main(String[] args) {
        logger.log("=== STEP 9: CI/CD INTEGRATION & AUTOMATED REPORTING ===");
        logger.log("Starting CI/CD-enabled test execution at: " + 
                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // Initialize reporting
        ExtentReportManager.initializeReport();
        logger.log("ExtentReports initialized at: " + ExtentReportManager.getReportPath());
        
        try {
            // Setup WebDriver
            setupWebDriver();
            
            // Load test data
            ExcelDataProvider dataProvider = new ExcelDataProvider();
            List<WebsiteTestData> testDataList = dataProvider.readWebsiteData("java_project/data/website-test-data.xlsx");
            logger.log("Loaded " + testDataList.size() + " test scenarios from Excel");
            
            // Execute tests with reporting
            executeTestsWithReporting(testDataList);
            
            // Analyze results and check quality gates
            TestResultAnalyzer.TestSummary summary = TestResultAnalyzer.analyzeResults(testResults);
            TestResultAnalyzer.QualityGateResult qualityResult = TestResultAnalyzer.checkQualityGates(summary);
            
            // Generate detailed report
            String detailedReport = TestResultAnalyzer.generateDetailedReport(summary);
            logger.log("\n" + detailedReport);
            
            // Send notifications (if configured)
            sendNotifications(summary, qualityResult);
            
            // Log final status
            logFinalStatus(summary, qualityResult);
            
        } catch (Exception e) {
            logger.log("❌ Application execution failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    
    private static void setupWebDriver() {
        logger.log("Setting up WebDriver for CI/CD environment...");
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // CI/CD environment optimizations
        if (System.getenv("CI") != null) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            logger.log("Running in CI/CD mode with headless browser");
        }
        
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        logger.log("ChromeDriver initialized successfully");
    }
    
    private static void executeTestsWithReporting(List<WebsiteTestData> testDataList) {
        logger.log("Executing " + testDataList.size() + " tests with ExtentReports integration...");
        
        for (WebsiteTestData testData : testDataList) {
            long startTime = System.currentTimeMillis();
            
            // Create ExtentTest for this scenario
            var extentTest = ExtentReportManager.createTest(
                "Website Test: " + testData.getTestName(),
                "Verify " + testData.getWebsite() + " website functionality"
            );
            
            try {
                ExtentReportManager.attachEnvironmentInfo();
                ExtentReportManager.attachTestData(testData);
                
                logger.log("🧪 Testing: " + testData.getTestName());
                extentTest.info("Starting test execution for: " + testData.getWebsite());
                
                // Set environment
                EnvironmentConfig.setEnvironment(testData.getEnvironment());
                extentTest.info("Environment set to: " + testData.getEnvironment());
                
                // Execute test using Page Object Model
                PageObjectManager pageManager = new PageObjectManager(driver);
                driver.get(testData.getWebsiteUrl());
                
                ExtentReportManager.captureScreenshot(driver, "Page loaded");
                extentTest.info("Navigated to: " + testData.getWebsiteUrl());
                
                BasePage page = pageManager.getCurrentPage();
                var result = page.verifyPageElements();
                
                ExtentReportManager.captureScreenshot(driver, "Page verification");
                
                long executionTime = System.currentTimeMillis() - startTime;
                
                if (result.allTestsPassed()) {
                    extentTest.pass("✅ Test completed successfully");
                    extentTest.info("Page title verified: " + driver.getTitle());
                    logger.log("✅ " + testData.getTestName() + " - PASSED (" + executionTime + "ms)");
                    
                    testResults.add(new TestResultAnalyzer.TestResult(
                        testData.getTestName(), 
                        TestResultAnalyzer.TestStatus.PASSED, 
                        null, 
                        executionTime
                    ));
                } else {
                    String failureMessage = "Page verification failed: " + result.getFailureMessage();
                    extentTest.fail("❌ " + failureMessage);
                    ExtentReportManager.captureScreenshot(driver, "Test failure");
                    logger.log("❌ " + testData.getTestName() + " - FAILED: " + failureMessage);
                    
                    testResults.add(new TestResultAnalyzer.TestResult(
                        testData.getTestName(), 
                        TestResultAnalyzer.TestStatus.FAILED, 
                        failureMessage, 
                        executionTime
                    ));
                }
                
            } catch (Exception e) {
                long executionTime = System.currentTimeMillis() - startTime;
                String errorMessage = "Test execution error: " + e.getMessage();
                
                extentTest.fail("💥 " + errorMessage);
                ExtentReportManager.captureScreenshot(driver, "Exception occurred");
                logger.log("💥 " + testData.getTestName() + " - ERROR: " + errorMessage);
                
                testResults.add(new TestResultAnalyzer.TestResult(
                    testData.getTestName(), 
                    TestResultAnalyzer.TestStatus.FAILED, 
                    errorMessage, 
                    executionTime
                ));
            }
        }
        
        ExtentReportManager.flushReport();
        logger.log("ExtentReports generated successfully");
    }
    
    private static void sendNotifications(TestResultAnalyzer.TestSummary summary, TestResultAnalyzer.QualityGateResult qualityResult) {
        try {
            // Only send notifications if Slack webhook is configured
            String slackWebhook = System.getenv("SLACK_WEBHOOK");
            if (slackWebhook != null && !slackWebhook.isEmpty()) {
                logger.log("Sending Slack notifications...");
                
                SlackNotifier slackNotifier = new SlackNotifier(slackWebhook, "#qa-alerts");
                slackNotifier.sendTestCompletionNotification(summary);
                
                // Send pipeline status
                String pipelineStatus = qualityResult.isOverallPassed() ? "success" : "failure";
                slackNotifier.sendPipelineStatusNotification("Step 9 Test Suite", pipelineStatus, null);
                
                logger.log("Slack notifications sent successfully");
            } else {
                logger.log("Slack webhook not configured - skipping notifications");
            }
        } catch (Exception e) {
            logger.log("Warning: Failed to send notifications: " + e.getMessage());
        }
    }
    
    private static void logFinalStatus(TestResultAnalyzer.TestSummary summary, TestResultAnalyzer.QualityGateResult qualityResult) {
        logger.log("\n" + "=".repeat(60));
        logger.log("FINAL EXECUTION STATUS");
        logger.log("=".repeat(60));
        
        if (qualityResult.isOverallPassed()) {
            logger.log("🎉 ALL QUALITY GATES PASSED! 🎉");
            logger.log("✅ Success Rate: " + summary.getSuccessRate() + "%");
            logger.log("✅ Execution Time: " + summary.getExecutionTime() + "ms");
            logger.log("✅ Failed Tests: " + summary.getFailedTests());
        } else {
            logger.log("🚨 QUALITY GATES FAILED 🚨");
            for (String message : qualityResult.getGateMessages()) {
                logger.log("❌ " + message);
            }
        }
        
        logger.log("📊 ExtentReports: " + ExtentReportManager.getReportPath());
        logger.log("📋 Test execution completed at: " + 
                  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
    
    private static void cleanup() {
        if (driver != null) {
            driver.quit();
            logger.log("WebDriver session closed");
        }
        
        ExtentReportManager.flushReport();
        logger.showLogLocation();
    }
}