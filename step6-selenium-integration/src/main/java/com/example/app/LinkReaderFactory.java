package com.example.app;

public class LinkReaderFactory {
    public static LinkReader getReader(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "txt":
                return new TxtLinkReader();
            case "csv":
                return new CsvLinkReader();
            case "json":
                return new JsonLinkReader();
            default:
                throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }
    }
}