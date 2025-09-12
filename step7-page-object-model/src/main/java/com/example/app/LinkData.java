package com.example.app;

/**
 * Data class to hold link information including URL and expected title
 */
public class LinkData {
    private final String name;
    private final String url;
    private final String expectedTitle;
    
    public LinkData(String name, String url, String expectedTitle) {
        this.name = name;
        this.url = url;
        this.expectedTitle = expectedTitle;
    }
    
    public LinkData(String name, String url) {
        this(name, url, null);
    }
    
    public String getName() {
        return name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getExpectedTitle() {
        return expectedTitle;
    }
    
    @Override
    public String toString() {
        return String.format("LinkData{name='%s', url='%s', expectedTitle='%s'}", 
                           name, url, expectedTitle);
    }
}