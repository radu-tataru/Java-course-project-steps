package com.example.app;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;

public class SimpleFileReader {

    private static final String DATA_DIR = "java_project/data/";

    public static void main(String[] args) {
        System.out.println("--- Reading and Opening from TXT file ---");
        readAndOpenTxtFile(DATA_DIR + "links.txt");

        System.out.println("\n--- Reading and Opening from CSV file ---");
        readAndOpenCsvFile(DATA_DIR + "links.csv");

        System.out.println("\n--- Reading and Opening from JSON file ---");
        readAndOpenJsonFile(DATA_DIR + "links.json");
    }

    private static void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Opened: " + url);
            } else {
                System.out.println("Desktop browsing not supported. Could not open: " + url);
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error opening link " + url + ": " + e.getMessage());
        }
    }

    private static void readAndOpenTxtFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("TXT Link: " + line);
                openLink(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading TXT file: " + e.getMessage());
        }
    }

    private static void readAndOpenCsvFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String url = parts[1].trim();
                    System.out.println("CSV Link - Name: " + parts[0].trim() + ", URL: " + url);
                    openLink(url);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    private static void readAndOpenJsonFile(String filePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String url = obj.getString("url");
                System.out.println("JSON Link - Name: " + obj.getString("name") + ", URL: " + url);
                openLink(url);
            }
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("Error parsing JSON file: " + e.getMessage());
        }
    }
}