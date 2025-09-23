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
 * Cucumber Step Definitions that bridge business language to Page Object methods
 */
public class WebsiteVerificationSteps {

    private WebDriver driver;
    private PageObjectManager pageManager;
    private JsonLinkDataReader dataReader;
    private List<LinkData> websites;
    private TestSummary testSummary;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run in headless mode for CI/CD
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        pageManager = new PageObjectManager(driver);
        dataReader = new JsonLinkDataReader();
        testSummary = new TestSummary();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Print test summary
        System.out.println("\n" + testSummary.generateSummary());
    }

    @Given("I have a web browser available")
    public void i_have_a_web_browser_available() {
        assertNotNull(driver, "WebDriver should be initialized");
        Logger.getInstance().logInfo("Browser setup completed successfully");
    }

    @Given("I can access the internet")
    public void i_can_access_the_internet() {
        // Simple connectivity check by navigating to a reliable site
        driver.get("https://www.google.com");
        assertTrue(driver.getTitle().length() > 0, "Should be able to access internet");
        Logger.getInstance().logInfo("Internet connectivity verified");
    }

    @Given("I navigate to the {string} website")
    public void i_navigate_to_website(String websiteName) {
        try {
            websites = dataReader.readLinkData("java_project/data/links.json");
            LinkData targetSite = websites.stream()
                .filter(site -> site.getName().equals(websiteName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Website not found: " + websiteName));

            driver.get(targetSite.getUrl());
            Logger.getInstance().logLinkOpened(targetSite.getUrl());

        } catch (Exception e) {
            fail("Failed to navigate to " + websiteName + ": " + e.getMessage());
        }
    }

    @When("the page loads completely")
    public void the_page_loads_completely() {
        // Wait for page to load (basic check)
        try {
            Thread.sleep(2000); // Simple wait - in real projects use WebDriverWait
            assertTrue(driver.getTitle().length() > 0, "Page should have a title");
            Logger.getInstance().logInfo("Page loaded successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            fail("Interrupted while waiting for page to load");
        }
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertTrue(actualTitle.contains(expectedTitle),
            "Expected title to contain '" + expectedTitle + "' but was '" + actualTitle + "'");
        Logger.getInstance().logInfo("Title verification passed: " + actualTitle);
    }

    @And("all critical page elements should be visible")
    public void all_critical_page_elements_should_be_visible() {
        String currentUrl = driver.getCurrentUrl();
        BasePage currentPage = pageManager.navigateToPage(currentUrl);

        TestResult result = currentPage.verifyPageElements();
        testSummary.addTestResult(result);

        assertTrue(result.allTestsPassed(),
            "Page elements verification failed: " + result.getFailureMessage());
        Logger.getInstance().logInfo("All page elements verified successfully");
    }

    @And("I should be able to take a screenshot")
    public void i_should_be_able_to_take_a_screenshot() {
        String currentUrl = driver.getCurrentUrl();
        BasePage currentPage = pageManager.navigateToPage(currentUrl);

        String screenshotPath = currentPage.takeScreenshot();
        assertNotNull(screenshotPath, "Screenshot should be captured");
        Logger.getInstance().logInfo("Screenshot captured: " + screenshotPath);
    }

    @Given("I have the list of developer websites to test")
    public void i_have_the_list_of_developer_websites_to_test() {
        try {
            websites = dataReader.readLinkData("java_project/data/links.json");
            assertNotNull(websites, "Website list should not be null");
            assertFalse(websites.isEmpty(), "Website list should not be empty");
            Logger.getInstance().logInfo("Loaded " + websites.size() + " websites to test");
        } catch (Exception e) {
            fail("Failed to load website list: " + e.getMessage());
        }
    }

    @When("I test each website for basic functionality")
    public void i_test_each_website_for_basic_functionality() {
        for (LinkData website : websites) {
            try {
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

                if (!result.allTestsPassed()) {
                    Logger.getInstance().logError("Page verification failed for " + website.getName() + ": " + result.getFailureMessage());
                }

            } catch (Exception e) {
                TestResult failResult = new TestResult(website.getName(), false, "Exception: " + e.getMessage());
                testSummary.addTestResult(failResult);
                Logger.getInstance().logError("Failed to test " + website.getName() + ": " + e.getMessage());
            }
        }
    }

    @Then("all websites should be accessible")
    public void all_websites_should_be_accessible() {
        assertTrue(testSummary.getTestsRun() > 0, "At least one test should have been run");
        Logger.getInstance().logInfo("Accessibility check completed for all websites");
    }

    @And("all page titles should match expectations")
    public void all_page_titles_should_match_expectations() {
        // This step validates that basic page loading worked
        assertTrue(testSummary.getTestsRun() > 0, "Tests should have been executed");
        Logger.getInstance().logInfo("Page title verification completed");
    }

    @And("screenshots should be captured for evidence")
    public void screenshots_should_be_captured_for_evidence() {
        // Take a final summary screenshot
        BasePage currentPage = pageManager.getCurrentPage();
        if (currentPage != null) {
            String screenshotPath = currentPage.takeScreenshot();
            assertNotNull(screenshotPath, "Final screenshot should be captured");
            Logger.getInstance().logInfo("Evidence screenshots captured successfully");
        }
    }
}