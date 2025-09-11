package com.qa.step3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes JSON files containing URL data.
 * 
 * This class extends the BaseFileProcessor abstract class,
 * demonstrating inheritance and polymorphism in OOP.
 */
public class JsonFileProcessor extends BaseFileProcessor {
    
    @Override
    public List<String> processFile(String filePath) throws IOException {
        List<String> urls = new ArrayList<>();
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray linksArray = jsonObject.getJSONArray("links");
            
            for (int i = 0; i < linksArray.length(); i++) {
                JSONObject linkObj = linksArray.getJSONObject(i);
                if (linkObj.has("url")) {
                    String url = linkObj.getString("url");
                    if (isValidUrl(url)) {
                        urls.add(url.trim());
                    }
                }
            }
        } catch (Exception e) {
            throw new IOException("Error parsing JSON file: " + e.getMessage(), e);
        }
        
        return urls;
    }
    
    @Override
    public String getDescription() {
        return "JSON file processor (*.json) - extracts URLs from links array";
    }
}