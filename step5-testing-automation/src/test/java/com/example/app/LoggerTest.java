package com.example.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    @Test
    @DisplayName("Logger should be a singleton")
    void testSingletonPattern() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        
        assertNotNull(logger1, "First logger instance should not be null");
        assertNotNull(logger2, "Second logger instance should not be null");
        assertSame(logger1, logger2, "Both instances should be the same object");
    }

    @Test
    @DisplayName("Logger should handle null input safely")
    void testLoggerWithNullInput() {
        Logger logger = Logger.getInstance();
        
        // These should not throw exceptions
        assertDoesNotThrow(() -> {
            logger.logLinkOpened(null);
        }, "Should handle null link in logLinkOpened");
        
        assertDoesNotThrow(() -> {
            logger.logLinkFailed(null);
        }, "Should handle null link in logLinkFailed");
    }

    @Test
    @DisplayName("Logger methods should execute without errors")
    void testLoggerMethods() {
        Logger logger = Logger.getInstance();
        
        assertDoesNotThrow(() -> {
            logger.logLinkOpened("https://junit.org");
            logger.logLinkFailed("https://broken-link.com");
            logger.showLogLocation();
        }, "All logger methods should execute without throwing exceptions");
    }
}