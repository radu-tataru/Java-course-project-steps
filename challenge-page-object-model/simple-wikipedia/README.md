# Simple Challenge: Wikipedia Page Object Model

Complete solution for the **Simple Wikipedia Challenge** from the Page Object Model Challenge.

## 📋 Challenge Overview

Create a Page Object Model framework for Wikipedia article pages with search, navigation, and verification capabilities.

## 🏗️ Solution Architecture

### Classes Implemented

1. **BasePage.java** - Abstract base class with common functionality
   - WebDriver and WebDriverWait management
   - Common interaction methods (click, type, getText)
   - Screenshot capability
   - Element visibility checks
   - Abstract methods for page title and loaded state

2. **WikipediaArticlePage.java** - Wikipedia-specific page object
   - Search functionality
   - Random article navigation
   - Main page navigation
   - Table of contents verification
   - Article content verification

3. **WikipediaTest.java** - Comprehensive test suite
   - 5 test scenarios covering main functionality
   - Search verification
   - Navigation testing
   - Content validation
   - Screenshot capture

## ✅ Features Implemented

- ✓ Search for articles
- ✓ Navigate to random articles
- ✓ Verify article title and content
- ✓ Check table of contents (when present)
- ✓ Take screenshots for documentation
- ✓ Navigate to main page
- ✓ Proper encapsulation (private locators, public actions)
- ✓ Explicit waits (no Thread.sleep)
- ✓ Clean, readable test scenarios

## 🚀 Running the Tests

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

### Run All Tests
```bash
cd simple-wikipedia
mvn test
```

### Run from Parent Directory
```bash
cd challenge-page-object-model
mvn test -pl simple-wikipedia
```

## 📊 Expected Test Output

```
WikipediaTest
  ✓ Search for Selenium article and verify title
  ✓ Search for Java article and verify content
  ✓ Navigate to random article
  ✓ Search and check table of contents
  ✓ Navigate to main page and search

Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

## 🎯 Key Design Decisions

### 1. BasePage Pattern
- Centralized common functionality to avoid code duplication
- Abstract methods force child classes to implement page-specific behavior
- WebDriverWait configured once and reused across all page objects

### 2. Locator Organization
- All locators are `private final` - proper encapsulation
- Organized by page section (header, article, sidebar)
- Descriptive variable names matching UI elements

### 3. Method Naming
- Action methods describe what user does: `searchForArticle()`, `navigateToRandomArticle()`
- Verification methods ask questions: `hasTableOfContents()`, `isPageLoaded()`
- Clear intent from method names alone

### 4. Explicit Waits
- All interactions use WebDriverWait through BasePage methods
- No `Thread.sleep()` calls
- Proper handling of element timing issues

### 5. Test Independence
- Each test sets up fresh WebDriver instance
- Tests can run in any order
- Teardown ensures browser cleanup

## 💡 Learning Points

### Before: Without Page Objects
```java
@Test
void test() {
    driver.get("https://en.wikipedia.org");
    WebElement searchBox = driver.findElement(By.id("searchInput"));
    searchBox.sendKeys("Selenium");
    driver.findElement(By.cssSelector("button[type='submit']")).click();
    // Lots of technical details mixed with test logic
}
```

### After: With Page Objects
```java
@Test
void test() {
    articlePage.searchForArticle("Selenium");
    assertTrue(articlePage.isPageLoaded());
    assertTrue(articlePage.getArticleTitle().contains("Selenium"));
    // Clean, readable, maintainable
}
```

## 🔧 Maintenance Benefits

If Wikipedia changes their UI:
- **Without POM**: Update dozens of test methods
- **With POM**: Update locators in ONE place (WikipediaArticlePage)

Example: If search input ID changes from `searchInput` to `wiki-search`:
- Edit ONE line in WikipediaArticlePage
- All 5 tests continue working without changes

## 📸 Screenshots

Screenshots are automatically saved to `screenshots/` directory when:
- Explicitly called with `takeScreenshot()`
- Tests include screenshot capture steps

## 🎓 Challenge Complete!

This solution demonstrates:
- ✅ Proper POM architecture
- ✅ Clean separation of concerns
- ✅ Reusable, maintainable code
- ✅ Industry-standard patterns
- ✅ Comprehensive test coverage
- ✅ Professional test automation practices

**Next Step:** Try the Advanced Challenge with DemoQA Book Store for more complex scenarios including login, state management, and multi-page workflows.
