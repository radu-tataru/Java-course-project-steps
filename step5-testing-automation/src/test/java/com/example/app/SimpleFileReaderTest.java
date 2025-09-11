package com.example.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleFileReaderTest {

    private SimpleFileReader reader;

    @BeforeEach
    void setUp() {
        reader = new SimpleFileReader();
    }

    @Test
    @DisplayName("Read links from TXT file")
    void testReadValidTxtFile() throws IOException {
        String testFile = getClass().getClassLoader()
            .getResource("test-links.txt").getPath();
        List<String> links = reader.readLinks(testFile);
        
        assertNotNull(links, "Links list should not be null");
        assertEquals(3, links.size(), "Should read exactly 3 links");
        assertTrue(links.contains("https://junit.org"));
    }

    @Test
    @DisplayName("Read links from CSV file")
    void testReadValidCsvFile() throws IOException {
        String testFile = getClass().getClassLoader()
            .getResource("test-links.csv").getPath();
        List<String> links = reader.readLinks(testFile);
        
        assertNotNull(links, "Links list should not be null");
        assertEquals(3, links.size(), "Should read exactly 3 links");
        assertTrue(links.contains("https://maven.apache.org"));
    }

    @Test
    @DisplayName("Read links from JSON file")
    void testReadValidJsonFile() throws IOException {
        String testFile = getClass().getClassLoader()
            .getResource("test-links.json").getPath();
        List<String> links = reader.readLinks(testFile);
        
        assertNotNull(links, "Links list should not be null");
        assertEquals(3, links.size(), "Should read exactly 3 links");
        assertTrue(links.contains("https://github.com"));
    }

    @Test
    @DisplayName("Handle non-existent file")
    void testReadNonExistentFile() {
        assertThrows(IOException.class, () -> {
            reader.readLinks("non-existent-file.txt");
        }, "Should throw IOException for non-existent file");
    }
}