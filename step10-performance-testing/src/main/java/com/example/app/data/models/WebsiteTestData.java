package com.example.app.data.models;

/**
 * Data model for website test scenarios
 * Supports builder pattern for flexible object creation
 */
public class WebsiteTestData {
    private final String testName;
    private final String website;
    private final String websiteUrl;
    private final String expectedTitle;
    private final String buttonText;
    private final String environment;
    private final String priority;
    
    private WebsiteTestData(Builder builder) {
        this.testName = builder.testName;
        this.website = builder.website;
        this.websiteUrl = builder.websiteUrl;
        this.expectedTitle = builder.expectedTitle;
        this.buttonText = builder.buttonText;
        this.environment = builder.environment;
        this.priority = builder.priority;
    }
    
    // Getters
    public String getTestName() { return testName; }
    public String getWebsite() { return website; }
    public String getWebsiteUrl() { return websiteUrl; }
    public String getExpectedTitle() { return expectedTitle; }
    public String getButtonText() { return buttonText; }
    public String getEnvironment() { return environment; }
    public String getPriority() { return priority; }
    
    /**
     * Builder pattern implementation for flexible object creation
     */
    public static class Builder {
        private String testName;
        private String website;
        private String websiteUrl;
        private String expectedTitle;
        private String buttonText;
        private String environment = "dev"; // Default environment
        private String priority = "medium";  // Default priority
        
        public Builder testName(String testName) {
            this.testName = testName;
            return this;
        }
        
        public Builder website(String website) {
            this.website = website;
            // Auto-generate URL based on website name
            this.websiteUrl = generateWebsiteUrl(website);
            return this;
        }
        
        public Builder websiteUrl(String websiteUrl) {
            this.websiteUrl = websiteUrl;
            return this;
        }
        
        public Builder expectedTitle(String expectedTitle) {
            this.expectedTitle = expectedTitle;
            return this;
        }
        
        public Builder buttonText(String buttonText) {
            this.buttonText = buttonText;
            return this;
        }
        
        public Builder environment(String environment) {
            this.environment = environment;
            return this;
        }
        
        public Builder priority(String priority) {
            this.priority = priority;
            return this;
        }
        
        /**
         * Auto-generates website URL based on website name
         */
        private String generateWebsiteUrl(String website) {
            switch (website.toLowerCase()) {
                case "github":
                    return "https://github.com";
                case "junit":
                    return "https://junit.org";
                case "maven":
                    return "https://maven.apache.org";
                case "selenium":
                    return "https://selenium.dev";
                default:
                    return "https://" + website.toLowerCase() + ".com";
            }
        }
        
        public WebsiteTestData build() {
            // Validation
            if (testName == null || testName.trim().isEmpty()) {
                throw new IllegalArgumentException("Test name is required");
            }
            if (website == null || website.trim().isEmpty()) {
                throw new IllegalArgumentException("Website is required");
            }
            
            return new WebsiteTestData(this);
        }
    }
    
    /**
     * Static factory method for builder
     */
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public String toString() {
        return String.format("WebsiteTestData{testName='%s', website='%s', url='%s', environment='%s', priority='%s'}", 
                           testName, website, websiteUrl, environment, priority);
    }
}