package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Checkout Complete Page.
 * URL: https://www.saucedemo.com/checkout-complete.html
 */
public class CheckoutCompletePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutCompletePage.class);

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By completeHeader = By.cssSelector(".complete-header");
    private final By completeText = By.cssSelector(".complete-text");
    private final By backHomeButton = By.id("back-to-products");
    private final By ponyExpressImage = By.cssSelector(".pony_express");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    // ============= ACTIONS =============

    public ProductsPage clickBackHome() {
        logger.info("Clicking back to products button");
        click(backHomeButton);
        return new ProductsPage(driver);
    }

    // ============= VALIDATIONS =============

    public boolean isCheckoutCompleteDisplayed() {
        boolean displayed = isDisplayed(pageTitle);
        logger.debug("Checkout complete page displayed: {}", displayed);
        return displayed;
    }

    public String getPageTitle() {
        String title = getText(pageTitle);
        logger.debug("Page title: {}", title);
        return title;
    }

    public String getCompleteHeader() {
        String header = getText(completeHeader);
        logger.info("Order complete header: {}", header);
        return header;
    }

    public String getCompleteText() {
        String text = getText(completeText);
        logger.info("Order complete text: {}", text);
        return text;
    }

    public boolean isPonyExpressImageDisplayed() {
        boolean displayed = isDisplayed(ponyExpressImage);
        logger.debug("Pony express image displayed: {}", displayed);
        return displayed;
    }
}
