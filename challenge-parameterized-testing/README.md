# Parameterized Testing Challenge - Solutions

This module contains solutions for the **Parameterized Testing Challenge** from the Java Course.

## 📋 Overview

This challenge demonstrates how to transform repetitive test code into elegant, data-driven parameterized tests using JUnit 5's `@ParameterizedTest` annotations.

## 🎯 Challenge Levels

### Simple Challenge: Calculator Tests
**File:** `CalculatorTestSolution.java`

Demonstrates basic parameterized testing techniques:
- `@CsvSource` - Inline test data for multiple parameters
- `@ValueSource` - Simple single-value test data
- Custom test names with parameters
- Multiple assertion scenarios

**Methods Tested:**
- `add()`, `subtract()`, `multiply()`, `divide()` - Basic arithmetic operations
- `isEven()` - Even/odd number checking
- `isPrime()` - Prime number validation

### Advanced Challenge: UserValidator Tests
**File:** `UserValidatorTestSolution.java`

Demonstrates advanced parameterized testing techniques:
- `@CsvSource` - Inline test data with descriptions
- `@CsvFileSource` - External CSV file data sources
- `@MethodSource` - Complex test data from methods
- Custom test data classes
- Stream-based test data generation

**Validation Rules Tested:**
- **Username:** 3-20 chars, starts with letter, alphanumeric + underscore
- **Email:** Valid email format with @ and domain
- **Password:** 8+ chars, at least 3 character types (upper/lower/digit/special)

## 🏗️ Project Structure

```
challenge-parameterized-testing/
├── pom.xml
├── src/
│   ├── main/java/com/example/app/
│   │   ├── Calculator.java              # Simple challenge - calculator logic
│   │   └── UserValidator.java           # Advanced challenge - validation logic
│   └── test/
│       ├── java/com/example/app/
│       │   ├── CalculatorTestSolution.java       # Simple challenge solution
│       │   └── UserValidatorTestSolution.java    # Advanced challenge solution
│       └── resources/test-data/
│           └── users.csv                # Test data for @CsvFileSource
└── README.md
```

## 🚀 Running the Tests

### Run All Tests
```bash
cd challenge-parameterized-testing
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=CalculatorTestSolution
mvn test -Dtest=UserValidatorTestSolution
```

### Run from Parent Directory
```bash
cd qa-java-course-project-v2
mvn test -pl challenge-parameterized-testing
```

## 📊 Expected Test Output

### Simple Challenge (Calculator)
```
CalculatorTestSolution
  ✓ 2 + 3 = 5
  ✓ 10 + 5 = 15
  ✓ 10 - 5 = 5
  ✓ 2 * 3 = 6
  ✓ 10 / 2 = 5
  ✓ 2 is even
  ✓ 3 is odd
  ✓ 7 is prime
  ✓ 4 is not prime
  ...
Tests run: 50+, Failures: 0, Errors: 0
```

### Advanced Challenge (UserValidator)
```
UserValidatorTestSolution
  ✓ Valid username: 'john'
  ✓ Invalid username: 'ab' - Too short
  ✓ Valid email: 'john@example.com'
  ✓ Invalid email: 'notanemail' - Missing @ symbol
  ✓ Valid password: 'Password123'
  ✓ Invalid password: 'short' - Too short
  ✓ [CSV] User: john_doe, Email: john@example.com, Expected: true
  ✓ [Method] Valid user with all correct fields
  ...
Tests run: 40+, Failures: 0, Errors: 0
```

## 💡 Key Learning Points

### Before (Repetitive Code)
```java
@Test
void testAddition1() {
    assertEquals(5, calculator.add(2, 3));
}

@Test
void testAddition2() {
    assertEquals(15, calculator.add(10, 5));
}

@Test
void testAddition3() {
    assertEquals(0, calculator.add(-5, 5));
}
// ... many more repetitive tests
```

### After (Parameterized)
```java
@ParameterizedTest(name = "{0} + {1} = {2}")
@CsvSource({
    "2, 3, 5",
    "10, 5, 15",
    "-5, 5, 0"
})
void testAddition(int a, int b, int expected) {
    assertEquals(expected, calculator.add(a, b));
}
```

## 🎓 What You've Learned

1. **@ParameterizedTest** - Run same test with different data
2. **@CsvSource** - Inline test data for multiple parameters
3. **@ValueSource** - Simple single-value test data
4. **@CsvFileSource** - External CSV file data sources
5. **@MethodSource** - Complex test data generation
6. **Custom Test Names** - Readable test output with parameters
7. **Data-Driven Testing** - Professional QA automation approach

## 🔗 Related Course Content

- **Step 5:** Testing & Build Automation (JUnit 5 basics)
- **Step 6:** Selenium Integration (applying parameterized tests to browser automation)
- **Challenge Page:** https://your-course-url/parameterized-testing-challenge.html

## 📚 Further Reading

- [JUnit 5 Parameterized Tests Guide](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
- [JUnit 5 @CsvSource Documentation](https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/CsvSource.html)
- [JUnit 5 @MethodSource Documentation](https://junit.org/junit5/docs/current/api/org.junit.jupiter.params/org/junit/jupiter/params/provider/MethodSource.html)

---

**Challenge Complete! 🎉** You've mastered JUnit 5 parameterized testing and can now write elegant, data-driven tests that eliminate code duplication.
