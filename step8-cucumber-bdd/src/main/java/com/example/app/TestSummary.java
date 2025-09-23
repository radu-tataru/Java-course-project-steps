package com.example.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to aggregate and summarize multiple test results
 */
public class TestSummary {
    private List<TestResult> testResults;
    
    public TestSummary() {
        this.testResults = new ArrayList<>();
    }
    
    /**
     * Adds a test result to the summary
     * @param result TestResult to add
     */
    public void addTestResult(TestResult result) {
        testResults.add(result);
    }
    
    /**
     * Gets all test results
     * @return List of TestResult objects
     */
    public List<TestResult> getTestResults() {
        return testResults;
    }
    
    /**
     * Gets total number of test suites
     * @return Number of test suites
     */
    public int getTotalSuites() {
        return testResults.size();
    }
    
    /**
     * Gets total number of individual tests across all suites
     * @return Total number of tests
     */
    public int getTotalTests() {
        return testResults.stream().mapToInt(TestResult::getTotalTests).sum();
    }
    
    /**
     * Gets total number of passed tests
     * @return Total number of passed tests
     */
    public int getTotalPassed() {
        return testResults.stream().mapToInt(TestResult::getPassedCount).sum();
    }
    
    /**
     * Gets total number of failed tests
     * @return Total number of failed tests
     */
    public int getTotalFailed() {
        return testResults.stream().mapToInt(TestResult::getFailedCount).sum();
    }
    
    /**
     * Gets overall pass percentage
     * @return Pass percentage as double
     */
    public double getPassPercentage() {
        int total = getTotalTests();
        return total > 0 ? (getTotalPassed() * 100.0 / total) : 0.0;
    }
    
    /**
     * Checks if all tests passed
     * @return true if all tests passed
     */
    public boolean allTestsPassed() {
        return getTotalFailed() == 0 && getTotalPassed() > 0;
    }
    
    /**
     * Gets a comprehensive summary string
     * @return Summary string
     */
    public String getSummaryString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Test Summary:\n");
        sb.append("  Test Suites: ").append(getTotalSuites()).append("\n");
        sb.append("  Total Tests: ").append(getTotalTests()).append("\n");
        sb.append("  Passed: ").append(getTotalPassed()).append("\n");
        sb.append("  Failed: ").append(getTotalFailed()).append("\n");
        sb.append("  Pass Rate: ").append(String.format("%.1f%%", getPassPercentage())).append("\n");
        return sb.toString();
    }
    
    /**
     * Gets detailed results for all test suites
     * @return Detailed results string
     */
    public String getDetailedSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSummaryString()).append("\n");
        sb.append("Detailed Results:\n");
        
        for (TestResult result : testResults) {
            sb.append(result.getDetailedResults()).append("\n");
        }
        
        return sb.toString();
    }
}