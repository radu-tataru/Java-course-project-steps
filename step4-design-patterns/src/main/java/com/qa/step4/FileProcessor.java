package com.qa.step4;

import java.io.IOException;
import java.util.List;

/**
 * Interface for file processing operations
 * (Reused from Step 3 to maintain consistency)
 */
public interface FileProcessor {
    List<String> processFile(String filePath) throws IOException;
    String getDescription();
}