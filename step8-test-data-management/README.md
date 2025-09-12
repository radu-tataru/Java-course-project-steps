# Step 8: Test Data Management & Parameterization

## Overview
Step 8 transforms our testing framework from hardcoded data to flexible, data-driven testing with clean architecture and multiple data sources. This implementation demonstrates enterprise-level test data management and parameterized testing capabilities.

## Key Features

### Clean Architecture Implementation
```
com.example.app/
├── pages/              # Page Object Model classes
├── data/               # Test data management
│   ├── providers/      # Data provider classes  
│   ├── models/         # Data model classes
│   └── readers/        # File reading utilities
├── config/             # Configuration management
├── utils/              # Utility classes
├── tests/              # Test framework classes
└── Main.java           # Application entry point
```

### Data-Driven Testing Capabilities
- **Excel Data Provider**: Apache POI integration for comprehensive test data management
- **JSON Data Provider**: Legacy support with enhanced LinkData conversion
- **TestDataProvider**: Central factory for all data sources with filtering capabilities
- **Environment-Specific Data**: Support for dev, staging, and production test scenarios

### Parameterized Testing Framework
- **JUnit 5 Integration**: @ParameterizedTest with multiple data provider methods
- **TestNG Integration**: @DataProvider with advanced filtering and grouping
- **Data Provider Methods**: Flexible test data provisioning for different test scenarios
- **Test Categorization**: High-priority, environment-specific, and comprehensive test execution

### Environment Configuration Management
- **Multi-Environment Support**: dev.properties, staging.properties, prod.properties
- **Runtime Configuration**: Dynamic environment switching during test execution
- **Environment-Aware Testing**: Different browser settings and validation rules per environment
- **Configuration Properties**: Externalized settings for timeout, screenshots, headless mode

## Technical Implementation

### Excel Data Structure
| TestName | Website | ExpectedTitle | ButtonText | Environment | Priority |
|----------|---------|---------------|------------|-------------|----------|
| GitHub_Production_Test | GitHub | GitHub | Sign up | prod | high |
| JUnit_Staging_Test | JUnit | JUnit 5 | Guide | staging | medium |

### WebsiteTestData Builder Pattern
```java
WebsiteTestData testData = WebsiteTestData.builder()
    .testName("GitHub_Production_Test")
    .website("GitHub")
    .expectedTitle("GitHub")
    .buttonText("Sign up")
    .environment("prod")
    .priority("high")
    .build();
```

### Parameterized Test Example (JUnit 5)
```java
@ParameterizedTest
@MethodSource("getAllWebsiteTestData")
@DisplayName("Website verification with comprehensive test data")
void testWebsiteWithAllData(WebsiteTestData testData) {
    EnvironmentConfig.setEnvironment(testData.getEnvironment());
    BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
    TestResult result = performPageVerification(page, testData);
    assertThat(result.allTestsPassed()).isTrue();
}
```

### TestNG Data Provider Example
```java
@Test(dataProvider = "websiteTestData")
public void testWebsiteWithTestNGDataProvider(WebsiteTestData testData) {
    EnvironmentConfig.setEnvironment(testData.getEnvironment());
    BasePage page = pageManager.navigateToPage(testData.getWebsiteUrl());
    TestResult result = performPageVerification(page, testData);
    assertThat(result.allTestsPassed()).isTrue();
}

@DataProvider(name = "websiteTestData")
public Object[][] websiteTestDataProvider() {
    TestDataProvider provider = new TestDataProvider();
    List<WebsiteTestData> testData = provider.getAllWebsiteTestData();
    // Convert to TestNG format
}
```

## Dependencies Added
```xml
<!-- Apache POI for Excel reading -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.4</version>
</dependency>

<!-- TestNG for advanced parameterization -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.0</version>
</dependency>

<!-- Jackson for enhanced JSON processing -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>

<!-- AssertJ for fluent assertions -->
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.2</version>
</dependency>
```

## Usage

### Main Application
```bash
mvn exec:java -pl step8-test-data-management
```

### JUnit 5 Parameterized Tests
```bash
mvn test -pl step8-test-data-management -Dtest=ParameterizedWebsiteTests
```

### TestNG Tests with Groups
```bash
mvn test -pl step8-test-data-management -Dtest=TestNGParameterizedTests -Dgroups=smoke
```

### Environment-Specific Execution
```bash
mvn exec:java -pl step8-test-data-management -Dtest.env=staging
```

## Test Data Management Features

### Data Source Validation
- **Excel Structure Validation**: Header validation and data integrity checks
- **JSON Format Validation**: Backward compatibility with existing LinkData format
- **Data Provider Factory**: Centralized data source management with error handling

### Filtering and Categorization
```java
// High-priority tests for smoke testing
List<WebsiteTestData> smokeTests = provider.getSmokeTestData();

// Environment-specific test data
List<WebsiteTestData> stagingTests = provider.getTestDataForEnvironment("staging");

// Priority-based filtering
List<WebsiteTestData> highPriorityTests = provider.getTestDataByPriority("high");
```

### Enhanced Page Object Integration
- **Data Context Support**: Page objects receive test data context for enhanced logging
- **Environment-Aware Screenshots**: Conditional screenshot capture based on environment settings
- **Dynamic Verification**: Page verification methods accept expected values from test data

## Environment Configuration

### Development (dev.properties)
```properties
timeout.seconds=10
screenshot.enabled=true
browser.headless=false
test.data.validation=strict
```

### Production (prod.properties)
```properties
timeout.seconds=20
screenshot.enabled=false
browser.headless=true
test.data.validation=basic
```

## Benefits Over Previous Steps

### Maintainability
1. **Clean Package Organization**: Logical separation of concerns with proper package structure
2. **Data-Logic Separation**: Test logic independent of test data for maximum flexibility
3. **Centralized Configuration**: Environment-specific settings in externalized properties

### Scalability
1. **Multiple Data Sources**: Easy addition of new data providers (CSV, Database, API)
2. **Framework Extensibility**: Builder pattern and factory design support easy expansion
3. **Test Data Growth**: Single test method handles unlimited data variations

### Professional Standards
1. **Enterprise Architecture**: Industry-standard package organization and design patterns
2. **Parameterized Testing**: Both JUnit 5 and TestNG support for different team preferences
3. **Environment Management**: Professional multi-environment testing approach

This step establishes the foundation for Step 9 (Test Reporting & CI/CD Integration) by providing rich test data and execution context that can be analyzed and reported comprehensively.