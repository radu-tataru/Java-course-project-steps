package com.example.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonLinkReader implements LinkReader {
    @Override
    public List<String> readLinks(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                links.add(obj.getString("url"));
            }
        } catch (org.json.JSONException e) {
            throw new IOException("Error parsing JSON file: " + e.getMessage(), e);
        }
        return links;
    }
}