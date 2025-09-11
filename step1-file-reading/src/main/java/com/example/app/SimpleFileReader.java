package com.example.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class SimpleFileReader {

    private static final String DATA_DIR = "java_project/data/";

    public static void main(String[] args) {
        System.out.println("--- Reading from TXT file ---");
        readTxtFile(DATA_DIR + "links.txt");

        System.out.println("\n--- Reading from CSV file ---");
        readCsvFile(DATA_DIR + "links.csv");

        System.out.println("\n--- Reading from JSON file ---");
        readJsonFile(DATA_DIR + "links.json");
    }

    private static void readTxtFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("TXT Link: " + line);
            }
        } catch (IOException e) {
            System.err.println("Error reading TXT file: " + e.getMessage());
        }
    }

    private static void readCsvFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    System.out.println("CSV Link - Name: " + parts[0].trim() + ", URL: " + parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    private static void readJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                System.out.println("JSON Link - Name: " + obj.getString("name") + ", URL: " + obj.getString("url"));
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
        }
    }
}