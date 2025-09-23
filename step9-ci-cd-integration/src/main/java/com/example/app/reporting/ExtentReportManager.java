package com.example.app.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.example.app.config.EnvironmentConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * ExtentReportManager - Advanced test reporting with ExtentReports framework
 * Provides comprehensive HTML reports with screenshots, test data attachments,
 * and detailed analytics for CI/CD integration.
 */
public class ExtentReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String REPORT_PATH = "target/extent-reports/ExtentReport.html";
    
    /**
     * Initialize ExtentReports with configuration and system information
     */
    public static void initializeReport() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Test Automation Report");
            sparkReporter.config().setReportName("Web Application Testing - Step 9");
            sparkReporter.config().setTimelineEnabled(true);
            
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // Add system information
            extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
            extent.setSystemInfo("Environment", System.getProperty("env", "dev"));
            extent.setSystemInfo("Base URL", EnvironmentConfig.getProperty("base.url"));
            extent.setSystemInfo("Test Suite", "CI/CD Integration Suite");
            extent.setSystemInfo("Execution Date", LocalDateTime.now().toString());
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }
    }
    
    /**
     * Create a new test in the report
     */
    public static ExtentTest createTest(String testName, String description) {
        initializeReport();
        test = extent.createTest(testName, description);
        return test;
    }
    
    /**
     * Get current active test instance
     */
    public static ExtentTest getCurrentTest() {
        return test;
    }
    
    /**
     * Capture screenshot and attach to current test
     */
    public static void captureScreenshot(WebDriver driver, String stepName) {
        try {
            String screenshotPath = captureScreenshotAsFile(driver);
            test.addScreenCaptureFromPath(screenshotPath, stepName);
            test.info("Screenshot captured: " + stepName);
        } catch (Exception e) {
            test.fail("Screenshot capture failed: " + e.getMessage());
        }
    }
    
    /**
     * Capture screenshot and save to file system
     */
    private static String captureScreenshotAsFile(WebDriver driver) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String screenshotPath = "screenshots/" + System.currentTimeMillis() + ".png";
        File destFile = new File("target/extent-reports/" + screenshotPath);
        destFile.getParentFile().mkdirs();
        FileUtils.copyFile(sourceFile, destFile);
        return screenshotPath;
    }
    
    /**
     * Attach test data as JSON to the report
     */
    public static void attachTestData(Object testData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonData = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(testData);
            test.info("<details><summary>Test Data</summary><pre>" + jsonData + "</pre></details>");
        } catch (Exception e) {
            test.warning("Failed to serialize test data: " + e.getMessage());
        }
    }
    
    /**
     * Log test step with status
     */
    public static void logStep(Status status, String message) {
        if (test != null) {
            test.log(status, message);
        }
    }
    
    /**
     * Log info message
     */
    public static void logInfo(String message) {
        logStep(Status.INFO, message);
    }
    
    /**
     * Log pass message
     */
    public static void logPass(String message) {
        logStep(Status.PASS, message);
    }
    
    /**
     * Log fail message
     */
    public static void logFail(String message) {
        logStep(Status.FAIL, message);
    }
    
    /**
     * Log warning message
     */
    public static void logWarning(String message) {
        logStep(Status.WARNING, message);
    }
    
    /**
     * Attach environment information to the test
     */
    public static void attachEnvironmentInfo() {
        if (test != null) {
            test.assignCategory("Environment: " + System.getProperty("env", "dev"));
            test.assignAuthor("CI/CD Pipeline");
            test.assignDevice("Browser: " + System.getProperty("browser", "chrome"));
        }
    }
    
    /**
     * Flush reports and generate final HTML
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
    
    /**
     * Get the report file path
     */
    public static String getReportPath() {
        return REPORT_PATH;
    }
}