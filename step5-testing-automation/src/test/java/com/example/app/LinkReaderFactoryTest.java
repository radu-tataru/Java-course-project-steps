package com.example.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class LinkReaderFactoryTest {

    @Test
    @DisplayName("Create TXT reader")
    void testCreateTxtReader() {
        LinkReader reader = LinkReaderFactory.getReader("txt");
        assertNotNull(reader, "Should create a reader for TXT files");
        assertTrue(reader instanceof TxtLinkReader, "Should create TxtLinkReader instance");
    }

    @Test
    @DisplayName("Create CSV reader")
    void testCreateCsvReader() {
        LinkReader reader = LinkReaderFactory.getReader("csv");
        assertNotNull(reader, "Should create a reader for CSV files");
        assertTrue(reader instanceof CsvLinkReader, "Should create CsvLinkReader instance");
    }

    @Test
    @DisplayName("Create JSON reader")
    void testCreateJsonReader() {
        LinkReader reader = LinkReaderFactory.getReader("json");
        assertNotNull(reader, "Should create a reader for JSON files");
        assertTrue(reader instanceof JsonLinkReader, "Should create JsonLinkReader instance");
    }

    @Test
    @DisplayName("Handle case insensitive extensions")
    void testCaseInsensitiveExtensions() {
        assertNotNull(LinkReaderFactory.getReader("TXT"), "Should handle uppercase extension");
        assertNotNull(LinkReaderFactory.getReader("Csv"), "Should handle mixed case extension");
        assertNotNull(LinkReaderFactory.getReader("JSON"), "Should handle uppercase JSON");
    }

    @Test
    @DisplayName("Reject unsupported file types")
    void testUnsupportedFileType() {
        assertThrows(IllegalArgumentException.class, () -> {
            LinkReaderFactory.getReader("xml");
        }, "Should throw exception for unsupported file type");
        
        assertThrows(IllegalArgumentException.class, () -> {
            LinkReaderFactory.getReader("pdf");
        }, "Should throw exception for PDF files");
    }
}