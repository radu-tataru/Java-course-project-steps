package com.example.app;

/**
 * Data class to hold link information including URL and expected title.
 * Implements Builder pattern for flexible object construction.
 */
public class LinkData {
    private final String name;
    private final String url;
    private final String expectedTitle;

    // Private constructor - only accessible via Builder
    private LinkData(Builder builder) {
        this.name = builder.name;
        this.url = builder.url;
        this.expectedTitle = builder.expectedTitle;
    }

    // Legacy constructors for backward compatibility
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

    /**
     * Builder class for constructing LinkData objects with a fluent API.
     * Implements the Builder design pattern for flexible and readable object creation.
     *
     * Example usage:
     * LinkData link = new LinkData.Builder()
     *                     .withName("GitHub")
     *                     .withUrl("https://github.com")
     *                     .withExpectedTitle("GitHub: Let's build from here")
     *                     .build();
     */
    public static class Builder {
        private String name;
        private String url;
        private String expectedTitle;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withExpectedTitle(String expectedTitle) {
            this.expectedTitle = expectedTitle;
            return this;
        }

        /**
         * Builds the LinkData object with validation.
         * @return A new LinkData instance
         * @throws IllegalArgumentException if required fields are missing
         */
        public LinkData build() {
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Name is required");
            }
            if (url == null || url.trim().isEmpty()) {
                throw new IllegalArgumentException("URL is required");
            }
            return new LinkData(this);
        }
    }

    /**
     * Static factory method to create a Builder instance.
     * @return A new Builder instance
     */
    public static Builder builder() {
        return new Builder();
    }
}