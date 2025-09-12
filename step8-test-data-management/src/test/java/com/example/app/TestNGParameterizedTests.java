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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestNG-based parameterized tests demonstrating advanced data provider features
 */
public class TestNGParameterizedTests {
    
    private WebDriver driver;
    private PageObjectManager pageManager;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        
        driver = new ChromeDriver(options);
        pageManager = new PageObjectManager(driver);
        
        Logger.getInstance().log("TestNG setup completed");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            Logger.getInstance().log("TestNG cleanup completed");
        }
    }
    
    @Test(dataProvider = "websiteTestData", 
          description = "Comprehensive website testing with TestNG data provider")
    public void testWebsiteWithTestNGDataProvider(WebsiteTestData testData) {
        // Set environment from test data
        EnvironmentConfig.setEnvironment(testData.getEnvironment());
        
        Logger.getInstance().logParameterizedTest("TESTNG_" + testData.getTestName(), 
            "TestNG execution with data: " + testData.toString());
        
        // Execute test
        BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
        page.verifyPageLoaded(testData.getTestName());
        
        TestResult result = performPageVerification(page, testData);
        
        // TestNG assertions
        assertThat(result.allTestsPassed())
            .as("TestNG assertion for: " + testData.getTestName())
            .isTrue();
    }
    
    @Test(dataProvider = "priorityFilteredData", 
          groups = {"smoke", "high-priority"},
          description = "High-priority tests with TestNG grouping")
    public void testHighPriorityWithTestNG(WebsiteTestData testData) {
        Logger.getInstance().logParameterizedTest("SMOKE_TESTNG_" + testData.getTestName(), 
            "TestNG smoke test execution");
        
        EnvironmentConfig.setEnvironment(testData.getEnvironment());
        
        BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
        TestResult result = performPageVerification(page, testData);
        
        // Smoke tests must pass completely
        assertThat(result.getFailedCount()).isEqualTo(0);
        assertThat(result.getPassedCount()).isGreaterThan(0);
    }
    
    @Test(dataProvider = "environmentSpecificData",
          description = "Environment-specific test execution with TestNG")
    public void testEnvironmentSpecificWithTestNG(String environment, WebsiteTestData testData) {
        Logger.getInstance().logParameterizedTest("ENV_TESTNG_" + testData.getTestName(), 
            "TestNG environment test: " + environment);
        
        EnvironmentConfig.setEnvironment(environment);
        
        BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
        TestResult result = performPageVerification(page, testData);
        
        Logger.getInstance().logParameterizedTest("ENV_TESTNG_" + testData.getTestName(), 
            "Environment " + environment + " result: " + result.getOverallResult());
        
        assertThat(result.getPassedCount()).isGreaterThan(0);
    }
    
    /**
     * TestNG data provider for all website test data
     */
    @DataProvider(name = "websiteTestData")
    public Object[][] websiteTestDataProvider() {
        TestDataProvider provider = new TestDataProvider();
        List<WebsiteTestData> testData = provider.getAllWebsiteTestData();
        
        Logger.getInstance().logDataProviderAction("TestNGDataProvider", 
            "Providing " + testData.size() + " test cases for TestNG execution");
        
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        
        return data;
    }
    
    /**
     * TestNG data provider for high-priority tests only
     */
    @DataProvider(name = "priorityFilteredData")
    public Object[][] priorityFilteredDataProvider() {
        TestDataProvider provider = new TestDataProvider();
        List<WebsiteTestData> highPriorityData = provider.getSmokeTestData();
        
        Logger.getInstance().logDataProviderAction("TestNGDataProvider", 
            "Providing " + highPriorityData.size() + " high-priority test cases");
        
        Object[][] data = new Object[highPriorityData.size()][1];
        for (int i = 0; i < highPriorityData.size(); i++) {
            data[i][0] = highPriorityData.get(i);
        }
        
        return data;
    }
    
    /**
     * TestNG data provider that combines environment and test data
     */
    @DataProvider(name = "environmentSpecificData")
    public Object[][] environmentSpecificDataProvider() {
        TestDataProvider provider = new TestDataProvider();
        String[] environments = {"dev", "staging", "prod"};
        List<WebsiteTestData> allData = provider.getAllWebsiteTestData();
        
        // Create combinations of environments and test data
        Object[][] data = new Object[environments.length * allData.size()][2];
        int index = 0;
        
        for (String env : environments) {
            for (WebsiteTestData testData : allData) {
                data[index][0] = env;
                data[index][1] = testData;
                index++;
            }
        }
        
        Logger.getInstance().logDataProviderAction("TestNGDataProvider", 
            "Providing " + data.length + " environment-specific test combinations");
        
        return data;
    }
    
    /**
     * Performs page verification (shared logic with JUnit tests)
     */
    private TestResult performPageVerification(BasePage page, WebsiteTestData testData) {
        if (page instanceof GitHubHomePage) {
            GitHubHomePage githubPage = (GitHubHomePage) page;
            return githubPage.verifyPageElements(testData.getExpectedTitle(), 
                                               testData.getButtonText(), 
                                               testData.getTestName());
        } else {
            TestResult result = new TestResult("Generic Page Verification (TestNG)", testData.getTestName());
            
            String actualTitle = page.getPageTitle();
            boolean titleMatches = testData.getExpectedTitle() == null || 
                                 actualTitle.toLowerCase().contains(testData.getExpectedTitle().toLowerCase());
            
            result.addTest("Page Title Check", titleMatches, 
                          "Expected: '" + testData.getExpectedTitle() + "', Actual: '" + actualTitle + "'");
            
            return result;
        }
    }
}