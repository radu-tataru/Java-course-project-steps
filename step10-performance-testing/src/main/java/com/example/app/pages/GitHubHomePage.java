package com.example.app.pages;

import com.example.app.tests.TestResult;
import com.example.app.utils.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object for GitHub homepage with data-driven testing support
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
    public void verifyPageLoaded(String testDataContext) {
        wait.until(ExpectedConditions.titleContains("GitHub"));
        takeScreenshotWithContext("github_home_loaded", testDataContext);
        Logger.getInstance().logPageTest("GitHub HomePage", 
            "Page loaded successfully (Context: " + testDataContext + ")");
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
     * Checks if specific button is visible based on test data
     * @param buttonText Expected button text from test data
     * @return true if button with expected text is visible
     */
    public boolean isExpectedButtonVisible(String buttonText) {
        boolean isSignUpVisible = isElementDisplayed(signUpButton);
        boolean isSignInVisible = isElementDisplayed(signInButton);
        
        if (buttonText == null) {
            // If no specific button expected, check for any primary button
            boolean anyButtonVisible = isSignUpVisible || isSignInVisible;
            Logger.getInstance().logElementInteraction("Any Button", 
                "Visibility check: " + (anyButtonVisible ? "Visible" : "Not visible"));
            return anyButtonVisible;
        }
        
        boolean expectedButtonFound = false;
        String buttonTextLower = buttonText.toLowerCase();
        
        if (buttonTextLower.contains("sign up") && isSignUpVisible) {
            expectedButtonFound = true;
            Logger.getInstance().logElementInteraction("Sign Up Button", 
                "Found expected button: " + buttonText);
        } else if (buttonTextLower.contains("sign in") && isSignInVisible) {
            expectedButtonFound = true;
            Logger.getInstance().logElementInteraction("Sign In Button", 
                "Found expected button: " + buttonText);
        }
        
        if (!expectedButtonFound) {
            Logger.getInstance().logElementInteraction("Expected Button", 
                "Button '" + buttonText + "' not found. SignUp visible: " + isSignUpVisible + 
                ", SignIn visible: " + isSignInVisible);
        }
        
        return expectedButtonFound;
    }
    
    /**
     * Performs comprehensive page verification with test data context
     * @param expectedTitle Expected title from test data
     * @param buttonText Expected button text from test data
     * @param testDataContext Context from test data
     * @return TestResult object with verification details
     */
    public TestResult verifyPageElements(String expectedTitle, String buttonText, String testDataContext) {
        TestResult result = new TestResult("GitHub HomePage Verification - " + testDataContext);
        
        // Test page title
        String actualTitle = getPageTitle();
        boolean titleMatches = expectedTitle == null || 
                              actualTitle.toLowerCase().contains(expectedTitle.toLowerCase());
        result.addTest("Page Title Check", titleMatches, 
                      "Expected: '" + expectedTitle + "', Actual: '" + actualTitle + "'");
        
        // Test main heading
        String heading = getMainHeading();
        result.addTest("Main Heading Check", !heading.isEmpty(), 
                      "Expected: non-empty heading, Actual: '" + heading + "'");
        
        // Test expected button
        result.addTest("Expected Button Check", isExpectedButtonVisible(buttonText), 
                      "Expected button: '" + buttonText + "'");
        
        // Test GitHub logo
        result.addTest("GitHub Logo Check", isElementDisplayed(githubLogo), 
                      "Expected: GitHub logo displayed");
        
        takeScreenshotWithContext("github_verification_complete", testDataContext);
        Logger.getInstance().logPageTest("GitHub HomePage", result.getOverallResult());
        
        return result;
    }
    
    /**
     * Legacy method for backward compatibility
     */
    public TestResult verifyPageElements() {
        return verifyPageElements(null, null, "legacy");
    }
}