# QA Java Course Project - Standalone Steps

A progressive Java learning project designed for QA Engineers transitioning to QA Automation. Each step is a **completely independent application** that builds a complete automation testing framework from basic Java concepts to professional BDD and reporting tools.

## Project Structure

```
qa-java-course-project-v2/
├── step1-file-reading/          # Basic file I/O operations (TXT, CSV, JSON)
├── step2-desktop-api/           # Desktop integration and browser opening
├── step3-oop-architecture/      # OOP principles and clean architecture
├── step4-design-patterns/       # Singleton pattern and production practices
├── step5-testing-automation/    # JUnit 5 and TestNG test frameworks
├── step6-selenium-integration/  # Selenium WebDriver browser automation
├── step7-page-object-model/     # Industry-standard Page Object Model
├── step8-cucumber-bdd/          # Cucumber BDD with Gherkin scenarios
└── step9-extent-reports/        # ExtentReports professional HTML reporting
```

## How to Build and Run Each Step

Each step is an independent Maven module that can be built and executed separately:

### Build All Steps
```bash
mvn clean compile
```

### Run Individual Steps

**Step 1: File Reading Operations**
```bash
mvn exec:java -pl step1-file-reading
```

**Step 2: Desktop API Integration**
```bash
mvn exec:java -pl step2-desktop-api
```

**Step 3: OOP Architecture**
```bash
mvn exec:java -pl step3-oop-architecture
```

**Step 4: Design Patterns**
```bash
mvn exec:java -pl step4-design-patterns
```

**Step 5: Testing & Build Automation**
```bash
mvn test -pl step5-testing-automation
```

**Step 6: Selenium WebDriver Integration**
```bash
mvn exec:java -pl step6-selenium-integration
```

**Step 7: Page Object Model**
```bash
mvn exec:java -pl step7-page-object-model
```

**Step 8: Cucumber BDD Integration**
```bash
mvn test -pl step8-cucumber-bdd
```

**Step 9: ExtentReports Integration**
```bash
mvn test -pl step9-extent-reports
```

### Build and Test Specific Step
```bash
# Build only Step 7
mvn clean compile -pl step7-page-object-model

# Run Step 7
mvn exec:java -pl step7-page-object-model

# Run tests for Step 8 Cucumber BDD
mvn test -pl step8-cucumber-bdd

# Run tests for Step 9 with ExtentReports
mvn test -pl step9-extent-reports
```

## Complete Learning Progression

### 🏗️ Foundation Phase (Steps 1-4)
Build core Java programming skills essential for automation testing.

#### Step 1: Basic File I/O Operations (30 min, Intermediate)
- Reading TXT, CSV, and JSON files with proper error handling
- Understanding file paths and BufferedReader
- JSON parsing with org.json library
- Foundation for test data management

#### Step 2: System Integration (25 min, Intermediate)
- Java Desktop API for browser integration
- Opening URLs in default browser
- Platform compatibility checking
- Combining file operations with system calls

#### Step 3: Object-Oriented Programming (45 min, Advanced)
- Interface design and implementation
- Factory pattern for modular code
- Professional code organization
- Clean architecture principles

#### Step 4: Production Practices (35 min, Advanced)
- Singleton pattern for global services
- Application-wide logging system
- Thread-safe implementations
- Configuration management

### ⚡ Testing Automation Phase (Steps 5-7)
Transform Java skills into professional testing capabilities.

#### Step 5: Testing & Build Automation (40 min, Advanced)
- JUnit 5 and TestNG frameworks
- Maven test lifecycle and reporting
- Automated test execution
- CI/CD pipeline preparation

#### Step 6: Selenium WebDriver Integration (35 min, Advanced)
- WebDriver setup and configuration
- Browser automation and interaction
- Screenshot capture for evidence
- Enhanced website verification

#### Step 7: Page Object Model (45 min, Expert)
- Industry-standard POM design pattern
- Reusable page components
- Professional test framework architecture
- Comprehensive test reporting with statistics

### 🚀 Enterprise Framework Phase (Steps 8-9)
Complete professional QA automation framework with BDD and reporting.

#### Step 8: Cucumber BDD Integration (40 min, Expert)
- Gherkin feature files with Given-When-Then syntax
- Cucumber step definitions bridging business language to technical code
- Tagged test execution (@smoke, @regression)
- Business-readable test scenarios integrated with Page Objects

#### Step 9: ExtentReports Integration (35 min, Expert)
- Professional HTML test reports with rich dashboards
- Screenshot integration and visual evidence
- Enhanced Cucumber steps with detailed reporting
- Test analytics, charts, and execution statistics

## 🎯 Final Framework Capabilities

Upon completing all 9 steps, you'll have built a complete **enterprise-ready QA automation framework** featuring:

- ✅ **Professional Page Object Model** architecture
- ✅ **Selenium WebDriver** browser automation
- ✅ **Cucumber BDD** business-readable test scenarios
- ✅ **ExtentReports** comprehensive HTML reporting
- ✅ **JUnit 5 & TestNG** test execution frameworks
- ✅ **Maven** build and dependency management
- ✅ **Screenshot evidence** and visual testing
- ✅ **Data-driven testing** with JSON/CSV support
- ✅ **Modular design** with clean architecture
- ✅ **Production-ready** logging and configuration

## 🛠️ Technologies Used

| Technology | Purpose | Introduced in Step |
|------------|---------|-------------------|
| **Java 11** | Core programming language | Step 1 |
| **Maven** | Build and dependency management | Step 1 |
| **org.json** | JSON data processing | Step 1 |
| **JUnit 5** | Unit testing framework | Step 5 |
| **TestNG** | Alternative testing framework | Step 5 |
| **Selenium WebDriver** | Browser automation | Step 6 |
| **WebDriverManager** | Automatic driver management | Step 6 |
| **Cucumber** | BDD framework with Gherkin | Step 8 |
| **ExtentReports** | Professional HTML reporting | Step 9 |

## 🎓 Prerequisites

- **Java 11 or higher** installed
- **Maven 3.6+** for build management
- **Basic understanding** of Java syntax (covered in HTML course fundamentals)
- **Chrome browser** for Selenium automation (Steps 6-9)

## 🚀 Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/radu-tataru/radu-tataru-qa-java-course-project-v2.git
   cd qa-java-course-project-v2
   ```

2. **Build all projects:**
   ```bash
   mvn clean compile
   ```

3. **Start with Step 1:**
   ```bash
   mvn exec:java -pl step1-file-reading
   ```

4. **Progress through each step** following the HTML course lessons

5. **Run advanced testing steps:**
   ```bash
   mvn test -pl step8-cucumber-bdd    # BDD scenarios
   mvn test -pl step9-extent-reports  # Professional reporting
   ```

## 📚 Companion Course

This repository contains the **practical implementations** for the [Java Course HTML lessons](https://github.com/radu-tataru/Java-course). For complete learning:

1. **Read the HTML lesson** for each step
2. **Run the corresponding implementation** from this repository
3. **Examine the code** to understand the concepts
4. **Modify and experiment** to deepen your understanding

## 🏆 Career Impact

This progressive course prepares QA Engineers for real-world automation roles by building practical experience with:

- **Modern testing frameworks** used in enterprise environments
- **Industry-standard design patterns** like Page Object Model
- **Professional reporting** that stakeholders can understand
- **BDD practices** that bridge business and technical teams
- **Clean code principles** essential for maintainable test suites

Perfect preparation for **QA Automation Engineer**, **SDET**, and **Test Automation** roles.