package com.example.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main class demonstrating Apache Log4j 2 integration
 * This replaces the custom Singleton Logger from Step 4 with enterprise-grade logging
 */
public class Main {
    // Get logger for this class - replaces Logger.getInstance()
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("=== Step 10: Professional Logging with Log4j 2 ===");
        logger.info("Demonstrating enterprise-grade logging framework");
        System.out.println();

        // Demonstrate different log levels
        demonstrateLogLevels();
        System.out.println();

        // Demonstrate parameterized logging
        demonstrateParameterizedLogging();
        System.out.println();

        // Demonstrate exception logging
        demonstrateExceptionLogging();
        System.out.println();

        // Show log file locations
        displayLogFileInformation();

        logger.info("=== Log4j 2 Demonstration Complete ===");
        logger.info("Check the logs/ directory for generated log files");
    }

    /**
     * Demonstrates all Log4j 2 log levels
     */
    private static void demonstrateLogLevels() {
        logger.info("--- Demonstrating Log Levels ---");
        System.out.println("📋 Testing all log levels (check logs/ directory for output):");

        // TRACE - Very detailed diagnostic
        logger.trace("TRACE: This is extremely detailed diagnostic information");
        System.out.println("   ✓ TRACE logged (very detailed diagnostics)");

        // DEBUG - Detailed debugging info
        logger.debug("DEBUG: This is detailed debugging information");
        System.out.println("   ✓ DEBUG logged (detailed debugging)");

        // INFO - Important business events
        logger.info("INFO: Application milestone reached - demonstration started");
        System.out.println("   ✓ INFO logged (important events)");

        // WARN - Potentially harmful situations
        logger.warn("WARN: This is a warning - something unusual happened");
        System.out.println("   ✓ WARN logged (potential issues)");

        // ERROR - Error events
        logger.error("ERROR: An error occurred in the demonstration");
        System.out.println("   ✓ ERROR logged (error events)");

        // FATAL - Critical failures
        logger.fatal("FATAL: Critical system failure (demonstration only)");
        System.out.println("   ✓ FATAL logged (critical failures)");
    }

    /**
     * Demonstrates parameterized logging for better performance
     */
    private static void demonstrateParameterizedLogging() {
        logger.info("--- Demonstrating Parameterized Logging ---");
        System.out.println("🔧 Using parameterized logging (more efficient than string concatenation):");

        String username = "john.doe@example.com";
        int attempts = 3;
        double executionTime = 1.234;

        // Parameterized logging - Log4j only formats if the level is enabled
        logger.info("User '{}' logged in after {} attempts", username, attempts);
        System.out.println("   ✓ Logged user login with parameters");

        logger.debug("Test execution completed in {} seconds", executionTime);
        System.out.println("   ✓ Logged execution time with parameter");

        // Multiple parameters
        String testName = "LoginTest";
        String status = "PASSED";
        long duration = 2500L;
        logger.info("Test '{}' completed with status '{}' in {} ms", testName, status, duration);
        System.out.println("   ✓ Logged test result with multiple parameters");
    }

    /**
     * Demonstrates exception logging with stack traces
     */
    private static void demonstrateExceptionLogging() {
        logger.info("--- Demonstrating Exception Logging ---");
        System.out.println("⚠️ Logging exceptions with full stack traces:");

        try {
            // Simulate an exception for demonstration
            simulateException();
        } catch (Exception e) {
            // Log exception with full stack trace
            logger.error("Exception occurred during demonstration: {}", e.getMessage(), e);
            System.out.println("   ✓ Exception logged with stack trace");
            System.out.println("   ↳ Check log file for full stack trace details");
        }

        try {
            // Simulate another exception
            simulateNullPointerException();
        } catch (NullPointerException e) {
            // Different way to log exceptions
            logger.error("NullPointerException caught", e);
            System.out.println("   ✓ NullPointerException logged with stack trace");
        }
    }

    /**
     * Displays information about log file locations
     */
    private static void displayLogFileInformation() {
        logger.info("--- Log File Information ---");
        System.out.println("📁 Log files generated by Log4j 2:");
        System.out.println();
        System.out.println("   📄 logs/app.log");
        System.out.println("      ↳ Simple file appender - all logs in one file");
        System.out.println();
        System.out.println("   📄 logs/test-automation.log");
        System.out.println("      ↳ Rolling file appender - rotates at 10MB or daily");
        System.out.println("      ↳ Keeps up to 30 historical files");
        System.out.println();
        System.out.println("   💻 Console output");
        System.out.println("      ↳ You're seeing this right now!");
        System.out.println();
        System.out.println("🔧 Configuration file:");
        System.out.println("   src/main/resources/log4j2.xml");
        System.out.println();
        System.out.println("✨ Log4j 2 Benefits:");
        System.out.println("   ✓ Zero-code configuration via XML");
        System.out.println("   ✓ Multiple output destinations simultaneously");
        System.out.println("   ✓ Automatic log rotation (prevents huge files)");
        System.out.println("   ✓ Package-specific log levels");
        System.out.println("   ✓ Professional formatting and timestamps");
        System.out.println("   ✓ Industry-standard logging framework");
    }

    /**
     * Simulates an exception for demonstration
     */
    private static void simulateException() throws Exception {
        logger.debug("About to throw demonstration exception");
        throw new Exception("This is a demonstration exception to show logging");
    }

    /**
     * Simulates a NullPointerException for demonstration
     */
    private static void simulateNullPointerException() {
        logger.debug("About to throw NullPointerException");
        String nullString = null;
        nullString.length(); // This will throw NullPointerException
    }
}
