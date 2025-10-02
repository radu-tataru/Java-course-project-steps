package com.example.app.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object for SauceDemo Shopping Cart Page.
 * URL: https://www.saucedemo.com/cart.html
 */
public class CartPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(CartPage.class);

    // Locators
    private final By pageTitle = By.cssSelector(".title");
    private final By cartItems = By.cssSelector(".cart_item");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // ============= DYNAMIC LOCATORS =============

    private By removeButton(String productName) {
        return By.xpath(String.format("//div[@class='cart_item'][.//div[text()='%s']]//button[contains(@id, 'remove')]", productName));
    }

    private By cartItemName(String productName) {
        return By.xpath(String.format("//div[@class='inventory_item_name' and text()='%s']", productName));
    }

    private By cartItemPrice(String productName) {
        return By.xpath(String.format("//div[@class='cart_item'][.//div[text()='%s']]//div[@class='inventory_item_price']", productName));
    }

    // ============= ACTIONS =============

    public void removeProductFromCart(String productName) {
        logger.info("Removing product from cart: {}", productName);
        click(removeButton(productName));
    }

    public CheckoutStepOnePage proceedToCheckout() {
        logger.info("Proceeding to checkout");
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }

    public ProductsPage continueShopping() {
        logger.info("Continuing shopping");
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }

    // ============= VALIDATIONS =============

    public boolean isCartPageDisplayed() {
        boolean displayed = isDisplayed(pageTitle);
        logger.debug("Cart page displayed: {}", displayed);
        return displayed;
    }

    public String getPageTitle() {
        String title = getText(pageTitle);
        logger.debug("Page title: {}", title);
        return title;
    }

    public int getCartItemCount() {
        int count = getElementCount(cartItems);
        logger.info("Cart contains {} items", count);
        return count;
    }

    public boolean isProductInCart(String productName) {
        boolean inCart = isDisplayed(cartItemName(productName));
        logger.info("Product '{}' in cart: {}", productName, inCart);
        return inCart;
    }

    public String getProductPrice(String productName) {
        String price = getText(cartItemPrice(productName));
        logger.info("Price of '{}' in cart: {}", productName, price);
        return price;
    }
}
