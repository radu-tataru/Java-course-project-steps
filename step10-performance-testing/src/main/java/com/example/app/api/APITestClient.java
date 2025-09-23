package com.example.app.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * APITestClient - REST API testing automation with REST Assured
 * Provides comprehensive API testing capabilities including authentication,
 * validation, and contract testing
 */
public class APITestClient {
    private String baseUri;
    private RequestSpecification requestSpec;
    private ObjectMapper objectMapper;
    
    public APITestClient(String baseUri) {
        this.baseUri = baseUri;
        this.objectMapper = new ObjectMapper();
        setupDefaultConfiguration();
    }
    
    private void setupDefaultConfiguration() {
        RestAssured.baseURI = baseUri;
        requestSpec = given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .log().all(); // Log all requests for debugging
    }
    
    /**
     * Test GitHub API health check
     */
    public APITestResult testGitHubHealthCheck() {
        String testName = "GitHub API Health Check";
        long startTime = System.currentTimeMillis();
        
        try {
            Response response = given(requestSpec)
                .when()
                .get("https://api.github.com")
                .then()
                .statusCode(200)
                .time(lessThan(5000L), TimeUnit.MILLISECONDS)
                .extract().response();
                
            long executionTime = System.currentTimeMillis() - startTime;
            
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://api.github.com")
                .method("GET")
                .statusCode(response.getStatusCode())
                .responseTime(response.getTime())
                .executionTime(executionTime)
                .passed(true)
                .build();
                
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://api.github.com")
                .method("GET")
                .executionTime(executionTime)
                .passed(false)
                .errorMessage(e.getMessage())
                .build();
        }
    }
    
    /**
     * Test public API endpoints
     */
    public List<APITestResult> testPublicAPIs() {
        List<APITestResult> results = new ArrayList<>();
        
        // Test JSONPlaceholder API
        results.add(testJSONPlaceholderPosts());
        results.add(testJSONPlaceholderUsers());
        
        // Test httpbin.org endpoints
        results.add(testHttpBinStatus());
        results.add(testHttpBinHeaders());
        
        return results;
    }
    
