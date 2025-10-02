package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SOLUTION: Simple Challenge - Calculator Parameterized Tests
 *
 * This demonstrates how to transform repetitive test methods into
 * elegant parameterized tests using @CsvSource and @ValueSource
 */
@DisplayName("Calculator Parameterized Tests - Simple Challenge Solution")
class CalculatorTestSolution {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource({
        "2, 3, 5",
        "10, 5, 15",
        "0, 0, 0",
        "-5, 5, 0",
        "100, 200, 300",
        "-10, -20, -30"
    })
    @DisplayName("Addition tests")
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, calculator.add(a, b),
            () -> a + " + " + b + " should equal " + expected);
    }

    @ParameterizedTest(name = "{0} - {1} = {2}")
    @CsvSource({
        "10, 5, 5",
        "5, 10, -5",
        "0, 0, 0",
        "100, 50, 50",
        "-5, -3, -2"
    })
    @DisplayName("Subtraction tests")
    void testSubtraction(int a, int b, int expected) {
        assertEquals(expected, calculator.subtract(a, b),
            () -> a + " - " + b + " should equal " + expected);
    }

    @ParameterizedTest(name = "{0} * {1} = {2}")
    @CsvSource({
        "2, 3, 6",
        "5, 5, 25",
        "0, 100, 0",
        "-2, 3, -6",
        "-2, -3, 6",
        "10, 0, 0"
    })
    @DisplayName("Multiplication tests")
    void testMultiplication(int a, int b, int expected) {
        assertEquals(expected, calculator.multiply(a, b),
            () -> a + " * " + b + " should equal " + expected);
    }

    @ParameterizedTest(name = "{0} / {1} = {2}")
    @CsvSource({
        "10, 2, 5",
        "100, 10, 10",
        "9, 3, 3",
        "-10, 2, -5",
        "0, 5, 0"
    })
    @DisplayName("Division tests")
    void testDivision(int a, int b, int expected) {
        assertEquals(expected, calculator.divide(a, b),
            () -> a + " / " + b + " should equal " + expected);
    }

    @ParameterizedTest(name = "{0} is even")
    @ValueSource(ints = {0, 2, 4, 10, 100, -2, -8})
    @DisplayName("Even number tests")
    void testEvenNumbers(int number) {
        assertTrue(calculator.isEven(number),
            () -> number + " should be even");
    }

    @ParameterizedTest(name = "{0} is odd")
    @ValueSource(ints = {1, 3, 5, 7, 99, -1, -7})
    @DisplayName("Odd number tests")
    void testOddNumbers(int number) {
        assertFalse(calculator.isEven(number),
            () -> number + " should be odd");
    }

    @ParameterizedTest(name = "{0} is prime")
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29})
    @DisplayName("Prime number tests")
    void testPrimeNumbers(int number) {
        assertTrue(calculator.isPrime(number),
            () -> number + " should be prime");
    }

    @ParameterizedTest(name = "{0} is not prime")
    @ValueSource(ints = {0, 1, 4, 6, 8, 9, 10, 12, 15, 20})
    @DisplayName("Non-prime number tests")
    void testNonPrimeNumbers(int number) {
        assertFalse(calculator.isPrime(number),
            () -> number + " should not be prime");
    }
}
