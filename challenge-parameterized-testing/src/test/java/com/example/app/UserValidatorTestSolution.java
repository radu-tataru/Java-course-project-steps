package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SOLUTION: Advanced Challenge - UserValidator Parameterized Tests
 *
 * This demonstrates advanced parameterized testing techniques:
 * - @CsvSource for inline test data
 * - @CsvFileSource for external test data files
 * - @MethodSource for complex test data generation
 */
@DisplayName("UserValidator Parameterized Tests - Advanced Challenge Solution")
class UserValidatorTestSolution {

    private UserValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UserValidator();
    }

    // ========== USERNAME VALIDATION ==========

    @ParameterizedTest(name = "Valid username: ''{0}''")
    @CsvSource({
        "john",
        "alice123",
        "user_name",
        "Test_User_99",
        "a12"  // exactly 3 chars (minimum)
    })
    @DisplayName("Valid usernames should pass")
    void testValidUsernames(String username) {
        assertTrue(validator.isValidUsername(username),
            () -> "Username '" + username + "' should be valid");
    }

    @ParameterizedTest(name = "Invalid username: ''{0}'' - {1}")
    @CsvSource({
        "'', Empty username",
        "'  ', Whitespace only",
        "ab, Too short (less than 3 chars)",
        "1john, Starts with number",
        "_john, Starts with underscore",
        "john@doe, Contains special char",
        "this_is_a_very_long_username_that_exceeds_limit, Too long (over 20 chars)"
    })
    @DisplayName("Invalid usernames should fail")
    void testInvalidUsernames(String username, String reason) {
        assertFalse(validator.isValidUsername(username),
            () -> "Username '" + username + "' should be invalid: " + reason);
    }

    // ========== EMAIL VALIDATION ==========

    @ParameterizedTest(name = "Valid email: ''{0}''")
    @CsvSource({
        "john@example.com",
        "alice.smith@company.co.uk",
        "user+tag@domain.org",
        "test.email123@sub.domain.com"
    })
    @DisplayName("Valid emails should pass")
    void testValidEmails(String email) {
        assertTrue(validator.isValidEmail(email),
            () -> "Email '" + email + "' should be valid");
    }

    @ParameterizedTest(name = "Invalid email: ''{0}'' - {1}")
    @CsvSource({
        "'', Empty email",
        "notanemail, Missing @ symbol",
        "missing@domain, Missing TLD",
        "@domain.com, Missing local part",
        "user@, Missing domain",
        "user space@domain.com, Contains space"
    })
    @DisplayName("Invalid emails should fail")
    void testInvalidEmails(String email, String reason) {
        assertFalse(validator.isValidEmail(email),
            () -> "Email '" + email + "' should be invalid: " + reason);
    }

    // ========== PASSWORD VALIDATION ==========

    @ParameterizedTest(name = "Valid password: ''{0}''")
    @CsvSource({
        "Password123",      // upper + lower + digit
        "MyP@ssw0rd",       // upper + lower + digit + special
        "abcd1234!",        // lower + digit + special
        "HELLO123!",        // upper + digit + special
        "LongPass123Word"   // long password with variety
    })
    @DisplayName("Valid passwords should pass")
    void testValidPasswords(String password) {
        assertTrue(validator.isValidPassword(password),
            () -> "Password '" + password + "' should be valid");
    }

    @ParameterizedTest(name = "Invalid password: ''{0}'' - {1}")
    @CsvSource({
        "short, Too short (less than 8 chars)",
        "alllowercase, Only lowercase",
        "ALLUPPERCASE, Only uppercase",
        "12345678, Only digits",
        "password, Only lowercase - no variety"
    })
    @DisplayName("Invalid passwords should fail")
    void testInvalidPasswords(String password, String reason) {
        assertFalse(validator.isValidPassword(password),
            () -> "Password '" + password + "' should be invalid: " + reason);
    }

    // ========== CSV FILE SOURCE (ADVANCED) ==========

    @ParameterizedTest(name = "[CSV] User: {0}, Email: {1}, Expected: {3}")
    @CsvFileSource(resources = "/test-data/users.csv", numLinesToSkip = 1)
    @DisplayName("User validation from CSV file")
    void testUserValidationFromCsv(String username, String email, String password,
                                   boolean shouldBeValid, String testCase) {
        UserValidator.ValidationResult result = validator.validateUser(username, email, password);

        assertEquals(shouldBeValid, result.isValid(),
            () -> "Test case: " + testCase + " - Expected valid=" + shouldBeValid +
                  ", but got errors: " + result.getErrors());
    }

    // ========== METHOD SOURCE (ADVANCED) ==========

    @ParameterizedTest(name = "[Method] {0}")
    @MethodSource("provideUserTestCases")
    @DisplayName("User validation from method source")
    void testUserValidationFromMethod(UserTestCase testCase) {
        UserValidator.ValidationResult result = validator.validateUser(
            testCase.username, testCase.email, testCase.password
        );

        assertEquals(testCase.expectedValid, result.isValid(),
            () -> "Test: " + testCase.description + " - Expected valid=" + testCase.expectedValid +
                  ", but got errors: " + result.getErrors());
    }

    // Method that provides test data
    static Stream<UserTestCase> provideUserTestCases() {
        return Stream.of(
            new UserTestCase("john_doe", "john@example.com", "Password123", true,
                "Valid user with all correct fields"),
            new UserTestCase("alice", "alice@company.org", "SecurePass1!", true,
                "Valid user with strong password"),
            new UserTestCase("ab", "short@test.com", "Password123", false,
                "Invalid - username too short"),
            new UserTestCase("validuser", "invalid-email", "Password123", false,
                "Invalid - bad email format"),
            new UserTestCase("validuser", "valid@email.com", "weak", false,
                "Invalid - weak password"),
            new UserTestCase("", "", "", false,
                "Invalid - all fields empty")
        );
    }

    // Helper class for method source
    static class UserTestCase {
        String username;
        String email;
        String password;
        boolean expectedValid;
        String description;

        UserTestCase(String username, String email, String password,
                    boolean expectedValid, String description) {
            this.username = username;
            this.email = email;
            this.password = password;
            this.expectedValid = expectedValid;
            this.description = description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
