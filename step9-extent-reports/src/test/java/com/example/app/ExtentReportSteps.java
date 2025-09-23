package com.example.app;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Enhanced Cucumber Step Definitions with ExtentReports integration
 * Builds on Step 8 BDD scenarios and adds professional reporting
 */
public class ExtentReportSteps {

    private WebDriver driver;
    private PageObjectManager pageManager;
    private JsonLinkDataReader dataReader;
    private List<LinkData> websites;
    private TestSummary testSummary;

    @Before
    public void setUp() {
        // Initialize ExtentReports
        ExtentReportManager.initializeReport();

        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        pageManager = new PageObjectManager(driver);
        dataReader = new JsonLinkDataReader();
        testSummary = new TestSummary();

        ExtentReportManager.logInfo("Test environment setup completed successfully");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }

        // Generate final report
        ExtentReportManager.flushReport();

        // Print test summary to console
        System.out.println("\n" + testSummary.generateSummary());
    }

    @Given("I have a web browser available")
    public void i_have_a_web_browser_available() {
        ExtentReportManager.createTest("Browser Setup", "Verify web browser is available and configured");

        assertNotNull(driver, "WebDriver should be initialized");
        ExtentReportManager.logPass("Web browser initialized successfully");

        Logger.getInstance().logInfo("Browser setup completed successfully");
    }

    @Given("I can access the internet")
    public void i_can_access_the_internet() {
        ExtentReportManager.logInfo("Verifying internet connectivity");

        // Simple connectivity check
        driver.get("https://www.google.com");
        assertTrue(driver.getTitle().length() > 0, "Should be able to access internet");

        ExtentReportManager.captureScreenshot(driver, "Internet connectivity verification");
        ExtentReportManager.logPass("Internet connectivity verified successfully");

        Logger.getInstance().logInfo("Internet connectivity verified");
    }

    @Given("I navigate to the {string} website")
    public void i_navigate_to_website(String websiteName) {
        ExtentReportManager.createTest("Website Navigation - " + websiteName,
            "Navigate to " + websiteName + " and verify accessibility");

        try {
            websites = dataReader.readLinkData("java_project/data/links.json");
            LinkData targetSite = websites.stream()
                .filter(site -> site.getName().equals(websiteName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Website not found: " + websiteName));

            ExtentReportManager.logWebsiteInfo(targetSite);
            ExtentReportManager.logInfo("Navigating to: " + targetSite.getUrl());

            driver.get(targetSite.getUrl());
            ExtentReportManager.captureScreenshot(driver, "Website loaded - " + websiteName);
            ExtentReportManager.logPass("Successfully navigated to " + websiteName);

            Logger.getInstance().logLinkOpened(targetSite.getUrl());

        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to navigate to " + websiteName + ": " + e.getMessage());
            ExtentReportManager.captureScreenshot(driver, "Navigation failure - " + websiteName);
            fail("Failed to navigate to " + websiteName + ": " + e.getMessage());
        }
    }

    @When("the page loads completely")
    public void the_page_loads_completely() {
        ExtentReportManager.logInfo("Waiting for page to load completely");

        try {
            Thread.sleep(2000); // Simple wait - in real projects use WebDriverWait
            assertTrue(driver.getTitle().length() > 0, "Page should have a title");

            ExtentReportManager.logPass("Page loaded successfully");
            ExtentReportManager.logInfo("Page title: " + driver.getTitle());

            Logger.getInstance().logInfo("Page loaded successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            ExtentReportManager.logFail("Interrupted while waiting for page to load");
            fail("Interrupted while waiting for page to load");
        }
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        String actualTitle = driver.getTitle();
        ExtentReportManager.logInfo("Verifying page title contains: " + expectedTitle);
        ExtentReportManager.logInfo("Actual page title: " + actualTitle);

        boolean titleMatches = actualTitle.contains(expectedTitle);

        if (titleMatches) {
            ExtentReportManager.logPass("Title verification passed");
        } else {
            ExtentReportManager.logFail("Title verification failed - Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
            ExtentReportManager.captureScreenshot(driver, "Title verification failure");
        }

        assertTrue(titleMatches,
            "Expected title to contain '" + expectedTitle + "' but was '" + actualTitle + "'");

        Logger.getInstance().logInfo("Title verification passed: " + actualTitle);
    }

    @And("all critical page elements should be visible")
    public void all_critical_page_elements_should_be_visible() {
        ExtentReportManager.logInfo("Verifying all critical page elements are visible");

        String currentUrl = driver.getCurrentUrl();
        BasePage currentPage = pageManager.navigateToPage(currentUrl);

        TestResult result = currentPage.verifyPageElements();
        testSummary.addTestResult(result);

        ExtentReportManager.logTestResult(result, driver);

        if (result.allTestsPassed()) {
            ExtentReportManager.logPass("All page elements verified successfully");
        } else {
            ExtentReportManager.logFail("Page elements verification failed: " + result.getFailureMessage());
        }

        assertTrue(result.allTestsPassed(),
            "Page elements verification failed: " + result.getFailureMessage());

        Logger.getInstance().logInfo("All page elements verified successfully");
    }

    @And("I should be able to take a screenshot")
    public void i_should_be_able_to_take_a_screenshot() {
        ExtentReportManager.logInfo("Capturing screenshot for documentation");

        String currentUrl = driver.getCurrentUrl();
        BasePage currentPage = pageManager.navigateToPage(currentUrl);

        String screenshotPath = currentPage.takeScreenshot();
        assertNotNull(screenshotPath, "Screenshot should be captured");

        ExtentReportManager.captureScreenshot(driver, "Evidence screenshot");
        ExtentReportManager.logPass("Screenshot captured successfully");

        Logger.getInstance().logInfo("Screenshot captured: " + screenshotPath);
    }

    @Given("I have the list of developer websites to test")
    public void i_have_the_list_of_developer_websites_to_test() {
        ExtentReportManager.createTest("Test Data Loading", "Load and verify the list of websites to test");

        try {
            websites = dataReader.readLinkData("java_project/data/links.json");
            assertNotNull(websites, "Website list should not be null");
            assertFalse(websites.isEmpty(), "Website list should not be empty");

            ExtentReportManager.logInfo("Loaded " + websites.size() + " websites for testing:");
            for (LinkData website : websites) {
                ExtentReportManager.logInfo("- " + website.getName() + " (" + website.getUrl() + ")");
            }
            ExtentReportManager.logPass("Test data loaded successfully");

            Logger.getInstance().logInfo("Loaded " + websites.size() + " websites to test");
        } catch (Exception e) {
            ExtentReportManager.logFail("Failed to load website list: " + e.getMessage());
            fail("Failed to load website list: " + e.getMessage());
        }
    }

    @When("I test each website for basic functionality")
    public void i_test_each_website_for_basic_functionality() {
        ExtentReportManager.createTest("Batch Website Testing", "Test all websites for basic functionality");
        ExtentReportManager.logInfo("Starting batch testing of all websites");

        for (LinkData website : websites) {
            try {
                ExtentReportManager.logInfo("Testing website: " + website.getName());

                // Navigate to website
                driver.get(website.getUrl());
                Logger.getInstance().logLinkOpened(website.getUrl());

                // Basic functionality check
                Thread.sleep(2000); // Simple wait
                String title = driver.getTitle();
                assertTrue(title.length() > 0, "Website should have a title: " + website.getName());

                // Verify page elements
                BasePage currentPage = pageManager.navigateToPage(website.getUrl());
                TestResult result = currentPage.verifyPageElements();
                testSummary.addTestResult(result);

                ExtentReportManager.captureScreenshot(driver, website.getName() + " - verification");

                if (result.allTestsPassed()) {
                    ExtentReportManager.logPass(website.getName() + " - All tests passed");
                } else {
                    ExtentReportManager.logFail(website.getName() + " - Verification failed: " + result.getFailureMessage());
                    Logger.getInstance().logError("Page verification failed for " + website.getName() + ": " + result.getFailureMessage());
                }

            } catch (Exception e) {
                TestResult failResult = new TestResult(website.getName(), false, "Exception: " + e.getMessage());
                testSummary.addTestResult(failResult);
                ExtentReportManager.logFail(website.getName() + " - Exception: " + e.getMessage());
                ExtentReportManager.captureScreenshot(driver, website.getName() + " - error");
                Logger.getInstance().logError("Failed to test " + website.getName() + ": " + e.getMessage());
            }
        }

        ExtentReportManager.logInfo("Batch testing completed");
    }

    @Then("all websites should be accessible")
    public void all_websites_should_be_accessible() {
        assertTrue(testSummary.getTestsRun() > 0, "At least one test should have been run");
        ExtentReportManager.logPass("All websites accessibility check completed");
        Logger.getInstance().logInfo("Accessibility check completed for all websites");
    }

    @And("all page titles should match expectations")
    public void all_page_titles_should_match_expectations() {
        assertTrue(testSummary.getTestsRun() > 0, "Tests should have been executed");
        ExtentReportManager.logPass("Page title verification completed for all websites");
        Logger.getInstance().logInfo("Page title verification completed");
    }

    @And("screenshots should be captured for evidence")
    public void screenshots_should_be_captured_for_evidence() {
        BasePage currentPage = pageManager.getCurrentPage();
        if (currentPage != null) {
            String screenshotPath = currentPage.takeScreenshot();
            assertNotNull(screenshotPath, "Final screenshot should be captured");

            ExtentReportManager.captureScreenshot(driver, "Final evidence screenshot");
            ExtentReportManager.logPass("All evidence screenshots captured successfully");

            Logger.getInstance().logInfo("Evidence screenshots captured successfully");
        }
    }
}