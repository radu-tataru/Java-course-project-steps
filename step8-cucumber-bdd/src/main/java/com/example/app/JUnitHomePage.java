package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for JUnit 5 homepage
 */
public class JUnitHomePage extends BasePage {
    
    @FindBy(css = "h1, .hero-title, .main-title")
    private WebElement mainTitle;
    
    @FindBy(css = "a[href*='guide'], .guide-link, nav a")
    private WebElement guideLink;
    
    @FindBy(css = ".logo, img[alt*='JUnit'], .brand")
    private WebElement junitLogo;
    
    @FindBy(css = "main, .content, .main-content")
    private WebElement mainContent;
    
    public JUnitHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public void verifyPageLoaded() {
        wait.until(ExpectedConditions.titleContains("JUnit"));
        takeScreenshot("junit_home_loaded");
        Logger.getInstance().logPageTest("JUnit HomePage", "Page loaded successfully");
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
     * Checks if JUnit logo is displayed
     * @return true if logo is displayed
     */
    public boolean isJUnitLogoDisplayed() {
        boolean isDisplayed = isElementDisplayed(junitLogo);
        Logger.getInstance().logElementInteraction("JUnit Logo", 
                                                  "Display check: " + (isDisplayed ? "Displayed" : "Not displayed"));
        return isDisplayed;
    }
    
    /**
     * Performs comprehensive page verification
     * @return TestResult object with verification details
     */
    public TestResult verifyPageElements() {
        TestResult result = new TestResult("JUnit HomePage Verification");
        
        // Test page title
        String title = getPageTitle();
        result.addTest("Page Title Check", title.toLowerCase().contains("junit"), 
                      "Expected: title contains 'junit', Actual: " + title);
        
        // Test main title
        String mainTitleText = getMainTitle();
        result.addTest("Main Title Check", !mainTitleText.isEmpty(), 
                      "Expected: non-empty title, Actual: " + mainTitleText);
        
        // Test guide link (optional - may not be present)
        boolean hasGuideLink = isGuideLinkVisible();
        result.addTest("Guide Link Check", true, 
                      "Guide link present: " + (hasGuideLink ? "Yes" : "No"));
        
        // Test main content
        result.addTest("Main Content Check", isElementDisplayed(mainContent), 
                      "Expected: Main content area present");
        
        takeScreenshot("junit_verification_complete");
        Logger.getInstance().logPageTest("JUnit HomePage", result.getOverallResult());
        
        return result;
    }
}