package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Login Page.
 * URL: https://www.saucedemo.com/
 */
public class LoginPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // URL
    private static final String LOGIN_URL = "https://www.saucedemo.com/";

    // Locators
    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By errorButton = By.cssSelector(".error-button");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ============= ACTIONS =============

    public void navigateToLoginPage() {
        logger.info("Navigating to SauceDemo login page");
        navigateTo(LOGIN_URL);
    }

    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameInput, username);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        type(passwordInput, password);
    }

    public ProductsPage clickLogin() {
        logger.info("Clicking login button");
        click(loginButton);
        return new ProductsPage(driver);
    }

    public ProductsPage login(String username, String password) {
        logger.info("Attempting login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        ProductsPage productsPage = clickLogin();
        logger.info("Login successful, navigated to products page");
        return productsPage;
    }

    // ============= VALIDATIONS =============

    public boolean isErrorMessageDisplayed() {
        boolean displayed = isDisplayed(errorMessage);
        logger.info("Error message displayed: {}", displayed);
        return displayed;
    }

    public String getErrorMessage() {
        String error = getText(errorMessage);
        logger.warn("Login error displayed: {}", error);
        return error;
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }
}
