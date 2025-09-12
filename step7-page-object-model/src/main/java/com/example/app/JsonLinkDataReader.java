package com.example.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced JSON reader that extracts both URLs and expected titles from JSON files
 */
public class JsonLinkDataReader {
    
    /**
     * Reads LinkData objects from a JSON file
     * @param filePath Path to the JSON file
     * @return List of LinkData objects with name, url, and expectedTitle
     * @throws IOException If file reading or JSON parsing fails
     */
    public List<LinkData> readLinkData(String filePath) throws IOException {
        List<LinkData> linkDataList = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                
                String name = obj.getString("name");
                String url = obj.getString("url");
                String expectedTitle = obj.optString("expectedTitle", null);
                
                linkDataList.add(new LinkData(name, url, expectedTitle));
            }
        } catch (org.json.JSONException e) {
            throw new IOException("Error parsing JSON file: " + e.getMessage(), e);
        }
        return linkDataList;
    }
}