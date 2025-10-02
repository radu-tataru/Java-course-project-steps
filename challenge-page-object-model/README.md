# Page Object Model Challenge - Solutions

This module contains solutions for the **Page Object Model Challenge** from the Java Course.

## 📋 Overview

This challenge demonstrates how to design and implement professional Page Object Model frameworks for real websites, following industry-standard test automation patterns.

## 🎯 Challenge Levels

### Simple Challenge: Wikipedia POM
**Directory:** `simple-wikipedia/`

A complete Page Object Model implementation for Wikipedia article pages.

**Features:**
- BasePage with common functionality
- WikipediaArticlePage for article-specific actions
- 5 comprehensive test scenarios
- Search, navigation, and verification capabilities

**Tech Stack:**
- Selenium WebDriver 4.15.0
- WebDriverManager 5.6.2
- JUnit 5.9.2

**Run Tests:**
```bash
cd simple-wikipedia
mvn test
```

### Advanced Challenge: DemoQA Book Store POM
**Directory:** `advanced-demoqa/`

A more complex Page Object Model implementation for an e-commerce-style book store application with user authentication and state management.

**Features:**
- BasePage with advanced waits and error handling
- Multiple page objects (Login, BookStore, BookDetails, Profile)
- User authentication flows
- Collection management
- 8+ comprehensive test scenarios

**Tech Stack:**
- Selenium WebDriver 4.15.0
- WebDriverManager 5.6.2
- JUnit 5.9.2

**Run Tests:**
```bash
cd advanced-demoqa
mvn test
```

## 🏗️ Project Structure

```
challenge-page-object-model/
├── pom.xml (parent POM)
├── simple-wikipedia/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/example/app/
│   │   │   ├── BasePage.java
│   │   │   └── WikipediaArticlePage.java
│   │   └── test/java/com/example/app/
│   │       └── WikipediaTest.java
│   ├── screenshots/
│   └── README.md
├── advanced-demoqa/
│   ├── pom.xml
│   ├── src/
│   │   ├── main/java/com/example/app/
│   │   │   ├── BasePage.java
│   │   │   ├── LoginPage.java
│   │   │   ├── BookStoreHomePage.java
│   │   │   ├── BookDetailsPage.java
│   │   │   └── ProfilePage.java
│   │   └── test/java/com/example/app/
│   │       └── BookStoreTest.java
│   ├── screenshots/
│   └── README.md
└── README.md (this file)
```

## 🚀 Running All Tests

### Run All Challenges
```bash
cd challenge-page-object-model
mvn test
```

### Run Specific Challenge
```bash
mvn test -pl simple-wikipedia
mvn test -pl advanced-demoqa
```

## 💡 Key Learning Points

### 1. Page Object Model Benefits
- **Separation of Concerns:** Page structure separate from test logic
- **Reusability:** Page objects used across multiple tests
- **Maintainability:** UI changes updated in ONE place
- **Readability:** Tests read like user stories

### 2. BasePage Pattern
```java
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Common methods all pages need
    protected void click(By locator) { }
    protected void type(By locator, String text) { }
    protected String getText(By locator) { }

    // Force implementation in child classes
    public abstract String getPageTitle();
    public abstract boolean isPageLoaded();
}
```

### 3. Proper Encapsulation
```java
public class WikipediaArticlePage extends BasePage {
    // ❌ WRONG: Public locators
    public By searchInput = By.id("searchInput");

    // ✅ CORRECT: Private locators, public actions
    private final By searchInput = By.id("searchInput");

    public void searchForArticle(String term) {
        type(searchInput, term);
        click(searchButton);
    }
}
```

### 4. Explicit Waits
```java
// ❌ WRONG: Hard-coded delays
Thread.sleep(5000);

// ✅ CORRECT: Explicit waits
wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
```

## 📊 Success Criteria Met

Both solutions demonstrate:

✅ **BasePage class** with common functionality
✅ **Multiple page objects** (2 for simple, 5 for advanced)
✅ **Proper encapsulation** - private locators, public actions
✅ **Meaningful method names** describing user actions
✅ **Comprehensive test scenarios** (5+ for simple, 8+ for advanced)
✅ **All tests passing** with proper assertions
✅ **Screenshot capability** for documentation
✅ **Clean, maintainable code** following DRY principle
✅ **Test independence** - each test runs standalone
✅ **Explicit waits** - no Thread.sleep()

## 🎓 Challenge Complete!

These solutions demonstrate professional-grade Page Object Model implementations ready for production test automation frameworks.

**Skills Demonstrated:**
- Industry-standard POM design patterns
- Clean architecture and separation of concerns
- Reusable, maintainable test automation code
- Professional Selenium WebDriver practices
- Comprehensive test coverage

## 📚 Further Reading

- [Selenium POM Documentation](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [Martin Fowler: PageObject](https://martinfowler.com/bliki/PageObject.html)
- Course Step 7: Page Object Model & Advanced Testing

---

**Ready for Cucumber BDD?** Continue to Step 8 to learn how to write business-readable tests using these page objects!
