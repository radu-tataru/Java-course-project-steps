package com.example.app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for GitHub homepage
 */
public class GitHubHomePage extends BasePage {
    
    @FindBy(css = "h1")
    private WebElement mainHeading;
    
    @FindBy(css = "a[href='/signup']")
    private WebElement signUpButton;
    
    @FindBy(css = "a[href='/login']")
    private WebElement signInButton;
    
    @FindBy(css = ".octicon-mark-github")
    private WebElement githubLogo;
    
    @FindBy(css = "main")
    private WebElement mainContent;
    
    public GitHubHomePage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    public void verifyPageLoaded() {
        wait.until(ExpectedConditions.titleContains("GitHub"));
        takeScreenshot("github_home_loaded");
        Logger.getInstance().logPageTest("GitHub HomePage", "Page loaded successfully");
    }
    
    /**
     * Gets the main heading text
     * @return Main heading text
     */
    public String getMainHeading() {
        try {
            wait.until(ExpectedConditions.visibilityOf(mainHeading));
            String headingText = mainHeading.getText();
            Logger.getInstance().logElementInteraction("Main Heading", "Retrieved text: " + headingText);
            return headingText;
        } catch (Exception e) {
            Logger.getInstance().logElementInteraction("Main Heading", "Failed to retrieve text: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Checks if sign up button is visible
     * @return true if sign up button is visible
     */
    public boolean isSignUpButtonVisible() {
        boolean isVisible = isElementDisplayed(signUpButton);
        Logger.getInstance().logElementInteraction("Sign Up Button", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Checks if sign in button is visible
     * @return true if sign in button is visible
     */
    public boolean isSignInButtonVisible() {
        boolean isVisible = isElementDisplayed(signInButton);
        Logger.getInstance().logElementInteraction("Sign In Button", 
                                                  "Visibility check: " + (isVisible ? "Visible" : "Not visible"));
        return isVisible;
    }
    
    /**
     * Checks if GitHub logo is displayed
     * @return true if logo is displayed
     */
    public boolean isGitHubLogoDisplayed() {
        boolean isDisplayed = isElementDisplayed(githubLogo);
        Logger.getInstance().logElementInteraction("GitHub Logo", 
                                                  "Display check: " + (isDisplayed ? "Displayed" : "Not displayed"));
        return isDisplayed;
    }
    
    /**
     * Checks if main content area is present
     * @return true if main content is present
     */
    public boolean isMainContentPresent() {
        boolean isPresent = isElementDisplayed(mainContent);
        Logger.getInstance().logElementInteraction("Main Content", 
                                                  "Presence check: " + (isPresent ? "Present" : "Not present"));
        return isPresent;
    }
    
    /**
     * Performs comprehensive page verification
     * @return TestResult object with verification details
     */
    public TestResult verifyPageElements() {
        TestResult result = new TestResult("GitHub HomePage Verification");
        
        // Test page title
        String title = getPageTitle();
        result.addTest("Page Title Check", title.toLowerCase().contains("github"), 
                      "Expected: title contains 'github', Actual: " + title);
        
        // Test main heading
        String heading = getMainHeading();
        result.addTest("Main Heading Check", !heading.isEmpty(), 
                      "Expected: non-empty heading, Actual: " + heading);
        
        // Test sign up button
        result.addTest("Sign Up Button Check", isSignUpButtonVisible(), 
                      "Expected: Sign up button visible");
        
        // Test sign in button
        result.addTest("Sign In Button Check", isSignInButtonVisible(), 
                      "Expected: Sign in button visible");
        
        // Test GitHub logo
        result.addTest("GitHub Logo Check", isGitHubLogoDisplayed(), 
                      "Expected: GitHub logo displayed");
        
        takeScreenshot("github_verification_complete");
        Logger.getInstance().logPageTest("GitHub HomePage", result.getOverallResult());
        
        return result;
    }
}