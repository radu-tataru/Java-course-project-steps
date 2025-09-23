package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

/**
 * Main class demonstrating ExtentReports integration with existing Page Objects and BDD tests
 * Shows how to generate professional HTML reports for test results
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Step 9: ExtentReports Integration ===");
        System.out.println("This step demonstrates how to add professional HTML reporting to existing BDD tests.");
        System.out.println();

        // Initialize ExtentReports
        ExtentReportManager.initializeReport();

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
            System.out.println("Loaded " + websites.size() + " websites for reporting demonstration");
            System.out.println();

            // Execute tests with ExtentReports integration
            System.out.println("=== ExtentReports Demo: Professional Test Reporting ===");

            for (LinkData website : websites) {
                // Create a test in ExtentReports
                ExtentReportManager.createTest("Website Verification - " + website.getName(),
                    "Verify accessibility and functionality of " + website.getName());

                System.out.println("\n📊 Creating ExtentReport test for: " + website.getName());

                try {
                    // Log website information to report
                    ExtentReportManager.logWebsiteInfo(website);
                    ExtentReportManager.logInfo("Starting test execution for " + website.getName());

                    // Navigate to website
                    System.out.println("   ↳ Navigating to: " + website.getUrl());
                    driver.get(website.getUrl());
                    logger.logLinkOpened(website.getUrl());
                    ExtentReportManager.logInfo("Successfully navigated to " + website.getUrl());

                    // Wait for page to load
                    Thread.sleep(2000);
                    ExtentReportManager.logInfo("Page loaded completely");

                    // Verify title
                    String actualTitle = driver.getTitle();
                    boolean titleMatches = actualTitle.contains(website.getExpectedTitle());
                    System.out.println("   ↳ Title verification: " + (titleMatches ? "✅ PASS" : "❌ FAIL"));

                    if (titleMatches) {
                        ExtentReportManager.logPass("Title verification passed: " + actualTitle);
                    } else {
                        ExtentReportManager.logFail("Title verification failed. Expected: '" +
                            website.getExpectedTitle() + "', Actual: '" + actualTitle + "'");
                    }

                    // Verify page elements
                    System.out.println("   ↳ Verifying page elements...");
                    BasePage currentPage = pageManager.navigateToPage(website.getUrl());
                    TestResult result = currentPage.verifyPageElements();
                    testSummary.addTestResult(result);

                    // Log result to ExtentReports with screenshot
                    ExtentReportManager.logTestResult(result, driver);

                    // Capture screenshot for documentation
                    ExtentReportManager.captureScreenshot(driver, "Page verification completed");

                    if (result.allTestsPassed()) {
                        System.out.println("   ↳ Page elements: ✅ PASS");
                        ExtentReportManager.logPass("All page elements verified successfully");
                    } else {
                        System.out.println("   ↳ Page elements: ❌ FAIL - " + result.getFailureMessage());
                        ExtentReportManager.logFail("Page elements verification failed: " + result.getFailureMessage());
                    }

                } catch (Exception e) {
                    System.out.println("   ↳ Exception: ❌ FAIL - " + e.getMessage());
                    ExtentReportManager.logFail("Test execution failed: " + e.getMessage());
                    ExtentReportManager.captureScreenshot(driver, "Test failure");

                    TestResult failResult = new TestResult(website.getName(), false, "Exception: " + e.getMessage());
                    testSummary.addTestResult(failResult);
                }
            }

            System.out.println();
            System.out.println("=== ExtentReports Generation Complete ===");
            System.out.println(testSummary.generateSummary());
            System.out.println();

            System.out.println("=== Key ExtentReports Features Demonstrated ===");
            System.out.println("✓ Professional HTML reports with rich formatting");
            System.out.println("✓ Screenshot integration for visual evidence");
            System.out.println("✓ Test categorization and status tracking");
            System.out.println("✓ System information and environment details");
            System.out.println("✓ Timeline and execution history");
            System.out.println();

            System.out.println("=== Generated Reports ===");
            System.out.println("📊 ExtentReports HTML: target/extent-reports/ExtentReport.html");
            System.out.println("📸 Screenshots: target/extent-reports/screenshots/");
            System.out.println();

            System.out.println("To run full Cucumber tests with ExtentReports:");
            System.out.println("mvn test");

        } catch (Exception e) {
            System.err.println("Error during ExtentReports demonstration: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Finalize and save the report
            ExtentReportManager.flushReport();
            driver.quit();
        }
    }
}
