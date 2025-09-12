package com.example.app;

import org.openqa.selenium.WebDriver;

/**
 * Factory class to manage page object creation and lifecycle
 */
public class PageObjectManager {
    private WebDriver driver;
    
    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }
    
    /**
     * Creates appropriate page object based on URL
     * @param url The URL to create page object for
     * @return BasePage object (specific implementation based on URL)
     */
    public BasePage getPageObject(String url) {
        if (url.contains("github.com")) {
            return new GitHubHomePage(driver);
        } else if (url.contains("junit.org")) {
            return new JUnitHomePage(driver);
        } else if (url.contains("maven.apache.org")) {
            return new MavenHomePage(driver);
        } else if (url.contains("selenium.dev")) {
            return new SeleniumHomePage(driver);
        } else {
            // Return generic BasePage for unknown URLs
            return new BasePage(driver);
        }
    }
    
    /**
     * Creates page object and navigates to the URL
     * @param url URL to navigate to
     * @return BasePage object for the URL
     */
    public BasePage navigateToPage(String url) {
        Logger.getInstance().log("Navigating to: " + url);
        driver.get(url);
        
        // Wait a moment for page to start loading
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        BasePage page = getPageObject(url);
        page.verifyPageLoaded();
        
        return page;
    }
    
    /**
     * Gets page object type name based on URL
     * @param url URL to check
     * @return Page type name
     */
    public String getPageTypeName(String url) {
        if (url.contains("github.com")) {
            return "GitHub HomePage";
        } else if (url.contains("junit.org")) {
            return "JUnit HomePage";
        } else if (url.contains("maven.apache.org")) {
            return "Maven HomePage";
        } else if (url.contains("selenium.dev")) {
            return "Selenium HomePage";
        } else {
            return "Generic Page";
        }
    }
}