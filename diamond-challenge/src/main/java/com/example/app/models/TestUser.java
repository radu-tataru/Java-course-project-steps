package com.example.app.models;

/**
 * Test user data model with Builder pattern for SauceDemo users.
 * Demonstrates Builder pattern for creating test data objects with optional fields.
 */
public class TestUser {
    private final String username;
    private final String password;
    private final String userType;
    private final boolean shouldBeLockedOut;

    private TestUser(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.userType = builder.userType;
        this.shouldBeLockedOut = builder.shouldBeLockedOut;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public boolean shouldBeLockedOut() {
        return shouldBeLockedOut;
    }

    @Override
    public String toString() {
        return String.format("TestUser{username='%s', type='%s', lockedOut=%s}",
                username, userType, shouldBeLockedOut);
    }

    /**
     * Builder class for constructing TestUser objects.
     */
    public static class Builder {
        private String username;
        private String password;
        private String userType = "standard";
        private boolean shouldBeLockedOut = false;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withUserType(String userType) {
            this.userType = userType;
            return this;
        }

        public Builder shouldBeLockedOut(boolean shouldBeLockedOut) {
            this.shouldBeLockedOut = shouldBeLockedOut;
            return this;
        }

        public TestUser build() {
            if (username == null || username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username is required");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password is required");
            }
            return new TestUser(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    // Predefined test users for convenience
    public static TestUser standardUser() {
        return builder()
                .withUsername("standard_user")
                .withPassword("secret_sauce")
                .withUserType("standard")
                .build();
    }

    public static TestUser lockedOutUser() {
        return builder()
                .withUsername("locked_out_user")
                .withPassword("secret_sauce")
                .withUserType("locked")
                .shouldBeLockedOut(true)
                .build();
    }

    public static TestUser problemUser() {
        return builder()
                .withUsername("problem_user")
                .withPassword("secret_sauce")
                .withUserType("problem")
                .build();
    }

    public static TestUser performanceUser() {
        return builder()
                .withUsername("performance_glitch_user")
                .withPassword("secret_sauce")
                .withUserType("performance")
                .build();
    }
}
