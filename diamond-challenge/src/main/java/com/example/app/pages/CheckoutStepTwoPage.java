package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Checkout Step Two (Order Overview).
 * URL: https://www.saucedemo.com/checkout-step-two.html
 */
public class CheckoutStepTwoPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CheckoutStepTwoPage.class);

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By subtotalLabel = By.cssSelector(".summary_subtotal_label");
    private final By taxLabel = By.cssSelector(".summary_tax_label");
    private final By totalLabel = By.cssSelector(".summary_total_label");
    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    // ============= ACTIONS =============

    public CheckoutCompletePage clickFinish() {
        logger.info("Clicking finish button to complete order");
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    public CartPage clickCancel() {
        logger.info("Cancelling checkout");
        click(cancelButton);
        return new CartPage(driver);
    }

    // ============= VALIDATIONS =============

    public boolean isCheckoutStepTwoDisplayed() {
        boolean displayed = isDisplayed(pageTitle);
        logger.debug("Checkout step two displayed: {}", displayed);
        return displayed;
    }

    public String getPageTitle() {
        String title = getText(pageTitle);
        logger.debug("Page title: {}", title);
        return title;
    }

    public int getCartItemCount() {
        int count = getElementCount(cartItems);
        logger.info("Order contains {} items", count);
        return count;
    }

    public String getSubtotal() {
        String subtotal = getText(subtotalLabel);
        logger.info("Order subtotal: {}", subtotal);
        return subtotal;
    }

    public String getTax() {
        String tax = getText(taxLabel);
        logger.info("Order tax: {}", tax);
        return tax;
    }

    public String getTotal() {
        String total = getText(totalLabel);
        logger.info("Order total: {}", total);
        return total;
    }

    public double getTotalAmount() {
        String total = getTotal();
        // Extract numeric value from "Total: $XX.XX"
        String numericValue = total.replaceAll("[^0-9.]", "");
        double amount = Double.parseDouble(numericValue);
        logger.info("Order total amount: ${}", amount);
        return amount;
    }
}
