package com.example.app.data.providers;

import com.example.app.data.models.WebsiteTestData;
import com.example.app.utils.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel data provider using Apache POI
 * Reads test data from Excel files (.xlsx format)
 */
public class ExcelDataProvider {
    
    /**
     * Reads website test data from Excel file
     * @param filePath Path to the Excel file
     * @return List of WebsiteTestData objects
     */
    public List<WebsiteTestData> readWebsiteTestData(String filePath) {
        List<WebsiteTestData> testDataList = new ArrayList<>();
        
        Logger.getInstance().logDataProviderAction("Excel", "Starting to read data from: " + filePath);
        
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Logger.getInstance().logDataProviderAction("Excel", "Found sheet with " + sheet.getLastRowNum() + " rows");
            
            // Skip header row (row 0)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                try {
                    WebsiteTestData testData = WebsiteTestData.builder()
                        .testName(getCellValue(row.getCell(0)))
                        .website(getCellValue(row.getCell(1)))
                        .expectedTitle(getCellValue(row.getCell(2)))
                        .buttonText(getCellValue(row.getCell(3)))
                        .environment(getCellValue(row.getCell(4)))
                        .priority(getCellValue(row.getCell(5)))
                        .build();
                    
                    testDataList.add(testData);
                    Logger.getInstance().logDataProviderAction("Excel", "Loaded test data: " + testData.getTestName());
                    
                } catch (Exception e) {
                    Logger.getInstance().logDataProviderAction("Excel", "Error processing row " + i + ": " + e.getMessage());
                }
            }
            
        } catch (IOException e) {
            Logger.getInstance().logDataProviderAction("Excel", "Error reading Excel file: " + e.getMessage());
        }
        
        Logger.getInstance().logDataProviderAction("Excel", "Successfully loaded " + testDataList.size() + " test cases");
        return testDataList;
    }
    
    /**
     * Gets cell value as string, handling different cell types
     * @param cell Excel cell
     * @return String value of the cell
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
    
    /**
     * Validates Excel file structure
     * @param filePath Path to the Excel file
     * @return true if file structure is valid
     */
    public boolean validateExcelStructure(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            
            if (headerRow == null) {
                Logger.getInstance().logDataProviderAction("Excel", "Validation failed: No header row found");
                return false;
            }
            
            String[] expectedHeaders = {"TestName", "Website", "ExpectedTitle", "ButtonText", "Environment", "Priority"};
            
            for (int i = 0; i < expectedHeaders.length; i++) {
                Cell cell = headerRow.getCell(i);
                String actualHeader = getCellValue(cell);
                
                if (!expectedHeaders[i].equalsIgnoreCase(actualHeader)) {
                    Logger.getInstance().logDataProviderAction("Excel", 
                        "Validation failed: Expected header '" + expectedHeaders[i] + "' but found '" + actualHeader + "'");
                    return false;
                }
            }
            
            Logger.getInstance().logDataProviderAction("Excel", "File structure validation passed");
            return true;
            
        } catch (IOException e) {
            Logger.getInstance().logDataProviderAction("Excel", "Validation error: " + e.getMessage());
            return false;
        }
    }
}