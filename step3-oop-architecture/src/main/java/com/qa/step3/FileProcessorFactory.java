package com.qa.step3;

/**
 * Factory class for creating appropriate file processors.
 * 
 * This class demonstrates the Factory Design Pattern,
 * encapsulating the logic for creating different types of processors
 * based on file extensions.
 */
public class FileProcessorFactory {
    
    /**
     * Create a file processor based on the file extension
     * 
     * @param filePath the path to the file
     * @return appropriate FileProcessor for the file type
     * @throws IllegalArgumentException if file type is not supported
     */
    public static FileProcessor createProcessor(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        
        String lowerPath = filePath.toLowerCase();
        
        if (lowerPath.endsWith(".txt")) {
            return new TxtFileProcessor();
        } else if (lowerPath.endsWith(".csv")) {
            return new CsvFileProcessor();
        } else if (lowerPath.endsWith(".json")) {
            return new JsonFileProcessor();
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + getFileExtension(filePath));
        }
    }
    
    /**
     * Get list of supported file extensions
     * 
     * @return array of supported extensions
     */
    public static String[] getSupportedExtensions() {
        return new String[]{".txt", ".csv", ".json"};
    }
    
    /**
     * Check if a file type is supported
     * 
     * @param filePath the file path to check
     * @return true if the file type is supported
     */
    public static boolean isSupported(String filePath) {
        try {
            createProcessor(filePath);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Extract file extension from path
     */
    private static String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
            return filePath.substring(lastDotIndex);
        }
        return "";
    }
}