package com.example.app.tests;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced TestSummary class for aggregating test results in data-driven framework
 */
public class TestSummary {
    private List<TestResult> testResults;
    private String summaryContext;
    
    public TestSummary() {
        this.testResults = new ArrayList<>();
        this.summaryContext = "";
    }
    
    public TestSummary(String summaryContext) {
        this.testResults = new ArrayList<>();
        this.summaryContext = summaryContext;
    }
    
    public void addTestResult(TestResult result) {
        testResults.add(result);
    }
    
    public List<TestResult> getTestResults() {
        return testResults;
    }
    
    public int getTotalSuites() {
        return testResults.size();
    }
    
    public int getTotalTests() {
        return testResults.stream().mapToInt(TestResult::getTotalTests).sum();
    }
    
    public int getTotalPassed() {
        return testResults.stream().mapToInt(TestResult::getPassedCount).sum();
    }
    
    public int getTotalFailed() {
        return testResults.stream().mapToInt(TestResult::getFailedCount).sum();
    }
    
    public double getPassPercentage() {
        int total = getTotalTests();
        return total > 0 ? (getTotalPassed() * 100.0 / total) : 0.0;
    }
    
    public boolean allTestsPassed() {
        return getTotalFailed() == 0 && getTotalPassed() > 0;
    }
    
    public String getSummaryString() {
        StringBuilder sb = new StringBuilder();
        String contextInfo = summaryContext.isEmpty() ? "" : " [" + summaryContext + "]";
        sb.append("Test Summary").append(contextInfo).append(":\n");
        sb.append("  Test Suites: ").append(getTotalSuites()).append("\n");
        sb.append("  Total Tests: ").append(getTotalTests()).append("\n");
        sb.append("  Passed: ").append(getTotalPassed()).append("\n");
        sb.append("  Failed: ").append(getTotalFailed()).append("\n");
        sb.append("  Pass Rate: ").append(String.format("%.1f%%", getPassPercentage())).append("\n");
        return sb.toString();
    }
    
    public String getDetailedSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append(getSummaryString()).append("\n");
        sb.append("Detailed Results:\n");
        
        for (TestResult result : testResults) {
            sb.append(result.getDetailedResults()).append("\n");
        }
        
        return sb.toString();
    }
    
    public String getSummaryContext() {
        return summaryContext;
    }
    
    public void setSummaryContext(String summaryContext) {
        this.summaryContext = summaryContext;
    }
}