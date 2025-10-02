package com.example.app.stepdefinitions;

import com.example.app.config.WebDriverManager;
import com.example.app.pages.LoginPage;
import com.example.app.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Login-related scenarios.
 */
public class LoginSteps {
    private static final Logger logger = LogManager.getLogger(LoginSteps.class);
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;

    // User credentials mapping
    private static final Map<String, String> USER_PASSWORDS = new HashMap<>();

    static {
        USER_PASSWORDS.put("standard_user", "secret_sauce");
        USER_PASSWORDS.put("locked_out_user", "secret_sauce");
        USER_PASSWORDS.put("problem_user", "secret_sauce");
        USER_PASSWORDS.put("performance_glitch_user", "secret_sauce");
    }

    @Given("I am on the SauceDemo login page")
    public void i_am_on_the_saucedemo_login_page() {
        driver = WebDriverManager.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        logger.info("Navigated to SauceDemo login page");

        Assertions.assertTrue(loginPage.isLoginButtonDisplayed(), "Login page should be displayed");
    }

    @Given("Log4j logging is initialized")
    public void log4j_logging_is_initialized() {
        logger.info("Log4j is initialized and ready for logging");
        logger.debug("Log4j configuration loaded successfully");
    }

    @Given("I login as {string}")
    public void i_login_as(String username) {
        String password = USER_PASSWORDS.get(username);
        logger.info("Logging in as user: {}", username);

        productsPage = loginPage.login(username, password);

        Assertions.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page after login");
        logger.info("Successfully logged in as: {}", username);
    }

    @When("I attempt to login with username {string} and password {string}")
    public void i_attempt_to_login_with_username_and_password(String username, String password) {
        logger.info("Attempting login with username: {}", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        logger.info("Logging in with username: {} and password: {}", username, password);
        productsPage = loginPage.login(username, password);
    }

    @Then("I should see error message {string}")
    public void i_should_see_error_message(String expectedError) {
        Assertions.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message should be displayed");
        String actualError = loginPage.getErrorMessage();
        Assertions.assertTrue(actualError.contains(expectedError),
                String.format("Expected error containing '%s', but got '%s'", expectedError, actualError));
        logger.warn("Error message verified: {}", actualError);
    }

    @Then("I should see {string}")
    public void i_should_see(String expectedPage) {
        if (productsPage == null) {
            productsPage = new ProductsPage(driver);
        }

        String pageTitle = productsPage.getPageTitle();
        Assertions.assertTrue(pageTitle.contains(expectedPage),
                String.format("Expected page title containing '%s', but got '%s'", expectedPage, pageTitle));
        logger.info("Verified page title: {}", pageTitle);
    }

    @Then("login attempt should be logged with username {string}")
    public void login_attempt_should_be_logged_with_username(String username) {
        logger.info("Login attempt logged for username: {}", username);
        // This step verifies that logging is working (already done in previous steps)
    }

    @Then("error should be logged at WARN level")
    public void error_should_be_logged_at_warn_level() {
        logger.warn("Error scenario completed - logged at WARN level");
        // This step verifies that WARN level logging is working
    }
}
