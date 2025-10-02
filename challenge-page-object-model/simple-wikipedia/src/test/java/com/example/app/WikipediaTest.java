package com.example.app;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Wikipedia Article page
 * Simple Challenge Solution
 */
@DisplayName("Wikipedia Article Tests")
class WikipediaTest {

    private WebDriver driver;
    private WikipediaArticlePage articlePage;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        driver = new ChromeDriver(options);
        driver.get("https://en.wikipedia.org");

        articlePage = new WikipediaArticlePage(driver);
    }

    @Test
    @DisplayName("Search for Selenium article and verify title")
    void testSearchSeleniumArticle() {
        articlePage.searchForArticle("Selenium (software)");

        assertTrue(articlePage.isPageLoaded(),
                "Article page should be fully loaded");
        assertTrue(articlePage.getArticleTitle().contains("Selenium"),
                "Article title should contain 'Selenium'");
        assertTrue(articlePage.getPageTitle().contains("Selenium"),
                "Browser page title should contain 'Selenium'");
    }

    @Test
    @DisplayName("Search for Java article and verify content")
    void testSearchJavaArticle() {
        articlePage.searchForArticle("Java (programming language)");

        assertTrue(articlePage.isPageLoaded(),
                "Article page should be fully loaded");
        assertEquals("Java (programming language)", articlePage.getArticleTitle(),
                "Article title should match exactly");
        assertTrue(articlePage.hasArticleContent(),
                "Article should have content visible");
    }

    @Test
    @DisplayName("Navigate to random article")
    void testRandomArticleNavigation() {
        articlePage.navigateToRandomArticle();

        assertTrue(articlePage.isPageLoaded(),
                "Random article page should be loaded");
        assertNotNull(articlePage.getArticleTitle(),
                "Random article should have a title");
        assertFalse(articlePage.getArticleTitle().isEmpty(),
                "Article title should not be empty");
    }

    @Test
    @DisplayName("Search and check table of contents")
    void testArticleTableOfContents() {
        articlePage.searchForArticle("Page Object Model");

        assertTrue(articlePage.isPageLoaded(),
                "Article page should be loaded");

        // Note: Not all articles have TOC, this is just an example test
        if (articlePage.hasTableOfContents()) {
            System.out.println("✓ Article has table of contents");
        } else {
            System.out.println("✓ Article does not have table of contents (short article)");
        }

        // Take screenshot for evidence
        articlePage.takeScreenshot("page-object-model-article");
    }

    @Test
    @DisplayName("Navigate to main page and search")
    void testMainPageNavigation() {
        // First search for something
        articlePage.searchForArticle("WebDriver");
        assertTrue(articlePage.isPageLoaded());

        // Navigate back to main page
        articlePage.navigateToMainPage();

        // Search for something else
        articlePage.searchForArticle("Test automation");
        assertTrue(articlePage.isPageLoaded());
        assertTrue(articlePage.getArticleTitle().toLowerCase().contains("test"),
                "Article should be about testing");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
