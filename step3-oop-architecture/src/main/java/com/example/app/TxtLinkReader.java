package com.example.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtLinkReader implements LinkReader {
    @Override
    public List<String> readLinks(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                links.add(line);
            }
        }
        return links;
    }
}