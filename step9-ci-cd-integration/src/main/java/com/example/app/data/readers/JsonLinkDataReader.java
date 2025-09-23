package com.example.app.data.readers;

import com.example.app.data.models.LinkData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON reader for legacy LinkData format
 * Maintains backward compatibility while supporting new architecture
 */
public class JsonLinkDataReader {
    
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

/**
 * Legacy LinkData class for backward compatibility
 */
class LinkData {
    private final String name;
    private final String url;
    private final String expectedTitle;
    
    public LinkData(String name, String url, String expectedTitle) {
        this.name = name;
        this.url = url;
        this.expectedTitle = expectedTitle;
    }
    
    public String getName() { return name; }
    public String getUrl() { return url; }
    public String getExpectedTitle() { return expectedTitle; }
}