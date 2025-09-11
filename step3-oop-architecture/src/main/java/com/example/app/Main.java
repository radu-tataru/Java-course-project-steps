package com.example.app;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Main {

    private static final String DATA_DIR = "java_project/data/";

    public static void main(String[] args) {
        processFile("links.txt", "txt");
        processFile("links.csv", "csv");
        processFile("links.json", "json");
    }

    private static void processFile(String fileName, String fileExtension) {
        System.out.println("\n--- Processing " + fileName + " ---");
        try {
            LinkReader reader = LinkReaderFactory.getReader(fileExtension);
            List<String> links = reader.readLinks(DATA_DIR + fileName);

            for (String link : links) {
                System.out.println("Read Link: " + link);
                openLink(link);
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error processing " + fileName + ": " + e.getMessage());
        }
    }

    private static void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
                System.out.println("Opened: " + url);
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error opening link " + url + ": " + e.getMessage());
        }
    }
}