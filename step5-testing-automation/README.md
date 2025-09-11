# Step 5: Testing and Build Automation

This module demonstrates unit testing with JUnit 5 and build automation with Maven.

## What You'll Learn

- JUnit 5 testing framework fundamentals
- Writing unit tests with assertions
- Test-driven development concepts
- Maven build lifecycle and testing
- Continuous integration basics

## Project Structure

```
step5-testing-automation/
├── src/
│   ├── main/java/com/example/app/    # Main application code
│   └── test/java/com/example/app/    # Unit tests
│       ├── test/resources/           # Test data files
├── java_project/data/                # Application data files
└── pom.xml                          # Maven configuration
```

## Running the Application

```bash
# Compile and run the main application
mvn clean compile exec:java -Dexec.mainClass="com.example.app.Main"

# Run all unit tests
mvn test

# Run with detailed test output
mvn test -Dtest="SimpleFileReaderTest"
```

## Key Testing Concepts

1. **Unit Tests**: Test individual components in isolation
2. **Test Data**: Use separate test files to avoid affecting real data
3. **Assertions**: Verify expected behavior with JUnit assertions
4. **Test Coverage**: Ensure comprehensive testing of all scenarios
5. **Build Automation**: Integrate testing into the build process

## Test Classes

- `SimpleFileReaderTest`: Tests file reading functionality
- `LinkOpenerTest`: Tests URL validation and link opening
- `LinkReaderFactoryTest`: Tests the Factory pattern implementation
- `LoggerTest`: Tests the Singleton pattern implementation

This builds upon all previous steps while introducing professional testing practices.