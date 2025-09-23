package com.example.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold test results and provide detailed reporting
 */
public class TestResult {
    private String testName;
    private List<SingleTest> tests;
    private int passedCount;
    private int failedCount;
    
    public TestResult(String testName) {
        this.testName = testName;
        this.tests = new ArrayList<>();
        this.passedCount = 0;
        this.failedCount = 0;
    }
    
    /**
     * Adds a test result
     * @param testDescription Description of the test
     * @param passed Whether the test passed
     * @param details Additional details about the test
     */
    public void addTest(String testDescription, boolean passed, String details) {
        tests.add(new SingleTest(testDescription, passed, details));
        if (passed) {
            passedCount++;
        } else {
            failedCount++;
        }
    }
    
    /**
     * Gets the overall result summary
     * @return Result summary string
     */
    public String getOverallResult() {
        int totalTests = passedCount + failedCount;
        return String.format("%s: %d/%d tests passed (%.1f%%)", 
                           testName, passedCount, totalTests, 
                           totalTests > 0 ? (passedCount * 100.0 / totalTests) : 0.0);
    }
    
    /**
     * Gets detailed test results
     * @return Detailed results string
     */
    public String getDetailedResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== ").append(testName).append(" ===\n");
        
        for (SingleTest test : tests) {
            String status = test.passed ? "✅ PASS" : "❌ FAIL";
            sb.append(status).append(": ").append(test.description).append("\n");
            if (!test.details.isEmpty()) {
                sb.append("    Details: ").append(test.details).append("\n");
            }
        }
        
        sb.append("\nSUMMARY: ").append(getOverallResult()).append("\n");
        return sb.toString();
    }
    
    /**
     * Checks if all tests passed
     * @return true if all tests passed
     */
    public boolean allTestsPassed() {
        return failedCount == 0 && passedCount > 0;
    }
    
    /**
     * Gets the number of passed tests
     * @return Number of passed tests
     */
    public int getPassedCount() {
        return passedCount;
    }
    
    /**
     * Gets the number of failed tests
     * @return Number of failed tests
     */
    public int getFailedCount() {
        return failedCount;
    }
    
    /**
     * Gets total number of tests
     * @return Total number of tests
     */
    public int getTotalTests() {
        return passedCount + failedCount;
    }
    
    /**
     * Inner class to represent a single test
     */
    private static class SingleTest {
        String description;
        boolean passed;
        String details;
        
        SingleTest(String description, boolean passed, String details) {
            this.description = description;
            this.passed = passed;
            this.details = details;
        }
    }
}