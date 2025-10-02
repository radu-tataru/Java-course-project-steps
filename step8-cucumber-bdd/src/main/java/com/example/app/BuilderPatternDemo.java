package com.example.app;

/**
 * Demonstration of the Builder Pattern for creating test data objects.
 *
 * The Builder pattern provides a fluent API for constructing complex objects
 * step-by-step. This is especially useful for test data creation where:
 * - Objects have many optional parameters
 * - Readability of test data setup is important
 * - Validation logic can be centralized in the build() method
 */
public class BuilderPatternDemo {

    public static void main(String[] args) {
        Logger.getInstance().log("=== Builder Pattern Demonstration ===\n");

        // Example 1: Creating LinkData with all fields using Builder
        Logger.getInstance().log("1. Creating LinkData with all fields:");
        LinkData githubLink = LinkData.builder()
                .withName("GitHub")
                .withUrl("https://github.com")
                .withExpectedTitle("GitHub: Let's build from here")
                .build();
        Logger.getInstance().log("   " + githubLink);
        Logger.getInstance().log("");

        // Example 2: Creating LinkData with only required fields
        Logger.getInstance().log("2. Creating LinkData with only required fields:");
        LinkData mavenLink = LinkData.builder()
                .withName("Maven")
                .withUrl("https://maven.apache.org")
                .build();
        Logger.getInstance().log("   " + mavenLink);
        Logger.getInstance().log("");

        // Example 3: Flexible field ordering - any order is valid!
        Logger.getInstance().log("3. Flexible field ordering:");
        LinkData seleniumLink = LinkData.builder()
                .withExpectedTitle("Selenium")  // Title first
                .withName("Selenium")           // Then name
                .withUrl("https://www.selenium.dev")  // Finally URL
                .build();
        Logger.getInstance().log("   " + seleniumLink);
        Logger.getInstance().log("");

        // Example 4: Backward compatibility - old constructors still work
        Logger.getInstance().log("4. Backward compatibility with legacy constructors:");
        LinkData junitLink = new LinkData("JUnit", "https://junit.org", "JUnit 5");
        Logger.getInstance().log("   " + junitLink);
        Logger.getInstance().log("");

        // Example 5: Builder validation - demonstrates error handling
        Logger.getInstance().log("5. Builder validation (will throw exception):");
        try {
            LinkData invalidLink = LinkData.builder()
                    .withName("")  // Empty name - invalid!
                    .withUrl("https://example.com")
                    .build();
        } catch (IllegalArgumentException e) {
            Logger.getInstance().log("   ✅ Caught expected exception: " + e.getMessage());
        }
        Logger.getInstance().log("");

        // Example 6: Creating multiple test data objects efficiently
        Logger.getInstance().log("6. Creating multiple test data objects for testing:");
        LinkData[] testLinks = {
            LinkData.builder()
                .withName("Test1")
                .withUrl("https://test1.com")
                .withExpectedTitle("Test 1 Title")
                .build(),
            LinkData.builder()
                .withName("Test2")
                .withUrl("https://test2.com")
                .build(),
            LinkData.builder()
                .withName("Test3")
                .withUrl("https://test3.com")
                .withExpectedTitle("Test 3 Title")
                .build()
        };

        for (LinkData link : testLinks) {
            Logger.getInstance().log("   " + link);
        }
        Logger.getInstance().log("");

        // Summary of Builder Pattern Benefits
        Logger.getInstance().log("=== Builder Pattern Benefits ===");
        Logger.getInstance().log("✅ Readable: Method names clearly indicate what's being set");
        Logger.getInstance().log("✅ Flexible: Fields can be set in any order");
        Logger.getInstance().log("✅ Immutable: Once built, objects cannot be changed");
        Logger.getInstance().log("✅ Validated: Build() method can enforce business rules");
        Logger.getInstance().log("✅ Optional Fields: Easy to handle objects with many optional parameters");
        Logger.getInstance().log("✅ Test-Friendly: Perfect for creating test data with varying configurations");
    }
}
