package com.example.app;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ExtentReports Manager for generating professional HTML test reports
 * Integrates with existing Page Object tests and Cucumber scenarios
 */
public class ExtentReportManager {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static final String REPORT_PATH = "target/extent-reports/ExtentReport.html";
    private static final String SCREENSHOTS_PATH = "target/extent-reports/screenshots/";

    /**
     * Initialize ExtentReports with professional configuration
     */
    public static void initializeReport() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);

        // Configure the report appearance
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("QA Automation Test Report");
        sparkReporter.config().setReportName("Website Verification Test Results");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information to the report
        extent.setSystemInfo("Test Environment", "Development");
        extent.setSystemInfo("Browser", "Chrome (Headless)");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Test Framework", "Selenium + Cucumber + ExtentReports");
        extent.setSystemInfo("Execution Date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    /**
     * Create a new test in the report
     */
    public static ExtentTest createTest(String testName, String description) {
        test = extent.createTest(testName, description);
        return test;
    }

    /**
     * Log an info message to the current test
     */
    public static void logInfo(String message) {
        if (test != null) {
            test.info(message);
        }
    }

    /**
     * Log a pass message to the current test
     */
    public static void logPass(String message) {
        if (test != null) {
            test.pass(message);
        }
    }

    /**
     * Log a fail message to the current test
     */
    public static void logFail(String message) {
        if (test != null) {
            test.fail(message);
        }
    }

    /**
     * Capture screenshot and attach to the current test
     */
    public static void captureScreenshot(WebDriver driver, String stepName) {
        if (test != null) {
            try {
                String screenshotPath = captureScreenshotAsFile(driver);
                test.addScreenCaptureFromPath(screenshotPath, stepName);
                test.info("Screenshot captured: " + stepName);
            } catch (IOException e) {
                test.fail("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    /**
     * Capture screenshot and return file path
     */
    private static String captureScreenshotAsFile(WebDriver driver) throws IOException {
        // Create screenshots directory if it doesn't exist
        File screenshotsDir = new File(SCREENSHOTS_PATH);
        if (!screenshotsDir.exists()) {
            screenshotsDir.mkdirs();
        }

        // Capture screenshot
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        // Generate unique filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String screenshotPath = "screenshots/" + timestamp + "_screenshot.png";
        File destFile = new File("target/extent-reports/" + screenshotPath);

        // Copy screenshot to destination
        FileUtils.copyFile(sourceFile, destFile);

        return screenshotPath;
    }

    /**
     * Log test result with screenshot
     */
    public static void logTestResult(TestResult result, WebDriver driver) {
        if (test != null) {
            if (result.allTestsPassed()) {
                test.pass("Test completed successfully: " + result.getTestName());
                captureScreenshot(driver, "Success - " + result.getTestName());
            } else {
                test.fail("Test failed: " + result.getTestName() + " - " + result.getFailureMessage());
                captureScreenshot(driver, "Failure - " + result.getTestName());
            }
        }
    }

    /**
     * Add website information to the report
     */
    public static void logWebsiteInfo(LinkData website) {
        if (test != null) {
            test.info("<b>Website:</b> " + website.getName());
            test.info("<b>URL:</b> <a href='" + website.getUrl() + "' target='_blank'>" + website.getUrl() + "</a>");
            test.info("<b>Expected Title:</b> " + website.getExpectedTitle());
        }
    }

    /**
     * Finalize and save the report
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
            System.out.println("ExtentReports generated at: " + new File(REPORT_PATH).getAbsolutePath());
        }
    }

    /**
     * Get the current test instance
     */
    public static ExtentTest getCurrentTest() {
        return test;
    }

    /**
     * Set test status based on result
     */
    public static void setTestStatus(boolean passed, String message) {
        if (test != null) {
            if (passed) {
                test.log(Status.PASS, message);
            } else {
                test.log(Status.FAIL, message);
            }
        }
    }
}