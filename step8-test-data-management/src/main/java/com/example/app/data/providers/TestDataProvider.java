package com.example.app.data.providers;

import com.example.app.data.models.WebsiteTestData;
import com.example.app.data.readers.JsonLinkDataReader;
import com.example.app.utils.Logger;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Central test data provider that aggregates data from multiple sources
 * Factory pattern implementation for data source management
 */
public class TestDataProvider {
    private static final String DATA_DIR = "java_project/data/";
    
    private final ExcelDataProvider excelProvider;
    private final JsonLinkDataReader jsonReader;
    
    public TestDataProvider() {
        this.excelProvider = new ExcelDataProvider();
        this.jsonReader = new JsonLinkDataReader();
    }
    
    /**
     * Gets website test data from Excel file
     * @return List of WebsiteTestData from Excel source
     */
    public List<WebsiteTestData> getWebsiteTestDataFromExcel() {
        String excelFile = DATA_DIR + "website-test-data.xlsx";
        Logger.getInstance().logDataProviderAction("TestDataProvider", "Loading data from Excel: " + excelFile);
        
        return excelProvider.readWebsiteTestData(excelFile);
    }
    
    /**
     * Gets website test data from JSON file (legacy support)
     * @return List of WebsiteTestData converted from JSON
     */
    public List<WebsiteTestData> getWebsiteTestDataFromJson() {
        String jsonFile = DATA_DIR + "links.json";
        Logger.getInstance().logDataProviderAction("TestDataProvider", "Loading data from JSON: " + jsonFile);
        
        try {
            return jsonReader.readLinkData(jsonFile)
                .stream()
                .map(linkData -> WebsiteTestData.builder()
                    .testName(linkData.getName() + "_JSON_Test")
                    .website(linkData.getName())
                    .websiteUrl(linkData.getUrl())
                    .expectedTitle(linkData.getExpectedTitle())
                    .environment("dev")
                    .priority("medium")
                    .build())
                .collect(Collectors.toList());
        } catch (Exception e) {
            Logger.getInstance().logDataProviderAction("TestDataProvider", "Error loading JSON data: " + e.getMessage());
            return List.of(); // Return empty list on error
        }
    }
    
    /**
     * Gets all website test data from all available sources
     * @return Combined list of WebsiteTestData from all sources
     */
    public List<WebsiteTestData> getAllWebsiteTestData() {
        Logger.getInstance().logDataProviderAction("TestDataProvider", "Loading data from all sources");
        
        List<WebsiteTestData> allData = getWebsiteTestDataFromExcel();
        List<WebsiteTestData> jsonData = getWebsiteTestDataFromJson();
        
        allData.addAll(jsonData);
        
        Logger.getInstance().logDataProviderAction("TestDataProvider", 
            "Loaded " + allData.size() + " total test cases from all sources");
        
        return allData;
    }
    
    /**
     * Gets test data filtered by environment
     * @param environment Environment to filter by (dev, staging, prod)
     * @return Filtered list of WebsiteTestData
     */
    public List<WebsiteTestData> getTestDataForEnvironment(String environment) {
        Logger.getInstance().logDataProviderAction("TestDataProvider", 
            "Loading data for environment: " + environment);
        
        return getAllWebsiteTestData()
            .stream()
            .filter(data -> environment.equals(data.getEnvironment()))
            .collect(Collectors.toList());
    }
    
    /**
     * Gets test data filtered by priority
     * @param priority Priority level (high, medium, low)
     * @return Filtered list of WebsiteTestData
     */
    public List<WebsiteTestData> getTestDataByPriority(String priority) {
        Logger.getInstance().logDataProviderAction("TestDataProvider", 
            "Loading data for priority: " + priority);
        
        return getAllWebsiteTestData()
            .stream()
            .filter(data -> priority.equals(data.getPriority()))
            .collect(Collectors.toList());
    }
    
    /**
     * Gets high-priority test data for smoke testing
     * @return List of high-priority WebsiteTestData
     */
    public List<WebsiteTestData> getSmokeTestData() {
        return getTestDataByPriority("high");
    }
    
    /**
     * Validates all data sources
     * @return true if all data sources are valid and accessible
     */
    public boolean validateDataSources() {
        Logger.getInstance().logDataProviderAction("TestDataProvider", "Validating all data sources");
        
        boolean excelValid = excelProvider.validateExcelStructure(DATA_DIR + "website-test-data.xlsx");
        boolean jsonValid = validateJsonStructure();
        
        boolean allValid = excelValid && jsonValid;
        
        Logger.getInstance().logDataProviderAction("TestDataProvider", 
            "Data source validation: " + (allValid ? "PASSED" : "FAILED"));
        
        return allValid;
    }
    
    /**
     * Validates JSON structure (basic validation)
     * @return true if JSON file is readable
     */
    private boolean validateJsonStructure() {
        try {
            String jsonFile = DATA_DIR + "links.json";
            List<?> data = jsonReader.readLinkData(jsonFile);
            return data != null && !data.isEmpty();
        } catch (Exception e) {
            Logger.getInstance().logDataProviderAction("TestDataProvider", 
                "JSON validation failed: " + e.getMessage());
            return false;
        }
    }
}