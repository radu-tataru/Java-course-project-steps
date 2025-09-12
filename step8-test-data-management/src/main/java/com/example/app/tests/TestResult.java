package com.example.app.tests;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced TestResult class with data-driven testing context
 */
public class TestResult {
    private String testName;
    private String testContext; // Additional context from test data
    private List<SingleTest> tests;
    private int passedCount;
    private int failedCount;
    
    public TestResult(String testName) {
        this.testName = testName;
        this.testContext = "";
        this.tests = new ArrayList<>();
        this.passedCount = 0;
        this.failedCount = 0;
    }
    
    public TestResult(String testName, String testContext) {
        this.testName = testName;
        this.testContext = testContext;
        this.tests = new ArrayList<>();
        this.passedCount = 0;
        this.failedCount = 0;
    }
    
    public void addTest(String testDescription, boolean passed, String details) {
        tests.add(new SingleTest(testDescription, passed, details));
        if (passed) {
            passedCount++;
        } else {
            failedCount++;
        }
    }
    
    public String getOverallResult() {
        int totalTests = passedCount + failedCount;
        String contextInfo = testContext.isEmpty() ? "" : " [" + testContext + "]";
        return String.format("%s%s: %d/%d tests passed (%.1f%%)", 
                           testName, contextInfo, passedCount, totalTests, 
                           totalTests > 0 ? (passedCount * 100.0 / totalTests) : 0.0);
    }
    
    public String getDetailedResults() {
        StringBuilder sb = new StringBuilder();
        String contextInfo = testContext.isEmpty() ? "" : " [" + testContext + "]";
        sb.append("\n=== ").append(testName).append(contextInfo).append(" ===\n");
        
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
    
    public boolean allTestsPassed() {
        return failedCount == 0 && passedCount > 0;
    }
    
    public int getPassedCount() {
        return passedCount;
    }
    
    public int getFailedCount() {
        return failedCount;
    }
    
    public int getTotalTests() {
        return passedCount + failedCount;
    }
    
    public String getTestContext() {
        return testContext;
    }
    
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