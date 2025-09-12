package com.example.app;

import java.io.IOException;
import java.util.List;

public interface LinkReader {
    List<String> readLinks(String filePath) throws IOException;
}