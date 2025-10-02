# 💎 Diamond Challenge: Complete Test Automation Framework

## Overview

The Diamond Challenge is the ultimate capstone project for the QA Java Course, integrating ALL concepts learned throughout Steps 1-10. This production-ready test automation framework demonstrates mastery of:

- **Page Object Model (POM)** - Industry-standard design pattern
- **Cucumber BDD** - Behavior-driven development with Gherkin syntax
- **ExtentReports** - Professional HTML reporting with dashboards
- **Log4j 2** - Enterprise-grade logging framework
- **Selenium WebDriver** - Browser automation
- **JUnit 5** - Modern testing framework
- **Maven** - Build automation and dependency management

## 🎯 Testing Target

**SauceDemo.com** - A demo e-commerce website specifically designed for test automation practice.

### Test Coverage

- ✅ Login flows (standard, locked out, problem users)
- ✅ Product browsing and selection
- ✅ Shopping cart operations
- ✅ Complete checkout process
- ✅ Order completion verification
- ✅ Error handling and validation
- ✅ Data-driven testing

## 🏗️ Architecture

### Package Structure

```
diamond-challenge/
├── src/main/java/com/example/app/
│   ├── pages/                      # Page Object Model classes
│   │   ├── BasePage.java           # Base page with common methods
│   │   ├── LoginPage.java          # Login page object
│   │   ├── ProductsPage.java       # Products page object
│   │   ├── CartPage.java           # Shopping cart page
│   │   ├── CheckoutStepOnePage.java
│   │   ├── CheckoutStepTwoPage.java
│   │   └── CheckoutCompletePage.java
│   ├── config/                     # Configuration classes
│   │   └── WebDriverManager.java  # Singleton WebDriver manager
│   └── utils/                      # Utility classes
│       └── ScreenshotUtil.java    # Screenshot capture utility
├── src/test/java/com/example/app/
│   ├── stepdefinitions/            # Cucumber step definitions
│   │   ├── CommonSteps.java       # Hooks (Before/After)
│   │   ├── LoginSteps.java        # Login-related steps
│   │   ├── ProductSteps.java      # Product-related steps
│   │   ├── CartSteps.java         # Cart-related steps
│   │   └── CheckoutSteps.java     # Checkout-related steps
│   └── TestRunner.java            # JUnit 5 test runner
├── src/test/resources/
│   ├── features/                   # Cucumber feature files
│   │   └── purchase_flow.feature  # BDD test scenarios
│   ├── extent.properties           # ExtentReports configuration
│   ├── extent-config.xml          # ExtentReports theme/styling
│   └── cucumber.properties        # Cucumber configuration
├── src/main/resources/
│   └── log4j2.xml                 # Log4j configuration
├── java_project/data/             # Test data files
│   └── test-users.json            # User credentials and test data
├── reports/                        # Generated reports
│   ├── DiamondChallengeReport.html
│   └── screenshots/
└── logs/                           # Log files
    ├── diamond-challenge.log
    ├── test-execution.log
    └── error.log
```

## 🚀 Running the Tests

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- Chrome browser installed

### Execution Commands

#### Run all tests:
```bash
mvn clean test -pl diamond-challenge
```

#### Run specific tags:
```bash
# Run only smoke tests
mvn clean test -pl diamond-challenge -Dcucumber.filter.tags="@smoke"

# Run only purchase flow tests
mvn clean test -pl diamond-challenge -Dcucumber.filter.tags="@purchase"

# Run negative tests
mvn clean test -pl diamond-challenge -Dcucumber.filter.tags="@negative"
```

#### View reports:
```bash
# Open ExtentReports in browser
start diamond-challenge/reports/DiamondChallengeReport.html

# View logs
type diamond-challenge\logs\diamond-challenge.log
```

## 📊 Reports and Logging

### ExtentReports

Professional HTML dashboard with:
- Test execution summary (passed/failed/skipped)
- Detailed test steps with timestamps
- Screenshots for successes and failures
- Test execution timeline
- Category and tag-based filtering
- System information

**Location**: `reports/DiamondChallengeReport.html`

### Log4j Logging

Multi-level logging with separate appenders:
- **Console**: INFO level and above
- **diamond-challenge.log**: All logs (DEBUG and above)
- **test-execution.log**: Test-specific logs
- **error.log**: ERROR level only

**Location**: `logs/` directory

### Cucumber Reports

Standard Cucumber HTML and JSON reports:
- **HTML**: `reports/cucumber-report.html`
- **JSON**: `reports/cucumber.json`

## 🧪 Test Scenarios

### Included Scenarios (7+)

