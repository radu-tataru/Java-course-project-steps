package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LinkOpenerTest {

    private LinkOpener linkOpener;

    @BeforeEach
    void setUp() {
        linkOpener = new LinkOpener();
    }

    @Test
    @DisplayName("Validate correct URL")
    void testValidUrl() {
        assertTrue(linkOpener.isValidUrl("https://junit.org"), 
                   "Should validate correct HTTPS URL");
        assertTrue(linkOpener.isValidUrl("http://example.com"), 
                   "Should validate correct HTTP URL");
    }

    @Test
    @DisplayName("Reject invalid URL")
    void testInvalidUrl() {
        assertFalse(linkOpener.isValidUrl("not-a-url"), 
                    "Should reject invalid URL format");
        assertFalse(linkOpener.isValidUrl(""), 
                    "Should reject empty string");
        assertFalse(linkOpener.isValidUrl("://invalid"), 
                    "Should reject malformed URL");
    }

    @Test
    @DisplayName("Handle null URL")
    void testNullUrl() {
        assertFalse(linkOpener.isValidUrl(null), 
                    "Should handle null URL gracefully");
    }
}