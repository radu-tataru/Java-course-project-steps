package com.qa.step3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes CSV files containing URL data.
 * 
 * This class extends the BaseFileProcessor abstract class,
 * demonstrating inheritance in OOP.
 */
public class CsvFileProcessor extends BaseFileProcessor {
    
    @Override
    public List<String> processFile(String filePath) throws IOException {
        List<String> urls = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header row
                    continue;
                }
                
                String[] columns = line.split(",");
                if (columns.length >= 2) {
                    String url = columns[1].trim();
                    if (isValidUrl(url)) {
                        urls.add(url);
                    }
                }
            }
        }
        
        return urls;
    }
    
    @Override
    public String getDescription() {
        return "CSV file processor (*.csv) - extracts URLs from second column";
    }
}