    private APITestResult testJSONPlaceholderPosts() {
        String testName = "JSONPlaceholder Posts API";
        long startTime = System.currentTimeMillis();
        
        try {
            Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue())
                .time(lessThan(3000L), TimeUnit.MILLISECONDS)
                .extract().response();
                
            long executionTime = System.currentTimeMillis() - startTime;
            
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://jsonplaceholder.typicode.com/posts/1")
                .method("GET")
                .statusCode(response.getStatusCode())
                .responseTime(response.getTime())
                .executionTime(executionTime)
                .passed(true)
                .build();
                
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://jsonplaceholder.typicode.com/posts/1")
                .method("GET")
                .executionTime(executionTime)
                .passed(false)
                .errorMessage(e.getMessage())
                .build();
        }
    }
    
    private APITestResult testJSONPlaceholderUsers() {
        String testName = "JSONPlaceholder Users API";
        long startTime = System.currentTimeMillis();
        
        try {
            Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("[0].id", equalTo(1))
                .body("[0].name", notNullValue())
                .body("[0].email", matchesRegex(".*@.*\\..*"))
                .time(lessThan(3000L), TimeUnit.MILLISECONDS)
                .extract().response();
                
            long executionTime = System.currentTimeMillis() - startTime;
            
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://jsonplaceholder.typicode.com/users")
                .method("GET")
                .statusCode(response.getStatusCode())
                .responseTime(response.getTime())
                .executionTime(executionTime)
                .passed(true)
                .build();
                
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://jsonplaceholder.typicode.com/users")
                .method("GET")
                .executionTime(executionTime)
                .passed(false)
                .errorMessage(e.getMessage())
                .build();
        }
    }
    
    private APITestResult testHttpBinStatus() {
        String testName = "HTTPBin Status API";
        long startTime = System.currentTimeMillis();
        
        try {
            Response response = given()
                .when()
                .get("https://httpbin.org/status/200")
                .then()
                .statusCode(200)
                .time(lessThan(5000L), TimeUnit.MILLISECONDS)
                .extract().response();
                
            long executionTime = System.currentTimeMillis() - startTime;
            
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://httpbin.org/status/200")
                .method("GET")
                .statusCode(response.getStatusCode())
                .responseTime(response.getTime())
                .executionTime(executionTime)
                .passed(true)
                .build();
                
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://httpbin.org/status/200")
                .method("GET")
                .executionTime(executionTime)
                .passed(false)
                .errorMessage(e.getMessage())
                .build();
        }
    }
    
    private APITestResult testHttpBinHeaders() {
        String testName = "HTTPBin Headers API";
        long startTime = System.currentTimeMillis();
        
        try {
            Response response = given()
                .header("Custom-Header", "test-value")
                .when()
                .get("https://httpbin.org/headers")
                .then()
                .statusCode(200)
                .body("headers.Custom-Header", equalTo("test-value"))
                .body("headers.Host", equalTo("httpbin.org"))
                .time(lessThan(5000L), TimeUnit.MILLISECONDS)
                .extract().response();
                
            long executionTime = System.currentTimeMillis() - startTime;
            
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://httpbin.org/headers")
                .method("GET")
                .statusCode(response.getStatusCode())
                .responseTime(response.getTime())
                .executionTime(executionTime)
                .passed(true)
                .build();
                
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            return APITestResult.builder()
                .testName(testName)
                .endpoint("https://httpbin.org/headers")
                .method("GET")
                .executionTime(executionTime)
                .passed(false)
                .errorMessage(e.getMessage())
                .build();
        }
    }
    
    /**
     * Generate API test summary report
     */
    public APITestSummary generateTestSummary(List<APITestResult> results) {
        int totalTests = results.size();
        int passedTests = (int) results.stream().mapToLong(r -> r.isPassed() ? 1 : 0).sum();
        int failedTests = totalTests - passedTests;
        
        double averageResponseTime = results.stream()
            .filter(APITestResult::isPassed)
            .mapToLong(APITestResult::getResponseTime)
            .average()
            .orElse(0.0);
            
        long maxResponseTime = results.stream()
            .filter(APITestResult::isPassed)
            .mapToLong(APITestResult::getResponseTime)
            .max()
            .orElse(0);
            
        double successRate = totalTests > 0 ? ((double) passedTests / totalTests) * 100 : 0;
        
        return APITestSummary.builder()
            .totalTests(totalTests)
            .passedTests(passedTests)
            .failedTests(failedTests)
            .successRate(successRate)
            .averageResponseTime(averageResponseTime)
            .maxResponseTime(maxResponseTime)
            .testResults(results)
            .build();
    }
    
    /**
     * API test result data class
     */
    public static class APITestResult {
        private String testName;
        private String endpoint;
        private String method;
        private int statusCode;
        private long responseTime;
        private long executionTime;
        private boolean passed;
        private String errorMessage;
        
        public static Builder builder() {
            return new Builder();
        }
        
        // Getters
        public String getTestName() { return testName; }
        public String getEndpoint() { return endpoint; }
        public String getMethod() { return method; }
        public int getStatusCode() { return statusCode; }
        public long getResponseTime() { return responseTime; }
        public long getExecutionTime() { return executionTime; }
        public boolean isPassed() { return passed; }
        public String getErrorMessage() { return errorMessage; }
        
        public static class Builder {
            private APITestResult result = new APITestResult();
            
            public Builder testName(String testName) { result.testName = testName; return this; }
            public Builder endpoint(String endpoint) { result.endpoint = endpoint; return this; }
            public Builder method(String method) { result.method = method; return this; }
            public Builder statusCode(int statusCode) { result.statusCode = statusCode; return this; }
            public Builder responseTime(long responseTime) { result.responseTime = responseTime; return this; }
            public Builder executionTime(long executionTime) { result.executionTime = executionTime; return this; }
            public Builder passed(boolean passed) { result.passed = passed; return this; }
            public Builder errorMessage(String errorMessage) { result.errorMessage = errorMessage; return this; }
            
            public APITestResult build() { return result; }
        }
    }
    
    /**
     * API test summary data class
     */
    public static class APITestSummary {
        private int totalTests;
        private int passedTests;
        private int failedTests;
        private double successRate;
        private double averageResponseTime;
        private long maxResponseTime;
        private List<APITestResult> testResults;
        
        public static Builder builder() {
            return new Builder();
        }
        
        // Getters
        public int getTotalTests() { return totalTests; }
        public int getPassedTests() { return passedTests; }
        public int getFailedTests() { return failedTests; }
        public double getSuccessRate() { return successRate; }
        public double getAverageResponseTime() { return averageResponseTime; }
        public long getMaxResponseTime() { return maxResponseTime; }
        public List<APITestResult> getTestResults() { return testResults; }
        
        public static class Builder {
            private APITestSummary summary = new APITestSummary();
            
            public Builder totalTests(int totalTests) { summary.totalTests = totalTests; return this; }
            public Builder passedTests(int passedTests) { summary.passedTests = passedTests; return this; }
            public Builder failedTests(int failedTests) { summary.failedTests = failedTests; return this; }
            public Builder successRate(double successRate) { summary.successRate = successRate; return this; }
            public Builder averageResponseTime(double averageResponseTime) { summary.averageResponseTime = averageResponseTime; return this; }
            public Builder maxResponseTime(long maxResponseTime) { summary.maxResponseTime = maxResponseTime; return this; }
            public Builder testResults(List<APITestResult> testResults) { summary.testResults = testResults; return this; }
            
            public APITestSummary build() { return summary; }
        }
    }
}