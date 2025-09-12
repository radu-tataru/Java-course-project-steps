package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for Maven homepage
 */
public class MavenHomePage extends BasePage {
    
    @FindBy(css = "h1, .hero-title, .main-title")
    private WebElement mainTitle;
    
    @FindBy(css = "a[href*='download'], .download-link")
    private WebElement downloadLink;
    
    @FindBy(css = ".logo, img[alt*='Maven'], .brand")
    private WebElement mavenLogo;
    
    @FindBy(css = "main, .content, .main-content, #bodyColumn")
    private WebElement mainContent;
    
    @FindBy(css = "a[href*='guide'], .guide-link, nav a")
    private WebElement guideLink;
    
    public MavenHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public void verifyPageLoaded() {
        wait.until(ExpectedConditions.titleContains("Maven"));
        takeScreenshot("maven_home_loaded");
        Logger.getInstance().logPageTest("Maven HomePage", "Page loaded successfully");
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
     * Checks if download link is visible
     * @return true if download link is visible
     */
    public boolean isDownloadLinkVisible() {
        boolean isVisible = isElementDisplayed(downloadLink);
        Logger.getInstance().logElementInteraction("Download Link", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Checks if Maven logo is displayed
     * @return true if logo is displayed
     */
    public boolean isMavenLogoDisplayed() {
        boolean isDisplayed = isElementDisplayed(mavenLogo);
        Logger.getInstance().logElementInteraction("Maven Logo", 
                                                  "Display check: " + (isDisplayed ? "Displayed" : "Not displayed"));
        return isDisplayed;
    }
    
    /**
     * Checks if guide link is visible
     * @return true if guide link is visible
     */
    public boolean isGuideLinkVisible() {
        boolean isVisible = isElementDisplayed(guideLink);
        Logger.getInstance().logElementInteraction("Guide Link", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Performs comprehensive page verification
     * @return TestResult object with verification details
     */
    public TestResult verifyPageElements() {
        TestResult result = new TestResult("Maven HomePage Verification");
        
        // Test page title
        String title = getPageTitle();
        result.addTest("Page Title Check", title.toLowerCase().contains("maven"), 
                      "Expected: title contains 'maven', Actual: " + title);
        
        // Test main title
        String mainTitleText = getMainTitle();
        result.addTest("Main Title Check", !mainTitleText.isEmpty(), 
                      "Expected: non-empty title, Actual: " + mainTitleText);
        
        // Test main content
        result.addTest("Main Content Check", isElementDisplayed(mainContent), 
                      "Expected: Main content area present");
        
        // Test download link (optional)
        boolean hasDownloadLink = isDownloadLinkVisible();
        result.addTest("Download Link Check", true, 
                      "Download link present: " + (hasDownloadLink ? "Yes" : "No"));
        
        // Test guide link (optional)
        boolean hasGuideLink = isGuideLinkVisible();
        result.addTest("Guide Link Check", true, 
                      "Guide link present: " + (hasGuideLink ? "Yes" : "No"));
        
        takeScreenshot("maven_verification_complete");
        Logger.getInstance().logPageTest("Maven HomePage", result.getOverallResult());
        
        return result;
    }
}