1. **Complete Purchase Flow** (@e2e, @purchase, @smoke)
   - Login → Add products → Checkout → Complete order

2. **Add Multiple Products to Cart** (@product, @cart)
   - Add 3 products and verify cart count

3. **Remove Product from Cart** (@product, @remove)
   - Add 2 products, remove 1, verify remaining

4. **Login with Locked Out User** (@negative, @login)
   - Attempt login with locked user, verify error

5. **Checkout Without Required Information** (@negative, @checkout)
   - Try checkout without filling fields, verify validation

6. **Sort Products by Price** (@sorting, @products)
   - Sort products and verify ordering

7. **Login with Different User Types** (@datadriven, @multipleusers)
   - Data-driven test with 3 user types

## 🏆 Success Criteria

✅ **6+ Page Objects** - All major pages implemented with BasePage inheritance
✅ **7+ Cucumber Scenarios** - Covering main user flows and edge cases
✅ **ExtentReports Integration** - Professional dashboard with screenshots
✅ **Log4j Configuration** - Console and file appenders with proper levels
✅ **External Test Data** - JSON file with user credentials and test data
✅ **Factory Pattern** - WebDriverManager singleton implementation
✅ **Singleton Pattern** - Single WebDriver instance management
✅ **All Tests Passing** - 100% success rate on SauceDemo.com

## 💡 Key Concepts Demonstrated

### Design Patterns

1. **Singleton Pattern**: WebDriverManager ensures single driver instance
2. **Page Object Model**: Clean separation of page logic from tests
3. **Factory Pattern**: Page object creation and navigation

### Best Practices

- ✅ DRY (Don't Repeat Yourself) - BasePage with common methods
- ✅ Explicit waits with WebDriverWait
- ✅ Meaningful logging at appropriate levels
- ✅ Screenshot capture for debugging
- ✅ Data-driven testing with external data sources
- ✅ BDD with readable Gherkin syntax
- ✅ Proper exception handling
- ✅ Clean code with proper naming conventions

### Testing Strategies

- **Positive Testing**: Happy path scenarios
- **Negative Testing**: Error handling and validation
- **Data-Driven Testing**: Scenario outlines with examples
- **End-to-End Testing**: Complete user journeys
- **Regression Testing**: Tag-based test suites

## 📝 Configuration Files

### log4j2.xml
- 4 appenders: Console, FileLogger, ErrorLogger, TestLogger
- Rolling file policy with size and time-based triggers
- Package-level logger configuration
- Reduced Selenium verbosity

### extent.properties
- Report output location
- Screenshot configuration
- System information
- Report theme (dark mode)

### cucumber.properties
- Plugin configuration
- Glue code path
- Tag filtering
- Execution options

## 🎓 Learning Outcomes

By completing this challenge, you demonstrate:

1. **Technical Skills**
   - Proficiency in Java and OOP concepts
   - Understanding of design patterns
   - Selenium WebDriver automation
   - BDD with Cucumber
   - Test reporting and logging

2. **Professional Practices**
   - Clean code architecture
   - Maintainable test framework design
   - Comprehensive documentation
   - Version control (Git)
   - Build automation (Maven)

3. **Testing Expertise**
   - Test strategy and planning
   - Multiple testing types (E2E, negative, data-driven)
   - Debugging and troubleshooting
   - Results analysis and reporting

## 🔧 Troubleshooting

### Common Issues

1. **ChromeDriver not found**
   - WebDriverManager should auto-download, but ensure Chrome is installed

2. **Tests failing on SauceDemo**
   - Check internet connection
   - Verify SauceDemo.com is accessible
   - Review logs in `logs/error.log`

3. **ExtentReports not generated**
   - Check `reports/` directory exists
   - Verify maven-surefire-plugin configuration
   - Review console output for errors

4. **Screenshots not captured**
   - Ensure `reports/screenshots/` directory has write permissions
   - Check ScreenshotUtil logs

## 📚 Resources

- [SauceDemo.com](https://www.saucedemo.com/) - Test application
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Cucumber BDD](https://cucumber.io/docs/cucumber/)
- [ExtentReports](https://www.extentreports.com/)
- [Log4j 2](https://logging.apache.org/log4j/2.x/)

## 🎉 Congratulations!

Completing this Diamond Challenge demonstrates your mastery of test automation with Java. You've built a production-ready framework that can serve as a template for real-world projects. This is portfolio-worthy work that showcases your skills to potential employers!

---

**Duration**: 120-150 minutes
**Difficulty**: 💎 Diamond (Ultimate)
**Prerequisites**: Completion of Steps 1-10
