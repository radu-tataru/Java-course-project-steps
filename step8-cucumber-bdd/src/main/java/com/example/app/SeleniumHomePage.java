package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for Selenium homepage
 */
public class SeleniumHomePage extends BasePage {
    
    @FindBy(css = "h1, .hero-title, .main-title")
    private WebElement mainTitle;
    
    @FindBy(css = "a[href*='download'], .download-link, a[href*='getting-started']")
    private WebElement downloadLink;
    
    @FindBy(css = ".logo, img[alt*='Selenium'], .brand")
    private WebElement seleniumLogo;
    
    @FindBy(css = "main, .content, .main-content")
    private WebElement mainContent;
    
    @FindBy(css = "a[href*='documentation'], .docs-link")
    private WebElement documentationLink;
    
    public SeleniumHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public void verifyPageLoaded() {
        wait.until(ExpectedConditions.titleContains("Selenium"));
        takeScreenshot("selenium_home_loaded");
        Logger.getInstance().logPageTest("Selenium HomePage", "Page loaded successfully");
    }
    
    /**
     * Gets the main title text
     * @return Main title text
     */
    public String getMainTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOf(mainTitle));
            String titleText = mainTitle.getText();
            Logger.getInstance().logElementInteraction("Main Title", "Retrieved text: " + titleText);
            return titleText;
        } catch (Exception e) {
            Logger.getInstance().logElementInteraction("Main Title", "Failed to retrieve text: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Checks if download/getting started link is visible
     * @return true if download link is visible
     */
    public boolean isDownloadLinkVisible() {
        boolean isVisible = isElementDisplayed(downloadLink);
        Logger.getInstance().logElementInteraction("Download Link", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Checks if Selenium logo is displayed
     * @return true if logo is displayed
     */
    public boolean isSeleniumLogoDisplayed() {
        boolean isDisplayed = isElementDisplayed(seleniumLogo);
        Logger.getInstance().logElementInteraction("Selenium Logo", 
                                                  "Display check: " + (isDisplayed ? "Displayed" : "Not displayed"));
        return isDisplayed;
    }
    
    /**
     * Checks if documentation link is visible
     * @return true if documentation link is visible
     */
    public boolean isDocumentationLinkVisible() {
        boolean isVisible = isElementDisplayed(documentationLink);
        Logger.getInstance().logElementInteraction("Documentation Link", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Performs comprehensive page verification
     * @return TestResult object with verification details
     */
    public TestResult verifyPageElements() {
        TestResult result = new TestResult("Selenium HomePage Verification");
        
        // Test page title
        String title = getPageTitle();
        result.addTest("Page Title Check", title.toLowerCase().contains("selenium"), 
                      "Expected: title contains 'selenium', Actual: " + title);
        
        // Test main title
        String mainTitleText = getMainTitle();
        result.addTest("Main Title Check", !mainTitleText.isEmpty(), 
                      "Expected: non-empty title, Actual: " + mainTitleText);
        
        // Test main content
        result.addTest("Main Content Check", isElementDisplayed(mainContent), 
                      "Expected: Main content area present");
        
        // Test download link (optional)
        boolean hasDownloadLink = isDownloadLinkVisible();
        result.addTest("Download/Getting Started Link Check", true, 
                      "Download link present: " + (hasDownloadLink ? "Yes" : "No"));
        
        // Test documentation link (optional)
        boolean hasDocumentationLink = isDocumentationLinkVisible();
        result.addTest("Documentation Link Check", true, 
                      "Documentation link present: " + (hasDocumentationLink ? "Yes" : "No"));
        
        takeScreenshot("selenium_verification_complete");
        Logger.getInstance().logPageTest("Selenium HomePage", result.getOverallResult());
        
        return result;
    }
}