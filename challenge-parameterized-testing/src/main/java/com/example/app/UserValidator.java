package com.example.app;

import java.util.regex.Pattern;

/**
 * User Validator for demonstrating advanced parameterized testing
 * Challenge: Advanced Level
 */
public class UserValidator {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 20;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        String trimmed = username.trim();
        if (trimmed.length() < MIN_USERNAME_LENGTH || trimmed.length() > MAX_USERNAME_LENGTH) {
            return false;
        }

        // Username must start with letter and contain only alphanumeric and underscore
        return trimmed.matches("^[a-zA-Z][a-zA-Z0-9_]*$");
    }

    public boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        // Password must have at least 3 out of 4 character types
        int typesCount = (hasUpper ? 1 : 0) + (hasLower ? 1 : 0) +
                        (hasDigit ? 1 : 0) + (hasSpecial ? 1 : 0);

        return typesCount >= 3;
    }

    public ValidationResult validateUser(String username, String email, String password) {
        boolean usernameValid = isValidUsername(username);
        boolean emailValid = isValidEmail(email);
        boolean passwordValid = isValidPassword(password);

        StringBuilder errors = new StringBuilder();
        if (!usernameValid) errors.append("Invalid username. ");
        if (!emailValid) errors.append("Invalid email. ");
        if (!passwordValid) errors.append("Invalid password. ");

        return new ValidationResult(
            usernameValid && emailValid && passwordValid,
            errors.toString().trim()
        );
    }

    public static class ValidationResult {
        private final boolean valid;
        private final String errors;

        public ValidationResult(boolean valid, String errors) {
            this.valid = valid;
            this.errors = errors;
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrors() {
            return errors;
        }
    }
}
