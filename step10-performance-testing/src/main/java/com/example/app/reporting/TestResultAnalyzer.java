package com.example.app.reporting;

import java.util.ArrayList;
import java.util.List;

/**
 * TestResultAnalyzer - Analyze and summarize test execution results
 * Provides comprehensive test analytics for CI/CD reporting and notifications
 */
public class TestResultAnalyzer {
    
    /**
     * Analyze test results and generate summary
     */
    public static TestSummary analyzeResults(List<TestResult> testResults) {
        TestSummary summary = new TestSummary();
        
        int totalTests = testResults.size();
        int passedTests = 0;
        int failedTests = 0;
        int skippedTests = 0;
        long totalExecutionTime = 0;
        List<String> failedTestNames = new ArrayList<>();
        
        for (TestResult result : testResults) {
            totalExecutionTime += result.getExecutionTime();
            
            switch (result.getStatus()) {
                case PASSED:
                    passedTests++;
                    break;
                case FAILED:
                    failedTests++;
                    failedTestNames.add(result.getTestName() + ": " + result.getFailureMessage());
                    break;
                case SKIPPED:
                    skippedTests++;
                    break;
            }
        }
        
        double successRate = totalTests > 0 ? ((double) passedTests / totalTests) * 100 : 0;
        
        summary.setTotalTests(totalTests);
        summary.setPassedTests(passedTests);
        summary.setFailedTests(failedTests);
        summary.setSkippedTests(skippedTests);
        summary.setSuccessRate(Math.round(successRate * 100.0) / 100.0);
        summary.setExecutionTime(totalExecutionTime);
        summary.setFailedTestNames(failedTestNames);
        
        return summary;
    }
    
    /**
     * Generate detailed report string
     */
    public static String generateDetailedReport(TestSummary summary) {
        StringBuilder report = new StringBuilder();
        report.append("=== TEST EXECUTION SUMMARY ===\n");
        report.append("Total Tests: ").append(summary.getTotalTests()).append("\n");
        report.append("Passed: ").append(summary.getPassedTests()).append("\n");
        report.append("Failed: ").append(summary.getFailedTests()).append("\n");
        report.append("Skipped: ").append(summary.getSkippedTests()).append("\n");
        report.append("Success Rate: ").append(summary.getSuccessRate()).append("%\n");
        report.append("Total Execution Time: ").append(summary.getExecutionTime()).append("ms\n");
        
        if (summary.getFailedTests() > 0) {
            report.append("\n=== FAILED TESTS ===\n");
            for (String failedTest : summary.getFailedTestNames()) {
                report.append("- ").append(failedTest).append("\n");
            }
        }
        
        return report.toString();
    }
    
    /**
     * Check if test execution meets quality gates
     */
    public static QualityGateResult checkQualityGates(TestSummary summary) {
        QualityGateResult result = new QualityGateResult();
        
        // Quality gate criteria
        double minimumSuccessRate = 98.0; // 98% pass rate required
        long maximumExecutionTime = 900000; // 15 minutes max
        int maximumFailures = 1; // Max 1 failure allowed
        
        boolean passRateGate = summary.getSuccessRate() >= minimumSuccessRate;
        boolean executionTimeGate = summary.getExecutionTime() <= maximumExecutionTime;
        boolean failureCountGate = summary.getFailedTests() <= maximumFailures;
        
        result.setPassRateGatePassed(passRateGate);
        result.setExecutionTimeGatePassed(executionTimeGate);
        result.setFailureCountGatePassed(failureCountGate);
        result.setOverallPassed(passRateGate && executionTimeGate && failureCountGate);
        
        // Generate gate messages
        List<String> gateMessages = new ArrayList<>();
        if (!passRateGate) {
            gateMessages.add("Pass rate (" + summary.getSuccessRate() + "%) below minimum (" + minimumSuccessRate + "%)");
        }
        if (!executionTimeGate) {
            gateMessages.add("Execution time (" + summary.getExecutionTime() + "ms) exceeded maximum (" + maximumExecutionTime + "ms)");
        }
        if (!failureCountGate) {
            gateMessages.add("Too many failures (" + summary.getFailedTests() + ") exceeded maximum (" + maximumFailures + ")");
        }
        
        result.setGateMessages(gateMessages);
        return result;
    }
    
    /**
     * Test execution summary data class
     */
    public static class TestSummary {
        private int totalTests;
        private int passedTests;
        private int failedTests;
        private int skippedTests;
        private double successRate;
        private long executionTime;
        private List<String> failedTestNames;
        
        // Getters and setters
        public int getTotalTests() { return totalTests; }
        public void setTotalTests(int totalTests) { this.totalTests = totalTests; }
        
        public int getPassedTests() { return passedTests; }
        public void setPassedTests(int passedTests) { this.passedTests = passedTests; }
        
        public int getFailedTests() { return failedTests; }
        public void setFailedTests(int failedTests) { this.failedTests = failedTests; }
        
        public int getSkippedTests() { return skippedTests; }
        public void setSkippedTests(int skippedTests) { this.skippedTests = skippedTests; }
        
        public double getSuccessRate() { return successRate; }
        public void setSuccessRate(double successRate) { this.successRate = successRate; }
        
        public long getExecutionTime() { return executionTime; }
        public void setExecutionTime(long executionTime) { this.executionTime = executionTime; }
        
        public List<String> getFailedTestNames() { return failedTestNames; }
        public void setFailedTestNames(List<String> failedTestNames) { this.failedTestNames = failedTestNames; }
    }
    
    /**
     * Quality gate evaluation result
     */
    public static class QualityGateResult {
        private boolean passRateGatePassed;
        private boolean executionTimeGatePassed;
        private boolean failureCountGatePassed;
        private boolean overallPassed;
        private List<String> gateMessages;
        
        // Getters and setters
        public boolean isPassRateGatePassed() { return passRateGatePassed; }
        public void setPassRateGatePassed(boolean passRateGatePassed) { this.passRateGatePassed = passRateGatePassed; }
        
        public boolean isExecutionTimeGatePassed() { return executionTimeGatePassed; }
        public void setExecutionTimeGatePassed(boolean executionTimeGatePassed) { this.executionTimeGatePassed = executionTimeGatePassed; }
        
        public boolean isFailureCountGatePassed() { return failureCountGatePassed; }
        public void setFailureCountGatePassed(boolean failureCountGatePassed) { this.failureCountGatePassed = failureCountGatePassed; }
        
        public boolean isOverallPassed() { return overallPassed; }
        public void setOverallPassed(boolean overallPassed) { this.overallPassed = overallPassed; }
        
        public List<String> getGateMessages() { return gateMessages; }
        public void setGateMessages(List<String> gateMessages) { this.gateMessages = gateMessages; }
    }
    
    /**
     * Individual test result
     */
    public static class TestResult {
        private String testName;
        private TestStatus status;
        private String failureMessage;
        private long executionTime;
        
        public TestResult(String testName, TestStatus status, String failureMessage, long executionTime) {
            this.testName = testName;
            this.status = status;
            this.failureMessage = failureMessage;
            this.executionTime = executionTime;
        }
        
        // Getters
        public String getTestName() { return testName; }
        public TestStatus getStatus() { return status; }
        public String getFailureMessage() { return failureMessage; }
        public long getExecutionTime() { return executionTime; }
    }
    
    public enum TestStatus {
        PASSED, FAILED, SKIPPED
    }
}