package com.example.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for Wikipedia Article pages
 * Simple Challenge Solution
 */
public class WikipediaArticlePage extends BasePage {

    // Locators - organized by section
    // Header section
    private final By searchInput = By.id("searchInput");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By wikiLogo = By.className("mw-wiki-logo");

    // Article section
    private final By articleTitle = By.id("firstHeading");
    private final By articleContent = By.id("mw-content-text");

    // Sidebar navigation
    private final By randomArticleLink = By.id("n-randompage");
    private final By mainPageLink = By.id("n-mainpage");

    // Table of contents
    private final By tableOfContents = By.id("toc");

    public WikipediaArticlePage(WebDriver driver) {
        super(driver);
    }

    /**
     * Search for an article by typing and clicking search
     */
    public void searchForArticle(String searchTerm) {
        type(searchInput, searchTerm);
        click(searchButton);
    }

    /**
     * Get the main article title
     */
    public String getArticleTitle() {
        return getText(articleTitle);
    }

    /**
     * Navigate to a random article
     */
    public void navigateToRandomArticle() {
        click(randomArticleLink);
    }

    /**
     * Navigate to the main page
     */
    public void navigateToMainPage() {
        click(mainPageLink);
    }

    /**
     * Check if article has a table of contents
     */
    public boolean hasTableOfContents() {
        return isElementVisible(tableOfContents);
    }

    /**
     * Check if article content is displayed
     */
    public boolean hasArticleContent() {
        return isElementVisible(articleContent);
    }

    @Override
    public String getPageTitle() {
        return driver.getTitle();
    }

    @Override
    public boolean isPageLoaded() {
        return isElementVisible(articleTitle) && isElementVisible(articleContent);
    }
}
