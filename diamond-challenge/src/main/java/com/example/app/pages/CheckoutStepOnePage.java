package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Checkout Step One (Customer Information).
 * URL: https://www.saucedemo.com/checkout-step-one.html
 */
public class CheckoutStepOnePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutStepOnePage.class);

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    // ============= ACTIONS =============

    public void enterFirstName(String firstName) {
        logger.info("Entering first name: {}", firstName);
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        logger.info("Entering last name: {}", lastName);
        type(lastNameInput, lastName);
    }

    public void enterPostalCode(String postalCode) {
        logger.info("Entering postal code: {}", postalCode);
        type(postalCodeInput, postalCode);
    }

    public CheckoutStepTwoPage clickContinue() {
        logger.info("Clicking continue button");
        click(continueButton);
        return new CheckoutStepTwoPage(driver);
    }

    public CheckoutStepTwoPage fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        logger.info("Filling checkout information for: {} {}", firstName, lastName);
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return clickContinue();
    }

    public CartPage clickCancel() {
        logger.info("Cancelling checkout");
        click(cancelButton);
        return new CartPage(driver);
    }

    // ============= VALIDATIONS =============

    public boolean isCheckoutStepOneDisplayed() {
        boolean displayed = isDisplayed(pageTitle);
        logger.debug("Checkout step one displayed: {}", displayed);
        return displayed;
    }

    public String getPageTitle() {
        String title = getText(pageTitle);
        logger.debug("Page title: {}", title);
        return title;
    }

    public boolean isErrorMessageDisplayed() {
        boolean displayed = isDisplayed(errorMessage);
        logger.info("Error message displayed: {}", displayed);
        return displayed;
    }

    public String getErrorMessage() {
        String error = getText(errorMessage);
        logger.warn("Checkout error: {}", error);
        return error;
    }
}
